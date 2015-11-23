package dobble;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Symbole {
	private int id;
	private String nom;
	private Image image;
	
	Symbole(int id)
	{
		setId(id);
		setNom(lecture("nomSymboles.txt", id));
		setImage();
	}
	
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getNom()
	{
		return this.nom;
	}
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public void setImage()
	{
		File f = new File("images/img" + id + ".png");
		if (f.exists())
		{
			this.image = new ImageIcon("/images/img" + id + ".png").getImage();
		}
		else
		{
			System.err.println("ERROR: The file: "+ f + " does not exist\n");
		}
	}
	
	public String toString()
	{
		return "nom: " + this.getNom() + " - ID: " + this.getId();
	}
	
	/**
	 * Retourne sous forme de chaine de caracteres une ligne d'un fichier texte
	 * @param fichier Chemin du fichier
	 * @param ligne (Première ligne: 1 et non 0)
	 * @return Une chaine contenant la ligne
	 */
	public static String lecture(String fichier, int ligne)
	{
		String chaine = "";
		int i = 0;
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String line;
			while ((line=br.readLine()) != null)
			{
				i += 1;
				if (i == ligne)
				{
					chaine = line;
					return chaine;
				}
			}
			br.close();
		}  
		catch (Exception e)
		{
			System.err.println(e.toString());
		}
		return chaine; //Pour ne pas faire d'erreurs: inutile? ><
	}
	
	public boolean equals(Symbole s)
	{
		if(this.id == s.id)
		{
			return true;
		}
		return false;
	}
}
