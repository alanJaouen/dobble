package dobble;

import org.junit.Assert;
import org.junit.Test;

public class TestMode {
	// Constructeurs:
	
	
	public static void main(String args[]) {
		
		// Test du constructeur par défaut et de la méthode toString:
		Mode[] mode_test = new Mode[6];
		mode_test[0] = new Mode(); // Constructeur par défaut
		mode_test[1] = new Mode(10, 7); // Constructeur par champs
		mode_test[2] = new Mode(-3, 12); // Constructeur par champs avec erreur 1er champ
		mode_test[3] = new Mode(3, -12); // Constructeur par champs avec erreur 2ème champ
		mode_test[4] = new Mode(-3, -12); // Constructeur par champs avec erreur 1er et 2ème champs
		mode_test[5] = new Mode(mode_test[2]); // Constructeur par copie
		
		System.out.println("Tests de la classe Mode:\n");
		for (Mode i: mode_test)
			System.out.println(i);
		System.out.println("\nFin des tests de la classe Mode");
	}
	
	//test JUnit
	
		@Test
		public void testConstructeurDefaut()
		{
			Mode s=new Mode();
			Assert.assertNotNull(s);
		}
		
		@Test
		public void testConstructeurChampAChamp()
		{
			Mode s=new Mode(3,2);
			Assert.assertNotNull(s);
		}
		
		@Test
		public void testConstructeurChampAChamp2()
		{
			Mode s=new Mode(-1,2);
			Assert.assertNotNull(s);
		}
		
		@Test
		public void testConstructeurChampAChamp3()
		{
			Mode s=new Mode(25000,2);
			Assert.assertNotNull(s);
		}
		
		@Test
		public void testConstructeurChampAChamp4()
		{
			Mode s=new Mode(1,-2);
			Assert.assertNotNull(s);
		}
		
		@Test
		public void testConstructeurChampAChamp5()
		{
			Mode s=new Mode(1,2000);
			Assert.assertNotNull(s);
		}
		
		@Test
		public void testConstructeurCopie()
		{
			Mode s=new Mode(3,2);
			Mode s2=new Mode(s);
			Assert.assertEquals(s, s2);
		}
		
		@Test
		public void testToString()
		{
			Mode s=new Mode(1,2);
			Assert.assertNotNull(s.toString());
		}
		
		@Test
		public void testEquals1()
		{
			Mode c = new Mode();
			Assert.assertEquals(c, c);
		}
		
		@Test
		public void testEquals2()
		{
			Mode c = new Mode();
			Assert.assertNotEquals(c, null);
		}
		
		@Test
		public void testEquals3()
		{
			Mode c = new Mode(1,1);
			Mode c2 = new Mode(2,1);
			Assert.assertNotEquals(c, c2);
		}
		
		@Test
		public void testEquals4()
		{
			Mode c = new Mode(1,1);
			Mode c2 = new Mode(1,2);
			Assert.assertNotEquals(c, c2);
		}
		
		@Test
		public void testEquals5()
		{
			Mode c = new Mode();		
			Assert.assertNotEquals(c,new Integer(5));
		}
		
		@Test
		public void testSetnbSymbole()
		{
			Mode c = new Mode();	
			c.setNbSymbole(5);
			Assert.assertEquals(c.getNbSymbole(),5);
		}
		
		@Test
		public void testSetTempsIA()
		{
			Mode c = new Mode();	
			c.setTempsIA(5);
			Assert.assertEquals(c.getTempsIA(),5);
		}
		
}
