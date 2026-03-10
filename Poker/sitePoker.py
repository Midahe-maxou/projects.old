from flask import Flask, render_template, url_for, request, redirect
import os

app = Flask(__name__)
dossier = "./jeu/" #chemin relatif vers le dossier

if not os.path.exists(dossier):
    os.makedirs(dossier)
if not os.path.exists(dossier + "/joueurs"):
    os.makedirs(dossier + "/joueurs")
    
##Gestion des fichiers pour lier le site avec le jeu

def nouveau_joueur(joueur, mdp, argent):

    """
    la fonction nouveau_joueur initialise la création d'un nouveau joueur en créant un dossier de statistique avec son pseudo, son mdp, son argent...; retourne True si la création s'est bien effectuée, retourne False si le joueur existait déjà.
    préconditions: une chaîne de caractères non nulle sans espaces.
    postcondition: un booléen.
    """

    if(type(joueur) != str): raise TypeError("Le nom du joueur doit être une chaîne de caractères") #lève une erreur (le gros truc rouge moche) pour dire que le nom doit être une chaîne de caractères
    if(joueur.find(" ") != -1): raise ValueError("Le nom du joueur ne peut contenir d'espaces")
    if(type(argent) != float and type(argent) != int): raise TypeError("L'argent doit être un nombre")
    if(joueur == "" or joueur == None): raise ValueError("Le nom du joueur ne peut être vide") #le nom ne peut être vide (None = vide)
    if(argent < 0): raise ValueError("Le montant d'argent ne peut être négatif") #on ne commence pas par être déficitaire (mais pas pour longtemps ...)

    if os.path.exists(dossier + "/joueurs/" + joueur): #on vérifie que le joueur n'existe déjà pas
        return False

    os.makedirs(dossier + "/joueurs/" + joueur) #on créé le dossier du joueur
    
    #on créé son mdp dans un fichier txt
    with open(dossier + '/joueurs/' + joueur + "/mdp.txt", 'w') as fichier: #créer un nouveau fichier (x) à l'emplacement dossier+'joueurs'+nom (+ '.txt' qui est l'extension). stocke le flux du fichier dans la variable 'fichier'
        fichier.write(mdp) #écrit le mdp dans le fichier
        fichier.close() #ferme le flux du fichier
    #on fait pareil avec l'argent
    with open(dossier + '/joueurs/' + joueur + "/argent.txt", 'w') as fichier:
        fichier.write(str(argent))
        fichier.close()
    return True

def recup_argent_joueur(joueur, mdp):
    """
    La fonction renvoie le montant d'argent du joueur précisé en paramètre, s'il n'existe pas ou que le mdp n'est pas bon, renvoie False.
    précondition: une chaîne de caractères sans espace, une chaîne de caractère.
    postcondition: un nombre flottant.
    """
    infos = recup_joueur(joueur, mdp) #récup les infos du joueur (nom, argent et date d'incription)
    if infos == False: return False #mdp pas bon/joueur n'existe pas

    return infos[1] #on renvoie que l'argent

def recup_joueur(joueur, mdp):
    """
    La fonction renvoie 2 valeurs par rapport au joueur précisé en parametre: nom du joueur, l'argent.
    précondition: chaîne de caractères sans espace, une chaîne de caractère sans espace.
    postcondition: une liste avec une chaine de caractères et un entier.
    """
    if(type(joueur) != str): raise TypeError("Le nom du joueur doit être une chaîne de caractères")
    if(joueur.find(" ") != -1): raise ValueError("Le nom du joueur ne peut contenir d'espaces")
    if(joueur == "" or joueur == None): raise ValueError("Le nom du joueur ne peut être vide")

    if not os.path.exists(dossier + '/joueurs/' + joueur): return False #le joueur n'existe pas

    with open(dossier + '/joueurs/' + joueur + "/mdp.txt", 'r') as fichier: #ouvre le fichier du mdp du joueur en mode lecture (r = read), si le joueur n'existe pas, lève une FileNotFoundError
        motdepasse = fichier.readline() #prend la 1ere ligne (yen a qu'une)
        if motdepasse != mdp: #le mdp n'est pas bon
            return False

        argent = 0
    with open(dossier + '/joueurs/' + joueur + "/argent.txt") as fichier:
        argent = float(fichier.readline())
        fichier.close()

    return [joueur, argent] #renvoie les 2 valeurs dans un tableau.

##Flask

@app.route('/')
def menu():
    return render_template("menu.html")

@app.route('/telechargement')
def telechargement():
    return render_template("telechargement.html")

@app.route('/connection', methods=['get', 'post'])
def connection():
    erreurs = []
    if(request.method == 'POST'):
        pseudo = request.form['pseudo']
        mdp = request.form['mdp']
        
        if(pseudo == ""): erreurs.append("Veuillez entrer un nom d'utilisateur")
        if(pseudo.find(" ") != -1): erreurs.append("Votre nom d'utilisateur ne doit pas contenir d'espace")
        if(len(pseudo) > 18): erreurs.append("Votre nom d'utilisateur ne doit pas dépasser 18 caractères")
        
        if(mdp == ""): erreurs.append("Veuillez entrer un mot de passe")
        if(mdp.find(" ") != -1): erreurs.append("Votre mot de passe ne doit pas contenir d'espace")
        
        if(recup_joueur(pseudo, mdp) == False): erreurs.append("Le nom d'utilisateur/Mot de passe n'est pas correct")
        
        if erreurs == []:
            return render_template("connecte.html", pseudo=pseudo, mdp=mdp)

    return render_template("connection.html", erreurs=erreurs)


@app.route('/inscription', methods=['get','post'])
def inscription():
    erreurs = []
    if(request.method == 'POST'):
        pseudo = request.form['pseudo']
        mdp = request.form['mdp']
        confirm_mdp = request.form['confirm_mdp']
        
        if(pseudo == ""): erreurs.append("Veuillez entrer un nom d'utilisateur")
        if(pseudo.find(" ") != -1): erreurs.append("Votre nom d'utilisateur ne doit pas contenir d'espace")
        if(len(pseudo) > 18): erreurs.append("Votre nom d'utilisateur ne doit pas dépasser 18 caractères")
        
        if(mdp == ""): erreurs.append("Veuillez entrer un mot de passe")
        if(mdp.find(" ") != -1): erreurs.append("Votre mot de passe ne doit pas contenir d'espace")
        
        if(confirm_mdp != mdp): erreurs.append("La vérification du mot de passe de correspond pas au mot de passe")
        
        if(os.path.exists(dossier + "/joueurs/" + pseudo)): erreurs.append("Ce nom d'utilisateur existe déjà")
        
        if erreurs == []:
            nouveau_joueur(pseudo, mdp, 1000)
            print("\033[92mL'utilisateur " + pseudo + " à bien été créé")
            return render_template("inscrit.html", pseudo=pseudo, mdp=mdp)
    
    return render_template("inscription.html", erreurs=erreurs)


app.run(debug=True)