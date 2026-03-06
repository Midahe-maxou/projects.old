<?php
session_start();

//Base de donnée de mysql
$bdd = new PDO("mysql:host=localhost;dbname=nsi;charset=utf8", 'root', '', array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));

$dossier = (isset($_GET['dir'])) ? $_GET['dir'] : "" ;
$d = dir("D:/WampServer/www".$dossier);
$chemin = $d->path.'/';

?>

<!DOCTYPE html>
<html>
<head>
    <title>éditeur de code | <?= (isset($_SESSION['pseudo'])) ? $_SESSION['pseudo'] : "Visiteur" ?></title>
    <meta charset="UTF-8">

    <style>
    
    .dossier{
        color: #2D0;
        text-decoration: none;
    }
    
    .fichier{
        color: #24F;
        text-decoration: none;
    }

    .erreur{
        color: red;
    }

    .code{
        color: #FFF;
        background-color: #000;
        width: 90%;
        height: 600px;
        margin: auto;
        margin-top: 20px;
        padding: 5px;
        border: 5px double #777;
        overflow-x: scroll;
    }

    str{
        color: #8BE;
    }

    str str{
        color: #58C;
    }

    .function{

    }
    
    </style>

</head>
<body>

<?php
echo $chemin.(isset($_GET['file']) ? $_GET['file']: "");
if(isset($_GET['file'])){//lecture d'un fichier
    $fichier = $_GET['file'];
    if(($texte=@file($chemin.$fichier)) != false){ //check si le fichier existe
        echo("<div class='code' contenteditable='true' oninput='input(event)'>");
        foreach($texte as $t)echo htmlspecialchars($t)."<br>";
        echo("</div>");
    }else{
        echo("<p class='erreur'>Le fichier $dossier/$fichier est introuvable</p>");
    }
}else{
    while($file=$d->read()){
        if($file == "." || $file == "..") continue;

        if(($extension = strrpos($file, ".")) != null){ //c'est un fichier
            echo("<p><a href='?dir=$dossier&file=$file' class='fichier'>$file</a><p/>");
        }else{ //c'est un dossier
            echo("<p><a href='?dir=$dossier/$file' class='dossier'>$file</a><p/>");
        }
    }
}
$d->close();
?>

<script>
    function input(e){
        if(event.inputType == "insertText" && (event.data == '"' || event.data == "'")){
            //event.target.innerHTML = event.target.innerHTML + "<string> loool </string>";
            console.log(event.timeStamp);
        }
    }


    function colorText(){
        const code = document.getElementsByClassName("code")[0].innerHTML.split("<br>");
        let doublequotestr = false;
        let simplequotestr = false;
        let comment = false;

        for(let line in code){
            if(isNaN(line)) continue;
            let phrase = code[line];
            let passedletter = 0;

            let simplequotestr = false;
            let doublequotestr = false;
            console.log(findAllIndex(phrase, "'") + " | " + phrase);

            for(let index of findAllIndex(phrase, "'")){
                if(simplequotestr && phrase.substr(index-5, 5) != "<str>"){
                    phrase = phrase.substr(0, index) + "<str>" + phrase.substr(index);
                    simplequotestr = !simplequotestr;
                }
            }

            for(let index of findAllIndex(phrase, '"')){
                if(doublequotestr && phrase.substr(index-5, 5) != "<str>"){
                    phrase = phrase.substr(0, index) + "<str>" + phrase.substr(index);
                    doublequotestr = !doublequotestr;
                }
            }

            /*
            for(let letter of code[line].innerHTML){
                passedletter++;
                if(letter == "'"){
                    if(simplequotestr && )
                    //phrase += (simplequotestr) ? "</string>'" : "<string>'";
                    simplequotestr = !simplequotestr;
                }else if(letter == '"'){
                    //phrase += (doublequotestr) ? "</string>\"" : "<string>\"";
                    doublequotestr = !doublequotestr;
                }else{
                    phrase += letter;
                }
            }*/
            //console.log(phrase);
            code[line].innerHTML = phrase;
        }
    }

    function findAllIndex(string, char){
        let indexes = [];
        let index = string.indexOf(char);
		let lastIndex = 0;
        while(index != -1){
            indexes.push(index + lastIndex);
			lastIndex = index+1;
            string = string.substr(index+1);
			index = string.indexOf(char);
        }
        return indexes;
    }
</script>
    
</body>
</html>