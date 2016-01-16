package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
import javax.swing.event.ChangeEvent;

import dobble.Joueur;
import dobble.Mode;
import dobble.Symbole;
import javafx.scene.control.RadioButton;

public class FenetreParametres extends JFrame {

	// Attributs :.
	private Joueur j;
	private Mode mode;
	private int[] valeurs_initiales;

	private JLabel label_enregistrement;
	private JButton bouton_enregistrer;
	private boolean changement;

	private JComboBox combo_graphismes;
	private ArrayList<JRadioButton> groupe_son;
	private ArrayList<JRadioButton> groupe_voix;
	private ArrayList<JRadioButton> groupe_nombre_symboles;
	private JSlider slider_diffuculte;

	public FenetreParametres(Mode mode, Joueur j) {
		super("Parametres");
		this.j=j;
		this.setVisible(true);
		this.setIconImage(Symbole.getIcon().getImage());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.mode = mode;
		this.valeurs_initiales = charger_parametres();
		this.setSize(550, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.initialise();
		this.setValeurs();
		this.reset_changements();
	}

	private void initialise() {
		this.setLayout(new BorderLayout());
		
		this.addWindowListener(new FenetreListener());

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
		rb_3.addItemListener(new ChangementListener());
		JRadioButton rb_4 = new JRadioButton("4");
		rb_4.addItemListener(new ChangementListener());
		JRadioButton rb_6 = new JRadioButton("6");
		rb_6.addItemListener(new ChangementListener());
		JRadioButton rb_8 = new JRadioButton("8");
		rb_8.addItemListener(new ChangementListener());

		ButtonGroup bg = new ButtonGroup();
		bg.add(rb_3);
		bg.add(rb_4);
		bg.add(rb_6);
		bg.add(rb_8);
		
		this.groupe_nombre_symboles = new ArrayList<JRadioButton>();
		this.groupe_nombre_symboles.add(rb_3);
		this.groupe_nombre_symboles.add(rb_4);
		this.groupe_nombre_symboles.add(rb_6);
		this.groupe_nombre_symboles.add(rb_8);

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
		pan_ia.add(new JLabel("Niveau de difficulté")); // TODO changer le titre
														// de ce reglage

		// Slider :
		JSlider slider_difficulte = new JSlider(JSlider.HORIZONTAL, 0, 15, 7); // constante,
																				// min,
																				// max,
																				// valeur actuelle
		slider_difficulte.setMajorTickSpacing(5);
		slider_difficulte.setMinorTickSpacing(1);
		slider_difficulte.setPaintTicks(true);
		slider_difficulte.setPaintLabels(true);
		slider_difficulte.addChangeListener(new ChangementListener());
		this.slider_diffuculte = slider_difficulte;

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
		cb.addItemListener((ItemListener) new ChangementListener());
		this.combo_graphismes = cb;
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
		JRadioButton rb_oui = new JRadioButton("Non");
		rb_oui.addItemListener(new ChangementListener());
		JRadioButton rb_non = new JRadioButton("Oui");
		rb_non.addItemListener(new ChangementListener());

		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(rb_oui);
		bg1.add(rb_non);
		
		this.groupe_son = new ArrayList<JRadioButton>();
		this.groupe_son.add(rb_oui);
		this.groupe_son.add(rb_non);

		JPanel pan_radio1 = new JPanel();
		pan_radio1.setLayout(new GridLayout(2, 1)); // hauteur, largeur
		pan_radio1.add(rb_non);
		pan_radio1.add(rb_oui);
		

		pan_son.add(pan_radio1);

		// Radio voix :
		JRadioButton rb_mute = new JRadioButton("Aucune");
		rb_mute.addItemListener(new ChangementListener());
		JRadioButton rb_alan = new JRadioButton("Alan");
		rb_alan.addItemListener(new ChangementListener());
		JRadioButton rb_moussa = new JRadioButton("Moussa");
		rb_moussa.addItemListener(new ChangementListener());

		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(rb_mute);
		bg2.add(rb_alan);
		bg2.add(rb_moussa);
		
		this.groupe_voix = new ArrayList<JRadioButton>();
		this.groupe_voix.add(rb_mute);
		this.groupe_voix.add(rb_alan);
		this.groupe_voix.add(rb_moussa);

		JPanel pan_radio2 = new JPanel();
		pan_radio2.setLayout(new GridLayout(3, 1)); // hauteur, largeur
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
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.RED);
		pan.add(label, BorderLayout.CENTER);
		this.label_enregistrement = label;

		// - Enregistrer :
		bouton = new JButton("Enregistrer");
		bouton.addActionListener(new EnregistrerListener());
		bouton.setEnabled(false);
		pan.add(bouton, BorderLayout.EAST);
		this.bouton_enregistrer = bouton;

		return pan;
	}

	public void changement() {
		this.changement = true;
		this.label_enregistrement.setText("Changement non enregistré !");
		this.bouton_enregistrer.setEnabled(true);
	}

