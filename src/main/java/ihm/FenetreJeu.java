package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

import dobble.Carte;
import dobble.Joueur;
import dobble.Mode;
import dobble.MoteurJeu;
import dobble.Stats;

public class FenetreJeu extends JFrame implements ActionListener{
	

	private MoteurJeu mj;
	private JMenuBar menubar;
	private JMenu menuQuitter;
	private Container contentPane;
	private JLabel labScore;
	private JLabel time;
	
	private Timer timer;
	
	public FenetreJeu(MoteurJeu mj)  // instancie une fenêtre de jeu pour 2 joueurs pour le moment à partir d'un MoteurJeu
	{
		this.setTitle("Dobble");
		Toolkit t =Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();
		this.setSize(d);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.mj = mj;
		
		contentPane=this.getContentPane();
		contentPane.setLayout(new BorderLayout(0,0));
		this.contentPane.add(this.creePanels(),BorderLayout.CENTER);
		
		
		menubar = this.creeMenuBar();
		this.setJMenuBar(menubar);
		
		this.setVisible(true);
		
		timer = new Timer(1,this);
		timer.start();
		
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
		JMenu menu = new JMenu();
		JMenuItem quitter = new JMenuItem("quitter");
		menu.add(quitter);
		return menu;
	}
	
	private JPanel creePanels()
	{
		JPanel p= new JPanel();
		p.setLayout(new BorderLayout());


		JPanel panelCarteCentre = this.creePanelCarteCentre();
		p.add(panelCarteCentre, BorderLayout.NORTH);
		
		JPanel panelJoueurs = new JPanel();
		panelJoueurs.setLayout(new GridLayout(1,2));
		
			JPanel panelCarteJoueur = this.creePanelCarteJoueur();
			panelJoueurs.add(panelCarteJoueur);
			
			JPanel panelCarteAdverse = this.creePanelCarteAdverse();
			panelJoueurs.add(panelCarteAdverse);
		
		p.add(panelJoueurs, BorderLayout.CENTER);
		
		return p;
	}
	
	private JPanel creePanelCarteCentre()
	{
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout(0,0));
		p.setBackground(Color.blue);
		time = new JLabel();
		int tMin = mj.getChrono().getMinutes();
		int tSec = mj.getChrono().getSeconds();
		time.setText("" + tMin + " :" + tSec);
		p.add(time, BorderLayout.NORTH);
		p.add(new JPanelCarte(mj.getCartesCentre().
				get(mj.getCartesCentre().size() - 1)),BorderLayout.CENTER);//FIXME n'affiche pas la carte
		return p;
	}
	
	private JPanel creePanelCarteJoueur()
	{
		JPanel p = new JPanel();
		p.setBackground(Color.red);
		p.setLayout(new BorderLayout());
		JLabel labPseudo = new JLabel();//affiche le pseudo du joueur 1
		String pseudo = mj.getArrayJoueur().get(0).getNom(); //TODO ne marche que pour le premier joueur
		labPseudo.setText(pseudo);
		labPseudo.setForeground(Color.white);
		p.add(labPseudo, BorderLayout.SOUTH);
		
		labScore = new JLabel();//affiche le score du joueur 1
		int score = mj.getArrayJoueur().get(0).getScore();
		labScore.setText("" + score);
		p.add(labScore, BorderLayout.SOUTH);
		
		p.add(new JPanelCarte(mj.getArrayJoueur().get(0).getArrayCartes()
				.get(mj.getArrayJoueur().get(0).getArrayCartes().size() -1)),BorderLayout.CENTER);
		
		return p;
	}
	
	private JPanel creePanelCarteAdverse()
	{
		JPanel p = new JPanel();
		p.setBackground(Color.black);
		JLabel pseudo = new JLabel();
		String psdo = mj.getArrayJoueur().get(1).getNom(); //affiche le pseudo du joueur adverse
		pseudo.setText(psdo);
		pseudo.setForeground(Color.white);
		p.add(pseudo, BorderLayout.SOUTH);
		
		p.add(new JPanelCarte(mj.getArrayJoueur().get(1).getArrayCartes()
				.get(mj.getArrayJoueur().get(1).getArrayCartes().size() -1)),BorderLayout.CENTER);
		
		return p;
	}
	
	private void update()
	{
		int tMin = mj.getChrono().getMinutes();//update le timer
		int tSec = mj.getChrono().getSeconds() - 60*tMin;
		String text = "" + tMin + " :";
		if (tSec < 10)
		{
			text += "0";
		}
		text += tSec;
		time.setText(text);
		
		int score = mj.getArrayJoueur().get(0).getScore();//update le score du joueur
		labScore.setText("" + score);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.update();
		
	}

	
	public static void main(String[] args) {

		Mode mode = new Mode(); //Mode par defaut
		ArrayList<Carte> deck = Carte.genererDeck(mode); //Nouveau deck global de 55 cartes
		
		Joueur j1 = new Joueur(new ArrayList<Carte>(), new Stats(), "Bonneau", 0, "mdp"); //Nouveau Joueur, sans deck pour l'instant
		Joueur j2 = new Joueur(new ArrayList<Carte>(), new Stats(), "Bonneau Beau", 0, "mdp");
		
		
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>(); //Liste de joueurs
		joueurs.add(j1); //Ajout de notre joueur cree a la liste
		joueurs.add(j2);
		

		
		
		
		MoteurJeu jeu = new MoteurJeu(joueurs); //Cree un moteur de jeu
		
		jeu.initialiser(); //initialisation: scores et chronometre
		
		jeu.lancerJeu(deck); //Lancement: cartes distribuees
		
		FenetreJeu j= new FenetreJeu(jeu);
		
		while(true)
		{
			System.out.println(jeu.getChrono().getSeconds());
		}
	}

}
