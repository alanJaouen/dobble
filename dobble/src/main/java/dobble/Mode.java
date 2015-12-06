package dobble;



public class Mode implements Constantes_defaut_projet, serializable{
	
	// Attributs:
    private int nbSymbole;

    private int tempsIA;
    
    // -------------------------------------------------------------------
    // Constructeurs:
    // Par défaut:
    public Mode() {
    	this.nbSymbole = Constantes_defaut_projet.nbSymbole_defaut; // Valeur par défaut contenue dans l'interface Constantes_defaut_projet
    	this.tempsIA = Constantes_defaut_projet.tempsIA_defaut;     // Valeur par défaut contenue dans l'interface Constantes_defaut_projet
    }
    
    // Par champs:
    public Mode(int nbSymbole, int tempsIA) {
    	if (nbSymbole < 0 || nbSymbole > this.nbSymboleMax_defaut)
    		this.nbSymbole = this.nbSymbole_defaut;
    	else
    		this.nbSymbole = nbSymbole;
    	
    	if (tempsIA < 0 || tempsIA > this.tempsIAMax_defaut)
    		this.tempsIA = this.tempsIA_defaut;
    	else
    		this.tempsIA = tempsIA;
    }
    
    // Par copie:
    public Mode(Mode modele) {
    	this.nbSymbole = modele.getNbSymbole();
    	this.tempsIA = modele.getTempsIA();
    }
    
    // -------------------------------------------------------------------
    // Méthodes:
    
    /*
     * toString:
     * présente les valeurs du mode sous forme
     * de string.
     * 
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
    
    
    /*
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
    
    public int getTempsIA() {
    	return this.tempsIA;
    }

	public int getNbSymbole() {
		return this.nbSymbole;
	}

	public void setNbSymbole(int nbSymbole) {
		this.nbSymbole = nbSymbole;
	}

	public void setTempsIA(int tempsIA) {
		this.tempsIA = tempsIA;
	}

}
