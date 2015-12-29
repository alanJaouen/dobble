package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
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
	private JPanel contentPane;
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
		
		contentPane = new JPanel();
		this.creePanels();
		
		
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
	
	private void creePanels()
	{
		this.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0,0));
		JPanel panelCarteCentre = this.creePanelCarteCentre();
		contentPane.add(panelCarteCentre, BorderLayout.NORTH);
		JPanel panelCarteJoueur = this.creePanelCarteJoueur();
		contentPane.add(panelCarteJoueur, BorderLayout.WEST);
		JPanel panelCarteAdverse = this.creePanelCarteAdverse();
		contentPane.add(panelCarteAdverse, BorderLayout.EAST);
	}
	
	private JPanel creePanelCarteCentre()
	{
		JPanel p = new JPanel();
		p.setBackground(Color.blue);
		time = new JLabel();
		int tMin = mj.getChrono().getMinutes();
		int tSec = mj.getChrono().getSeconds();
		time.setText("" + tMin + " :" + tSec);
		p.add(time);
		return p;
	}
	
	private JPanel creePanelCarteJoueur()
	{
		JPanel p = new JPanel();
		p.setBackground(Color.red);
		
		JLabel labPseudo = new JLabel();//affiche le pseudo du joueur 1
		String pseudo = mj.getArrayJoueur().get(0).getNom(); //TODO ne marche que pour le premier joueur
		labPseudo.setText(pseudo);
		labPseudo.setForeground(Color.white);
		p.add(labPseudo);
		
		labScore = new JLabel();//affiche le score du joueur 1
		int score = mj.getArrayJoueur().get(0).getScore();
		labScore.setText("" + score);
		p.add(labScore);
		
		p.add(new JPanelCarte(new Carte(3)));
		
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
		p.add(pseudo);
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
