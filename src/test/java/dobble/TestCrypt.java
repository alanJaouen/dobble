package dobble;

import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class TestCrypt {

	//test visuel
	public static void main(String[] args) {
		String str=null;
		int i;
		Scanner sc =new Scanner(System.in);
		while(true)
		{
			System.out.println("\nTESTCRYPT:"
					+ "\n\t1:encrypter une chaine"
					+ "\n\t2:quitter");
			i=sc.nextInt();
			switch(i)
			{
			case 1:
				System.out.println("chaine a encrypter:");
				sc =new Scanner(System.in);
				str=sc.nextLine();
				System.out.println("chaine encryptee:"+Crypt.encrypte(str));
				break;
			case 2:
				sc.close();
				System.exit(0);
			}
		}

	}
	//test unitaire
	
	@Test
	public void testEncrypte() throws AssertionError
	{
		Assert.assertNotEquals(Crypt.encrypte("bla"),"bla");
	}
	
	@Test
	public void testConstructeur() throws AssertionError
	{
		Crypt t= new Crypt();
		Assert.assertNotNull(t);
	}

}
