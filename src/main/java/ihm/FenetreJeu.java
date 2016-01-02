package ihm;

import ihm.JPanelCarte.MonJLabel;
import ihm.JPanelCarte.SymboleListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.ImageIcon;
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
import dobble.Symbole;

public class FenetreJeu extends JFrame implements ActionListener{
	

	private MoteurJeu mj;
	private JMenuBar menubar;
	private JMenu menuQuitter;
	private Container contentPane;
	private JLabel labScore;
	private JLabel time;
	
	private Timer timer;
	
	private Image fond;
	private JPanel carte;
	
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
		
		this.fond = new ImageIcon("images/table.jpg").getImage();
		
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
		JMenu menu = new JMenu("Systeme");
		JMenuItem quitter = new JMenuItem("Quitter");
		menu.add(quitter);
		return menu;
	}
	
	public JPanel creePanels()
	{
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,1));


		JPanel panelCarteCentre = this.creePanelCarteCentre();
		p.add(panelCarteCentre);
		
		JPanel panelJoueurs = new JPanel();
		panelJoueurs.setLayout(new GridLayout(1,2));
		
			JPanel panelCarteJoueur = this.creePanelCarteJoueur();
			panelJoueurs.add(panelCarteJoueur);
			
			JPanel panelCarteAdverse = this.creePanelCarteAdverse();
			panelJoueurs.add(panelCarteAdverse);
		
		p.add(panelJoueurs);
		this.carte=p;
		return p;
	}
	
	private JPanel creePanelCarteCentre()
	{
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout(0,0));
		p.setOpaque(false);
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
		p.setOpaque(false);
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
		p.setOpaque(false);
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
	
	public void paint(Graphics g)
	{
		g.drawImage(this.fond, 0, 0, this.getWidth(), this.getHeight(), this);  //FIXME la carte doit s'adapter proportionnellement au conteneur
		super.paint(g);
	}
	
	
	public class JPanelCarte extends JPanel {
		
		private Image imgCarte;
		private Image imgCarteOmbre;
		
		private int tailleCarte;
		
		private Carte c;
		private Symbole[] symboles;
		private JLabel[] listLable;
		
		
		
		public JPanelCarte(Carte c)
		{
			super();
			this.setLayout(new BorderLayout());
			this.imgCarte = Toolkit.getDefaultToolkit().getImage("images/carte.png");
			this.imgCarteOmbre = Toolkit.getDefaultToolkit().getImage("images/carte-ombre.png");

			this.c = c;
			this.symboles = new Symbole[c.getArraySymbole().size()];
			this.listLable=new JLabel[c.getArraySymbole().size()];
			
			double nbligne=(c.getArraySymbole().size()+4)/4;
			
			
			this.setLayout(new GridLayout(4,(int)nbligne));
			this.setBackground(Color.BLUE);
			this.setOpaque(false);
			
			int compteur=0;
			
			for(int j=0; j< 4; j+=1)
			{

				for (int i = 0; i < nbligne; i += 1)
				{			
					
					
					if((j == 0 || j == 3) && (i == 0 || i == nbligne-1))
					{
						this.add(new JLabel());
						continue;
					}

					this.symboles[compteur] = this.c.getArraySymbole().get(compteur);
					JLabel t=new MonJLabel(this.symboles[compteur].getImage());
					t.addMouseListener(new SymboleListener(this.symboles[compteur]));
					this.listLable[compteur]=t;
					compteur++;
					this.add(t);
				}
				
			}
			
			this.setVisible(true);
			this.repaint();
		}
		
		public void paint(Graphics g)
		{
			int w = this.getWidth();
			int h = this.getHeight();
			if(w >= h)
			{
				this.tailleCarte = h;
			}
			else this.tailleCarte = w;
			
			g.drawImage(imgCarte, 0, 0, this.tailleCarte, this.tailleCarte, this);  //FIXME la carte doit s'adapter proportionnellement au conteneur
			super.paint(g);
			g.drawImage(imgCarteOmbre, 0, 0, this.tailleCarte, this.tailleCarte, this);
		}
		
			
			public void setC(Carte c) {
				this.c = c;
			}
			
		
			public class MonJLabel extends JLabel
			{
			
				private int rotation;
				
				private Image img;
				
				private int taille=0;
				
				public MonJLabel(Image m)
				{
					super();
					this.img=m;
					this.rotation= ((int) (Math.random()*100)%360);
					this.setVisible(true);
					this.setOpaque(false);
			
				}
				
				private int randomH()
				{
			
					return (int)(((this.getHeight())-(((int) (Math.random()*1000))%(this.getHeight()/3)))*1.5f); //TODO faire que le scaling se remarque un minimum?
				}
				 
			
				 public void paint(Graphics g)
				 {
					 super.paint(g);
					 
					 if(this.taille==0)
					 {
							this.taille=this.randomH();
							this.img=this.img.getScaledInstance(taille, taille,Image.SCALE_SMOOTH);
					 }
						 
					 
					 AffineTransform at = new AffineTransform();
					 
			         at.translate(getWidth() / 2, getHeight() / 2);
			         at.rotate(this.rotation);
			         at.scale(0.7, 0.7);
			         at.translate(-img.getWidth(this)/2, -img.getHeight(this)/2);
			         Graphics2D g2d = (Graphics2D) g;
			         g2d.drawImage(img, at,JPanelCarte.this);
				 }
			}

			public class SymboleListener implements MouseListener{//TODO ecouteur a modifier
				
				private Symbole monSymbole;
				
				public SymboleListener(Symbole monSymbole)
				{
					this.monSymbole=monSymbole;
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					
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
					System.out.println(this.monSymbole.getNom());
					try {
						if(FenetreJeu.this.mj.interagir(0,this.monSymbole))
						{
							System.out.println("YOUPI");
							FenetreJeu.this.contentPane.remove(carte);
							FenetreJeu.this.contentPane.add(FenetreJeu.this.creePanels(),BorderLayout.CENTER);	
								
						}
						FenetreJeu.this.repaint();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
		
				@Override
				public void mouseReleased(MouseEvent arg0) {
					
				}
		
				
			}
		}

	
	public static void main(String[] args) {

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

}
