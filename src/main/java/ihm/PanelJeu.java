package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import dobble.Carte;
import dobble.MoteurJeu;
import dobble.Symbole;

public class PanelJeu extends JPanel implements ActionListener{
	

	private static final long serialVersionUID = -6174972239935842965L;
	private MoteurJeu mj;
	private JLabel labScore;
	private JLabel time;
	
	private Timer timer;
	
	private Image fond;
	private JPanel carte;
	
	private int graphisme;
	
	public PanelJeu(MoteurJeu mj)  // instancie une fenêtre de jeu pour 2 joueurs pour le moment à partir d'un MoteurJeu
	{
		
		
		this.mj = mj;
		this.setLayout(new BorderLayout(0,0));
		this.add(this.creePanels(),BorderLayout.CENTER);
		
		this.fond = new ImageIcon("images/table2.jpg").getImage();
		
		this.setVisible(true);
		
		timer = new Timer(1,this);
		timer.start();
		this.setOpaque(false);
		
		this.setGraphisme();

	}
	
	
	public JPanel creePanels()
	{
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,3));
		p.add(new JPanelVide());

		JPanel panelCarteCentre = this.creePanelCarteCentre();
		p.add(panelCarteCentre);
		
		p.add(new JPanelVide());

			JPanel panelCarteJoueur = this.creePanelCarteJoueur();
			p.add(panelCarteJoueur);
			p.add(new JPanelVide());
			JPanel panelCarteAdverse = this.creePanelCarteAdverse();
			p.add(panelCarteAdverse);
		
		p.setOpaque(false);
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
		JPanel p = new PanelCarteAdverse();
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
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(this.fond, 0, 0, this.getWidth(), this.getHeight(), this);
		super.paint(g);
	}
	
	
	public class JPanelCarte extends JPanel
	{		

		private static final long serialVersionUID = 2082264326526372498L;
		private Image imgCarte;
		private Image imgCarteOmbre;
		
		private int tailleCarte;
		
		private Carte c;
		private Symbole[] symboles;
		private JLabel[] listLable;
		
		
		
		public JPanelCarte(Carte c)
		{
			super();
			this.imgCarte = Toolkit.getDefaultToolkit().getImage("images/carte.png");
			this.imgCarteOmbre = Toolkit.getDefaultToolkit().getImage("images/carte-ombre.png");

			this.c = c;
			this.symboles = new Symbole[c.getArraySymbole().size()];
			this.listLable = new JLabel[c.getArraySymbole().size()];
			
			int nbligne = 0;
			int nbcolone = 4;
			switch(this.c.getArraySymbole().size())
			{
			case 3:
				nbligne = 2;
				break;
			case 4:
				nbligne = 2;
				break;
			case 6:
				nbligne = 3;
				break;
			case 8:
				nbligne = 3;
				break;
			}
			
			
			this.setLayout(new BorderLayout(0,0)); //DIVISION DU PANEL DU HAUT EN 3 COLONNES
			this.setOpaque(false);
			//this.add(new JLabel()); //COLONNE DE GAUCHE VIDE
			
			JPanel carte = new JPanel(new GridLayout(4,(int)nbligne)); //COLONNE CENTRE GRIDDEE POUR LA CARTE
			carte.setOpaque(false);
			
			int compteur=0;
			
			for(int j=0; j< nbcolone; j+=1) //REMPLISSAGE CARTE AVEC SYMBOLES
			{

				for (int i = 0; i < nbligne; i += 1)
				{			
					
					
					if((j == 0 || j == nbcolone-1) && (i == 0 || i == nbligne-1))
					{
						carte.add(new JLabel());
						continue;
					}
					
					try
					{
						this.symboles[compteur] = this.c.getArraySymbole().get(compteur);
						JLabel t=new MonJLabel(this.symboles[compteur].getImage());
						t.addMouseListener(new SymboleListener(this.symboles[compteur]));
						this.listLable[compteur]=t;
						carte.add(t);
					}
					catch(java.lang.IndexOutOfBoundsException e)
					{
						carte.add(new JLabel());
					}
					compteur++;
					
				}
				
			}
			
			this.add(carte, BorderLayout.CENTER); //AJOUT CARTE AU MILIEU		
			this.add(new JLabelVide("EEEEEEEEEE"), BorderLayout.EAST);//sale
			this.add(new JLabelVide("EEEEEEEEEE"), BorderLayout.WEST);//sale aussi
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
			g.drawImage(imgCarte, (this.getWidth() - this.tailleCarte)/2, 0, this.tailleCarte, this.tailleCarte, this);  //FIXME la carte doit s'adapter proportionnellement au conteneur
			super.paint(g);
			g.drawImage(imgCarteOmbre, (this.getWidth() - this.tailleCarte)/2, 0, this.tailleCarte, this.tailleCarte, this);
		}
		
			
			public void setC(Carte c) {
				this.c = c;
			}
			
		
			public class MonJLabel extends JLabel
			{
				private static final long serialVersionUID = -7666504779479544722L;

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
							this.img=this.img.getScaledInstance(taille, taille,graphisme);
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
						if(PanelJeu.this.mj.interagir(0,this.monSymbole))
						{
							System.out.println("YOUPI");	
							if(PanelJeu.this.mj.isInGame())
							{
								PanelJeu.this.remove(carte);
								PanelJeu.this.add(PanelJeu.this.creePanels(),BorderLayout.CENTER);
							}
							else
								JOptionPane.showMessageDialog(PanelJeu.this, "Bravo tu as gagné",
										"gg", JOptionPane.INFORMATION_MESSAGE);
							PanelJeu.this.mj.faireDormirIA();
						}
						PanelJeu.this.repaint();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
		
				@Override
				public void mouseReleased(MouseEvent arg0) {
					
				}
		
				
			}
		}//fin inner class JPanelCarte
	public class JLabelVide extends JLabel {

		private static final long serialVersionUID = -5982350884964412423L;

		public void paint(Graphics g)
		{
			
		}
		
		public JLabelVide(String m)
		{
			super(m);
		}
	}//fin inner class JLabelVide
	
	public class JPanelVide extends JPanel {
		
		private static final long serialVersionUID = 8916582012263348044L;

		public JPanelVide()
		{
			super();
			this.setOpaque(false);
		}
	}//fin inner class JPanelVide
	
	public class PanelCarteAdverse extends JPanel {

		private static final long serialVersionUID = 5610541885102148879L;
		private Image dosCarte;
		private int tailleCarte;
		
		public PanelCarteAdverse()
		{
			super();
			this.dosCarte = new ImageIcon("images/dosCarte.png").getImage();
			this.tailleCarte = 0;
		}
		
		@Override
		public void paint(Graphics g)
		{
			if(this.tailleCarte == 0)
			{
				int w = this.getWidth();
				int h = this.getHeight();
				if(w >= h)
				{
					this.tailleCarte = h;
				}
				else this.tailleCarte = w;
			}
			
			g.drawImage(this.dosCarte, (this.getWidth() - this.tailleCarte)/2, 0, this.tailleCarte, this.tailleCarte, this);
			super.paint(g);
		}
	}//fin inner class PanelCarteAdverse


	public JPanel getCarte() {
		return carte;
	}
	
	/**
	 * 
	 * @param choix entre 1 et 5, 1 le plus rapide, 5 le plus beau
	 */
	public void setGraphisme()
	{
		String choix=Symbole.lecture("param.txt", 1);
		switch(choix)
		{
		case "1":
			this.graphisme=Image.SCALE_FAST;
			break;
		case "2":
			this.graphisme=Image.SCALE_DEFAULT;
			break;
		case "3":
			this.graphisme=Image.SCALE_REPLICATE;
			break;
		case "4":
			this.graphisme=Image.SCALE_AREA_AVERAGING;
			break;
		case "5":
			this.graphisme=Image.SCALE_SMOOTH;
			break;
		default:
			this.graphisme=Image.SCALE_DEFAULT;
			
		}
	}
	
	

}
