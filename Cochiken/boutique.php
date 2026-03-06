<?php
session_start();

//Base de donnée de mysql
$bdd = new PDO("mysql:host=localhost;dbname=nsi;charset=utf8", 'root', '', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));

?>
<!DOCTYPE html>
<html>
<head>
    <title>Document</title>
    <meta charset="utf-8" />
    <!--CSS-->
    <link rel="stylesheet" type="text/css" href="css/font.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <!--JS-->
    <script type="text/javascript" src="js/script.js"></script>

</head>
<body>
    <?php include("inclusions/header.php");?>
	
    <section id="contenu">
        <h1 class="titres">Nos produits exclusifs et qualitatifs</h1>

            <div class="bloc1">
                <form class="article" action="achat.php" method="get">
                    <img src="images/casque.png"/>
                    <p>Casque de survie pour poule de compagnie<br/>
                    Prix: 15$
                    </p>
                    <input type="hidden" name="article" value="casque">
                    <input type="submit" value="Acheter">
                </form>

                <form class="article" action="achat.php" method="get">
                    <img src="images/costume.png"/>
                    <p>Costume de poulet tout en 1<br/>
                    Prix: 30$
                    </p>
                    <input type="hidden" name="article" value="costume">
                    <input type="submit" value="Acheter">
                </form>
                        
                <form class="article" action="achat.php" method="get">
                    <img src="images/bonnet.png"/>
                    <p>Bonnet adulte poulet<br/>
                    Prix: 15$
                    </p>
                    <input type="hidden" name="article" value="bonnet">
                    <input type="submit" value="Acheter">
                </form>

                <form class="article" action="achat.php" method="get">
                    <img src="images/lego.png"/>
                    <p>Figurine poulet lego<br/>
                    Prix: 30$
                    </p>
                    <input type="hidden" name="article" value="lego">
                    <input type="submit" value="Acheter">
                </form>
            </div>


            <div class="bloc2">
                <form class="article" action="achat.php" method="get">
                    <img src="images/crocs.png"/>
                    <p>Crocs x FKC<br/>
                    Prix: 100$
                    </p>
                    <input type="hidden" name="article" value="crocs">
                    <input type="submit" value="Acheter">
                </form>

                <form class="article" action="achat.php" method="get">
                    <img src="images/minecraft.png"/>
                    <p>Lego minecraft poulailler<br/>
                    Prix: 35$
                    </p>
                    <input type="hidden" name="article" value="minecraft">
                    <input type="submit" value="Acheter">
                </form>

                <form class="article" action="achat.php" method="get">
                    <img src="images/nugget2.png"/>
                    <p>Coussin nugget<br/>
                    Prix: 165$
                    </p>
                    <input type="hidden" name="article" value="nugget">
                    <input type="submit" value="Acheter">
                </form>

                <form class="article" action="achat.php" method="get">
                    <img src="images/peluche.png"/>
                    <p>Peluche poule<br/>
                    Prix: 25$
                    </p>
                    <input type="hidden" name="article" value="peluche">
                    <input type="submit" value="Acheter">
                </form>
            </div>
    </section>

    <?php include("inclusions/copyright.php");?>
</body>
</html>