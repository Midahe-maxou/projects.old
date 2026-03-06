<?php
session_start();

//Base de donnée de mysql
$bdd = new PDO("mysql:host=localhost;dbname=nsi;charset=utf8", 'root', '', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));

if(!isset($_POST['email'])){ //si le mail n'est pas spécifié
    header("Location: index.php");
    exit();
}

$email = $_POST['email'];
//#^(([a-z0-9-_]+\.?)*[a-z0-9-_]+)@(([a-z0-9-_]+\.?)*[a-z0-9-_]+)\.[a-z]{2,4}$#i
?>


<!DOCTYPE html>
<html>
<head>
    <title>Inscription</title>
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
        <?php
        if(strpos($email, " ") || !preg_match("%^(([a-z0-9-_]+\.?)*[a-z0-9-_]+)@(([a-z0-9-_]+\.?)*[a-z0-9-_]+)\.[a-z]{2,4}$%i", $email)){ //vérifier si l'email contient un espace ou s'il n'a pas un pattern d'email?>
            <p class='erreur centre'>Votre Email n'est pas conforme</p>
            <div class="formulaire">
            <p class="titres">Entrer de nouveau votre email</p>
                <form action="abonnement.php" method="post">
                    <input type="email" name="email" id="email" placeholder="Votre@email.com"><br />
                    <input type="submit" value="Je m'abonne !">
                </form>
            </div>
        <?php
        }else {
            if(isset($_SESSION['id'])){
                $id = $_SESSION['id'];
                $ancien_email = $bdd->query("SELECT email FROM logins WHERE id='$id'")->fetch();

                if($ancien_email['email'] != $email){
                    $bdd->query("UPDATE logins SET email='$email' WHERE id='$id'");
                    echo "<p class='success centre'>Votre Email à bien été modifié<br /><a href='index.php'>Retourner à l'acceuil</a></p>";
                }else{
                    echo "<p class='erreur centre'>Votre Email est déjà $email<br /><a href='index.php'>Retourner à l'acceuil</a></p>";
                }
            }else{
                echo "<p class='success centre'>Votre Email à bien été enregistré<br /><a href='index.php'>Retourner à l'acceuil</a></p>";
            }
            /*
            *    //envoie d'un mail
            *    $to      = 'jeux.fr@sfr.fr';
            *    $subject = 'Un sujet random';
            *    $message = 'Bonjour ! Comment ça va ?';
            *    $headers = 'From: Kochiken.off@gmail.com' . "\r\n" .
            *    'Reply-To: webmaster@example.com' . "\r\n" .
            *    'X-Mailer: PHP/' . phpversion();
            *
            *    //mail($to, $subject, $message, $headers);
            *    require_once "../sendmail/mail.class.php";
            *    $mail=new SendMail("kochiken.off@gmail.com", "test", "message de test");
            *    $mail->addEventListener(new MailLog("Log.txt")); 
            *    $mail->SendMail("destinataire@domaine.com");
            */
        }
        ?>
    </div>

    <?php include("inclusions/copyright.php");?>
</body>
</html>