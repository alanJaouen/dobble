package dobble;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dobble.Stats.BddException;

public class Joueur {
	
	private ArrayList<Carte> arrayCartes;
	
	private Stats stats;
	
    private String nom;
    
    private String mdp;

    private int score;
    
    public Joueur(){
    	this(new ArrayList<Carte>(), new Stats(), "admin", 0, "admin");
    }
        
    public Joueur(ArrayList<Carte> arrayCartes, Stats stats, String nom, int score, String mdp) {
		super();
		this.arrayCartes = arrayCartes;
		this.stats = stats;
		this.nom = nom;
		this.score = score;
		this.mdp = mdp;
	}

    public Joueur(String nom, String mdp) throws BddException {
    	this.nom = nom;
    	this.mdp = mdp;
    	if (!this.chargerStats())
    	{
    		throw this.stats.new BddException("Echec du chargement des stats");
    	}
    }
    
    /**
     * vérifie que la chaine ne contient pas de caraceère "sensible" pour la base de donnee
     * @param str la chaine a tester
     * @return true ou false selon le test
     */
    public static boolean verifsql(String str)
    {
    	 return Pattern.compile(";--").matcher(str).find() || //TODO definir les regles regex pour verifier sql
    			 Pattern.compile(";--").matcher(str).find() ;
    }
    
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
	    			System.out.println("nom disponible, essai de creation d'un joueur");
	    			st.execute("INSERT into joueur VALUES ('" +nom+ "', 1, 0, 0, 0, 0,'"+Crypt.encrypte(mdp)+"');");//on execute le insert
	    			System.out.println("valeurs inseree");
	    			rs.close();
	    			rs = st.executeQuery("SELECT * FROM joueur WHERE nom='"+nom+"'"); //on enregistre le resultat de la requete
	    			
	    			if(!rs.next())//si toujours pas de resultat
	    				throw s.new BddException("probleme lors de l'INSERT");
	    			else System.out.println("joueur cree");
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
     * @return
     * @throws BddException 
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
    		st.execute("DELETE FROM joueur WHERE nom='"+nom+"'"); //on enregistre le resultat de la requete
    		rs = st.executeQuery("SELECT * FROM joueur WHERE nom='"+nom+"'"); //on enregistre le resultat de la requete
    		if(!rs.next())//si pas de resultat
    		{
    			System.out.println("joueur supprime avec succes");
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

    public String voirStats() {
    	return this.stats.toString();
    }

	public ArrayList<Carte> getArrayCartes() {
		return this.arrayCartes;
	}

	public void setArrayCartes(ArrayList<Carte> arrayCartes) {
		this.arrayCartes = arrayCartes;
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public String getMdp() {
		return this.mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public boolean chargerStats() throws BddException {
			this.stats= new Stats();
			this.stats.charger(this.nom, this.mdp);
			return true;
	}

	public boolean sauvegarderStats() throws BddException {
			this.stats.sauvegarder(this.nom, this.mdp);
			return true;
	}
}
