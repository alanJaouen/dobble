package ihm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window.Type;

public class FenetreWait extends JFrame {

	private JPanel contentPane;

	private JLabel label;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreWait frame = new FenetreWait("test");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FenetreWait(String titre) {
		setType(Type.POPUP);
		setUndecorated(true);
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle(titre);
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
	
	public void setLabel(String str)
	{
		this.label.setText(str);
	}

}
