package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dobble.Carte;
import dobble.Symbole;

public class JPanelCarte extends JPanel {
	
	private Image imgCarte;
	private Image imgCarteOmbre;
	
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
		
		System.out.println("sur "+nbligne+" lignes");
		
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
					System.out.println("case blanche");
					continue;
				}

				this.symboles[compteur] = this.c.getArraySymbole().get(compteur);
				
				System.out.println(compteur);
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
			
		g.drawImage(imgCarte, 0, 0,this.getWidth(),this.getHeight(), this);
		super.paint(g);
		g.drawImage(imgCarteOmbre, 0, 0,this.getWidth(),this.getHeight(), this);
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
		
				return (int)(((this.getHeight())-(((int) (Math.random()*1000))%(this.getHeight()/3)))*1.5f);
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
				
			}
	
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	
			
		}
	}
