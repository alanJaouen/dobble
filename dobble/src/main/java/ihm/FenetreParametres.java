package ihm;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import dobble.Mode;

public class FenetreParametres extends JFrame {
	
	private Mode mode;
	private JTabbedPane tp;
	
	public FenetreParametres(Mode mode) {
		super("Parametres");
		this.mode = mode;
		this.setSize(750, 600);
		this.setResizable(false);
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
		
		this.tp = new JTabbedPane();
		this.tp.add("General", this.getFirstPane());
		this.tp.add("Avance", this.getSecondPane());
		
		return this.tp;
	}
	
	private JPanel getFirstPane() {
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(1, 2));
		
		// Zone 1 :
		JPanel zone1 = new JPanel();
		zone1.setBorder(BorderFactory.createTitledBorder("Parametres de jeu"));
		pan.add(zone1);
		
		// Zone 2 :
		JPanel zone2 = new JPanel();
		zone2.setBorder(BorderFactory.createTitledBorder("Son et image"));
		pan.add(zone2);
		
		return pan;
	}
	
	private JPanel getSecondPane() {
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(1, 2));
		
		// Zone 1 :
		JPanel zone1 = new JPanel();
		zone1.setBorder(BorderFactory.createTitledBorder("Reglages de l'ordinateur"));
		pan.add(zone1);
		
		// Zone 2 :
		JPanel zone2 = new JPanel();
		zone2.setBorder(BorderFactory.createTitledBorder("Statistiques et scores"));
		pan.add(zone2);
		
		return pan;
	}

	private JPanel getPanelSouth() {
		JPanel pan = new JPanel();
		pan.setLayout(new BorderLayout());
		
		// Boutons :
		// - Retour :
		JButton bouton = new JButton("<- Retour");
		bouton.addActionListener(new RetourListener());
		pan.add(bouton, BorderLayout.WEST);
		
		// - Enregistrer :
		bouton = new JButton("Enregistrer");
		bouton.addActionListener(new EnregistrerListener());
		pan.add(bouton, BorderLayout.EAST);
		
		return pan;
	}
	
	
	// Inner classes :
	
	private class RetourListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog (null, "Tout changement non sauvegardé sera perdu. \nPour sauvegarder, appuyer sur le bouton Enregistrer. \nVoulez-vous vraiment retourner au menu principal ?","Retour", JOptionPane.ERROR_MESSAGE)){
				FenetreParametres.this.dispose();
			}
		}
	}
	
	private class EnregistrerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(FenetreParametres.this, "Les changements ont bien été sauvegardés", "Changements enregistrés", JOptionPane.INFORMATION_MESSAGE);
			FenetreParametres.this.dispose();
		}
	}
}
