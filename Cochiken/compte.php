<?php
session_start();

//Base de donnée de mysql
$bdd = new PDO("mysql:host=localhost;dbname=nsi;charset=utf8", 'root', '', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));

if(isset($_SESSION['id'])){
    $id = $_SESSION['id'];
    $pseudo = $_SESSION['pseudo'];
}else{
    header("Location: index.php");
    exit();
}
?>
<!DOCTYPE html>
<html>
<head>
    <title>Mon profile | <?php echo $pseudo?></title>
    <meta charset="UTF-8">
    <!--CSS-->
    <link rel="stylesheet" type="text/css" href="css/font.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <!--JS-->
    <script type="text/javascript" src="js/script.js"></script>
</head>
<body>
    <?php include("inclusions/header.php");?>

    <div id="contenu">
        <div class="mon-compte">
        <?php
            $changement = "";
            $valeur = "";
            $erreur = array();
            $mdp_incorrect = false;

            if(isset($_POST['change_pseudo'])) { //si l'utilisateur change son pseudo
                $changement = "pseudo";
                $valeur = $_POST['change_pseudo'];
                if($valeur == "") $erreur[] = "Veuillez entrer un pseudo"; //pseudo nul
                if(strpos($valeur, " ")) $erreur[] = "Votre pseudo contient un espace";
                if(strlen($valeur) > 18) $erreur[] = "Votre pseudo fait plus de 18 caractères";
                if($bdd->query("SELECT pseudo FROM logins WHERE pseudo='$valeur'")->fetch()) $erreur[] = "Ce pseudo existe déjà"; //vérification si le pseudo existe
            }
            if(isset($_POST["change_mdp"])) { //si l'utilisateur change son mdp
                $changement = "mdp";
                $valeur = $_POST['change_mdp'];
                if($valeur == "") $erreur[] = "Veuillez entrer un mot de passe"; //mdp nul
                if(strpos($valeur, " ")) $erreur[] = "Votre mot de passe contien un espace";
            }
            if(isset($_POST["change_email"])) { //si l'utilisateur change son email
                $changement = "email";
                $valeur = $_POST['change_email'];
                if($valeur == "") $erreur[] = "Veuillez entrer un email"; //email nul
                if(strpos($valeur, " ")) $erreur[] = "Votre email contient un espace";
            }

            if(isset($_POST['mdp'])){
                $reponse = $bdd->query("SELECT mdp FROM logins WHERE pseudo='$pseudo'")->fetch(); //prend dans la bbd le mdp
                if($reponse['mdp'] != $_POST['mdp']){ //vérifie si le mdp est incorrect
                    $mdp_incorrect = true; //le mdp est incorrect
                }
            }

            if(isset($_POST['mdp']) && !$mdp_incorrect && $erreur == array()){
                                
                switch($changement){
                    case "pseudo": //changement de pseudo
                        $bdd->query("UPDATE logins SET pseudo='$valeur'");
                        $_SESSION['pseudo'] = $valeur;
                    break;

                    case "mdp": //changement de mdp
                        $bdd->query("UPDATE logins SET mdp='$valeur'");
                    break;

                    case "email"://changement d'email
                        if($erreur == array()) $bdd->query("UPDATE logins SET email='$valeur'");
                    break;
                }

                if($changement == "mdp") $changement = "mot de passe"; //pour un affichage correct de la ligne en dessous
                echo "<p class='success'>Vous avez bien modifié votre $changement<br /><a href='index.php'>Retourner à l'acceuil</a></p>";

            }elseif($changement != "" && $erreur == array()){ //l'utilisateur doit entrer son mdp
                ?>
                <div class="formulaire">
                    <p>Pour changer votre <?php echo $changement;?>,<br /> veuillez entrer votre mot de passe actuel:</p>
                    <form action="#" method="post">
                        <input type="text" name="mdp" placeholder="Mot de passe" />
                        <input type="submit" class="changer-bouton" value="Valider" />
                        <?php if($mdp_incorrect) echo "<p class='erreur'>Votre mot de passe est incorrect</p>"?>
                        <input type="hidden" name="change_<?php echo $changement?>" value="<?php echo $valeur?>" />
                    </form>
                </div>
                <?php
            }else{
                ?>
                <p><em class="souligne">Pseudo:</em> <?php echo $pseudo;?></p>
                <p><em class="souligne">Email:</em> <?php
                $reponse = $bdd->query("SELECT email FROM logins WHERE pseudo='$pseudo'")->fetch();
                if($reponse['email'] == "rien"){
                    echo "Vous n'avez pas indiqué d'email";
                }else{
                    echo $reponse['email'];
                }
                ?></p>
                    <?php
                    foreach ($erreur as $e) {
                        echo "<p class='erreur'>$e</p>";
                    }
                    ?>
                <div class="formulaire">
                    <form action="#" method="post">
                        <p>Changer de pseudo ? (18 caractères max !)</p>
                        <input type="text" name="change_pseudo" class="saisie" placeholder="Votre pseudo"><br />
                        <input type="submit" value="Changer de pseudo" class="changer-bouton">
                    </form>

                    <form action="#" method="post" class="nouvelle-section">
                        <p>Changer de mot de passe ?</p>
                        <input type="text" name="change_mdp" class="saisie" placeholder="Votre mot de passe"><br />
                        <input type="submit" value="Changer de mot de passe" class="changer-bouton">
                    </form>

                    <form action="#" method="post" class="nouvelle-section">
                        <p>Changer d'email ?</p>
                        <input type="email" name="change_email" class="saisie" placeholder="Votre email"><br />
                        <input type="submit" value="Changer d'email" class="changer-bouton">
                    </form>
                </div>
                <?php
            }
                ?>
        </div>
    </div>

    <?php include("inclusions/copyright.php");?>
</body>
</html>