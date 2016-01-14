package ihm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import dobble.Symbole;

public class FenetreJeu extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7773249857163022532L;
	private Container contentPane;
	private PanelJeu lepanel;
	private JMenu menuQuitter;
	private MoteurJeu mj;
	private ActualiseThread thread;
	
	public FenetreJeu(MoteurJeu jeu) {
		super();
		this.setIconImage(Symbole.getIcon().getImage());
		this.mj=jeu;
		
		
		this.setTitle("Dobble");
		Toolkit t =Toolkit.getDefaultToolkit();
		double w = (t.getScreenSize().getWidth()) * 0.98;
		double h = ((double)((2.0/3.0)*w) - (13.0/100.0)*w) * 0.98; //1/2 de h = 1/3 de w - 13% de w
		
		if(h >= t.getScreenSize().getHeight()) //Il faut readapter proportionnellement si ca depasse
		{
			h = t.getScreenSize().getHeight();
			w = (double)((3.0/2.0)*h + (13.0/100.0)*h);
		}
		
		this.setSize((int)w,(int)h);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		
		contentPane=this.getContentPane();
		contentPane.setLayout(new BorderLayout(0,0));
		
		this.lepanel=new PanelJeu(jeu);
		this.setJMenuBar(this.creeMenuBar());
		
		thread= new ActualiseThread("actualisation de l ihm");
		thread.start();
		
		this.contentPane.add(lepanel, BorderLayout.CENTER);
		
		this.setLocationRelativeTo(null);
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
		quitter.addActionListener(new MenuQuitterActionListener());
		menu.add(quitter);
		return menu;
	}
	

	
	class MenuQuitterActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			FenetreJeu.this.dispose();
			try {
				FenetreJeu.this.mj.quitter();
			} catch (BddException e) {
				JOptionPane.showMessageDialog(FenetreJeu.this, "Erreur inattendu:\n"+e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	
	
	
	public void dispose()
	{
		this.mj.getArrayJoueur().get(0).setPenalite(0); //jcalc
		FenetreJeu.this.thread.fin();
		new FenetreMenuPrincipal(this.mj.getArrayJoueur().get(this.mj.getJoueurActif()));
		super.dispose();
	}
	
	/**
	 * Un thread permettant de rafraichir l'affichage de l'ihm
	 * @author Alan JAOUEN
	 *
	 */
	public class ActualiseThread extends Thread {

		public boolean continu;
		/**
		 * Constreucteur par defaut
		 * @param name nom du Thread
		 */
		public ActualiseThread(String name)
		{
			super(name);
			this.continu=true;
		}
		 
		public void fin() {
			this.continu=false;
			
		}

		/**
		 * Lance le thread
		 */
		@Override
		public void run()
		{
			while(this.continu)
			{

				if (FenetreJeu.this.mj.isInGame())
				{
					if(FenetreJeu.this.mj.getNeedUpdate())
					{
						mj.updatedone();
						lepanel.remove(lepanel.getCarte());
						lepanel.add(lepanel.creePanels(lepanel.carteJ),BorderLayout.CENTER);
					}
				}
				else
				{
					FenetreWait f=null;
					if(FenetreJeu.this.mj.getNeedUpdate())
					{
						JOptionPane.showMessageDialog(FenetreJeu.this, "Dommage tu as perdu",
							"DOMMAGE", JOptionPane.INFORMATION_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(FenetreJeu.this, "Bravo tu as gagné",
								"BRAVO", JOptionPane.INFORMATION_MESSAGE);
					f=new FenetreWait();
					try {
						
						f.setLabel("sauvegarde en cours");
						FenetreJeu.this.mj.finPartie();
						f.dispose();
					} catch (BddException e) {	
						f.dispose();
						JOptionPane.showMessageDialog(FenetreJeu.this, e.getMessage(),
								"Erreur", JOptionPane.ERROR_MESSAGE);
					}

					
					FenetreJeu.this.dispose();
					
					this.continu=false;
				}
				
				try 
				{
					Thread.sleep(200);
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
					
		}
	}//fin ActualiseThread

}
	
