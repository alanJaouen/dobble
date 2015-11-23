package dobble;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public class MoteurJeu {
	
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
    
    /*
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
    
    /*
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

    // -------------------------------------------------------------------
    // Méthodes :
    
    
    public void sauvegarder() {
    }

    public void charger() {
    }

    public void interagir() {
    }

    public void lancerJeu(ArrayList<Carte> paquet) {
    	this.distribuerCarte(paquet);
    	this.inGame = true;
    }

    public void finPartie() {
    	this.inGame = false;
    }

    public void initialiser() {
    }

    public void ajouterJoueur(Joueur nouveauJoueur) {
    	this.arrayJoueur.add(nouveauJoueur);
    }

    public void supprimerJoueur(Joueur supprimerJoueur) {
    	this.arrayJoueur.remove(supprimerJoueur);
    }

    public void changerJoueur(Joueur remplacant, int indice) {
    	this.arrayJoueur.remove(indice);
    	this.ajouterJoueur(remplacant);
    }

    public void quitter() {
    }
    
    //TODO distribuer les cartes equitablement avec de l'aleatoire entre les joueurs, avec la carte au centre
    private void distribuerCarte(ArrayList<Carte> paquet){
    	/*
    	int nb_alea, i = 0;
    	
    	while (paquet.size() > 1) {
    			nb_alea = (int) (Math.random() * paquet.size() + 1);     //Pour un entier entre 1 et le nombre de cartes restantes dans le paquet
    			// Ajout de la carte dans le joueur suivant :
    			this.getArrayJoueur().get(i).getArrayCartes().add(paquet.get(nb_alea));
    			// On enlève la carte du paquet :
    			paquet.remove(nb_alea);
    			if (i >= this.getArrayJoueur().size() - 1)
    				i = 0;
    			else
    				i++;
    	}
    	// On met la dernière carte au milieu et on la retire du paquet :
    	this.cartesCentre.add(paquet.get(0));
    	paquet.remove(0);
    	*/
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
}
