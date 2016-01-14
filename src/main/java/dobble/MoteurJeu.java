package dobble;

import ihm.JavaAudioPlayer;
import java.io.Serializable;
import java.sql.Connection;
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
    
	/**
	 * la liste des joueur de la partie
	 */
	private ArrayList<Joueur> arrayJoueur;
	
	/**
	 * la pile de carte au centre
	 */
	private ArrayList<Carte> cartesCentre;
	
	/**
	 * le chronometre de la partie
	 */
	private Chronometer chrono;
	
	/**
	 * le mode de jeu de la partie
	 */
	private Mode modeDeJeu;
	
	/**
	 * vrai si une partie est en cours, faux sinon
	 */
    private boolean inGame;

    /**
     * id du joueur humain
     */
    private int joueurActif;
    
    /**
     * indique aux vue si l'état a changer
     */
    private boolean needupdate;
    
    /**
     * variable responsable du jeu de l'ia
     */
    private boolean IAdoisDormir;
    

    /**
     * tableau de chonometre par joueur
     */
    private Chronometer[] tpsr;
    
    
    // -------------------------------------------------------------------
    // Constructeurs :
    
    /**
     * construvteur champs a champs
     * @param arrayJoueur la liste  de joueur
     * @param cartesCentre la carte au centre
     * @param chrono un chronometre
     * @param modeDeJeu le mode de jeu
     */
    public MoteurJeu(ArrayList<Joueur> arrayJoueur, ArrayList<Carte> cartesCentre, Chronometer chrono, Mode modeDeJeu) {
    	this.arrayJoueur = arrayJoueur;
    	this.cartesCentre = cartesCentre;
    	this.chrono = chrono;
    	this.modeDeJeu = modeDeJeu;
    	this.inGame = false;
    	this.joueurActif = 0;
    	this.tpsr=new Chronometer[arrayJoueur.size()];
    	for(int i=0; i<tpsr.length;i++)
    	{
    		tpsr[i]=new Chronometer();
    	}
    }
    
    /**
     * constructeur par copie
     * @param modele le moteurJeu a copier
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
    
    /**
     * constructeur par liste de joueur avec mode par defaut
     * @param joueurs la liste de joueur
     */
    public MoteurJeu(ArrayList<Joueur> joueurs, Mode m) {
    	this.arrayJoueur = joueurs;
    	this.cartesCentre = new ArrayList<Carte>();
    	this.chrono = new Chronometer();
    	this.modeDeJeu = m;
    	this.inGame = false;
    	this.joueurActif = 0;
    	this.needupdate=false;
    	this.tpsr=new Chronometer[arrayJoueur.size()];
    	for(int i=0; i<tpsr.length;i++)
    	{
    		tpsr[i]=new Chronometer();
    	}
    }

    // -------------------------------------------------------------------
    // Methodes :


	/**
	 * Fais interagir un joueur avec la carte centrale en comparant s et le symbole commun
     * si c'est le meme placer la carte du joueur au centre
     * 
     * @param idJoueur le joueur qui interagit
     * @param s le symbole que le joueur a choisi
	 * @return succes ou echec de l'opération
	 */
    public boolean interagir(int idJoueur,Symbole s)
    {

    	
    	// Si symbole commun est trouvé :
    	Symbole correct = Carte.getSymboleCommun(this.arrayJoueur.get(idJoueur).getArrayCartes().get(this.arrayJoueur.get(idJoueur).getArrayCartes().size() - 1),
    			this.cartesCentre.get(cartesCentre.size() - 1));
    	if (s.equals(correct)) {
    			      
    		this.cartesCentre.add(this.arrayJoueur.get(idJoueur).getArrayCartes().get(this.arrayJoueur.get(idJoueur).getArrayCartes().size() - 1));
    		this.arrayJoueur.get(idJoueur).getArrayCartes().remove(
    				this.arrayJoueur.get(idJoueur).getArrayCartes().size() - 1);
    		
    		//La voix énonce le symbole correct si option activée et si voix non muettes
    		if(Symbole.lecture("param.txt", 2).equals("1") && !(Symbole.lecture("param.txt", 5).equals("0")))
    		{
    			JavaAudioPlayer t = new JavaAudioPlayer(correct.getId(), idJoueur);
    			t.start();
    		}
    		
    		this.testfin(); //update
    		if(idJoueur != this.joueurActif)
    			this.needupdate=true;
    		
    		/*score*/
    		try{
    		this.arrayJoueur.get(idJoueur).setScore(this.arrayJoueur.get(idJoueur).getScore()+( (int) 100/this.tpsr[idJoueur].getSeconds()));
    		}
    		catch(ArithmeticException e)
    		{
    			//vide
    		}
    		/*temps de reaction*/
    		this.arrayJoueur.get(idJoueur).getStats().setTpsReaction(
    				(this.tpsr[idJoueur].getSeconds()+this.arrayJoueur.get(idJoueur).getStats().getTpsReaction())/2
    				);
    		
    		this.tpsr[idJoueur].start();
    		return true;
    	}
    	else // Si le joueur s'est trompé :
    	{
    		if(idJoueur != 0)
    		{
    			return false;
    		}
        	
    		this.ajoutePenalite(this.arrayJoueur.get(this.joueurActif)); //penalite +1
    		if (this.arrayJoueur.get(this.joueurActif).getPenalite() >= 5) //Si plus de 5 penalites
    		{
    			this.needupdate = true; //on fait gagner l'adversaire #jcalc
    			this.inGame = false;
    		}
    		return false;
    	}
    		
    }

    /**
     * verifie si la parie est terminée
     */
    private void testfin() {
		for(Joueur i : this.arrayJoueur)
		{
			if(i.getArrayCartes().size() == 0)
			{
				this.inGame=false;
			}
		}
		
	}

    /**
     * lance la partie avec le paque passer en parametre
     * @param paquet paquet de carte a utiliser
     */
	public void lancerJeu(ArrayList<Carte> paquet)
    {
    	this.distribuerCarte(paquet);
    	this.inGame = true;
    	this.chrono.start();
    	for(int i=0; i<tpsr.length; i++)
    		tpsr[i].start();
    	
    	new ActualiseThread("thread actualisation",this.modeDeJeu.getTempsIA()*1000,1);

    	
    }
	
	/**
	 * ajoute une penalite au joueur
	 * @param j 
	 */
	public void ajoutePenalite(Joueur j)
	{
		j.setPenalite(j.getPenalite() + 1);
	}

    /**
     *  met fin a la partie et met a jour les stats du joueur notament le score max sur le serveur
     * @throws BddException si probleme de comunication avec la bdd
     */
    public void finPartie() throws BddException {
    	this.inGame = false;
    	if(this.arrayJoueur.get(this.joueurActif).getStats().getExp() == 80)
    	{
    		this.arrayJoueur.get(this.joueurActif).getStats().setNiveau(this.arrayJoueur.get(this.joueurActif).getStats().getNiveau()+1);
    		this.arrayJoueur.get(this.joueurActif).getStats().setExp(0);
    	}
    	else
    		this.arrayJoueur.get(this.joueurActif).getStats().setExp(this.arrayJoueur.get(this.joueurActif).getStats().getExp()+20);
    	
    	this.arrayJoueur.get(this.joueurActif).getStats().setTempsDeJeu(this.arrayJoueur.get(this.joueurActif).getStats().getTempsDeJeu()+this.chrono.getMinutes());
    	this.arrayJoueur.get(this.joueurActif).sauvegarderStats();
    }

    /**
     * initialise le moteur jeu
     */
    public void initialiser() {
        
        // Les joueurs sont ajoutés dans le constructeur.
        // On met les scores des joueurs à 0 :
        for (Joueur i: this.arrayJoueur) {
        	i.setScore(0);
        }
        
        // On met le schronometre à 0 :
        this.chrono.start();
    }

    /**
     * ajoute un joueur a la partie
     * @param nouveauJoueur le joueur a ajouter a la partie
     */
    public void ajouterJoueur(Joueur nouveauJoueur) {
    	this.arrayJoueur.add(nouveauJoueur);
    }

    /**
     * supprime un joueur de la partie
     * @param supprimerJoueur le joueur a supprimer
     */
    public void supprimerJoueur(Joueur supprimerJoueur) {
    	this.arrayJoueur.remove(supprimerJoueur);
    }

    /**
     * définie le joueur actif
     * @param indice id du joueur actif
     * @throws Exception si l'indice n'est pas valide
     */
    public void changerJoueurActif(int indice) throws Exception {
    	
    	// Si Indice invalide :
    	if (indice < 0 || indice >= this.arrayJoueur.size())
    		throw new Exception("Indice non valide");
        else // Si l'indice est correct, alors :
        	this.joueurActif = indice;
    }

    /**
     * sert a eviter un ragequit du joueur
     * @throws BddException si un probleme survient lors de la communication avec la bdd
     */
	public void quitter() throws BddException {
		this.arrayJoueur.get(this.joueurActif).getStats().setExp(this.arrayJoueur.get(this.joueurActif).getStats().getExp()-20);
    	this.finPartie();
    }
    
	/**
	 * distribu les carte entre les joueurs
	 * @param paquet paquet de carte a distribuer
	 */
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
    
    /**
     * accesseur liste de joueur
     * @return la liste de joueurs
     */
	public ArrayList<Joueur> getArrayJoueur() {
		return arrayJoueur;
	}

	/**
	 * accesseur pile de carte au centre
	 * @return la pile de crte au centre
	 */
	public ArrayList<Carte> getCartesCentre() {
		return cartesCentre;
	}


	/**
	 * accesseur chronometre de la partie
	 * @return le chrnometre de la partie
	 */
	public Chronometer getChrono() {
		return chrono;
	}


	/**
	 * accesseur mode de jeu de la partie
	 * @return le mode de jeu de la partie
	 */
	public Mode getModeDeJeu() {
		return modeDeJeu;
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
	
	public void updatedone()
	{
		this.needupdate=false;
	}
	
	public boolean getNeedUpdate()
	{
		return this.needupdate;
	}
	
	public void faireDormirIA()
	{
		MoteurJeu.this.IAdoisDormir=true;
	}
	
	/**
	 * Un thread permettant le jeu de l'ia
	 * @author Alan JAOUEN, Adrien BOIZARD
	 *
	 */
	public class ActualiseThread extends Thread {

		private int tpsIA;
		
		private int idJoueur;
		
		
		
		/**
		 * constructeur champ a champ
		 * @param name nom du thread
		 * @param tpsIA temps de jeu de l'ia
		 * @param idJoueur id du joueur que l'ia fais jouer
		 */
		public ActualiseThread(String name, int tpsIA, int idJoueur)
		{
			super(name);
			this.tpsIA=tpsIA;
			this.idJoueur=idJoueur;
			MoteurJeu.this.IAdoisDormir=true;
			this.start();
		}
		 
		/**
		 * Lance le thread
		 */
		@Override
		public void run()
		{
			try 
			{
				Thread.sleep(this.getTempsIA());
			} 
			catch (InterruptedException e)
			{
				
			}
			
			while(MoteurJeu.this.isInGame())
			{
				System.out.println(this.getTempsIA());
				Carte carteIa = MoteurJeu.this.arrayJoueur.get(idJoueur).getArrayCartes().get(arrayJoueur.get(idJoueur).getArrayCartes().size() -1);
				Carte carteMilieu = MoteurJeu.this.cartesCentre.get(MoteurJeu.this.cartesCentre.size()-1);
				
				if(MoteurJeu.this.IAdoisDormir)
				{
					MoteurJeu.this.IAdoisDormir=false;
					
					try {
						MoteurJeu.this.interagir(idJoueur,Carte.getSymboleCommun(carteIa, carteMilieu));
						MoteurJeu.this.IAdoisDormir=true;
					} catch (Exception e1) {
						
					}
					
					try 
					{
						Thread.sleep(this.getTempsIA());
					} 
					catch (InterruptedException e)
					{
					
					}
				}
				
				
				
			}
		}
		
		/**
		 * 
		 * @return le temps a sleep pour l'ia en fonction de this.tpsIA +/- 15%
		 */
		private int getTempsIA()
		{
			return this.tpsIA+(int)((Math.random()*1000)%((this.tpsIA*1000)/3)) - (int)((Math.random()*1000)%((this.tpsIA*1000)/3));
		}
		

	}//fin AnctualiseThread
}
