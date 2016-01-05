package ihm;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import dobble.Mode;

public class FenetreParametres extends JFrame {
	
	
	// Attributs :
	private Mode mode;
	private JLabel label_enregistrement;
	private JButton bouton_enregistrer;
	
	
	
	
	public FenetreParametres(Mode mode) {
		super("Parametres");
		this.mode = mode;
		this.setSize(550, 400);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.initialise();
		this.setVisible(true);
	}
	
	private void initialise() {
		this.setLayout(new BorderLayout());
		
		// Ajout du panel centre :
		this.add(this.getPanelCenter(), BorderLayout.CENTER);
		
		// Ajout du panel des boutons retour et enregistrer :
		this.add(this.getPanelSouth(), BorderLayout.SOUTH);
	}

	private JComponent getPanelCenter() {
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(2, 1)); // hauteur, largeur
		
		// Zone 1 (jeu) :
		pan.add(this.getZone1());
		
		// Zone 2 (graphismes et sons) :
		pan.add(this.getZone2());
		
		return pan;
	}
	
	private JPanel getZone1() {
		JPanel pan = new JPanel();
		pan.setBorder(BorderFactory.createTitledBorder("Réglages du jeu"));
		pan.setLayout(new GridLayout(1, 2)); // hauteur, largeur
		
		// Nombre de symboles :
		
			JPanel pan_symboles = new JPanel();
			pan_symboles.setLayout(new GridLayout(2, 1)); // hauteur, largeur
			
			// Label symboles :
			pan_symboles.add(new JLabel("Nombre de symboles par carte"));
			
			// Radio boutons :
				JRadioButton rb_3 = new JRadioButton("3");
				JRadioButton rb_4 = new JRadioButton("4");
				JRadioButton rb_6 = new JRadioButton("6");
				JRadioButton rb_8 = new JRadioButton("8");
				rb_6.setSelected(true);
				
				ButtonGroup bg = new ButtonGroup();
				
				bg.add(rb_3);
				bg.add(rb_4);
				bg.add(rb_6);
				bg.add(rb_8);
				
				JPanel pan_radio = new JPanel();
				pan_radio.setLayout(new GridLayout(2, 2)); // hauteur, largeur
				
				pan_radio.add(rb_3);
				pan_radio.add(rb_4);
				pan_radio.add(rb_6);
				pan_radio.add(rb_8);
			
			pan_symboles.add(pan_radio);
			
		
		pan.add(pan_symboles);
		
		// Difficulté IA :
		
			JPanel pan_ia = new JPanel();
			pan_ia.setLayout(new GridLayout(2, 1)); // hauteur, largeur
			
			// Label :
			pan_ia.add(new JLabel("Difficulté de l'IA")); //TODO changer le titre de ce reglage
			
			
			// Slider :
			
				JSlider slider_difficulte = new JSlider(JSlider.HORIZONTAL, 0, 15, 7); // constante, min, max, valeur actuelle
				slider_difficulte.setMajorTickSpacing(5);
				slider_difficulte.setMinorTickSpacing(1);
				slider_difficulte.setPaintTicks(true);
				slider_difficulte.setPaintLabels(true);
				
			pan_ia.add(slider_difficulte);
				
			
			
		pan.add(pan_ia);		
		
		return pan;
	}
	
	private JPanel getZone2() {
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(1, 2)); // hauteur, largeur
		
		// Graphismes
		
			JPanel pan_graph = new JPanel();
			pan_graph.setLayout(new GridLayout(2, 1)); // hauteur, largeur
			
			// Label
			pan_graph.add(new JLabel("Qualité de l'interface et du jeu"));
			
			// ComboBox :
				String[] comboString = { "Basse", "Modérée", "Normale", "Bonne", "Haute" };
				JComboBox cb = new JComboBox(comboString);
				cb.setSelectedIndex(2);
				JPanel pan_combo = new JPanel();
				pan_combo.add(cb);
			
			pan_graph.add(pan_combo);
		
		// Sons et voix
		
			JPanel pan_son = new JPanel();
			pan_son.setLayout(new GridLayout(2, 2)); // hauteur, largeur
			
			// Label sons
			pan_son.add(new JLabel("Activer les sons"));
			
			// Label Voix
			pan_son.add(new JLabel("Activer une voix"));
		
		
			// Radio sons
				JRadioButton rb_oui = new JRadioButton("Oui");
				JRadioButton rb_non = new JRadioButton("Non");
				
				rb_oui.setSelected(true);
				
				ButtonGroup bg1 = new ButtonGroup();
				bg1.add(rb_oui);
				bg1.add(rb_non);
				
				JPanel pan_radio1 = new JPanel();
				pan_radio1.setLayout(new GridLayout(2, 1)); //hauteur, largeur
				pan_radio1.add(rb_oui);
				pan_radio1.add(rb_non);
				
			
			pan_son.add(pan_radio1);
		
		
			// Radio voix :
				JRadioButton rb_mute = new JRadioButton("Aucune");
				JRadioButton rb_alan = new JRadioButton("Alan");
				JRadioButton rb_moussa = new JRadioButton("Moussa");
				
				rb_mute.setSelected(true);
				
				ButtonGroup bg2 = new ButtonGroup();
				bg2.add(rb_mute);
				bg2.add(rb_alan);
				bg2.add(rb_moussa);
				
				JPanel pan_radio2 = new JPanel();
				pan_radio2.setLayout(new GridLayout(3, 1)); //hauteur, largeur
				pan_radio2.add(rb_mute);
				pan_radio2.add(rb_alan);
				pan_radio2.add(rb_moussa);
			
			pan_son.add(pan_radio2);
		
		
		// Ajout au panel pan
		pan.add(pan_graph);
		pan_graph.setBorder(BorderFactory.createTitledBorder("Graphismes"));
		pan.add(pan_son);
		pan_son.setBorder(BorderFactory.createTitledBorder("Sons et voix"));
		
		
		return pan;
	}

	private JPanel getPanelSouth() {
		JPanel pan = new JPanel();
		pan.setLayout(new BorderLayout());
		
		// Boutons :
		
			// - Retour :
			JButton bouton = new JButton("<   Retour");
			bouton.addActionListener(new RetourListener());
			pan.add(bouton, BorderLayout.WEST);
			
			// - Label :
			JLabel label = new JLabel("Aucun changement");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			pan.add(label, BorderLayout.CENTER);
			this.label_enregistrement = label;
			
			// - Enregistrer :
			bouton = new JButton("Enregistrer");
			bouton.addActionListener(new EnregistrerListener());
			pan.add(bouton, BorderLayout.EAST);
			this.bouton_enregistrer = bouton;
			
		
		return pan;
	}
	
	public void enregistrer() throws Exception {
		JOptionPane.showMessageDialog(FenetreParametres.this, "Les changements ont bien été sauvegardés", "Changements enregistrés", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	// Inner classes :
	
	private class RetourListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog (FenetreParametres.this, "Tout changement non sauvegardé sera perdu. \nPour sauvegarder, appuyer sur le bouton Enregistrer. \nVoulez-vous vraiment retourner au menu principal ?","Retour", JOptionPane.ERROR_MESSAGE)){
				FenetreParametres.this.dispose();
			}
		}
	}
	
	private class EnregistrerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				FenetreParametres.this.enregistrer();
			} catch (Exception ex) {
				
			}
			FenetreParametres.this.dispose();
		}
	}
}
