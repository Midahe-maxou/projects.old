<h1 align="center">Trainer Bioshock</h1>

### Trainer externe du jeu Bioshock 1, version non remasterisée.

Première expérience en rétro-ingénérie. Pour ce projet, je n'ai utilisé que Cheat Engine, un logiciel d'analyse dynamique.<br>
Bioshock n'était pas un excellent choix pour ce projet : étant basé sur Unreal Engine 2.5, le code était très dense, et beaucoup de temps a été passé à lire l'assembleur d'Unreal Engine.<br>
Le code du trainer externe est en C++.

#### Fonctionnalités

- Munitions / argent infinis
- Godmod
- Dégats infinis

Les dernières modifications sont les tests d'un gestionnaire de mémoire, car allouer 4096 octets pour en utiliser 10 faisait mal à mon petit coeur.<br>
NB: Le code est très long, peu lisible et la logique utilisée, bien que fonctionnelle, n'est pas optimale.

J'ai arrêter de travailler sur ce projet pour apprendre plus en profondeur la rétro-ingénierie avec les tutos de GuidedHacking.

- Dernières modification: 05/11/23
- État actuel: Toutes les fonctionnalités marchent, mais le main fait des tests du gestionnaire de mémoire.