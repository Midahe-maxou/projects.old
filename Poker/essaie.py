import tkinter as tk
from random import shuffle, randint
import webbrowser
import os

jeu = tk.Tk()
jeu.geometry('1366x768')
jeu.title('Poker Simulator')
jeu['bg'] = 'lightgrey'
jeu.resizable(height=False,width=False)
dossier = "./jeu/"

#importation des images et des fichiers externes

#---menu---#
titre_img = tk.PhotoImage(file='./images/titre.png')

click_btn_img = tk.PhotoImage(file='./images/jouer.png')

table_img = tk.PhotoImage(name="tablepoker", file="./images/tablepoker.png")

# jetons
jeton10_img = tk.PhotoImage(name="jeton10", file='./images/jetonblanc10nombre.png')
jeton20_img = tk.PhotoImage(name="jeton20", file='./images/jetonrouge20nombre.png')
jeton50_img = tk.PhotoImage(name="jeton50", file='./images/jetonbleu50nombre.png')
jeton100_img = tk.PhotoImage(name="jeton100", file='./images/jetonvert100nombre.png')
jeton500_img = tk.PhotoImage(name="jeton500", file='./images/jetonnoir500nombre.png')

# cartes
trefle_as_img = tk.PhotoImage(name="astrefle", file='./images/trefle/astrefle.png')
trefle_2_img = tk.PhotoImage(name="2trefle", file='./images/trefle/2trefle.png')
trefle_3_img = tk.PhotoImage(name="3trefle", file='./images/trefle/3trefle.png')
trefle_4_img = tk.PhotoImage(name="4trefle", file='./images/trefle/4trefle.png')
trefle_5_img = tk.PhotoImage(name="5trefle", file='./images/trefle/5trefle.png')
trefle_6_img = tk.PhotoImage(name="6trefle", file='./images/trefle/6trefle.png')
trefle_7_img = tk.PhotoImage(name="7trefle", file='./images/trefle/7trefle.png')
trefle_8_img = tk.PhotoImage(name="8trefle", file='./images/trefle/8trefle.png')
trefle_9_img = tk.PhotoImage(name="9trefle", file='./images/trefle/9trefle.png')
trefle_10_img = tk.PhotoImage(name="10trefle", file='./images/trefle/10trefle.png')
trefle_valet_img = tk.PhotoImage(name="valettrefle", file='./images/trefle/valettrefle.png')
trefle_reine_img = tk.PhotoImage(name="reinetrefle", file='./images/trefle/reinetrefle.png')
trefle_roi_img = tk.PhotoImage(name="roitrefle", file='./images/trefle/roitrefle.png')

coeur_as_img = tk.PhotoImage(name="ascoeur", file='./images/coeur/ascoeur.png')
coeur_2_img = tk.PhotoImage(name="2coeur", file='./images/coeur/2coeur.png')
coeur_3_img = tk.PhotoImage(name="3coeur", file='./images/coeur/3coeur.png')
coeur_4_img = tk.PhotoImage(name="4coeur", file='./images/coeur/4coeur.png')
coeur_5_img = tk.PhotoImage(name="5coeur", file='./images/coeur/5coeur.png')
coeur_6_img = tk.PhotoImage(name="6coeur", file='./images/coeur/6coeur.png')
coeur_7_img = tk.PhotoImage(name="7coeur", file='./images/coeur/7coeur.png')
coeur_8_img = tk.PhotoImage(name="8coeur", file='./images/coeur/8coeur.png')
coeur_9_img = tk.PhotoImage(name="9coeur", file='./images/coeur/9coeur.png')
coeur_10_img = tk.PhotoImage(name="10coeur", file='./images/coeur/10coeur.png')
coeur_valet_img = tk.PhotoImage(name="valetcoeur", file='./images/coeur/valetcoeur.png')
coeur_reine_img = tk.PhotoImage(name="reinecoeur", file='./images/coeur/reinecoeur.png')
coeur_roi_img = tk.PhotoImage(name="roicoeur", file='./images/coeur/roicoeur.png')