	public void enregistrer() {

		// Ecrire valeurs dans param.txt :

		int[] data = { 0, 0, 0, 0, 0 };

		data[0] = this.combo_graphismes.getSelectedIndex() + 1;
		data[1] = this.indexRadioButton(this.groupe_son);
		data[3] = 17 - (this.slider_diffuculte.getValue() + 1);
		data[4] = this.indexRadioButton(this.groupe_voix);
		
		switch (this.indexRadioButton(this.groupe_nombre_symboles)) {
		case 0:
			data[2] = 3;
			break;
		case 1:
			data[2] = 4;
			break;
		case 2:
			data[2] = 6;
			break;
		case 3:
			data[2] = 8;
			break;
		}
		
		
		
		File f = new File("param.txt");
		boolean probleme = false;

		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));

			for (int d : data) {
				pw.println(d);
			}

			pw.close();
		} catch (IOException exception) {
			JOptionPane.showMessageDialog(FenetreParametres.this,
					"Une erreur s'est produite lors de l'enregistrement.\nVeuillez reesayer plus tard.", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			probleme = true;
		}

		// Si aucun probleme alors :
		if (!probleme) {
			JOptionPane.showMessageDialog(FenetreParametres.this, "Les changements ont bien été sauvegardés",
					"Changements enregistrés", JOptionPane.INFORMATION_MESSAGE);
			this.reset_changements();
		}
	}

	private int[] charger_parametres() {
		int[] valeurs = { 0, 0, 0, 0, 0};

		try {
			for (int i = 0; i < 5; i++) {
				valeurs[i] = Integer.parseInt(Symbole.lecture("param.txt", i + 1));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erreur : une erreur à été trouvée lors du chargement de vos\nparametres. Veuillez reesayer plus tard.",
					"Erreur de chargement", JOptionPane.ERROR_MESSAGE);
			this.dispose();
			new FenetreMenuPrincipal(); // TODO sauvegarde utilisateur (Alan)
		}

		valeurs[0]--;
		valeurs[3]--;
		valeurs[3] = 15 - valeurs[3];

		return valeurs;
	}

	private void setValeurs() {
		this.combo_graphismes.setSelectedIndex(this.valeurs_initiales[0]);
		this.groupe_son.get(this.valeurs_initiales[1]).setSelected(true);
		this.slider_diffuculte.setValue(this.valeurs_initiales[3]);
		this.groupe_voix.get(this.valeurs_initiales[4]).setSelected(true);
		
		switch(this.valeurs_initiales[2]) {
		case 3:
			this.groupe_nombre_symboles.get(0).setSelected(true);
			break;
		case 4:
			this.groupe_nombre_symboles.get(1).setSelected(true);
			break;
		case 6:
			this.groupe_nombre_symboles.get(2).setSelected(true);
			break;
		case 8:
			this.groupe_nombre_symboles.get(3).setSelected(true);
			break;
		}
	}

	private void reset_changements() {
		this.changement = false;
		this.label_enregistrement.setText("");
		this.bouton_enregistrer.setEnabled(false);
	}
	
	private int indexRadioButton(ArrayList<JRadioButton> tab_buttons) {
		int index = 0;
		
		if (tab_buttons.size() > 0) {
			for (int i = 0; i < tab_buttons.size(); i++)
				if (tab_buttons.get(i).isSelected()) {
					index = i;
					break;
				}
		} else {
			return -1;
		}
		return index;
	}

	// Inner classes :

	private class ChangementListener implements javax.swing.event.ChangeListener, ItemListener {
		public void stateChanged(ChangeEvent arg0) {
			FenetreParametres.this.changement();
		}

		public void itemStateChanged(ItemEvent e) {
			FenetreParametres.this.changement();
		}
	}
	
	
	private class FenetreListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			if (FenetreParametres.this.changement) {
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(FenetreParametres.this,
						"Tout changement non sauvegardé sera perdu. \nPour sauvegarder, appuyer sur le bouton Enregistrer. \nVoulez-vous vraiment retourner au menu principal ?",
						"Retour", JOptionPane.ERROR_MESSAGE)) {
					new FenetreMenuPrincipal();
					FenetreParametres.this.dispose();
				}
			} else {
				new FenetreMenuPrincipal();
				FenetreParametres.this.dispose();
			}
		}
	}
	
	
	private class RetourListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (FenetreParametres.this.changement) {
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(FenetreParametres.this,
						"Tout changement non sauvegardé sera perdu. \nPour sauvegarder, appuyer sur le bouton Enregistrer. \nVoulez-vous vraiment retourner au menu principal ?",
						"Retour", JOptionPane.ERROR_MESSAGE)) {
					new FenetreMenuPrincipal();
					FenetreParametres.this.dispose();
				}
			} else {
				new FenetreMenuPrincipal(FenetreParametres.this.j);
				FenetreParametres.this.dispose();
			}
		}
	}

	private class EnregistrerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			FenetreParametres.this.enregistrer();
		}
	}
}
