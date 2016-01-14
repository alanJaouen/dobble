package dobble;

import java.io.Serializable;



public class Mode implements Constantes_defaut_projet, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7597834623218274441L;

	// Attributs:
	/**
	 * nombre de symbole par carte dans ce mode de jeu
	 */
    private int nbSymbole;

    /**
     * temps de jeu de l'ia dans ce mode de jeu
     */
    private int tempsIA;
    
    // -------------------------------------------------------------------
    // Constructeurs:
    // Par défaut:
    /**
     * constructeur par defaut
     */
    public Mode() {
    	this(Constantes_defaut_projet.nbSymbole_defaut,Constantes_defaut_projet.tempsIA_defaut);
    }
    
    // Par champs:
    /**
     * constructeur champs a champs
     * @param nbSymbole le nombre de symbole par carte de ce mode de jeu
     * @param tempsIA le temps de jeu de l'ia dans ce mode de jeu
     */
    public Mode(int nbSymbole, int tempsIA) {
    	if (nbSymbole < 0 || nbSymbole > Constantes_defaut_projet.nbSymboleMax_defaut)
    		this.nbSymbole = Constantes_defaut_projet.nbSymbole_defaut;
    	else
    		this.nbSymbole = nbSymbole;
    	
    	if (tempsIA < 0 || tempsIA > Constantes_defaut_projet.tempsIAMax_defaut)
    		this.tempsIA = Constantes_defaut_projet.tempsIA_defaut;
    	else
    		this.tempsIA = tempsIA;
    }
    
    // Par copie:
    /**
     * constructeur par copie
     * @param modele le Mode a copier
     */
    public Mode(Mode modele) {
    	this(modele.getNbSymbole(),modele.getTempsIA());
    }
    
    // -------------------------------------------------------------------
    // Méthodes:
    
    /**
     * présente les valeurs du mode sous forme de string.
     */
    public String toString() {
    	String str = new String("");
    	str += "Mode = [nbSymbole: ";
    	str += this.getNbSymbole();
    	str += ", tempsIA: ";
    	str += this.tempsIA;
    	str += "]";
    	return str;
    }
    
    
    /**
     * equals:
     * compare les valeurs des champs suivants:
     * 	- nbSylbole
     * 	- tempsIA
     * 
     * renvoie true si les champs sont égaux, false sinon
     * 
     */
    public boolean equals(Object modele) {
    	if (modele instanceof Mode) {
    		if (this.nbSymbole != ((Mode) modele).getNbSymbole())
    			return false;
    		
    		if (this.tempsIA != ((Mode) modele).getTempsIA())
    			return false;
    	} else return false;
    	return true;
    }
    
    // -------------------------------------------------------------------
    // Getter et Setters:
    
    /**
     *  accesseur variable tempsIA
     * @return le temps de jeu de l'ia dans ce mode de jeu
     */
    public int getTempsIA() {
    	return this.tempsIA;
    }

    /**
     *  accesseur variable nbSymbole
     * @return le nombre de symbole par carte dans ce mode de jeu
     */
	public int getNbSymbole() {
		return this.nbSymbole;
	}

	/**
	 * mutateur variable nbSymbole
	 * @param nbSymbole valeur a set
	 */
	public void setNbSymbole(int nbSymbole) {
		this.nbSymbole = nbSymbole;
	}

	/**
	 * mutateur variable tempsIA
	 * @param tempsIA valeur a set
	 */
	public void setTempsIA(int tempsIA) {
		this.tempsIA = tempsIA;
	}

}