carreau_as_img = tk.PhotoImage(name="ascarreaux", file='./images/carreau/ascarreaux.png')
carreau_2_img = tk.PhotoImage(name="2carreaux", file='./images/carreau/2carreaux.png')
carreau_3_img = tk.PhotoImage(name="3carreaux", file='./images/carreau/3carreaux.png')
carreau_4_img = tk.PhotoImage(name="4carreaux", file='./images/carreau/4carreaux.png')
carreau_5_img = tk.PhotoImage(name="5carreaux", file='./images/carreau/5carreaux.png')
carreau_6_img = tk.PhotoImage(name="6carreaux", file='./images/carreau/6carreaux.png')
carreau_7_img = tk.PhotoImage(name="7carreaux", file='./images/carreau/7carreaux.png')
carreau_8_img = tk.PhotoImage(name="8carreaux", file='./images/carreau/8carreaux.png')
carreau_9_img = tk.PhotoImage(name="9carreaux", file='./images/carreau/9carreaux.png')
carreau_10_img = tk.PhotoImage(name="10scarreaux", file='./images/carreau/10carreaux.png')
carreau_valet_img = tk.PhotoImage(name="valetcarreaux", file='./images/carreau/valetcarreaux.png')
carreau_reine_img = tk.PhotoImage(name="reinecarreaux", file='./images/carreau/reinecarreaux.png')
carreau_roi_img = tk.PhotoImage(name="roicarreaux", file='./images/carreau/roicarreaux.png')

pique_as_img = tk.PhotoImage(name="aspique", file='./images/pique/aspique.png')
pique_2_img = tk.PhotoImage(name="2pique", file='./images/pique/2pique.png')
pique_3_img = tk.PhotoImage(name="3pique", file='./images/pique/3pique.png')
pique_4_img = tk.PhotoImage(name="4pique", file='./images/pique/4pique.png')
pique_5_img = tk.PhotoImage(name="5pique", file='./images/pique/5pique.png')
pique_6_img = tk.PhotoImage(name="6pique", file='./images/pique/6pique.png')
pique_7_img = tk.PhotoImage(name="7pique", file='./images/pique/7pique.png')
pique_8_img = tk.PhotoImage(name="8pique", file='./images/pique/8pique.png')
pique_9_img = tk.PhotoImage(name="9pique", file='./images/pique/9pique.png')
pique_10_img = tk.PhotoImage(name="10pique", file='./images/pique/10pique.png')
pique_valet_img = tk.PhotoImage(name="valetpique", file='./images/pique/valetpique.png')
pique_reine_img = tk.PhotoImage(name="reinepique", file='./images/pique/reinepique.png')
pique_roi_img = tk.PhotoImage(name="roipique", file='./images/pique/roipique.png')

cartedos_img = tk.PhotoImage(name="doscarte", file='./images/doscarte.png')
cartedos_horizont_img = tk.PhotoImage(name="doscarte-horizontal", file='./images/doscarte-horizont.png')

fenetre = None

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

