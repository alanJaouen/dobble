package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import dobble.Joueur;
import dobble.MoteurJeu;
import dobble.Symbole;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.Vector;


public class FenetreStat extends JFrame {

	private static final long serialVersionUID = 3780582428111849380L;
	private DefaultTableModel model;
	private JPanel contentPane;
	private JTable table;
	private FenetreWait chargement;
	private Joueur nomJoueur;



	/**
	 * Constructeupar défaut, créer la fenetre
	 */
	public FenetreStat(Joueur j) {
		super("Statistiques");
		this.nomJoueur=j;
		this.setIconImage(Symbole.getIcon().getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension ecran=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(
				(int)ecran.getWidth()/2 -(int)ecran.getWidth()/4, 
				(int)ecran.getHeight()/2 -(int)ecran.getHeight()/4,
				(int)ecran.getWidth()/2, 
				(int)ecran.getHeight()/2);
		

		this.contentPane = new JPanel();
		setContentPane(contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));
		
		
		JPanel panelSud = getPanelSud();
		this.contentPane.add(panelSud, BorderLayout.SOUTH);
		
		
		JPanel panelCentre = getPanelCentre();
		this.contentPane.add(panelCentre, BorderLayout.CENTER);
		

		
		this.charger();
		this.setVisible(true);
	}
	
	/**
	 * lance un thread pour télécharger les données depuis la base de donnée
	 */
	private void charger()
	{
		this.chargement=new FenetreWait();
		ChargerThread t=new ChargerThread("t");
		t.start();
	}
	
