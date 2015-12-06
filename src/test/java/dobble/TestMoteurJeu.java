package dobble;

import java.util.ArrayList;
import java.util.Scanner;

public class TestMoteurJeu {

	public static void main(String[] args) {
		Mode mode = new Mode(); //Mode par defaut
		ArrayList<Carte> deck = Carte.genererDeck(mode); //Nouveau deck global de 55 cartes
		
		Joueur j1 = new Joueur(new ArrayList<Carte>(), new Stats(), "Bonneau", 0, "mdp"); //Nouveau Joueur, sans deck pour l'instant
		
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>(); //Liste de joueurs
		joueurs.add(j1); //Ajout de notre joueur cree a la liste
		
		ArrayList<Carte> cartesCentre = new ArrayList<Carte>(); //Deck central
		cartesCentre.add(new Carte(3)); //Première carte centrale, au pif
		
		
		
		MoteurJeu jeu = new MoteurJeu(joueurs); //Cree un moteur de jeu
		
		jeu.initialiser(); //initialisation: scores et chronometre
		
		jeu.lancerJeu(deck); //Lancement: cartes distribuees
		
		System.out.println("DEBUT JEU\n\n");
		
		
		Scanner sc = new Scanner(System.in);
		while (jeu.isInGame()) //BOUCLE DE JEU
		{
			System.out.println("DEBUT TOUR");
			jeu.interagir(1, sc.nextInt());
			
		}
	}

}
