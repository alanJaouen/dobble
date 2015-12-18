package ihm;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import dobble.Carte;
import java.awt.Dimension;


public class TestJPanelCarte extends JFrame {

	/**
	 * le contentPane principal
	 */
	private JPanel contentPane;

	public static void main(String[] args) {				
		TestJPanelCarte frame = new TestJPanelCarte();
		frame.setVisible(true);


	}
	/**
	 * constructeur par defaut, cree une fenetre d'attente qui ne peux etre fermée que par .dispose
	 */
	public TestJPanelCarte() {
		setType(Type.POPUP);
		setAlwaysOnTop(true);
		setTitle("test affichage carte");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//se ferme seul en cas de probleme
		Dimension ecran=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(
				(int)ecran.getWidth()/2 -(int)ecran.getWidth()/4, 
				(int)ecran.getHeight()/2 -(int)ecran.getHeight()/4,
				700, 
				700);
		this.contentPane = new JPanel();
		this.contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanelCarte panel = new JPanelCarte(new Carte(((int)(Math.random()*100)%54)+1));
		
		this.add(panel);
		setVisible(true);
	}
	
}
