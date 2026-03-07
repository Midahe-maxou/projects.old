<h1 align="center" id="header">Recherche</h1>

## Algorithme de recherche type Regex

J'ai fait ce programme en une soirée, car j'étais bloqué chez mon père sans internet avec loRdi du lycée, et je m'ennuyais.<br>

Le programme est composé de deux fonctions :

- recherche(pattern, rep, depth, inverse, verbose)
> Permet de rechercher récursivement un fichier à partir d'un répertoire dont le nom correspond au paterne.

- _match(name, pattern) -> bool
> Permet de savoir le nom du fichier correspond
> C'est un algorithme qui recherche de façon récursive un paterne dans une chaîne de caractère.
> les paternes prennent en compte :
> - \* <=> tout caractère, autant de fois que possible
> - ? <=> tout caractère, une fois
> - ! <=> tout caractère, zéro ou une fois
> - caractère\<nb> <=> répétition entre 0 et nb du caractère
> - caractère\<min:max> <=> répétition entre min et max du caractère

Le code est très compacte, et le noms des variables et des fonctions n'est pas clair.

- Dernière modification: 12/08/21
- État actuel: Terminé