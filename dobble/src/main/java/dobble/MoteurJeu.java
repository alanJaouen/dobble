package dobble;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;

import org.postgresql.Driver;

import dobble.Stats.BddException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public class MoteurJeu implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4484304832690972266L;

	// -------------------------------------------------------------------
	// Attributs :
	/**
	 * l'url d'acces a la bdd
	 */
	private static String url ="jdbc:postgresql://horton.elephantsql.com:5432/sctbmxbq";
	
	/**
	 * le nom d'utilisateur pour se connecter a la bdd
	 */
	private static String user = "sctbmxbq";
	
	/**
	 * le mot de passe pour de connecter a la bdd
	 */
	private static String password ="Npei07vJfUOQOw9SQ4hQrRZM5xJlCzqW";
    
	
	private ArrayList<Joueur> arrayJoueur;
	
	private ArrayList<Carte> cartesCentre;
	
	private Chronometer chrono;
	
	private Mode modeDeJeu;
	
    private boolean inGame;

    private int joueurActif;
    
    
    
    // -------------------------------------------------------------------
    // Constructeurs :
    
    /**
     * Constructeur par champs :
     */
    public MoteurJeu(ArrayList<Joueur> arrayJoueur, ArrayList<Carte> cartesCentre, Chronometer chrono, Mode modeDeJeu) {
    	this.arrayJoueur = arrayJoueur;
    	this.cartesCentre = cartesCentre;
    	this.chrono = chrono;
    	this.modeDeJeu = modeDeJeu;
    	this.inGame = false;
    	this.joueurActif = 0;
    }
    
    /**
     * Constructeur par copie :
     */
    public MoteurJeu(MoteurJeu modele) {
    	this.arrayJoueur = new ArrayList<Joueur>(modele.getArrayJoueur());
    	this.cartesCentre = new ArrayList<Carte>(modele.getCartesCentre());
    	this.chrono = new Chronometer(modele.getChrono());
    	this.modeDeJeu = new Mode(modele.getModeDeJeu());
    	this.inGame = modele.isInGame();
    	this.joueurActif = modele.getJoueurActif();
    }
    
    /**
     * constructeur par defaut
     */
    public MoteurJeu()
    {
    	this.arrayJoueur = null;
    	this.cartesCentre = null;
    	this.chrono = null;
    	this.modeDeJeu = null;
    	this.inGame = false;
    	this.joueurActif = 0;
    }
    
    public MoteurJeu(ArrayList<Joueur> joueurs) {
    	this.arrayJoueur = joueurs;
    	this.cartesCentre = new ArrayList<Carte>();
    	this.chrono = new Chronometer();
    	this.modeDeJeu = new Mode();
    	this.inGame = false;
    	this.joueurActif = 0;
    }

    // -------------------------------------------------------------------
    // Methodes :
    
    /**
     * ecrit l'objet courant dans un fichier
     */
    public void sauvegarder() throws Exception {
    	
    	// On essaie de faire une sauvegarde :
    	try {
    		File fichier =  new File("sauvegarde.txt") ;

    		// Ouverture d'un flux sur un fichier
    		ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;
    	
    		oos.writeObject(this);
    		
    		oos.close();
    	} catch (Exception e) { // Si sauvegarde n'a pas marché :
    		// Emition d'une exception si sauvegarde impossible :
    		throw new Exception("Erreur lors de la sauvegarde"); //TODO a changer
    	}
    }

    /**
     * restaure l'objet courant depuis un fichier
     * @throws Exception 
     */
    public void charger() throws Exception {
    	try{
    	File fichier =  new File("sauvegarde.txt") ;

    	 // ouverture d'un flux sur un fichier
    	ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(fichier)) ;
    			
    	 // désérialization de l'objet
    	MoteurJeu mj = (MoteurJeu)ois.readObject(); //TODO a finir
    	
    	ois.close();
    	}
    	catch (Exception e)
    	{
    		throw new Exception("Erreur lors de la sauvegarde"); //TODO a changer, deg
    	}
    }

    /**
     * Fais interagir un joueur avec la carte centrale en comparant s et le symbole commun
     * si c'est le meme placer la carte du joueur au centre
     * 
     * TODO changer le type de retour de la methode en boolean pour plus tard changer les stats du joueur dynamiquement
     * 
     * @param idJoueur le joueur qui interagit
     * @param s le symbole que le joueur a choisi
     */
    public boolean interagir(int idJoueur,Symbole s) throws Exception
    {
    	// Si symbole commun est trouvé :
    	if (s.equals(Carte.getSymboleCommun(this.arrayJoueur.get(idJoueur).getArrayCartes().get(this.arrayJoueur.get(idJoueur).getArrayCartes().size() - 1),
    			this.cartesCentre.get(cartesCentre.size() - 1)))) {
    		this.cartesCentre.remove(cartesCentre.size() - 1);
    		this.cartesCentre.add(this.arrayJoueur.get(idJoueur).getArrayCartes().get(this.arrayJoueur.get(idJoueur).getArrayCartes().size() - 1));
    		return true;
    	}
    	else // Si le joueur s'est trompé :
    		return false;
    }

    
    public void lancerJeu(ArrayList<Carte> paquet)
    {
    	this.distribuerCarte(paquet);
    	this.inGame = true;
    	this.chrono.start();
    	//TODO lancer un thread pour l'ia
    }

    /**
     * met fin a la partie et met a jour les stats du joueur notament le score max
     */
    public void finPartie() throws BddException {
    	this.inGame = false;
    	this.arrayJoueur.get(this.joueurActif).sauvegarderStats();
    	//TODO mise a jour du score du joueur courant
    	//TODO sauvegarde du profil du joueur sur la bdd (voir classe Joueur)
    }

    
    public void initialiser() {
        
        // Les joueurs sont ajoutés dans le constructeur.
        // On met les scores des joueurs à 0 :
        for (Joueur i: this.arrayJoueur) {
        	i.setScore(0);
        }
        
        // On met le schronometre à 0 :
        this.chrono.start();
    }

    public void ajouterJoueur(Joueur nouveauJoueur) {
    	this.arrayJoueur.add(nouveauJoueur);
    }

    public void supprimerJoueur(Joueur supprimerJoueur) {
    	this.arrayJoueur.remove(supprimerJoueur);
    }

    public void changerJoueurActif(int indice) throws Exception {
    	
    	// Si Indice invalide :
    	if (indice < 0 || indice >= this.arrayJoueur.size())
    		throw new Exception("Indice non valide");
        else // Si l'indice est correct, alors :
        	this.joueurActif = indice;
    }

    /**
     * sert a eviter un ragequit du joueur ( on catchera plus tard les ctrl+c , alt+F4 etc)
     * @throws BddException 
     */
    public void quitter() throws BddException {
    	this.finPartie();
    	//TODO plus tard si le temps ajouter un malus aux leavers.
    }
    
    //TODO distribuer les cartes equitablement avec de l'aleatoire entre les joueurs, avec la carte au centre
    private void distribuerCarte(ArrayList<Carte> paquet){
    	int nb_alea, i = 0;
    	
    	while (paquet.size() > 1) {
    			nb_alea = (int) (Math.random() * paquet.size());     //Pour un entier entre 1 et le nombre de cartes restantes dans le paquet
    			// Ajout de la carte dans le joueur suivant :
    			this.getArrayJoueur().get(i).getArrayCartes().add(paquet.get(nb_alea));
    			// On enleve la carte du paquet :
    			paquet.remove(nb_alea);
    			if (i >= this.getArrayJoueur().size() - 1)
    				i = 0;
    			else
    				i++;
    	}
    	// On met la derniere carte au milieu et on la retire du paquet :
    	this.cartesCentre.add(paquet.get(0));
    	paquet.remove(0);
    	
    }
	
    /**
     * Permet de recuperer une instance de Connection lie a notre bdd
     * @return l'instance de Connection liee a la bdd
     */
    public static Connection connection()
    {
    	/* Chargement du driver JDBC pour plsql */
    	try 
    	{
    	    Class.forName("org.postgresql.Driver");
    	} 
    	catch ( ClassNotFoundException e ) 
    	{
    	   System.out.println("echec du changement de Driver BDD");
    	   e.printStackTrace();
    	}
    	
    	/*connection a la bdd*/
    	try
    	{
    		Connection con = DriverManager.getConnection(url, user, password);//on se connecte
    		return con;
	    } 
    	catch (SQLException ex) 
    	{
    		System.out.println("echec de la connection a la bdd");
    	}
	    	
    	return null;
    }
	
    // -------------------------------------------------------------------
	// Getters & Setters :
    
	public ArrayList<Joueur> getArrayJoueur() {
		return arrayJoueur;
	}
	
	public void setArrayJoueur(ArrayList<Joueur> arrayJoueur) {
		this.arrayJoueur = arrayJoueur;
	}

	public ArrayList<Carte> getCartesCentre() {
		return cartesCentre;
	}

	public void setCartesCentre(ArrayList<Carte> cartesCentre) {
		this.cartesCentre = cartesCentre;
	}

	public Chronometer getChrono() {
		return chrono;
	}

	public void setChrono(Chronometer chrono) {
		this.chrono = chrono;
	}

	public Mode getModeDeJeu() {
		return modeDeJeu;
	}

	public void setModeDeJeu(Mode modeDeJeu) {
		this.modeDeJeu = modeDeJeu;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public int getJoueurActif() {
		return joueurActif;
	}

	public void setJoueurActif(int joueurActif) {
		this.joueurActif = joueurActif;
	}
	
	
	/**
	 * Un thread permettant le jeu de l'ia
	 * @author 
	 *
	 */
	public class ActualiseThread extends Thread {

		private int tpsIA;
		
		private int idJoueur;
		
		/**
		 * Constreucteur par defaut
		 * @param name nom du Thread
		 */
		public ActualiseThread(String name, int tpsIA, int idJoueur)
		{
			super(name);
			this.tpsIA=tpsIA;
			this.idJoueur=idJoueur;
		}
		 
		/**
		 * Lance le thread
		 */
		@Override
		public void run()
		{
			while(MoteurJeu.this.isInGame())
			{
				//TODO faire jouer l'ia
				
				try 
				{
					Thread.sleep(this.getTempsIA());
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		/**
		 * 
		 * @return le temps a sleep pour l'ia en fonction de this.tpsIA +/- 15%
		 */
		private int getTempsIA()
		{
			return (int)Math.random()*(this.tpsIA*(115/100)-this.tpsIA*(85/100)+1)
					+ this.tpsIA*(85/100);
			//int pour le moment,  peut-être à changer en double pour plus de réalisme
		}

	}//fin AnctualiseThread
}
