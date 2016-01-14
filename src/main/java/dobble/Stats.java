/*					CeCILL licence
 *
 * Copyright or © or Copr. Klervi BLESCHET, Eva TERZAGO, Adrien BOIZARD, Kyllian GAUTIER, Alan JAOUEN
 *
 *  - b_klervi@hotmail.fr
 *  - evalukario@hotmail.fr
 *  - adria.boizard@live.fr
 *  - kyllian.gt@hotmail.fr
 *  - alan.jaouen.96@gmail.com 
 *  
 *  This software is a computer program whose purpose is to play to the Dobble game.
 *
 *  This software is governed by the CeCILL license under French law and abiding by the rules of distribution of free software. You can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at the following URL "http://www.cecill.info".
 *
 *  As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors have only limited
 *  liability.
 *
 *  In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to manipulate, and that also
 *  therefore means that it is reserved for developers and experienced professionals having in-depth computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the same conditions as regards security.
 *
 *  The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its terms.
 */

package dobble;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe modelisant les statistiques d'un joueur
 * test55
 * @author Alan JAOUEN
 *
 */
public class Stats {

	/**
	 * Le niveau du joueur
	 */
    private int niveau;
    
    /**
     * l'experience du joueur
     */
    private int exp;

   
    /**
     * le temps de jeu du joueur en min
     */
    private int tempsDeJeu;

   
    /**
     * le temps de reaction du joueur en ms
     */
    private int tpsReaction;

  
    /**
     * Le meilleur score du joueur
     */
    private int meilleurScore;

    /**
     * constructeur par dï¿½faut
     */
    public Stats()
    {
    	this(1,0,0,0,0);
    }
    
    /**
     * constructeur champ a champ
     * @param unNiveau le niveau du joueur
     * @param unExp l'esperience du joueur
     * @param unTempsDeJeu le temps de jeu du joueur
     * @param unTempsDeReaction le temps de reaction du joueur
     * @param unMeilleurScore le meilleur score du joueur
     */
    public Stats(int unNiveau, int unExp, int unTempsDeJeu, int unTempsDeReaction, int unMeilleurScore)
    {
    	super();
    	this.setNiveau(unNiveau);
    	this.setExp(unExp);
    	this.setTempsDeJeu(unTempsDeJeu);
    	this.setTpsReaction(unTempsDeReaction);
    	this.setMeilleurScore(unMeilleurScore);
    }
    
    /**
     * Constructeur par copie depuis la bdd
     * @param nom le nom du joueur dans la bdd
     * @param mdp le mot de passe du joueur dans la bdd
     * @throws BddException si un probleme survient lors de la communication avec la bdd
     */
    public Stats( String nom, String mdp) throws BddException
    {
    	this();
    	this.charger( nom, mdp);
    }

    /**
     * constructeur par copie en local
     * @param unStats l'instance de Stats a copier
     */
	public Stats (Stats unStats)
    {
    	this(unStats.getNiveau(),unStats.getExp(),unStats.getTempsDeJeu(),unStats.getTpsReaction(),
    			unStats.getMeilleurScore());
    }
    
	/**
	 * permet de changer les attribut de l'objet courant pour correspondre avec ceux stokes en ligne
	 * @param nom id dans la bdd des stats a recuperer
	 * @param mdp mot de passe associe aux stats a recuperer sur la bdd
	 * @throws BddException si un probleme survient lors de la communication avec la bdd
	 */
    public void charger(String nom, String mdp) throws BddException 
    {
		Stats st= this.getBddStat(nom, mdp);
		this.setNiveau(st.getNiveau());
		this.setExp(st.getExp());
		this.setMeilleurScore(st.getMeilleurScore());
		this.setTempsDeJeu(st.getTempsDeJeu());
		this.setTpsReaction(st.getTpsReaction());
	}
    