def recup_carte_img(carte):
    if carte[1]=="pique":
        if carte[0]==14: return pique_as_img
        if carte[0]==2: return pique_2_img
        if carte[0]==3: return pique_3_img
        if carte[0]==4: return pique_4_img
        if carte[0]==5: return pique_5_img
        if carte[0]==6: return pique_6_img
        if carte[0]==7: return pique_7_img
        if carte[0]==8: return pique_8_img
        if carte[0]==9: return pique_9_img
        if carte[0]==10: return pique_10_img
        if carte[0]==11: return pique_valet_img
        if carte[0]==12: return pique_reine_img
        if carte[0]==13: return pique_roi_img

    if carte[1]=="trefle":
        if carte[0]==14: return trefle_as_img
        if carte[0]==2: return trefle_2_img
        if carte[0]==3: return trefle_3_img
        if carte[0]==4: return trefle_4_img
        if carte[0]==5: return trefle_5_img
        if carte[0]==6: return trefle_6_img
        if carte[0]==7: return trefle_7_img
        if carte[0]==8: return trefle_8_img
        if carte[0]==9: return trefle_9_img
        if carte[0]==10: return trefle_10_img
        if carte[0]==11: return trefle_valet_img
        if carte[0]==12: return trefle_reine_img
        if carte[0]==13: return trefle_roi_img

    if carte[1]=="coeur":
        if carte[0]==14: return coeur_as_img
        if carte[0]==2: return coeur_2_img
        if carte[0]==3: return coeur_3_img
        if carte[0]==4: return coeur_4_img
        if carte[0]==5: return coeur_5_img
        if carte[0]==6: return coeur_6_img
        if carte[0]==7: return coeur_7_img
        if carte[0]==8: return coeur_8_img
        if carte[0]==9: return coeur_9_img
        if carte[0]==10: return coeur_10_img
        if carte[0]==11: return coeur_valet_img
        if carte[0]==12: return coeur_reine_img
        if carte[0]==13: return coeur_roi_img

    if carte[1]=="carreau":
        if carte[0]==14: return carreau_as_img
        if carte[0]==2: return carreau_2_img
        if carte[0]==3: return carreau_3_img
        if carte[0]==4: return carreau_4_img
        if carte[0]==5: return carreau_5_img
        if carte[0]==6: return carreau_6_img
        if carte[0]==7: return carreau_7_img
        if carte[0]==8: return carreau_8_img
        if carte[0]==9: return carreau_9_img
        if carte[0]==10: return carreau_10_img
        if carte[0]==11: return carreau_valet_img
        if carte[0]==12: return carreau_reine_img
        if carte[0]==13: return carreau_roi_img


def quitter():
    jeu.destroy()

def regles():
    reinitialiser_fenetre()

    reglesjeu= tk.Label(jeu, text = "lablabla")
    reglesjeu.pack()

def reinitialiser_fenetre():
    global fenetre
    if fenetre is not None: fenetre.destroy()
    fenetre = tk.Frame(jeu, width=1366, height=768)
    fenetre.place(x=0, y=0)

