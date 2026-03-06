<?php
session_start();

//Base de donnée de mysql
$bdd = new PDO("mysql:host=localhost;dbname=nsi;charset=utf8", 'root', '', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));


if(!isset($_SESSION['id'])){
    header("Location: index.php");
    exit();
}

$une_commande_a_ete_annulee = false;

if(isset($_POST['annuler']) && is_numeric($_POST['annuler'])){ //annulation d'une commande
    $num_commande = $_POST['annuler']; //n° de commande à annuler
    if(isset($_SESSION['panier'][$num_commande])){
        unset($_SESSION['panier'][$num_commande]); //annuler la commande
        sort($_SESSION['panier']); //réorganiser le panier
        $une_commande_a_ete_annulee = true;
    }
}

?>

<!DOCTYPE html>
<html>
<head>
    <title>Mon panier</title>
    <meta charset="UTF-8" />
    <!--CSS-->
    <link rel="stylesheet" type="text/css" href="css/font.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <!--JS-->
    <script type="text/javascript" src="js/script.js"></script>
</head>
<body>
    <?php include("inclusions/header.php");?>
    <div id="contenu">
        <h1 class="titres">Mon panier</h1>
        <div class="commandes">
            <?php
            if($une_commande_a_ete_annulee) echo "<p class='success centre'>Votre commande à bien été annulée</p>";

            if($panier != array()){
                $index = 0;
                $panier = $_SESSION['panier'];
                foreach($panier as $article){
                    ?>
                    <div class="commandes nouvelle-section">
                        <h2 class="titres">Article N°<?php echo $index+1?></h2>
                        <div class="conteneur-horizontal">
                            <img src="<?php echo petite_image_article($article)?>" alt="<?php echo $article?>">
                            <div class="descriptif">
                                <p class="droite"><?php echo nom_article($article);?></p>
                                <p class="droite">Prix: <?php echo prix_article($article);?>$</p>
                                <form action="#" method="post">
                                    <input type="hidden" name="annuler" value="<?php echo $index?>">
                                    <input type="submit" value="Annuler cette commande" class="bouton">
                                </form>
                            </div>
                        </div>
                    </div>
                    <?php
                    $index = $index + 1;
                }
            }else{
                ?>

                <p class="attention centre">Votre panier est vide<br />
                <a href="boutique.php">Allez acheter quelques chose :)</a>
                </p>
                
                <?php
            }
            ?>
        </div>
    </div>    
    <?php include("inclusions/copyright.php");?>
</body>
</html>