package dobble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Carte implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6256323073888330163L;
	
	/**
	 * arrayList qui contient les symbole de la carte
	 */
	private ArrayList<Symbole> arraySymbole;
	
	/**
	 * Constructeur de la carte avec l'id (lecture dans le fichier carte.txt)
	 * @param id id de la carte (ligne dans le fichier)
	 * @param fichier le nom du fichier dans lequel lire
	 * @throws Exception si le nom du ficier est incorect
	 */
    public Carte(int id, int fichier) throws Exception {
    	if(!(fichier == 3 || fichier == 4 || fichier == 8|| fichier == 6) )
    		throw new Exception("nb de symbole incorect");
    		
    	Symbole s;
    	String symbolesId = Symbole.lecture("cartes"+fichier+".txt", id);
    	int[] tab = Carte.intsFromString(symbolesId);
    	int taille = tab.length;
    	
    	this.arraySymbole = new ArrayList<Symbole>();
    	for(int i = 0; i < taille; i += 1)
    	{
    		s = new Symbole(tab[i]);
    		this.arraySymbole.add(s);
    	}
    }
    
    /**
     * Retourne un tableau contenant les entiers d'une chaine donn�e
     * @param s Chaine contenant des nombres
     * @return Nombres extraits
     */
    public static int[] intsFromString(String s)
    {
    	LinkedList<String> list = new LinkedList<String>();
        Matcher matcher = Pattern.compile("\\d+").matcher(s);
        while (matcher.find())
        {
            list.add(matcher.group());
        }
        String[] tabString = list.toArray(new String[list.size()]);
        
        int taille = tabString.length;
    	int[] tabInt = new int[taille];
    	
    	for(int i = 0; i < taille; i += 1)
    	{
    		tabInt[i] = Integer.parseInt(tabString[i]);
    	}
    	
    	return tabInt;
    }
    
    /**
     * Genere un deck complet de 55 cartes, sous la forme d'une arrayList
     * @param modedejeu Le mode de jeu
     * @return Le deck généré
     * @throws Exception si probleme lors de la lecure des cartes
     */
    public static ArrayList<Carte> genererDeck(Mode modedejeu) throws Exception{
    	int nbcarte=0;
    	switch(modedejeu.getNbSymbole())
    	{
    	case 3:
    		nbcarte=7;
    		break;
    	case 4:
    		nbcarte=13;
    		break;
      	case 6:
    		nbcarte=31;
    		break;
    	case 8:
        	nbcarte=55;
        	break;
        default:
        	return null;
    		
    	}
    	
    	ArrayList<Carte> deck = new ArrayList<Carte>();
    	for (int i = 1; i <= nbcarte; i += 1)
    	{
    		deck.add(new Carte(i, modedejeu.getNbSymbole()));
    	}
    	return deck;
    }
    
    /**
     * Compare deux cartes et indique le symbole commun
     * @param c1 La premiere carte
     * @param c2 La seconde carte
     * @return Le symbole commun
     */
    public static Symbole getSymboleCommun(Carte c1, Carte c2)
    {
    	int taille = c1.arraySymbole.size();
    	
    	for (int i = 0; i < taille; i += 1)
    	{
    		for (int j = 0; j < taille; j += 1)
    		{
    			if (c1.arraySymbole.get(i).equals(c2.arraySymbole.get(j)))
    			{
    				return c1.arraySymbole.get(i);
    			}
    		}
    	}
    	System.err.println("Erreur: Pas de symbole commun");
    	return null;
    }

    /**
     * acesseur liste de symbole de la carte
     * @return l'arraylist de symbole de la carte
     */
	public ArrayList<Symbole> getArraySymbole() {
		return arraySymbole;
	}

	/**
	 * mutateur liste de symbole de la carte
     * @param arraySymbole l'arraylist de symbole de la carte
	 */
	public void setArraySymbole(ArrayList<Symbole> arraySymbole) {
		this.arraySymbole = arraySymbole;
	}
	
	/**
	 * affichage de l'objet courant sous forme de chaine de caractère
	 */
	public String toString()
	{
		String s = "";
		for (int i = 0; i < this.arraySymbole.size(); i += 1)
		{
			s += i + ". " + arraySymbole.get(i) + "\n";
		}
		return s;
	}

}
