package dobble;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

public class Symbole implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1793540733891826530L;
	private int id;
	private String nom;
	private transient BufferedImage image;
	
	
	public Symbole(int id)
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
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public void setImage()
	{
		
		try {
			File f = new File("images/img" + id + ".png");
			this.image = ImageIO.read(f);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public String toString()
	{
		return this.getNom() + " - ID " + this.getId();
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
					br.close();
					return chaine;
				}
			}
			br.close();
		}  
		catch (Exception e)
		{
			System.err.println(e.toString());
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Symbole other = (Symbole) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
