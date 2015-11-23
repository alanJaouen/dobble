package dobble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Carte {
	
	private ArrayList<Symbole> arraySymbole;
	
	/**
	 * Constructeur de la carte avec l'id (lecture dans le fichier carte.txt)
	 * @param id id de la carte (ligne dans le fichier)
	 */
    public Carte(int id) {
    	Symbole s;
    	String symbolesId = Symbole.lecture("cartes.txt", id);
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
     * @return Le deck
     */
    public ArrayList<Carte> genererDeck(Mode modedejeu){
    	ArrayList<Carte> deck = new ArrayList<Carte>();
    	for (int i = 1; i <= 55; i += 1) //55 cartes (pour mode normal)
    	{
    		deck.add(new Carte(i));
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

	public ArrayList<Symbole> getArraySymbole() {
		return arraySymbole;
	}

	public void setArraySymbole(ArrayList<Symbole> arraySymbole) {
		this.arraySymbole = arraySymbole;
	}
	
	public String toString()
	{
		String s = "";
		for (int i = 0; i < 8; i += 1)
		{
			s += i + ": " + arraySymbole.get(i) + "\n";
		}
		return s;
	}

}
