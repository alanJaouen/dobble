package dobble;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;

import dobble.Stats.BddException;

public class Joueur implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8131165726505362546L;

	/**
	 * deck du joueur
	 */
	private ArrayList<Carte> arrayCartes;
	
	/**
	 * statistique du joueur
	 */
	private Stats stats;
	
	/**
	 * nom du joueur
	 */
    private String nom;
    
    /**
     * mot de passe du joueur
     */
    private String mdp;

    /**
     * score du joueur
     */
    private int score;
    
    /**
     * compteur de penalites du joueur
     */
    private int penalite;
    
    /**
     * constructeur par defaut
     */
    public Joueur(){
    	this(new ArrayList<Carte>(), new Stats(), "admin", 0, "admin");
    }
        
    /**
     * constructeur champ a champ
     * @param arrayCartes deck du joueur
     * @param stats stats du joueur
     * @param nom nom du joueur
     * @param score score du joueur
     * @param mdp mot de passe du joueur
     */
    public Joueur(ArrayList<Carte> arrayCartes, Stats stats, String nom, int score, String mdp) {
		super();
		this.arrayCartes = arrayCartes;
		this.stats = stats;
		this.nom = nom;
		this.score = score;
		this.mdp = mdp;
		this.penalite = 0;
	}

    /**
     * constructeur en ligne 
     * @param nom id dans la base de donnée
     * @param mdp mot de passe dans la base de donnée
     * @throws BddException si il y a eu un probleme lors de la connection
     */
    public Joueur(String nom, String mdp) throws BddException {
    	this.nom = nom;
    	this.mdp = mdp;
    	this.arrayCartes=new ArrayList<Carte>();
    	if (!this.chargerStats())
    	{
    		throw this.stats.new BddException("Echec du chargement des stats");
    	}
    	this.penalite = 0;
    }
    
    /**
     * vérifie que la chaine ne contient pas de caraceère "sensible" pour la base de donnee
     * @param str la chaine a tester
     * @return true ou false selon le test
     */
    public static boolean verifsql(String str)
    {
    	 return Pattern.compile(";--").matcher(str).find() || //TODO definir les regles regex pour verifier sql
    			 Pattern.compile(";").matcher(str).find() ;
    }
    
    /**
     * Creer un nouveau joueur dans la base de donnée
     * @param nom id a entrer dans la bdd
     * @param mdp mdp a entrer dans la bdd
     * @throws BddException en cas de probleme lors de la communication ou de champ incorect
     */
    public static void nouveauJoueur(String nom, String mdp) throws BddException{
    	Stats s= new Stats();
    	/* Verification */
    	if(nom.trim().equals("")||mdp.trim().equals(""))
    		throw s.new BddException("Champ(s) vide(s)");
    	
    	/* anti sql injection*/
    	if(Joueur.verifsql(nom) || Joueur.verifsql(mdp))
    		throw s.new BddException("injection sql detectee et bloquee");
    	
    	Connection con= MoteurJeu.connection();
    	
    	if (con==null)//si echec de la connection
    		throw s.new BddException("Echec de la connection a la BDD");
    	
    	Statement st=null;
    	ResultSet rs=null;
    	
    	try
    	{
   
    		st = con.createStatement();//creation stattement
    		rs = st.executeQuery("SELECT * FROM joueur WHERE nom='"+nom+"'"); //on enregistre le resultat de la requete
    		if(!rs.next())//si pas de resultat
    		{
	    			st.execute("INSERT into joueur VALUES ('" +nom+ "', 1, 0, 0, 0, 0,'"+Crypt.encrypte(mdp)+"');");//on execute le insert
	    			rs.close();
	    			rs = st.executeQuery("SELECT * FROM joueur WHERE nom='"+nom+"'"); //on enregistre le resultat de la requete
	    			
	    			if(!rs.next())//si toujours pas de resultat
	    				throw s.new BddException("probleme lors de l'INSERT");
    		}
    		else throw s.new BddException("nom deja utilise");
	    	
    	}
    	catch (SQLException ex) 
    	{
    		ex.printStackTrace();
    		throw s.new BddException("erreur lors de la communication avec la BDD");
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
    			 throw s.new BddException("erreur lors de la fermeture de la connection a la BDD");
	         }
    	}
    }
    
    /**
     * supprime le joueur courant de la bdd
     * @return succes ou echec de l'opération
     * @throws BddException si problemme de comunication avec la bdd
     */
    public boolean supprimerJoueur() throws BddException{
    	
    	Connection con= MoteurJeu.connection();
    	
    	if (con==null)//si echec de la connection
    		throw this.stats.new BddException("Echec de la connection a la BDD");
    	
    	Statement st=null;
    	ResultSet rs=null;
    	
    	try
    	{
   
    		st = con.createStatement();//creation stattement
    		st.execute("DELETE FROM joueur WHERE nom='"+nom+"'"); //on execute la requete
    		rs = st.executeQuery("SELECT * FROM joueur WHERE nom='"+nom+"'"); //on enregistre le resultat de la requete
    		if(!rs.next())//si pas de resultat
    		{
	    		return true;
    		}
    		else throw this.stats.new BddException("erreur lors du DELETE");
	    	
    	}
    	catch (SQLException ex) 
    	{
    		ex.printStackTrace();
    		throw this.stats.new BddException("erreur lors de la communication avec la BDD");
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
    			 throw this.stats.new BddException("erreur lors de la fermeture de la connection a la BDD");
	         }
    	}
    	
    }

    /**
     * permet d'acceder aux stats du joueur au format texte
     * @return les stats au format texte
     */
    public String voirStats() {
    	return this.stats.toString();
    }

    /**
     * accesseur liste de carte du joueur
     * @return la liste de carte du joueur
     */
	public ArrayList<Carte> getArrayCartes() {
		return this.arrayCartes;
	}

	/**
	 * mutateur liste de carte du joueur
	 * @param arrayCartes la liste de carte a set
	 */
	public void setArrayCartes(ArrayList<Carte> arrayCartes) {
		this.arrayCartes = arrayCartes;
	}

    /**
     * accesseur stats du joueur
     * @return les stats du joueur
     */
	public Stats getStats() {
		return stats;
	}
	
	/**
	 * mutateur statistique du joueur
	 * @param stats les stats a set
	 */
	public void setStats(Stats stats) {
		this.stats = stats;
	}

	/**
	 * accesseur nom du joueur
	 * @return le nom du joueur
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * mutateur nom du joueur
	 * @param nom nom a set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * accesseur score du joueur
	 * @return le score du joueur
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * mutateur score du joueur
	 * @param score le score a set
	 */
	public void setScore(int score) {
		this.score = score;
		if(score>this.getStats().getMeilleurScore())
			this.getStats().setMeilleurScore(score);
	}
	
	/**
	 * accesseur penalites du joueur
	 * @return penalite du joueur
	 */
	public int getPenalite() {
		return this.penalite;
	}
	
	/**
	 * mutateur penalites du joueur
	 * @param la penalite a set
	 */
	public void setPenalite(int penalite) {
		this.penalite = penalite;
	}

	
	/**
	 * charge les stats du joueur courant depuis la bdd
	 * @return true si aucun probleme, null sinon
	 * @throws BddException si probleme lors de la communication avec la bdd
	 */
	public boolean chargerStats() throws BddException {
			this.stats= new Stats();
			this.stats.charger(this.nom, this.mdp);
			return true;
	}

	/**
	 * sauvegarde les stats du joueur courant sur la bdd
	 * @return true si aucun probleme, null sinon
	 * @throws BddException si probleme lors de la communication avec la bdd
	 */
	public boolean sauvegarderStats() throws BddException {
			this.stats.sauvegarder(this.nom, this.mdp);
			return true;
	}
}