    /**
     * sauvegarde les stats dans la base de donnée
     * @param nom id de connection
     * @param mdp mot de passe de connection
     * @throws BddException si un probleme survient lors de la communication avec la bdd
     */
    public void sauvegarder(String nom, String mdp) throws BddException 
	{
		/* Verification */
    	if(nom.trim().equals("")||mdp.trim().equals(""))
    		throw new BddException("Champ(s) vide(s)");
    	
    	/* anti sql injection*/
    	if(Joueur.verifsql(nom) || Joueur.verifsql(mdp))
    		throw new BddException("injection sql detectee et bloquee");

    		
    	
    	Connection con= MoteurJeu.connection();
    	
    	if (con==null)//si echec de la connection
    		throw new BddException("Echec de la connection ï¿½ la BDD");
    	
    	Statement st=null;
    	
    	try
    	{
    		st = con.createStatement();//creation stattement
    		
	    	if(st.executeUpdate("UPDATE joueur "
	    			+ "SET lvl="+ this.getNiveau()+","
	    			+"exp = "+this.getExp()+","
	    			+"tpsj = "+this.getTempsDeJeu()+","
	    			+"tpsr = "+this.getTpsReaction()+","
	    			+"score = "+this.getMeilleurScore()
	    			+"WHERE nom = '"+nom+"' AND mdp='"+Crypt.encrypte(mdp)+"'") !=1)
	    		throw new BddException("nom ou Mot de passe incorrect");
	    	
    	}
    	catch (SQLException ex) 
    	{
    		ex.printStackTrace();
    		throw new BddException("erreur lors de la communication avec la BDD");
    	}
    	finally
    	{
    		 try 
    		 {
	             if (st != null)
	                 st.close();
	             if (con != null)
	                 con.close();
    		 } 
    		 catch (SQLException ex) 
    		 {
    			 throw new BddException("erreur lors de la fermeture de la connection a la BDD");
	         }
    	}
	}
    
    
    
