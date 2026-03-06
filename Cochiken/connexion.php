<?php
session_start();

//Base de donnée de mysql
$bdd = new PDO("mysql:host=localhost;dbname=nsi;charset=utf8", 'root', '', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));

$tentative_de_connection = false;

if(isset($_POST['pseudo'], $_POST['mdp'])) {
    $pseudo = $_POST['pseudo'];
    $mdp = $_POST['mdp'];
    $tentative_de_connection = true;
    $reponse = $bdd->query("SELECT * FROM logins WHERE pseudo='$pseudo' AND mdp='$mdp'"); //récupérer la requête avec le pseudo-mdp

    if($donnees = $reponse->fetch()){ //la combinaison pseudo-mdp existe
        $_SESSION['id'] = $donnees['id']; //variable superglobale qui stocke l'id du compte connecté
        $_SESSION['pseudo'] = $donnees['pseudo']; //idem pour le pseudo
        if(isset($_POST['souvenir']) && $_POST['souvenir'] == "on"){
            setcookie("pseudo", $pseudo, time()+3600); //créer un cookie pour le pseudo qui expire dans 3600secondes
            setcookie("mdp", $mdp, time()+3600); //idem pour le mdp
        }
        header('Location: index.php'); //redirection vers le menu
        exit();
    }
}
?>


<!DOCTYPE html>
<html>
<head>
    <title>Connection</title>
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
    <h1 class="titres">Identifiez-vous</h1>

        <form action="#" method="post" class="formulaire">
            <p class="ligne">Nom d'utilisateur: <input type="username" name="pseudo" class="saisie"/></p>
            <p class="ligne"> Mot de passe: <input type="password" name="mdp" class="saisie"/> </p>

            <p class="souvenir"><input type="checkbox" name="souvenir"/>Se souvenir de moi</p>
            
            <input type="submit" value="Se connecter" class="bouton" />

            <?php 
            if($tentative_de_connection){ //mauvais pseudo-mdp
                echo('<p class="erreur">Pseudo ou mot de passe incorrect');
            }
            ?>

            <p class="inscription">Pas encore de compte ? <br><br> <button type="button" class="bouton" onclick="window.location.href='inscription.php'">S'inscrire</button></p>
        </form>
    </div>

    <?php include("inclusions/copyright.php");?>
</body>
</html>