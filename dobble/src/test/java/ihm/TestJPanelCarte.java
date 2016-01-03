package ihm;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import dobble.Carte;
import dobble.Symbole;

import java.awt.Dimension;
import java.util.ArrayList;


public class TestJPanelCarte extends JFrame {

	/**
	 * le contentPane principal
	 */
	private JPanel contentPane;

	public static void main(String[] args) throws Exception {				
		TestJPanelCarte frame = new TestJPanelCarte();
		frame.setVisible(true);


	}
	/**
	 * constructeur par defaut, cree une fenetre d'attente qui ne peux etre fermée que par .dispose
	 * @throws Exception 
	 */
	public TestJPanelCarte() throws Exception {
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
		
		ArrayList<Symbole> list=new ArrayList<Symbole>();
		
		for(int i=0;i<=14;i+=1)//TODO need multiple de 4 a changer
			list.add(new Symbole(((int)(Math.random()*100)%57)+1));
		
		Carte c= new Carte(1,8);
		c.setArraySymbole(list);
		
		//JPanelCarte panel = new JPanelCarte(c);
		
		JPanelCarte panel = new JPanelCarte(new Carte(((int)(Math.random()*100)%54)+1 , 8));
		
		this.add(panel);
		setVisible(true);
	}
	
}
