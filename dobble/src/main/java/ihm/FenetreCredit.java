package ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Window.Type;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import dobble.Chronometer;

public class FenetreCredit extends CUndecoratedResizeableDialog {

	private JPanel contentPane;
	
	private Chronometer chrono;
	
	private String[] texte;
	
	private Font font;
	
	private static final int COEF=200;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FenetreCredit frame = new FenetreCredit();
		frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 */
	public FenetreCredit() {
		super();
		this.chrono=new Chronometer();
		this.chrono.start();
		this.texte=FenetreCredit.getTexte();
		
		this.font=null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("monofonto.ttf"));
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 300);
		
		new ActualiseThread("actualisation ihm");
	}
	
	private static String[] getTexte()
	{
		String[] str= new String[5];
		
		str[0]="Dobble";
		str[1]="";
		str[2]="Projet Tutauré";
		str[3]="IUT de Villetaneuse";
		str[4]="2015-2016";
		
		return str;
	}
	
	
	public void paint(Graphics g)
	{

		//super.paint(g);
		int w= this.getWidth();
		int h=this.getHeight();
		float f=w/20;
		g.setFont(font.deriveFont(f));
		
		g.setColor(Color.black);
		g.fillRect(0, 0, w, h);
		
		
		g.setColor(Color.white);;
		String str2=""+h/2+","+(h-(chrono.getMilliseconds())/FenetreCredit.COEF);
		g.drawString(str2, 50,50);
		
		for(int i=0; i<FenetreCredit.this.texte.length;i++)
		{
			g.drawString(FenetreCredit.this.texte[i],(w-(FenetreCredit.this.texte[i].length()*(w/40)))/2 , h-((chrono.getMilliseconds())/FenetreCredit.COEF)+i*(w/20));
		}
		
			
	}
	
	public Chronometer getChrono() {
		return chrono;
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
			this.start();
		}
		 
		/**
		 * Lance le thread
		 */
		@SuppressWarnings("deprecation")
		@Override
		public void run()
		{
			while(true)
			{
				if((60000-chrono.getMilliseconds())/FenetreCredit.COEF < 0)
				{
					FenetreCredit.this.dispose();
					this.stop();
				}
					
				try 
				{
					Thread.sleep(100);
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				FenetreCredit.this.repaint();
			}
		}

	}//fin AnctualiseThread

}
