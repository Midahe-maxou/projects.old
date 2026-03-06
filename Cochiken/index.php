<?php
session_start();

//Base de donnée de mysql
$bdd = new PDO("mysql:host=localhost;dbname=nsi;charset=utf8", 'root', '', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));


if(isset($_SESSION['id'])){
    $id = $_SESSION['id'];
    $pseudo = $_SESSION['pseudo'];
}

?>

<!DOCTYPE html>
<html>
<head>
    <title>Page d'accueil</title>
    <meta charset="utf-8" />
    <!--CSS-->
    <link rel="stylesheet" type="text/css" href="css/font.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <!--JS-->
    <script type="text/javascript" src="js/script.js"></script>
</head>
<body>
    <!--header-->
    <?php include("inclusions/header.php");?>

    <div id="contenu">
    <?php 
    if(isset($_POST['note'], $_POST['avis']) && is_numeric($_POST['note']) && $_POST['note']<5){
        if(!isset($_SESSION['id'])) echo "<p class='erreur'>Vous devez être connecté pour pouvoir poster un commentaire<br><a href='connexion.php'>Connectez-vous</a></p>";
        else{
            $avis = $_POST['avis'];
            $note = (int)$_POST['note'];
            $stmt = $bdd->prepare("INSERT INTO avis(pseudo, note, avis) VALUES(:pseudo, :note, :avis)");
            //$sth->bindValue('avis', $avis);
            $stmt->execute(array(':pseudo' => $pseudo, ':note' => $note, ':avis' => $avis));
            echo "<p class='success'>Votre commentaire a bien été enregistré</p>";
        }
    }
    ?>
        <form action="#" method="post" class="clients">
            <h2 style="margin: 0" class="titres gauche souligne">Écrivez votre propre commentaire :</h2>
            Note:
            <div class="note">
                <img src="/images/etoilepleine.png" alt="*" onmouseenter="note_entre(0)" onmouseleave="note_sort()" onclick="select_note(0)">
                <img src="/images/etoilevide.png" alt="*" onmouseenter="note_entre(1)" onmouseleave="note_sort()" onclick="select_note(1)">
                <img src="/images/etoilevide.png" alt="*" onmouseenter="note_entre(2)" onmouseleave="note_sort()" onclick="select_note(2)">
                <img src="/images/etoilevide.png" alt="*" onmouseenter="note_entre(3)" onmouseleave="note_sort()" onclick="select_note(3)">
                <img src="/images/etoilevide.png" alt="*" onmouseenter="note_entre(4)" onmouseleave="note_sort()" onclick="select_note(4)">
            </div>
            
            <input type="hidden" name="note" id="note" value="0">

            <script>
                var note_selectionnee = 0;
                const note = document.getElementsByClassName('note')[0];

                function select_note(nb) {
                    note_selectionnee = nb;
                    document.getElementById('note').value = nb;
                }

                function note_entre(nb){
                    let i = 0;
                    while(i<5){
                        if(i<=nb){
                            note.children[i].src = "/images/etoilepleine.png";
                        }else{
                            note.children[i].src = "/images/etoilevide.png";
                        }
                        i++;
                    }
                }

                function note_sort(){
                    let i = 0;
                    while(i<5){
                        if(i<=note_selectionnee){
                            note.children[i].src = "/images/etoilepleine.png";
                        }else{
                            note.children[i].src = "/images/etoilevide.png";
                        }
                        i++;
                    }
                }
            </script>

            <textarea name="avis" id="avis" class="commentaire" cols="100" rows="10" placeholder="Votre commentaire"></textarea><br>
            <input type="submit" class="bouton changer-bouton" value="Envoyer !">
        </form>

        <h2 class="titres">Livre d'or de nos clients satisfaits</h2>

        <?php
        $reponse = $bdd->query("SELECT * FROM avis");
        if($un_avis = $reponse->fetch()){
            do{
        ?>
            <div class="clients">
                <p class="ligne souligne"><?php echo $un_avis['pseudo']?>:
                <?php
                $nb = $un_avis['note'];
                $i = 0;

                while($i < 5){
                    if($i <= $nb){
                        echo "<img src='images/etoilepleine.png'/>";
                    }else{
                        echo "<img src='images/etoilevide.png'/>";
                    }
                    $i++;
                }
                ?>
                </p>
                <p class="ligne"><?php echo $un_avis['avis']?></p>
            </div>
        <?php
            }while($un_avis = $reponse->fetch());
        }else{
            echo "<p class='attention'>Aucun commentaire pour l'instant<br>Soyez le premier à donner votre avis</p>";
        }
        ?>
    </div>
    <!--copyright-->
    <?php include("inclusions/copyright.php");?>
</body>
</html>
