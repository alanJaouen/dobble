package dobble;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe avec methode pour securiser la bdd
 * @author Alan JAOUEN
 *
 */
public class Crypt {
	
	/**
	 * sel pour le cryptage
	 */
	private static String salt="Z1rYiWaU";
	
	/**
	 * Permet d'ncrypter une chaine de caracteres pour eviter le vol de mot de passe depuis la bdd
	 * @param args la chaine a encrypter
	 * @return la chaine encrypter
	 */
	public static String encrypte(String args) 
	{
		String original = args+Crypt.salt;
		MessageDigest md;
		
		try 
		{
			md = MessageDigest.getInstance("SHA1");
			md.update(original.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) 
			{
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();
		}
		catch (NoSuchAlgorithmException e) //pas besoin de traiter car SHA1 static
		{
		}
			return null;
	}

	/**
	 * constructeur par defaut
	 */
	public Crypt() {
		super();
	}
	
}
