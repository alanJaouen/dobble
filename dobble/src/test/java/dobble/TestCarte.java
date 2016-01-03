package dobble;

import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;


public class TestCarte {

	/* test visuel */
	public static void main(String[] args) throws Exception
	{
		Scanner scan = new Scanner(System.in);
		while(true) 
		{
			System.out.println("Que voulez-vous tester?\n1. intsFromString\n2. Creation d'une carte\n3. getSymboleCommun\n4. quitter");
			int choix = scan.nextInt();
			
			switch(choix)
			{
				case 1: System.out.println("TEST METHODE INTSFROMSTRINGS");
						String s = Symbole.lecture("cartes.txt", 1);
						int[] tab = Carte.intsFromString(s);
						for (int i = 0; i < tab.length; i++)
						{
							System.out.println(tab[i]);
						}
						break;
				
				case 2: System.out.println("TEST CREATION D'UNE CARTE");
						Carte carte = new Carte(5, 8);
						System.out.println("Carte générée:\n" + carte);
						break;
			
				case 3: System.out.println("TEST METHODE GETSYMBOLECOMMUN");
						Carte carte1 = new Carte(1,8);
						Carte carte2 = new Carte(50,8);
				
						System.out.println("Carte1 :\n" + carte1 + "\nCarte2:\n" + carte2);
						System.out.println("Symbole commun: " + Carte.getSymboleCommun(carte1, carte2));
						break;
				case 4:
					scan.close();
					System.exit(0);
			}
		}
	}
	
	/* test avec JUnit */
	
	@Test
	public void  testConstructeur1()
	{
		/*Carte c = new Carte(-28);
		Assert.assertNotNull(c);*/
	}
	
	@Test
	public void  testConstructeur2()
	{
			Carte c;
			try {
				c = new Carte(1,3);
				Assert.assertNotNull(c);
			} catch (Exception e) {
				Assert.fail();
				
			}
			
	}
	
	@Test
	public void  testToString()
	{
		Carte c;
		try {
			c = new Carte(5,8);
			Assert.assertNotNull(c.toString());
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void  testSymboleCommun()
	{
		Carte c;
		try {
			c = new Carte(2,8);
			Carte c2 = new Carte(1,8);
			Assert.assertEquals(new Symbole(19), Carte.getSymboleCommun(c, c2));
		} catch (Exception e) {
			Assert.fail();
		}
		
		
		
	}
	
	@Test
	public void  testSymboleBug()
	{
		
		try {
			new Carte(2,1);
			Assert.fail();
		} catch (Exception e) {
			
		}
	}
	
}
