package ihm;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Cursor;
import java.awt.Dimension;

/**
 * Une fenetre de chargement infini affichant un texte
 * @author Alan JAOUEN
 *
 */
public class FenetreWait extends JFrame {

	
	private static final long serialVersionUID = -7853301710810551886L;

	/**
	 * le contentPane principal
	 */
	private JPanel contentPane;

	/**
	 * le texte de la fenetre
	 */
	private JLabel label;


	/**
	 * constructeur par defaut, cree une fenetre d'attente qui ne peux etre fermée que par .dispose
	 */
	public FenetreWait() {
		setType(Type.POPUP);
		setUndecorated(true);
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("chargement en cour");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//se ferme seul en cas de probleme
		Dimension ecran=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(
				(int)ecran.getWidth()/2 -(int)ecran.getWidth()/8, 
				(int)ecran.getHeight()/2 -(int)ecran.getHeight()/40,
				(int)ecran.getWidth()/4, 
				(int)ecran.getHeight()/20);
		this.contentPane = new JPanel();
		this.contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		this.contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setForeground(Color.GREEN);
		
		JPanel t= new JPanel();
		t.setLayout(new BorderLayout());
		t.add(progressBar, BorderLayout.CENTER);
		
		panel.add(t, BorderLayout.CENTER);
		
		JPanel text = new JPanel();
		panel.add(text, BorderLayout.NORTH);
		text.setLayout(new FlowLayout());
		
		this.label = new JLabel("Chargement");
		text.add(this.label);
		setVisible(true);
	}
	
	/**
	 * permet de changer le texte de la fenetre ainsi que son titre (visible dans la bare des tâche)
	 * @param str le texte a afficher
	 */
	public void setLabel(String str)
	{
		this.label.setText(str);
		this.setTitle(str);
	}

}
