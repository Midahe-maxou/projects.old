<?php
session_start();

//Base de donnée de mysql
$bdd = new PDO("mysql:host=localhost;dbname=nsi;charset=utf8", 'root', '', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));

?>


<!DOCTYPE html>
<html>
<head>
	<title>Nos recettes</title>
	<meta charset="utf-8" />
	<!--CSS-->
	<link rel="stylesheet" type="text/css" href="css/font.css" />
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<!--JS-->
	<script type="text/javascript" src="../js/script.js"></script>
</head>
<body>
	<!--header-->
	<?php include("inclusions/header.php"); ?>

	<!--contenu de la page-->
	<section id="contenu">
		<h2 style="padding: 20px 0;text-align: center;">Nos recettes</h2>
		<div id="recettes">
			<article class="recette">
				<img src="../images/poulet.png" />
				<p class="description">Poulet rôti et ses pommes de terre</p>
			</article>
		</div>
	</section>

	<?php include("inclusions/copyright.php");?>
</body>
</html>