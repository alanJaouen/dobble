package dobble;

import java.io.Serializable;

/**
 * Classe permettant de chronometrer une durée
 * @author Silveira
 * @author Alan JAOUEN
 *
 */
public final class Chronometer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7652108967106185910L;

	/**
	 * Constructeur par défaut, créé une nouvelle instance de Chronometer
	 */
	public Chronometer() {
		super();
		this.begin=0;
	}

	/**
	 * Constructeur par copie
	 * @param unChrono le chronometre a copier
	 */
	public Chronometer(Chronometer unChrono)
	{
		this();
		this.begin=unChrono.getBegin();
	}
	
	/**
	 * @return the begin
	 */
	public int getBegin() {
		return begin;
	}

	/**
	 * permet de stoker le temps du systeme au lancement du chronometre
	 */
    private int begin;
 
    /**
     * Lance le chronometre
     */
    public void start(){
        begin = (int) System.currentTimeMillis();
    }
    
    /**
     * Permet d'obtenir le temps depuis start() en milliseconde
     * 
     * @return le temps depuis start() en milliseconde
     */
    public int getMilliseconds() {
        return (int) System.currentTimeMillis()-begin;
    }
 
    /**
     * Permet d'obtenir le temps depuis start() en seconde
     * 
     * @return le temps depuis start() en seconde
     */
    public int getSeconds() {
        return (int) (System.currentTimeMillis() - begin) / 1000;
    }
 
    /**
     * Permet d'obtenir le temps depuis start() en minute
     * 
     * @return le temps depuis start() en minute
     */
    public int getMinutes() {
        return (int) (System.currentTimeMillis() - begin) / 60000;
    }
 
    /**
     * Permet d'obtenir le temps depuis start() en heure
     * 
     * @return le temps depuis start() en heure
     */
    public int getHours() {
        return (int) (System.currentTimeMillis() - begin) / 3600000;
    }

	/** 
	 * resume l'etat de l'objet dans une chaine de caracteres
	 * @see java.lang.Object#toString()
	 * 
	 * @return la chaine de caracteres corespondant a l'etat actuel de l'objet
	 */
	@Override
	public String toString() {
		return "Chronometer [begin=" + begin + "]";
	}

	/**
	 * compare un objet avec l'objet courant
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @param obj l'objet a comparer avec l'objet courant
	 * @return true si obj est egal a l'objet courant
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chronometer other = (Chronometer) obj;
		if (begin != other.begin)
			return false;
		return true;
	}
}