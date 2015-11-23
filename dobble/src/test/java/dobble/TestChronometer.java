package dobble;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de la classe Chronometer
 * @author Alan JAOUEN
 *
 */
public class TestChronometer {

	//test visuel
	public static void main(String[] args) {
		
		Chronometer chrono=new Chronometer();
		
		//on lance le chrono
		chrono.start();
		try 
		{
			while (true)
			{
				Thread.sleep(500);
				System.out.println("il s'est ecoule " +chrono.getHours() + " heure(s) ou "
				+ chrono.getMinutes() + "minute(s) ou "
				+ chrono.getSeconds()+ " seconde(s) ou "
				+ chrono.getMilliseconds() + " milliseconde(s)");
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

	}
	//	-----------------------------
	// test unitaire
	@Test
	public void testConstructeur()
	{
		Chronometer c = new Chronometer();
		Assert.assertNotNull(c);
	}
	

	@Test
	public void testConstructeurCopie()
	{
		Chronometer c = new Chronometer();
		Chronometer c2 = new Chronometer(c);
		
		Assert.assertEquals(c, c2);
	}
	
	@Test
	public void testGetBeginEtStart()
	{
		Chronometer c = new Chronometer();
		c.start();
		int a=c.getBegin();
		
		Assert.assertTrue(a!=0);
	}
	
	@Test
	public void testGetMilliseconds()
	{
		Chronometer c = new Chronometer();
		c.start();
		int a=c.getMilliseconds();
		Assert.assertTrue(!(a>15));
	}
	
	@Test
	public void testGetSeconds()
	{
		Chronometer c = new Chronometer();
		c.start();
		int a=c.getSeconds();
		Assert.assertTrue(a==0);
	}

	@Test
	public void testGetMinutes()
	{
		Chronometer c = new Chronometer();
		c.start();
		int a=c.getMinutes();
		Assert.assertTrue(a==0);
	}
	
	@Test
	public void testGetHours()
	{
		Chronometer c = new Chronometer();
		c.start();
		int a=c.getHours();
		Assert.assertTrue(a==0);
	}
	
	@Test
	public void testToString()
	{
		Chronometer c = new Chronometer();
		c.start();
		int a=c.getBegin();
		Assert.assertEquals(c.toString(), "Chronometer [begin=" + a + "]");
	}
	
	@Test
	public void testEquals1()
	{
		Chronometer c = new Chronometer();
		c.start();
		Assert.assertEquals(c, c);
	}
	
	@Test
	public void testEquals2()
	{
		Chronometer c = new Chronometer();
		c.start();
		Assert.assertNotEquals(c, null);
	}
	
	@Test
	public void testEquals3()
	{
		Chronometer c = new Chronometer();
		c.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
		}
		Chronometer c2 = new Chronometer();
		c2.start();
		Assert.assertNotEquals(c, c2);
	}
	
	@Test
	public void testEquals5()
	{
		Chronometer c = new Chronometer();
		c.start();
		
		Assert.assertNotEquals(c,new Integer(5));
	}
	
}
