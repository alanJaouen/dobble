package dobble;

import java.sql.Connection;
import org.postgresql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public class MoteurJeu implements Serializable {
	
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

    // -------------------------------------------------------------------
    // MÃ©thodes :
    
    /**
     * ecrit l'objet courant dans un fichier
     */
    public void sauvegarder() {
    	
    	try{
    		File fichier =  new File("sauvegarde.txt") ;

    		// ouverture d'un flux sur un fichier
    		ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;
    	
    		oos.writeObject(this);
    	} catch (Exception e)
    	{
    		System.err.println("Erreur lors de la sauvegarde du fichier");
    	}
    	finally oos.close(); //TODO reste à tester!
    }

    /**
     * restaure l'objet courant depuis un fichier
     */
    public void charger() {
    	//TODO
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
    public void interagir(int idJoueur,Symbole s) throws Exception
    {
    	// Si symbole commun est trouvé :
    	if (s.equals(getSymboleCommun(this.arrayJoueurs.get(idJoueur).getArrayCartes.get(this.arrayJoueurs.get(idJoueur).getArrayCartes.size() - 1), 
    			this.cartesCentre.get(cartesCentre.size() - 1)))) {
    		this.cartesCentre.remove(cartesCentre.size() - 1);
    		this.cartesCentre.add(this.arrayJoueurs.get(idJoueur).getArrayCartes.get(this.arrayJoueurs.get(idJoueur).getArrayCartes.size() - 1));
    	}
    	else // Si le joueur s'est trompé :
    		throw new Exception("Symbole incorrect");
    }

    
    public void lancerJeu(ArrayList<Carte> paquet)
    {
    	this.distribuerCarte(paquet);
    	this.inGame = true;
    	//TODO lancer le chonometre
    	//TODO lancer un thread pour l'ia
    }

    /**
     * met fin a la partie et met a jour les stats du joueur notament le score max
     */
    public void finPartie() {
    	this.inGame = false;
    	//TODO mise a jour du score du joueur courant
    	//TODO sauvegarde du profil du joueur sur la bdd (voir classe Joueur)
    }

    
    public void initialiser() {
    	/*
    	 * TODO ajouter les differents joueur + ia en fonction du mode
    	 * TODO mettre les score a 0 des joueurs
    	 * TODO mettre le chonometre a 0
    	 * 
    	 */
    }

    public void ajouterJoueur(Joueur nouveauJoueur) {
    	this.arrayJoueur.add(nouveauJoueur);
    }

    public void supprimerJoueur(Joueur supprimerJoueur) {
    	this.arrayJoueur.remove(supprimerJoueur);
    }

    public void changerJoueurActif(Joueur remplacant, int indice) {
    	this.arrayJoueur.remove(indice);
    	this.ajouterJoueur(remplacant);
    }

    /**
     * sert a eviter un ragequit du joueur ( on catchera plus tard les ctrl+c , alt+F4 etc)
     */
    public void quitter() {
    	//TODO fermer la partie meme si non terminÃ©e ( this.finpartie()), TODO plus tard si le temps ajouter un malus aux leavers.
    }
    
    //TODO distribuer les cartes equitablement avec de l'aleatoire entre les joueurs, avec la carte au centre
    private void distribuerCarte(ArrayList<Carte> paquet){
    	/*
    	int nb_alea, i = 0;
    	
    	while (paquet.size() > 1) {
    			nb_alea = (int) (Math.random() * paquet.size() + 1);     //Pour un entier entre 1 et le nombre de cartes restantes dans le paquet
    			// Ajout de la carte dans le joueur suivant :
    			this.getArrayJoueur().get(i).getArrayCartes().add(paquet.get(nb_alea));
    			// On enlÃ¨ve la carte du paquet :
    			paquet.remove(nb_alea);
    			if (i >= this.getArrayJoueur().size() - 1)
    				i = 0;
    			else
    				i++;
    	}
    	// On met la derniÃ¨re carte au milieu et on la retire du paquet :
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
			return 0;//TODO a changer
		}

	}//fin AnctualiseThread
}
