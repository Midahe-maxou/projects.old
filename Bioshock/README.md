<h1 align="center">Trainer Bioshock</h1>

### Trainer externe du jeu Bioshock 1, version non remasterisée.

Première expérience en rétro-ingénérie. Pour ce projet, je n'ai utilisé que Cheat Engine, un logiciel d'analyse dynamique.<br>
Le code du trainer externe est en C++.

#### Fonctionnalités

- Munitions / argent infinis
- Godmod
- Dégats infinis

Les dernières modifications sont les tests d'un gestionnaire de mémoire, car allouer 4096 octets pour en utiliser 10 me faisait mal.<br>
NB: Je n'avais pas le concept d'arena; le code est assez affreux (j'ai tout recommencer de 0 deux fois car c'était plus simple que de reprendre là où je m'étais arrêter).

J'ai arrêter de travailler sur ce projet pour apprendre plus en profondeur la rétro-ingénierie avec les tutos de GuidedHacking.

- Dernières modification: 05/11/23
- État actuel: Toutes les fonctionnalités marchent, mais le main fait des tests du gestionnaire de mémoire.