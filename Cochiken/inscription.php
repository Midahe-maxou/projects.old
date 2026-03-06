<?php
session_start();
//Base de donnée de mysql
$bdd = new PDO("mysql:host=localhost;dbname=nsi;charset=utf8", 'root', '', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));

$tentative_inscription = false;

if(isset($_POST['pseudo'], $_POST['mdp'], $_POST['mdp_confirm'])){ //vérifier si les 3 champs existent
    $pseudo = $_POST['pseudo'];
    $mdp = $_POST['mdp'];
    $tentative_inscription = true;
    $erreur = array();

    if($bdd->query("SELECT * FROM logins WHERE pseudo='$pseudo'")->fetch()) $erreur[] = "Ce pseudo existe déjà"; //vérification que le pseudo n'existe pas
    if($pseudo == "") $erreur[] = "Veuillez renseigner un pseudo";
    if($mdp == "") $erreur[] = "Veuillez renseigner un mot de passe";
    if(strpos($pseudo, " ") || strpos($mdp, " ")) $erreur[] = "Le pseudo/mot de passe contient un espace";
    if($mdp != $_POST['mdp_confirm']) $erreur[] = "La confirmation ne correspond pas au mot de passe";
    if(strlen($pseudo) > 18) $erreur[] = "Votre pseudo doit faire au maximum 18 caractères";

    if($erreur == array())//vérifier si il n'y a aucune erreur
        $bdd->exec("INSERT INTO logins(pseudo, mdp, email) VALUES('$pseudo', '$mdp', 'rien')");
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Inscription</title>
    <meta charset="utf-8" />
    <!--CSS-->
    <link rel="stylesheet" type="text/css" href="css/font.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <!--JS-->
    <script type="text/javascript" src="js/script.js"></script>

</head>
<body>

    <!--header-->
    <?php include("inclusions/header.php"); ?>

    <!--contenu de la page-->
    <div id="contenu">
        <?php
        if($tentative_inscription && $erreur == array()){
        ?>
            <p class="success">Création du compte réussie !<br /><a href="connexion.php">Connectez-vous</a></p>

        <?php
        }else{
        ?>
            <h1 class="titres">Création d'un compte</h1>

            <form action="#" method="post" class="formulaire">
                <p class="ligne">Nom d'utilisateur: (18 caractères max !)<br /><input class="saisie" type="username" name="pseudo" /></p>
                <p class="ligne">Mot de passe: <br /><input class="saisie" type="password" name="mdp" /> </p>
                <p class="ligne">Confirmation du mot de passe: <br /><input class="saisie" type="password" name="mdp_confirm" /></p>

                <input type="submit" value="S'inscrire" class="bouton">
                <?php
                if($tentative_inscription){
                    foreach ($erreur as $e) {                  
                    echo("<p class='erreur'>$e</p>");
                    }
                }
                ?>

                <p class="connection">déjà inscrit ? <br><br> <button type="button" class="bouton" onclick="window.location.href='connexion.php'">Se connecter</button></p>
            </form>
        <?php
        }
        ?>
    </div>

    <?php include("inclusions/copyright.php");?>
</body>
</html>