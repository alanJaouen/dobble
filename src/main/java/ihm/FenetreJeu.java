package ihm;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import dobble.MoteurJeu;

public class FenetreJeu extends JFrame{
	

	private MoteurJeu mj;
	private JMenuBar menubar;
	private JMenu menuQuitter;
	
	public FenetreJeu()
	{
		this.setTitle("Dobble");
		Toolkit t =Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();
		this.setSize(d);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		menubar = this.creeMenuBar();
		this.setJMenuBar(menubar);
		
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
		JMenu menu = new JMenu();
		JMenuItem quitter = new JMenuItem("quitter");
		menu.add(quitter);
		return menu;
	}
	
	
	public static void main(String[] args) {

		FenetreJeu j= new FenetreJeu();
	}

}