def poker(joueur):

	cartes = [(n,c) for n in range(2,15) for c in ["carreau", "trefle", "pique", "coeur"]]
	shuffle(cartes)

	cartes_joueur = {
	"est": cartes[0:2],
	"nord": cartes[2:4],
	"ouest": cartes[4:6],
	"joueur": cartes[6:8],
	"centre": (cartes[8:13])
	}

	del(cartes[0:13]) #inutile mais LOL

	def jeu(premier_joueur = "est"):
		nonlocal cartes_joueur, coucher, check, remiser_bouton, remiser_valeur

		tours_sans_relance = 0
		nb_cartes_posee = 0
		mise = 0
		argent_en_jeu = 1

		joueurs_couches = []
		
		def placer_cartes_joueurs(c=0):
			cartes_joueur = [(carte_est1, (1050, 390)), (carte_est2, (1050, 280)), (carte_nord1, (690,80)), (carte_nord2, (580, 80)), (carte_ouest1, (185, 280)), (carte_ouest2, (185, 390)), (carte1, (560, 530)), (carte2, (700, 530))]

			while True:
				cartes_joueur[c][0].place(x=cartes_joueur[c][1][0], y=cartes_joueur[c][1][1])
				c+=1

				if len(cartes_joueur) > c:
					fenetre.after(500, placer_cartes_joueurs(c).__next__)
				yield

		def placer_cartes_centre(nb_cartes):
			carte_centre_x = 400
			carte_centre_y = 310

			for numero_carte in range(5):
				if nb_cartes > numero_carte:
					fenetre.after(500*numero_carte, lambda numero_carte=numero_carte: tk.Label(fenetre, image=recup_carte_img(cartes_joueur["centre"][numero_carte])).place(x=carte_centre_x + 115*numero_carte, y=carte_centre_y))
				else:
					fenetre.after(500*numero_carte, lambda numero_carte=numero_carte: tk.Label(fenetre, image=cartedos_img).place(x=carte_centre_x + 115*numero_carte, y=carte_centre_y))

		def rafraichir_argent():
			nonlocal argent_joueur, mise_affichage, argent_en_jeu_affichage

			if argent_joueur.place_info() != {}: argent_joueur.destroy()
			if mise_affichage.place_info() != {}: mise_affichage.destroy()
			if argent_en_jeu_affichage.place_info() != {}: argent_en_jeu_affichage.destroy()

			argent_joueur = tk.Label(fenetre, text="Argent: " + str(joueur[1]) + "€", font=("Helvetica", 18))
			mise_affichage = tk.Label(fenetre, text="Mise: " + str(mise) + "€", font=("Helvetica", 18))
			argent_en_jeu_affichage = tk.Label(fenetre, text="Argent en jeu: " + str(argent_en_jeu) + "€", font=("Helvetica", 18))

			argent_joueur.place(x=1100, y=10)
			mise_affichage.place(x=1100, y=40)
			argent_en_jeu_affichage.place(x=1100, y=70)

		def ajouter_historique(texte):
			ancien_txt = historique.cget("text")
			historique.configure(text=ancien_txt + '\n' + texte)

		def activer_boutons(activer):
			activation = 'disable'
			if activer: activation = 'active'

			coucher['state'] = activation
			check['state'] = activation
			remiser_bouton['state'] = activation
			remiser_valeur['state'] = activation


		def reconnaitre_combinaisons(cartes, main): #Bahahaha
			tout = cartes + main
			tout.sort()
			nb_cartes = len(tout)

			#retourne un dict avec les effectifs des cartes
			def recup_effectif_carte():
				effectif_cartes = {}
				for valeur in range(14,1, -1):
					effectif_cartes[valeur] = [*map(lambda x: x[0], tout)].count(valeur)
				return effectif_cartes

			#detection de suite
			def reconn_suite():
				suite = 1
				max_suite = 1
				debut_suite = 1
				for k in range(nb_cartes-1, 0, -1):
					if(tout[k][0] == tout[k-1][0] +1):
						suite = suite + 1
						if suite > max_suite:
							max_suite = suite
							debut_suite = k-1
					else:
						suite = 1
					if max_suite == 5: return tout[debut_suite: debut_suite+5]
				return 0

			#Flush
			def reconn_flush(cartes):
				couleurs = [*map(lambda x: x[1], cartes)]
				if len(set(couleurs)) == 1: return True
				return False

			#Carré
			def reconn_carre():
				effectifs = recup_effectif_carte()
				if 4 not in effectifs.values(): return 0
				carre = filter(lambda x: x[1] == 4, effectifs.items())
				return carre.__next__()[0]

			#Brelan
			def reconn_brelan():
				effectifs = recup_effectif_carte()
				
				if 2 in effectifs.values() or 3 not in effectifs.values(): return 0
				trois = filter(lambda x: x[1] == 3, effectifs.items())
				return trois.__next__()[0]

			#Full
			def reconn_full():
				effectifs = recup_effectif_carte()
				trois = 0
				deux = 0
				for carte in effectifs:
					if effectifs[carte] == 3 and trois == 0:
						trois = carte
					if 4 > effectifs[carte] >= 2 and carte != trois:
						deux = carte
					if trois and deux: return [trois, deux]
				return 0
			#Double paire
			def reconn_double_paire():
				effectifs = recup_effectif_carte()
				
				deux = 0
				for carte in effectifs:
					if effectifs[carte] == 2:
						if deux != 0:
							return [deux, carte]
						deux = carte
				return 0
			#Paire
			def reconn_paire():
				effectifs = recup_effectif_carte()
				if reconn_double_paire() != 0 or 2 not in effectifs.values(): return 0
				deux = filter(lambda x: x[1] == 2, effectifs.items())
				return deux.__next__()[0]
			
			#Détéction des cartes
			#Suite
			suite = reconn_suite()
			if suite != 0:
				if reconn_flush(suite):
					return 150+suite[0][0], "Quinte Flush"
				return 100+suite[0][0], "Quinte"

			#Carré
			carre = reconn_carre()
			if carre != 0:
				return 80+carre, "Carré"

			#Full
			full = reconn_full()
			if full != 0:
				return 60+full[0], "Full"
			
			#Brelan
			brelan = reconn_brelan()
			if brelan != 0:
				return 40+brelan, "Brelan"
			
			#Double paire
			db_paire = reconn_double_paire()
			if db_paire != 0:
				return 20+db_paire[0], "Double paire"

			#Paire
			paire = reconn_paire()
			if paire != 0:
				return 1+paire, "Paire"
			
			return 0, "Rien"

		def fin():
			point_est, main_est = reconnaitre_combinaisons(cartes_joueur["centre"], cartes_joueur["est"])
			point_nord, main_nord = reconnaitre_combinaisons(cartes_joueur["centre"], cartes_joueur["nord"])
			point_ouest, main_ouest = reconnaitre_combinaisons(cartes_joueur["centre"], cartes_joueur["ouest"])
			point_joueur, main_joueur = reconnaitre_combinaisons(cartes_joueur["centre"], cartes_joueur["joueur"])

			print("est:" +  str(point_est))
			print("nord:" +  str(point_nord))
			print("ouest:" +  str(point_ouest))
			print("joueur:" +  str(point_joueur))

			fenetre.after(3000, jeu)
			return

		def river():
			nonlocal nb_cartes_posee

			nb_cartes_posee = 5
			placer_cartes_centre(5)

			ajouter_historique("** Phase: River! **")

		def turn():
			nonlocal nb_cartes_posee

			nb_cartes_posee = 4
			placer_cartes_centre(4)

			ajouter_historique("** Phase: Turn! **")

		def flop():
			nonlocal nb_cartes_posee

			nb_cartes_posee = 3
			placer_cartes_centre(3)

			ajouter_historique("** Phase: Flop! **")

		def niveau_jeu_superireur(joueur):
			nonlocal tours_sans_relance

			if nb_cartes_posee == 3:
				turn()
			if nb_cartes_posee == 4:
				river()
			if nb_cartes_posee == 5:
				fin()
				return

			tours_sans_relance = 0
			print(tours_sans_relance)
			ouest_joue()


		def IA():
			nb = randint(0, 100)
			if nb > 90:
				return "couche"
			elif nb > 15:
				return "check"
			else:
				return nb

		def est_joue():
			nonlocal tours_sans_relance, mise, argent_en_jeu, nb_cartes_posee, joueurs_couches
			print(tours_sans_relance)
			if "est" in joueurs_couches:
				nord_joue()
				return

			if tours_sans_relance > 3:
				fenetre.after(1500, lambda: niveau_jeu_superireur(est_joue))
				return

			tours_sans_relance = tours_sans_relance + 1

			decision = IA(cartes_joueur["centre"][0:nb_cartes_posee], cartes_joueur["est"])

			if decision == "check":
				ajouter_historique("Est check")
				mise = mise + argent_en_jeu
			elif decision == "couche":
				ajouter_historique("Est se couche")
				joueurs_couches.append("est")
			else:
				pass


			rafraichir_argent()
			fenetre.after(2000, nord_joue)

		def nord_joue():
			nonlocal tours_sans_relance, mise, argent_en_jeu, nb_cartes_posee

			if "nord" in joueurs_couches:
				ouest_joue()
				return

			if tours_sans_relance > 3:
				fenetre.after(1500, lambda: niveau_jeu_superireur(nord_joue))
				return

			tours_sans_relance = tours_sans_relance + 1

			decision = IA(cartes_joueur["centre"][0:nb_cartes_posee], cartes_joueur["nord"])

			if decision == "check":
				ajouter_historique("Nord check")
				mise = mise + argent_en_jeu
			
			rafraichir_argent()
			fenetre.after(2000, ouest_joue)

		def ouest_joue():
			nonlocal tours_sans_relance, mise, argent_en_jeu, nb_cartes_posee

			if "ouest" in joueurs_couches:
				tour_joueur()
			
			if tours_sans_relance > 3:
				fenetre.after(1500, lambda: niveau_jeu_superireur(ouest_joue))
				return

			tours_sans_relance = tours_sans_relance + 1

			decision = IA(cartes_joueur["centre"][0:nb_cartes_posee], cartes_joueur["ouest"])

			if decision == "check":
				ajouter_historique("Ouest check")
				mise = mise + argent_en_jeu
			
			rafraichir_argent()
			fenetre.after(2000, tour_joueur)


		def se_coucher():
			nonlocal joueurs_couches
			joueurs_couches.append("joueur")
			ajouter_historique(joueur[0] + " se couche")
			activer_boutons(False)

			fenetre.after(2000, est_joue)

		def checker():
			nonlocal joueur, mise, argent_en_jeu

			if joueur[1] < mise:
				ajouter_historique("Vous n'avez pas assez d'argent pour suivre, Tapis")
				joueur[1] = 0
				argent_en_jeu = argent_en_jeu + joueur[1]
			else:
				ajouter_historique(joueur[0] +  " check")
				joueur[1], mise = joueur[1] - argent_en_jeu, mise + argent_en_jeu
			
			rafraichir_argent()

			activer_boutons(False)
			fenetre.after(2000, est_joue)

		def remiser():
			nonlocal tours_sans_relance, mise, argent_en_jeu

			tours_sans_relance = 0

			joueur[1]= joueur[1] - remiser_valeur.get()
			mise = mise + remiser_valeur.get()
			argent_en_jeu = argent_en_jeu + remiser_valeur.get()

			rafraichir_argent()
			activer_boutons(False)
			fenetre.after(2000, est_joue)

		def tour_joueur():
			nonlocal tours_sans_relance, mise

			if "joueur" in joueurs_couches:
				est_joue()
				return
			
			if tours_sans_relance > 3:
				fenetre.after(1500, lambda: niveau_jeu_superireur(tour_joueur))
				return
			tours_sans_relance = tours_sans_relance + 1

			if joueur[1] == 0:
				ajouter_historique("Vous n'avez plus d'argent")
				se_coucher()
				return

			activer_boutons(True)


		coucher['command'] = se_coucher
		check['command'] = checker
		remiser_bouton['command'] = remiser

		carte_est1 = tk.Label(fenetre, image=cartedos_horizont_img)
		carte_est2 = tk.Label(fenetre, image=cartedos_horizont_img)

		carte_nord1 = tk.Label(fenetre, image=cartedos_img)
		carte_nord2 = tk.Label(fenetre, image=cartedos_img)

		carte_ouest1 = tk.Label(fenetre, image=cartedos_horizont_img)
		carte_ouest2 = tk.Label(fenetre, image=cartedos_horizont_img)

		carte1 = tk.Label(fenetre, image=recup_carte_img(cartes_joueur["joueur"][0]))
		carte2 = tk.Label(fenetre, image=recup_carte_img(cartes_joueur["joueur"][1]))

		argent_joueur = tk.Label(fenetre, text="Argent: " + str(joueur[1]) + "€", font=("Helvetica", 18))
		mise_affichage = tk.Label(fenetre, text="Mise: " + str(mise) + "€", font=("Helvetica", 18))
		argent_en_jeu_affichage = tk.Label(fenetre, text="Argent en jeu: " + str(argent_en_jeu) + "€", font=("Helvetica", 18))

		rafraichir_argent()

		placer_cartes_joueurs().__next__()

		fenetre.after(4500, flop)

		if premier_joueur == "est": fenetre.after(7000, est_joue)
		elif premier_joueur == "nord": fenetre.after(7000, nord_joue)
		elif premier_joueur == "ouest": fenetre.after(7000, ouest_joue)
		else: fenetre.after(7000, tour_joueur)

	reinitialiser_fenetre()

	table = tk.Label(fenetre, image=table_img)
	table.place(x=82, y= -10)

	nord = tk.Label(fenetre, text="NORD", bg="black", fg="#F92", font=("Helvetica", 20, "bold"), padx=70, pady=5)
	nord.place(x=572, y=15)

	est = tk.Label(fenetre, text="EST", bg="black", fg="#F92", font=("Helvetica", 20, "bold"), padx=10, pady=50, wraplength=1)
	est.place(x=1225, y=280)

	ouest = tk.Label(fenetre, text="OUEST", bg="black", fg="#F92", font=("Helvetica", 20, "bold"), padx=10, pady=50, wraplength=1)
	ouest.place(x=105, y=250)

	vous = tk.Label(fenetre, text=joueur[0], bg="black", fg="#F92", font=("Helvetica", 20, "bold"), padx=70, pady=5)
	vous.place(x=572, y=700)

	coucher = tk.Button(fenetre, text="Se coucher", font=("Helvetica", 18), state="disable")
	coucher.place(x=150, y=700)

	check = tk.Button(fenetre, text="Check", font=("Helvetica", 18), state="disable")
	check.place(x=350, y=700)

	remiser_bouton = tk.Button(fenetre, text="Remiser:", font=("Helvetica", 18), state="disable")
	remiser_bouton.place(x=900, y=700)

	remiser_valeur = tk.Scale(fenetre, from_=1, to=joueur[1], length=150, orient="horizontal", tickinterval=joueur[1]/5, state="disable")
	remiser_valeur.place(x=1050, y=700)
	
	historique = tk.Label(fenetre, width=23, height=6, bg="black", fg="white", anchor="sw", padx=5, pady=5, justify="left", font=("Helvetica", 18))
	historique.place(x=20, y=30)
	historique_titre = tk.Label(fenetre, text="Historique des actions", bg="black", fg="white", justify="left", font=("Helvetica", 20))
	historique_titre.place(x=20, y=0)

	fenetre.after(1000, jeu)


