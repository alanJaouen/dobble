package ihm;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dobble.Carte;
import dobble.Joueur;
import dobble.Mode;
import dobble.MoteurJeu;
import dobble.Stats;

public class FenetreMenuPrincipal extends JFrame {
	public FenetreMenuPrincipal() {
		super("Dobble");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(400, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.initialise();
		this.setVisible(true);
	}
	
	private void initialise() {
		// Layout
		this.setLayout(new GridLayout(5, 1));
		
		// Noms des boutons :
		String[] nomBoutons = {"Jouer", 
							"Statistiques", 
							"Parametres", 
							"Autre Bouton", 
							"Quitter Dobble :'("
							};
		
		
		// Creation et ajout des boutons a la fenetre :
		JButton bouton;
		for (int i = 0; i < 5; i++) {
			bouton = new JButton(nomBoutons[i]);
			bouton.addActionListener(new BoutonListener(i + 1));
			this.add(bouton);
		}
	}
	
	// Inner Classes
	
	private class BoutonListener implements ActionListener {
		
		// Attributs :
		private int id;
		
		// Constructeur :
		public BoutonListener(int id) {
			this.id = id;
		}
		
		// Action :
		public void actionPerformed(ActionEvent e) {
			
			// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// LES DIFFERENTES ACTIONS DES BOUTONS :
			// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			switch(this.id) {
			case 1: // Bouton jouer
				Mode mode = new Mode(); //Mode par defaut
				ArrayList<Carte> deck;
				try {
					deck = Carte.genererDeck(mode);//Nouveau deck selon mode
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(FenetreMenuPrincipal.this, "erreur fatale:\n"+e1.getMessage(), "Bug", JOptionPane.ERROR_MESSAGE);
					break;
				} 
				
				Joueur j1 = new Joueur(new ArrayList<Carte>(), new Stats(), "Bonneau Lait", 0, "mdp"); //Nouveau Joueur, sans deck pour l'instant
				Joueur j2 = new Joueur(new ArrayList<Carte>(), new Stats(), "Bonneau Beau", 0, "mdp");
			
				ArrayList<Joueur> joueurs = new ArrayList<Joueur>(); //Liste de joueurs
				joueurs.add(j1); //Ajout de notre joueur cree a la liste
				joueurs.add(j2);

				MoteurJeu jeu = new MoteurJeu(joueurs); //Cree un moteur de jeu
				jeu.initialiser(); //initialisation: scores et chronometre
				jeu.lancerJeu(deck); //Lancement: cartes distribuees
				FenetreJeu j= new FenetreJeu(jeu);
				break;
			case 2: // Bouton stats
				new FenetreStat();
				break;
			case 3: // Bouton parametres
				new FenetreParametres(new Mode());
				break;
			case 4: // Bouton vide
				JOptionPane.showMessageDialog(FenetreMenuPrincipal.this, "Ce bouton n'est pas encore attribué. Il le sera éventuellement dans une future mise à jour.", "Bouton non attribué", JOptionPane.INFORMATION_MESSAGE);
				break;
			case 5: // Bouton quitter
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog (FenetreMenuPrincipal.this, "Etes-vous sur(e) de vouloir quitter Dobble ?","Quitter Dobble", 0)){
					FenetreMenuPrincipal.this.dispose();
				}
				break;
			}
		}
	}
}
