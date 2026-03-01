<h1 align="center">Bioshock Bis</h1>

## Trainer interne du jeu Bioshock 1, version non remasterisée.

Après avoir suivi une partie des tutos de GuidedHacking, J'ai repris la rétro-ingénierie de Bioshock, mais en combinant analyse dynamique avec Cheat Engine et analyse statique avec Ghidra.<br>
Le but était surtout de porter ce que j'avais précedemment fait en interne.

L'injecteur utilisé est celui mis à disposition par GuidedHacking.

### Techiques utilisés
- Appels de fonctions.
- Utilisation de hook pour récupérer le flux d'exécution d'un thread en particulier, pour éviter les data races.
- Gestionnaire de mémoire fonctionnel.

### Fonctionnalités (en plus)
- Création d'items


- Dernières modification: 13/09/2025
- État actuel: Fonctionnel