def menu(joueur):

    reinitialiser_fenetre()

    titre= tk.Label(fenetre, image=titre_img)
    titre.place(x=480, y=111)

    bouton = tk.Button(fenetre, text="Jouer", bg='white', fg='white', image=click_btn_img, command=lambda: poker(joueur))
    bouton.place(x=550, y=334)

def connection():
    reinitialiser_fenetre()
    titre = tk.Label(fenetre, text="Se connecter", font=("Helvetica",26, "bold"))
    titre.place(x=570, y=60)

    nom = tk.Label(fenetre, text="Nom d'utilisateur", font=("Helvetica",20, "italic"))
    nom.place(x=520, y=130)
    blaze= tk.Entry(fenetre, width=20, font=("Helvetica",18))
    blaze.place(x=510, y=165)

    mdp = tk.Label(fenetre, text="Mot de passe", font=("Helvetica",20, "italic"))
    mdp.place(x=520, y=220)
    code = tk.Entry(fenetre, width=20, font=("Helvetica",18))
    code.place(x=510, y=255)

    connecter = tk.Button(fenetre, text="Se connecter", bg='white', command=lambda: connectation(blaze.get(), code.get()))
    connecter.place(x=600, y=300)

def connectation(blaze, code):
    if blaze == "" or code == "" or recup_joueur(blaze, code) == False:
        erreur = tk.Label(fenetre, text="Attention: Votre nom d'utilisateur ou\nou votre mot de passe n’est pas correct", bg="red", font=("Helvetica", 22,"bold"), fg="black")
        erreur.place(x=370, y=650)
    else:
        joueur = recup_joueur(blaze, code)
        menu(joueur)



#--menu--#
mon_menu = tk.Menu(jeu)
#--sous onglets--#
parametres = tk.Menu(mon_menu, tearoff=0)
parametres.add_command(label="Quitter", command=quitter)
parametres.add_command(label="Règles", command=regles)
parametres.add_command(label="Menu", command=menu)
#--parametres--#
mon_menu.add_cascade(label="Paramètres", menu=parametres)
jeu.config(menu=mon_menu)


connection()
jeu.mainloop()