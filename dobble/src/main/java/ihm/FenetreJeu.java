package ihm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import dobble.Carte;
import dobble.Joueur;
import dobble.Mode;
import dobble.MoteurJeu;
import dobble.Stats;
import dobble.Stats.BddException;

public class FenetreJeu extends JFrame {

	
	private Container contentPane;
	private PanelJeu lepanel;
	private JMenu menuQuitter;
	private MoteurJeu mj;
	
	public FenetreJeu(MoteurJeu jeu) {
		super();
		
		this.mj=jeu;
		
		
		this.setTitle("Dobble");
		Toolkit t =Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();
		this.setSize(d);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		contentPane=this.getContentPane();
		contentPane.setLayout(new BorderLayout(0,0));
		
		this.lepanel=new PanelJeu(jeu);
		this.setJMenuBar(this.creeMenuBar());
		
		ActualiseThread thread= new ActualiseThread("actualisation de l ihm");
		thread.start();
		
		this.contentPane.add(lepanel, BorderLayout.CENTER);
		this.setVisible(true);
	}

	private JMenuBar creeMenuBar()
	{
		JMenuBar mb = new JMenuBar();
		menuQuitter = creeMenuQuitter();
		mb.add(menuQuitter);
		return mb;
	}
	
	private JMenu creeMenuQuitter()
	{
		JMenu menu = new JMenu("Systeme");
		JMenuItem quitter = new JMenuItem("Quitter");
		menu.add(quitter);
		return menu;
	}
	
	
	public static void main(String[] args) throws Exception {

		Mode mode = new Mode(); //Mode par defaut
		ArrayList<Carte> deck = Carte.genererDeck(mode); //Nouveau deck global de 55 cartes
		
		Joueur j1 = new Joueur(new ArrayList<Carte>(), new Stats(), "Bonneau Lait", 0, "mdp"); //Nouveau Joueur, sans deck pour l'instant
		Joueur j2 = new Joueur(new ArrayList<Carte>(), new Stats(), "Bonneau Beau", 0, "mdp");
		
		
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>(); //Liste de joueurs
		joueurs.add(j1); //Ajout de notre joueur cree a la liste
		joueurs.add(j2);
		
		
		
		MoteurJeu jeu = new MoteurJeu(joueurs); //Cree un moteur de jeu
		jeu.initialiser(); //initialisation: scores et chronometre
		jeu.lancerJeu(deck); //Lancement: cartes distribuees
		FenetreJeu j= new FenetreJeu(jeu);
		
	}
	
	/**
	 * Un thread permettant de rafraichir l'affichage de l'ihm
	 * @author Alan JAOUEN
	 *
	 */
	public class ActualiseThread extends Thread {

		/**
		 * Constreucteur par defaut
		 * @param name nom du Thread
		 */
		public ActualiseThread(String name)
		{
			super(name);
		}
		 
		/**
		 * Lance le thread
		 */
		@Override
		public void run()
		{
			while(true)
			{
				try 
				{
					Thread.sleep(200);
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				if (FenetreJeu.this.mj.isInGame())
				{
					if(FenetreJeu.this.mj.getNeedUpdate())
					{
						mj.updatedone();
						lepanel.remove(lepanel.getCarte());
						lepanel.add(lepanel.creePanels(),BorderLayout.CENTER);
					}
				}
				else
				{
					if(FenetreJeu.this.mj.getNeedUpdate())
					{
						JOptionPane.showMessageDialog(FenetreJeu.this, "Dommage tu as perdu",
							"bg", JOptionPane.INFORMATION_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(FenetreJeu.this, "Bravo tu as gagné",
								"gg", JOptionPane.INFORMATION_MESSAGE);
					try {
						FenetreJeu.this.mj.finPartie();
					} catch (BddException e) {	
						JOptionPane.showMessageDialog(FenetreJeu.this, e.getMessage(),
								"err", JOptionPane.ERROR_MESSAGE);
					}
					FenetreJeu.this.dispose();
					new FenetreMenuPrincipal();
					this.stop();
				}
			}
					
		}
	}//fin ActualiseThread

}
	
