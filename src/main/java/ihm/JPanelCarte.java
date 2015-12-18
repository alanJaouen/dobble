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
	private Image carteOmbre;
	
	private JPanelCartenotfull content;
	
	public JPanelCarte(Carte c)
	{
		super();
		this.setLayout(new BorderLayout());
		this.content=new JPanelCartenotfull(c);
		this.add(this.content,BorderLayout.CENTER);
		this.imgCarte = Toolkit.getDefaultToolkit().getImage("images/carte.png");
		this.carteOmbre = Toolkit.getDefaultToolkit().getImage("images/carte-ombre.png");
		this.setVisible(true);
		this.repaint();
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(imgCarte, 0, 0,this.getWidth(),this.getHeight(), this);
		this.content.paint(g);
		g.drawImage(carteOmbre, 0, 0,this.getWidth(),this.getHeight(),  this);
	}
	
	public class JPanelCartenotfull extends JPanel
	{
		private Carte c;
		private Symbole[] symboles;
		
		public JPanelCartenotfull(Carte c)
		{
			super();
			this.c = c;
			this.symboles = new Symbole[8];
			
			this.setLayout(new GridLayout(4,3));
			this.setBackground(Color.BLUE);
			this.setOpaque(false);
			
			for (int i = 0; i < 8; i += 1)
			{			
				this.symboles[i] = this.c.getArraySymbole().get(i);
				
				
				if(i == 0 || i == 1 || i == 7)
				{
					this.add(new JLabel());
				}
			
				MonJLabel t=new MonJLabel(this.symboles[i].getImage());
				t.addMouseListener(new SymboleListener(this.symboles[i]));
				this.add(t);
				this.repaint();
			}
			
			this.setVisible(true);
		}
		
		public void setC(Carte c) {
			this.c = c;
		}
		
	
		public void paint(Graphics g)
		{	
			super.paint(g);			
			
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
	
		public class SymboleListener implements MouseListener{
			
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
	
}
