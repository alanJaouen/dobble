package dobble;

import java.util.ArrayList;
import java.util.Scanner;

public class TestMoteurJeu {

	public static void main(String[] args) throws Exception {
		Mode mode = new Mode(); //Mode par defaut
		ArrayList<Carte> deck = Carte.genererDeck(mode); //Nouveau deck global de 55 cartes
		
		Joueur j1 = new Joueur(new ArrayList<Carte>(), new Stats(), "Bonneau", 0, "mdp"); //Nouveau Joueur, sans deck pour l'instant
		
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>(); //Liste de joueurs
		joueurs.add(j1); //Ajout de notre joueur cree a la liste
		
		
		
		
		MoteurJeu jeu = new MoteurJeu(joueurs); //Cree un moteur de jeu
		
		jeu.initialiser(); //initialisation: scores et chronometre
		
		jeu.lancerJeu(deck); //Lancement: cartes distribuees
		
		System.out.println("DEBUT JEU\n\n");
		
		
		Scanner sc = new Scanner(System.in);
		
		while (jeu.isInGame()) //BOUCLE DE JEU
		{
			System.out.println("DEBUT TOUR");
			
			
			
			System.out.println("Carte centrale:\n" + jeu.getCartesCentre().get(jeu.getCartesCentre().size() - 1) + "\n\n");
			
			System.out.println("Votre carte:\n" + jeu.getArrayJoueur().get(0).getArrayCartes().get(
																			jeu.getArrayJoueur().get(0).getArrayCartes().size() - 1) + "\n\n");
			
			int symbole = sc.nextInt();
			
			if(jeu.interagir(0, new Symbole(symbole))) //Si symbole trouvé
			{
				System.out.println("GG WOW SUCH SKILL, tour suivant");
			}
			else System.out.println("NUL ESPECE DE CACA, tour suivant");
			
		}
		
		sc.close();
	}

}
