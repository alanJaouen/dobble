package ihm;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dobble.*;
import dobble.Stats.BddException;



public class FenetreMenuPrincipal extends JFrame {
	
	private Joueur joueur;
	private JLabel info;
	private Mode mode;
	
	
	public FenetreMenuPrincipal() {
		super("Dobble");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(400, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		
		try
		{
			this.mode=new Mode(
				Carte.intsFromString(Symbole.lecture("param.txt", 3))[0],//nb de symbole
				Carte.intsFromString(Symbole.lecture("param.txt", 4))[0]); //tps ia
		}
		catch(Exception e7)
		{
			mode=new Mode();
			JOptionPane.showMessageDialog(FenetreMenuPrincipal.this,
					"erreur fatale:\n"+e7.getMessage()+"\n lancement avec les parametre par défaut et réinitialisation des parametres", "Bug", JOptionPane.ERROR_MESSAGE);
			this.razParam();
		}
		
		this.initialise();
		
		
		
		this.setVisible(true);
	}
	
	private void initialise() {
		this.setLayout(new BorderLayout());
		this.add(panelinfo(), BorderLayout.NORTH);
		this.add(panelbouton(), BorderLayout.CENTER);
	}
	
	
	private JPanel panelbouton()
	{
		JPanel p = new JPanel();
		// Layout
		p.setLayout(new GridLayout(6, 1));
		
		// Noms des boutons :
		String[] nomBoutons = {"Jouer", 
							"Statistiques", 
							"Parametres", 
							"Nouveau Joueur",
							"Supprimer mon compte",
							"Quitter Dobble :'("
							};
		
		
		// Creation et ajout des boutons a la fenetre :
		JButton bouton;
		for (int i = 0; i < 6; i++) {
			bouton = new JButton(nomBoutons[i]);
			bouton.addActionListener(new BoutonListener(i + 1));
			p.add(bouton);
		}
		return p;
	}
	
	private JPanel panelinfo()
	{
		JPanel p = new JPanel();
		this.info=new JLabel();
		this.info.setText("Vous n'etes pas encore connecté, cliquez ici pour vous connecter");
		this.info.addMouseListener(new ConnectionListener());
		p.add(this.info);
		return p;
	}
	
	private void razParam()
	{
		int[] data = {1, 1, 8, 10};
		File f = new File ("param.txt");
		 
		try
		{
		    FileWriter fw = new FileWriter (f);
		 
		    for (int d : data)
		    {
		        fw.write (String.valueOf (d));
		        fw.write ("\r\n");
		    }
		 
		    fw.close();
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
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
				ArrayList<Carte> deck;
				try {
					deck = Carte.genererDeck(FenetreMenuPrincipal.this.mode);//Nouveau deck selon mode
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(FenetreMenuPrincipal.this, "erreur fatale:\n"+e1.getMessage(), "Bug", JOptionPane.ERROR_MESSAGE);
					break;
				} 
				
				Joueur j1 =null;
				
				if(FenetreMenuPrincipal.this.joueur==null)
					j1= new Joueur(new ArrayList<Carte>(), new Stats(), "invite", 0, "invite");//Nouveau Joueur, sans deck pour l'instant
				else
					j1= FenetreMenuPrincipal.this.joueur;
				
				Joueur j2 = new Joueur(new ArrayList<Carte>(), new Stats(), "Bonneau Beau", 0, "mdp");
			
				ArrayList<Joueur> joueurs = new ArrayList<Joueur>(); //Liste de joueurs
				joueurs.add(j1); //Ajout de notre joueur cree a la liste
				joueurs.add(j2);

				MoteurJeu jeu = new MoteurJeu(joueurs); //Cree un moteur de jeu
				jeu.initialiser(); //initialisation: scores et chronometre
				jeu.lancerJeu(deck); //Lancement: cartes distribuees
				FenetreJeu j= new FenetreJeu(jeu);
				FenetreMenuPrincipal.this.dispose();
				break;
			case 2: // Bouton stats
				new FenetreStat();
				FenetreMenuPrincipal.this.dispose();
				break;
			case 3: // Bouton parametres
				new FenetreParametres(FenetreMenuPrincipal.this.mode);
				FenetreMenuPrincipal.this.dispose();
				break;
			case 4: // Bouton nouveau joueur
				FenetreMenuPrincipal.this.nouveauJoueur();
				break;
			case 5: // Bouton nouveau joueur
				if(FenetreMenuPrincipal.this.joueur != null)
				{
					if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog (FenetreMenuPrincipal.this,
							"Etes-vous sur(e) de vouloir supprimer votre compte Dobble ?","Supprimer mon compte", 0))
					{
						try 
						{
							FenetreMenuPrincipal.this.joueur.supprimerJoueur();
							FenetreMenuPrincipal.this.joueur=null;
							new FenetreMenuPrincipal();
							FenetreMenuPrincipal.this.dispose();
						} catch (BddException e1) 
						{	
							JOptionPane.showMessageDialog(FenetreMenuPrincipal.this, "erreur fatale:\n"+e1.getMessage(), "Bug", JOptionPane.ERROR_MESSAGE);
						}
					}
					
				}
				else
					JOptionPane.showMessageDialog(FenetreMenuPrincipal.this, "Vous devez etre connecté pour pouvoir supprimer votre compte",
							"Attention", JOptionPane.WARNING_MESSAGE);
				break;
			case 6: // Bouton quitter
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog (FenetreMenuPrincipal.this, "Etes-vous sur(e) de vouloir quitter Dobble ?","Quitter Dobble", 0)){
					FenetreMenuPrincipal.this.dispose();
				}
				break;
			}
			
		}
	}
	
	private class ConnectionListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			if(FenetreMenuPrincipal.this.joueur == null)
				this.connection();
			else 
				this.deconnection();
		
		}
		
		private void connection()
		{
			String id=JOptionPane.showInputDialog("Quel est votre identifiant?");
			
			if(id == null)//si l'utilisateur cancel
				return;
			String mdp=JOptionPane.showInputDialog("Quel est votre mot de passe?");
			
			if(mdp == null)//si l'utilisateur cancel
				return;
			
			
			try {
				FenetreMenuPrincipal.this.joueur=new Joueur(id,mdp);
				FenetreMenuPrincipal.this.info.setText("Bienvenu "+FenetreMenuPrincipal.this.joueur.getNom()+", cliquez ici pour vous déconnecter");
			} catch (BddException e) {
				JOptionPane.showMessageDialog(FenetreMenuPrincipal.this, e.getMessage(), "Une erreur s'est produite",
						JOptionPane.ERROR_MESSAGE);
			}	
		}
		
		
		
		private void deconnection()
		{
			Object[] options = { "Me déconnecter", "Annuler" };
			int choix =JOptionPane.showOptionDialog(FenetreMenuPrincipal.this, "Voulez vous vraiment vous déconnecter?", "Déconnection",
			        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
			        null, options, options[0]);
			
			if(choix == 0)
			{
				FenetreMenuPrincipal.this.joueur = null;
				FenetreMenuPrincipal.this.info.setText("Vous n'etes pas encore connecté, cliquez ici pour vous connecter");
			}
		}
		
		
		
		
	}
	
	private void nouveauJoueur()
	{
		String id=JOptionPane.showInputDialog("Quel identifiant souhaiteriez-vous ?");
		String mdp=JOptionPane.showInputDialog("Quel mot de passe souhaiteriez-vous ?");
		
		try {
			Joueur.nouveauJoueur(id, mdp);
		} catch (BddException e) {
			JOptionPane.showMessageDialog(FenetreMenuPrincipal.this, e.getMessage(), "Une erreur s'est produite",
					JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	public static void main(String[] args) {
		new FenetreMenuPrincipal();
	}

}