    /**
     * Creer une instance de {@link dobble.Stats} corespondant aux donnï¿½e stokees sur la bdd avec protection par id et mot de passe
     * @param nom id de connection
     * @param mdp mot de passe de connection
     * @return l'instance de Stats générée a partir des donnée de la bdd
     * @throws BddException si un probleme survient lors de la communication avec la bdd
     */
    public Stats getBddStat(String nom, String mdp) throws BddException
    {
    	/* Verification */
    	if(nom.trim().equals("")||mdp.trim().equals(""))
    		throw new BddException("Champ(s) vide(s)");

    	/* anti sql injection*/
    	if(Joueur.verifsql(nom) || Joueur.verifsql(mdp))
    		throw new BddException("injection sql detectee et bloquee");
    	
    	Connection con= MoteurJeu.connection();
    	
    	if (con==null)//si echec de la connection
    		throw new BddException("Echec de la connection ï¿½ la BDD");
    	
    	Statement st=null;
    	ResultSet rs=null;
    	Stats stats;
    	mdp=Crypt.encrypte(mdp);
    	
    	try
    	{
    		st = con.createStatement();//creation stattement
    		rs = st.executeQuery("SELECT * FROM joueur WHERE nom='"+nom+"'"); //on enregistre le resultat de la requete
    		
    		if(rs.next())
    		{
	    		if(mdp.equals(rs.getString("mdp")))
	    		{
	    			stats=new Stats(rs.getInt("lvl"),
	    					rs.getInt("exp"),
	    					rs.getInt("tpsj"),
	    					rs.getInt("tpsr"),
	    					rs.getInt("score"));
	    		}
	    		else throw new BddException("Mot de passe incorect");
    		}
    		else throw new BddException("nom incorect");
	    	
    	}
    	catch (SQLException ex) 
    	{
    		throw new BddException("erreur lors de la communication avec la BDD");
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
    			 throw new BddException("erreur lors de la fermeture de la connection a la BDD");
	         }
    	}
    	return stats;
    }
    
    
	/**
	 * acesseur de niveau
	 * @return la valeur de l'atribut niveau
	 * @see dobble.Stats#niveau
	 */
	public int getNiveau() {
		return niveau;
	}

	/**
	 * mutateur de niveau
	 * @param niveau la valeur a mettre dans l'atribut niveau
	 * @see dobble.Stats#niveau
	 */
	public void setNiveau(int niveau) {
		if(niveau>0)
			this.niveau = niveau;
	}

	/**
	 * acesseur de exp
	 * @return la valeur de l'atribut exp
	 * @see dobble.Stats#exp
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * mutateur de exp
	 * @param exp la valeur a mettre dans l'atribut exp
	 * @see dobble.Stats#exp
	 */
	public void setExp(int exp) {
		if(exp>0)
			this.exp = exp;
	}

	/**
	 * acesseur de tempsDeJeu
	 * @return la valeur de l'atribut tempsDeJeu
	 * @see dobble.Stats#tempsDeJeu
	 */
	public int getTempsDeJeu() {
		return tempsDeJeu;
	}

	/**
	 * mutateur de tempsDeJeu
	 * @param tempsDeJeu la valeur a mettre dans l'atribut tempsDeJeu
	 * @see dobble.Stats#tempsDeJeu
	 */
	public void setTempsDeJeu(int tempsDeJeu) {
		if(tempsDeJeu >0)
			this.tempsDeJeu = tempsDeJeu;
	}

	/**
	 * acesseur de tpsReaction
	 * @return la valeur de l'atribut tpsReaction
	 * @see dobble.Stats#tpsReaction
	 */
	public int getTpsReaction() {
		return tpsReaction;
	}

	/**
	 * mutateur de tpsReaction
	 * @param tpsReaction la valeur a mettre dans l'atribut tpsReaction
	 * @see dobble.Stats#tpsReaction
	 */
	public void setTpsReaction(int tpsReaction) {
		if(tpsReaction >0)
			this.tpsReaction = tpsReaction;
	}

	/**
	 * acesseur de meilleurScore
	 * @return la valeur de l'atribut meilleurScore
	 * @see dobble.Stats#meilleurScore
	 */
	public int getMeilleurScore() {
		return meilleurScore;
	}

	/**
	 * mutateur de meilleurScore
	 * @param meilleurScore la valeur a mettre dans l'atribut meilleurScore
	 * @see dobble.Stats#meilleurScore
	 */
	public void setMeilleurScore(int meilleurScore) {
		if(meilleurScore>0)
			this.meilleurScore = meilleurScore;
	}
	
	
	
	/** 
	 * tansforme l'objet courant en chaine de caracteres
	 * @see java.lang.Object#toString()
	 * @return une chaine de caracteres corespondant a l'objet courant
	 */
	@Override
	public String toString() {
		return "Stats [niveau=" + niveau + ", exp=" + exp + ", tempsDeJeu=" + tempsDeJeu + ", tpsReaction="
				+ tpsReaction + ", meilleurScore=" + meilleurScore + "]";
	}

	
	/**
	 * Compare l'objet courant avec un autre
	 * @return true si les objets sont égaux, false sinon
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stats other = (Stats) obj;
		if (exp != other.exp)
			return false;
		if (meilleurScore != other.meilleurScore)
			return false;
		if (niveau != other.niveau)
			return false;
		if (tempsDeJeu != other.tempsDeJeu)
			return false;
		if (tpsReaction != other.tpsReaction)
			return false;
		return true;
	}

	

	/**
	 * 
	 * @author Alan JAOUEN
	 *
	 */
	public class BddException extends Exception
	{

		private static final long serialVersionUID = 1L;
		
		/**
		 * constructeur champ a champ
		 * @param msg le message a faire remonte
		 */
		public BddException(String msg)
		{
			super(msg);
		}
	}


}
