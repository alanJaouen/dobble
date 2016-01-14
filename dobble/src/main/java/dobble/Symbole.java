package dobble;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * Modélise un Symbole
 * @author Adrien BOIZZARD, Eva TERZAGO
 *
 */
public class Symbole  {

	/**
	 * l'id du symbole
	 */
	private int id;
	/**
	 * le nom du symbole
	 */
	private String nom;
	/**
	 * l'image du symbole
	 */
	private  BufferedImage image;
	
	/**
	 * constructeur de symbole
	 * @param id l'id du symbole a construire
	 */
	public Symbole(int id)
	{
		this.id=id;
		setNom(lecture("nomSymboles.txt", id));
		setImage();
	}
	
	/**
	 * accesseur varaible id
	 * @return l'id du symbole
	 */
	public int getId()
	{
		return this.id;
	}

	
	/**
	 * acesseur nom du symbole
	 * @return e nom du symbole
	 */
	public String getNom()
	{
		return this.nom;
	}
	/**
	 * mutateur varable nom
	 * @param nom le nom a set
	 */
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	
	/**
	 * accesseur image du symbole
	 * @return l'image du symbole
	 */
	public BufferedImage getImage()
	{
		return image;
	}
	
	/**
	 * recupere l'image corespondant au symbole courant et la stocke dans l'instance
	 */
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
	
	/**
	 * génere l'icon pour les fenetre
	 * @return l'icone du logiciel
	 */
	public static ImageIcon getIcon()
	{
		return new ImageIcon("images/favicon.png");
	}

}
