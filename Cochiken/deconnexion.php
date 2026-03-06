<?php session_start();?>

<!DOCTYPE html>
<html>
<head>
    <title>Déconnexion</title>
    <meta charset="UTF-8">
</head>
<body>
    <?php
    if(isset($_COOKIE['pseudo'])) setcookie('pseudo', ""); //supprimer le cookie du pseudo s'il existe
    if(isset($_COOKIE['mdp'])) setcookie('mdp', ""); //idem pour le mdp
    session_destroy(); //destruction de la session (aka une déconnexion)
    header("Location: index.php"); //redirection vers la page d'acceuil
    exit();
    ?>
</body>
</html>