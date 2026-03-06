<?php
if(!isset($_SESSION['panier'])){
    $_SESSION['panier'] = array();
}

$panier = $_SESSION['panier'];
$nb_articles = count($panier);

function article($article){
    switch($article){
        case "casque":
            $petite_image = "/images/casque.png";
            $grande_image = "/images/casque-grand.png";
            $nom = "Casque de survie pour poule de compagnie";
            $prix = 15;
        break;
    
        case "costume":
            $petite_image = "/images/costume.png";
            $grande_image = "/images/costume-grand.png";
            $nom = "Costume de poulet tout en 1";
            $prix = 30;
        break;
    
        case "bonnet":
            $petite_image = "/images/bonnet.png";
            $grande_image = "/images/bonnet-grand.png";
            $nom = "Bonnet adulte poulet";
            $prix = 15;
        break;
    
        case "lego":
            $petite_image = "/images/lego.png";
            $grande_image = "/images/lego-grand.png";
            $nom = "Figurine poulet lego";
            $prix = 30;
        break;
    
        case "crocs":
            $petite_image = "/images/crocs.png";
            $grande_image = "/images/crocs-grand.png";
            $nom = "Crocs x FKC";
            $prix = 100;
        break;
    
        case "minecraft":
            $petite_image = "/images/minecraft.png";
            $grande_image = "/images/minecraft-grand.png";
            $nom = "Lego minecraft poulailler";
            $prix = 35;
        break;
    
        case "nugget":
            $petite_image = "/images/nugget2.png";
            $grande_image = "/images/nugget2-grand.png";
            $nom = "Coussin nugget";
            $prix = 165;
        break;
    
        case "peluche":
            $petite_image = "/images/peluche.png";
            $grande_image = "/images/peluche-grand.png";
            $nom = "Peluche poule";
            $prix = 25;
        break;
    
        default:
            $petite_image = "";
            $grande_image = "";
            $nom = "";
            $prix = 0;
        break;
    }
    return array("petite image" => $petite_image, "grande image"=>$grande_image, "nom"=>$nom, "prix"=>$prix);
}
function grande_image_article($article){
    $grande_image = article($article)['grande image'];
    return $grande_image;
}

function petite_image_article($article){
    $petite_image = article($article)['petite image'];
    return $petite_image;
}

function nom_article($article){
    $nom = article($article)['nom'];
    return $nom;
}

function prix_article($article){
    $prix = article($article)['prix'];
    return $prix;
}
?>


<header id="header">
    <div class="header-contenu">
        
        <div class="header-accueil">
            <h1>Kochicken</h1>
        </div>
        
        <div class="header-menu">
            <nav class="header-liens">
                <a href="index.php" class="header_menu-button">Accueil</a>
                <a href="boutique.php" class="header_menu-button">Boutique</a>
            </nav>
            
            <!--<input class="recherche" type="text" name="r" value="Rechercher" onfocus="if(this.value=='Rechercher') this.value=''" oblur="if(this.value=='') this.value='Rechercher'">-->
            <?php
            if(!isset($_SESSION['id']) && isset($_COOKIE['pseudo'], $_COOKIE['mdp'])){ //vérifier si les cookies pseudo-mdp existent
                $pseudo = $_COOKIE['pseudo'];
                $mdp = $_COOKIE['mdp'];
                $reponse = $bdd->query("SELECT id FROM logins WHERE pseudo='$pseudo' AND mdp='$mdp'"); //récupérer la requête avec le pseudo-mdp

                if($donnees = $reponse->fetch()){ //la combinaison pseudo-mdp existe
                    $_SESSION['id'] = $donnees['id']; //variable superglobale qui stocke l'id du compte connecté
                    $_SESSION['pseudo'] = $pseudo; //idem pour le pseudo
                }
            }

            if(isset($_SESSION['id'])){ //vérifier si l'utilisateur est connecté
            ?>
            <div class="header-connecte">
                <div class="menu-deroulant" onmouseenter="this.children[1].style.transform = 'scaleY(1)';" onmouseleave="this.children[1].style.transform = 'scaleY(0)';">
                    <p style="margin: 0px;">Mon compte<img src="/images/fleche.png"/></p>
                    <div class="liste-deroulante">
                        <nav class="pseudo"><?php echo $_SESSION['pseudo']?></nav>
                        <nav class="ligne-liste"><a class="lien-liste" href="compte.php">mon profile</a></nav>
                        <nav class="ligne-liste"><a class="lien-liste" href="panier.php">mon panier: <?php echo($nb_articles.(($nb_articles == 0 || $nb_articles == 1) ? " article": " articles"));?></a></nav>
                    </div>
                </div>
                <a class="header_connection-button" href="deconnexion.php">Se déconnecter</a>
            </div>
            <?php
            }else{
            ?>
            <nav class="header-connection">
                <a href="connexion.php" class="header_connection-button">Se connecter</a>
                <a href="inscription.php" class="header_connection-button">S'inscrire</a>
            </nav>
            <?php
            }
            ?>
        </div>
    </div>
</header>