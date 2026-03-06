<?php
session_start();

//Base de donnée de mysql
$bdd = new PDO("mysql:host=localhost;dbname=nsi;charset=utf8", 'root', '', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));

if(!isset($_SESSION['id'])){
    header("Location: connexion.php");
    exit();
}

if(!isset($_GET['article'])){
    header("Location: boutique.php");
    exit();
}

$article = $_GET['article'];

?>

<!DOCTYPE html>
<html>
<head>
    <title>Acheter | <?php echo $article?></title>
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
    $image = grande_image_article($article);
    $nom = nom_article($article);
    $prix = prix_article($article);

    $erreur = array();
    $achat_reussi = false;
    if(isset($_POST['nb']) && is_numeric($_POST['nb'])){ //vérifier si le nombre d'article est un nombre
        $nb = $_POST['nb'];
        if($nb < 0){
            $erreur[] = "Le nombre d'article ne peut pas être négatif";
        }elseif($nb > 3){
            $erreur[] = "Il n'y a plus assez d'article";
        }else{
            $achat_reussi = true;
            $i=0;
            while($i<$nb){
                $_SESSION['panier'][] = $article;
                $i++;
            }
        }
    }
    ?>
        <div class="achat">
            <div class="produit">
                <img src="<?php echo $image?>" alt="<?php echo  $article?>" />
            </div>

            <div class="acheter">
                <?php
                if($achat_reussi){
                    ?>
                    <p class="success">Votre achat: <?php echo $article." x".$nb?> a bien été enregistré<br />
                    <a href="boutique.php">Revenir à la boutique</a></p>
                    <?php
                }else{
                ?>
                    <form class="formulaire" action="#" method="post">
                        <h2 class="titres souligne"><?php echo $nom?></h2>
                        <div class="">
                        <p class="quantite">Quantité:</p>
                        <input type="number" name="nb" class="saisie" placeholder="Quantité"/>
                        <input type="submit" value="Acheter" class="bouton">
                        <p class="ligne">
                            Prix: <?php echo $prix?>$<br />
                            Prix de vos commandes pour l'instant:
                        <?php
                            $prix_total = 0;
                            foreach($_SESSION['panier'] as $a){
                                $prix_total = $prix_total + prix_article($a);
                            }
                            echo $prix_total;
                        ?>
                        $
                        </p>
                        <p class="attention">Attention: plus que 3 articles restants !<br/>Commandez vite !</p>
                        <?php
                            foreach ($erreur as $e) {
                                echo "<p class='erreur'>$e</p>";
                            }
                        ?>
                        </div>
                        <input type="hidden" name="article" value="<?php echo $article?>">
                </form>
                    <?php 
                }
                    ?>
            </div>
        </div>
    </div>
    <?php include("inclusions/copyright.php");?>
</body>
</html>