	/**
	 * Génère le paneau sud de la fenetre
	 * @return le paneau sud de la fenetre
	 */
	private JPanel getPanelSud()
	{
		JPanel panelSud = new JPanel();
		panelSud.setLayout(new BorderLayout(0, 0));
		
		JButton btnRevenirAuMenu = new JButton("Revenir au menu");
		panelSud.add(btnRevenirAuMenu, BorderLayout.EAST);
		btnRevenirAuMenu.addActionListener(new EcouteurMenu());
		
		
		return panelSud;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JPanel getPanelCentre()
	{
		JPanel panelCentre = new JPanel();
		String entete[]={"Nom","Niveau","Temps de jeu","Temps de réaction","Meilleur Score"};
		String rows[][]={};
		
		this.model=new DefaultTableModel(rows,entete)
		{
			private static final long serialVersionUID = -1166268462744362023L;
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		panelCentre.setLayout(new BorderLayout(0, 0));
		this.table=new JTable(model);
		table.setDefaultRenderer(Object.class, new MonCellRenderer(this.nomJoueur.getNom()));
		
		 TableRowSorter trs = new TableRowSorter(model);

			class IntComparator implements Comparator {
	            public int compare(Object o1, Object o2) {
	                Integer int1 = (Integer)o1;
	                Integer int2 = (Integer)o2;
	                return int1.compareTo(int2);
	            }

	            public boolean equals(Object o2) {
	                return this.equals(o2);
	            }
	        }
	        
			class IntegertpsComparator implements Comparator {
	            public int compare(Object o1, Object o2) {
	            	Integertps int1 = (Integertps)o1;
	            	Integertps int2 = (Integertps)o2;
	                return int1.compareTo(int2);
	            }

	            public boolean equals(Object o2) {
	                return this.equals(o2);
	            }
	        }

	        trs.setComparator(1, new IntComparator());
	        trs.setComparator(2, new IntegertpsComparator());
	        trs.setComparator(3, new IntegertpsComparator());
	        trs.setComparator(4, new IntComparator());

	        table.setRowSorter(trs);

		//this.table.setAutoCreateRowSorter(true);
		this.table.getTableHeader().setReorderingAllowed(false);
		panelCentre.add(new JScrollPane(this.table));
		return panelCentre;
	}
	public class ChargerThread extends Thread {

		/**
		 * Constreucteur par defaut
		 * @param name nom du Thread
		 */
		public ChargerThread(String name)
		{
			super(name);
		}
		 
		/**
		 * Lance le thread
		 */
		@SuppressWarnings("deprecation")
		@Override
		public void run()
		{
			FenetreStat.this.chargement.setLabel("Connection a la base de donnee");
			Connection con;
			int cpt=0;
			do
			{
				con =MoteurJeu.connection();
				if(cpt==3)
				{
					FenetreStat.this.chargement.dispose();
					JOptionPane.showMessageDialog(FenetreStat.this, 
					         "Impossible de se connecter a la base de donnee.\nVeuillez verifier votre connection internet"
							+"et si le probleme persiste contatez le gestionnaire de la base de donnee.",
					         " Erreur de comunication ",
					         JOptionPane.ERROR_MESSAGE);
					this.stop();
				}
				cpt++;
				FenetreStat.this.chargement.setLabel("Connection a la base de donnee (Essai n°"+cpt+"..)");
			}while( con==null);
			
			Statement st=null;
	    	ResultSet rs=null;
	    	
	    	try
	    	{
	    		FenetreStat.this.chargement.setLabel("Recuperation des profils");
	    		st = con.createStatement();//creation stattement
	    		rs = st.executeQuery("SELECT nom,lvl,tpsj,tpsr,score FROM joueur "); //on enregistre le resultat de la requete
	    		
	    		int count=0;
	    		while(rs.next())
	    		{
	    			Vector<Object> vec= new Vector<Object>();
		    		for(int i=1;i<=5;i++)
		    		{
		    			Object str;
		    			switch(i)
		    			{
		    			case 2:
		    				str=new Integer(rs.getInt(2));
		    				break;
		    			case 3:
		    				str=new Integertps(rs.getInt(3));
		    				break;
		    			case 4:
		    				str=new Integertpsreac(rs.getInt(4));
		    				break;
		    			case 5:
		    				str=new Integer(rs.getInt(5));
		    				break;
		    			default:
		    				str=rs.getString(i);
		    			
		    			}
		    			vec.addElement(str);
		    		}
					FenetreStat.this.model.addRow(vec);
					count++;
					FenetreStat.this.chargement.setLabel(count+" profils recuperes..");
	    		}
		    	
	    	}
	    	catch (SQLException ex) 
	    	{
	    		JOptionPane.showMessageDialog(FenetreStat.this, 
				         "Une erreur et survenue pendant la recuperation des profils, veuillez reessayer ulterieurement"
						+"Si le probleme persiste contatez le gestionnaire de la base de donnee.",
				         " Erreur de transmition ",
				         JOptionPane.ERROR_MESSAGE);
				FenetreStat.this.chargement.dispose();
				this.stop();
	    		
	    	}
	    	finally
	    	{
	    		 try 
	    		 {
	    			 if (rs != null) 
	    				 rs.close();
		             if (st != null)
		                 st.close();
		             if (con != null)
		                 con.close();
	    		 } 
	    		 catch (SQLException ex) 
	    		 {
	    			 JOptionPane.showMessageDialog(FenetreStat.this, 
					         "Une erreur et survenue lors de la fermeture de la connection,"
							+"Si cette erreur persiste contatez le gestionnaire de la base de donnee au plus vite.",
					         " Erreur de fin de transmition ",
					         JOptionPane.ERROR_MESSAGE);
					FenetreStat.this.chargement.dispose();
		         }
	    		 FenetreStat.this.chargement.dispose();
	    	}
		}

	}//fin AnctualiseThread
	
	public class EcouteurMenu implements ActionListener 
	{
		/**
		 * une action est effectuée
		 */
		public void actionPerformed(ActionEvent e)
		{
			
			
			FenetreStat.this.dispose();
			
		}    //fin ecouteur menu
	}
	
	@SuppressWarnings("rawtypes")
	class Integertps implements Comparable {
		
		private int valeur;

        public boolean equals(Object o2) {
            return this.equals(o2);
        }

		public int compareTo(Object arg0) {
			Integertps o= (Integertps) arg0;
			if(this.valeur>o.getValeur())
				return 1;
			if(this.valeur<o.getValeur())
				return -1;
			return 0;
		}
		
		public Integertps(int v)
		{
			super();
			this.valeur=v;
		}
		
		public int getValeur()
		{
			return this.valeur;
		}
		
		public String toString()
		{
			return Integer.toString((int)valeur/60)+" h "+Integer.toString((int)valeur%60)+" min";
		}
    }
	
	class Integertpsreac extends Integertps{
		
		
		public Integertpsreac(int v)
		{
			super(v);
		}
		
		public String toString()
		{
			return Integer.toString((int)super.valeur)+" sec";
		}
    }
	
	public void dispose()
	{
		new FenetreMenuPrincipal(this.nomJoueur);
		super.dispose();
	}
	
	
}
