package dobble;

import org.junit.Assert;
import org.junit.Test;

public class TestSymbole {

	/* Test visuel*/
	public static void main(String[] args) {
		Symbole s = new Symbole(47);
		int id = s.getId();
		String nom = s.getNom();
		System.out.println("nom: " + nom + "\nId: " + id );
		if (s.getImage() == null)
		{
			System.out.println("pas d'image");
		}
		else
		{
			System.out.println("Il y a une image");
		}
	}
	
	/* test avec Junit*/
	
	@Test
	public void testConstructeurInt1()
	{
		for(int i=1; i<=57;i++)
		{
			Symbole s= new Symbole(i);
			Assert.assertNotNull(s);
			Assert.assertNotNull(s.getImage());
		
			
		}
	}
	
	@Test
	public void testConstructeurInt2()
	{
		Symbole s= new Symbole(-9);
		Assert.assertNotNull(s);
		Assert.assertNull(s.getImage());

	}
	
	@Test
	public void testToString()
	{
		Symbole s= new Symbole(1);
		Assert.assertEquals("ampoule - ID 1" , s.toString());
		
	}
	
	@Test
	public void testEquals1()
	{
		
		Assert.assertNotEquals(new Symbole(1), new Symbole(6));
		
	}
	
	@Test
	public void testEquals2()
	{
		
		Assert.assertEquals(new Symbole(4), new Symbole(4));
		
	}
	
	@Test
	public void testEquals3()
	{
		Symbole s= new Symbole(4);
		Assert.assertEquals(s, s);
		
	}
	
	@Test
	public void testEquals4()
	{
		Assert.assertNotEquals(new Symbole(4), new Integer(25));
		
	}
	
	@Test
	public void testEquals5()
	{
		Assert.assertNotEquals(new Symbole(4), null);
		
	}
}
