package dobble;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import dobble.Stats.BddException;

public class TestJoueur {

	/* test visuels*/
	public static void main(String[] args) {


		Scanner sc= new Scanner(System.in);
		while(true)
		{
			sleep();
			System.out.println("\n\n\n\n\nTest de la classe Stats\n\n"
					+ "\t1:ajouteTom\n"
					+ "\t2:supprimeTom\n"
					+ "\t5:quitter");
			switch(sc.nextInt())
			{
				case 1:
					ajouteTom();
					break;
				case 2:
					supprimeTom();
					break;
				case 5:
					sc.close();
					System.exit(0);
			}
		}
	}
	
	
	private static void ajouteTom()
	{
		try {
			Joueur.nouveauJoueur("tom", "tom");
		} catch (BddException e) {
			e.printStackTrace();
		}
	}
	
	private static void supprimeTom()
	{
		try {
			Joueur j=new Joueur("tom","tom");
			j.supprimerJoueur();
		} catch (BddException e) {
			e.printStackTrace();
		}
	}
	
	private static void sleep()
	{
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
		}
	}
	
	/* test avec JUnit */
	
	@Test
	public void testConstructeurDefaut()
	{
		Joueur j= new Joueur();
		Assert.assertNotNull(j);
	}
	
	@Test
	public void testConstructeurChampAChamp()
	{
		Joueur j= new Joueur(new ArrayList<Carte>(), new Stats(), "calamar", 280, "bob");
		Assert.assertNotNull(j);
	}
	
	@Test
	public void testConstructeurStringString() 
	{
		//necessite internet
		Joueur j=null;
		try {
			j = new Joueur("bob", "bob");
		} catch (BddException e) {
			throw new AssertionError(e.getMessage());
		}
		Assert.assertNotNull(j);
	}
	
	@Test
	public void testConstructeurStringString2()
	{
		Joueur j=null;
		try {
			j = new Joueur("bob", "poulet");
		} catch (BddException e) {
		}
		Assert.assertNull(j);
	}
	
	
	
	
	@Test
	public void testVerifieSQL1()
	{
		Assert.assertFalse(Joueur.verifsql("bob"));
	}
	
	@Test
	public void testVerifieSQL2()
	{
		Assert.assertTrue(Joueur.verifsql("bob';--"));
	}
	
	@Test
	public void testNouveauJoueur1()
	{//necessite internet 
				
		try {
			Joueur.nouveauJoueur("bob", "salut");
		} catch (BddException e) {
			if (e.getMessage().equals(("nom deja utilise")))
			{
				return;
			}
			else throw new AssertionError("exeption inatendue:"+e.getMessage());
		}
		
	}
	
	@Test
	public void testNouveauJoueur2()
	{//necessite internet 
		
		String str= new Integer((int)(Math.random()*1000)).toString();
		try {
			Joueur.nouveauJoueur("test"+str, "test"+str);
		} catch (BddException e) 
		{
			throw new AssertionError("exeption inatendue:"+e.getMessage());
		}
	}
	@Test
	public void testNouveauJoueur3()
	{//necessite internet 
		
		String str= new Integer((int)(Math.random()*1000)).toString();
		try {
			Joueur.nouveauJoueur("", "test"+str);
		} catch (BddException e) 
		{
			Assert.assertEquals("Champ(s) vide(s)", e.getMessage());
		}
	}
	
	@Test
	public void testNouveauJoueur4()
	{//necessite internet 
		
		String str= new Integer((int)(Math.random()*1000)).toString();
		try {
			Joueur.nouveauJoueur("test"+str, "");
		} catch (BddException e) 
		{
			Assert.assertEquals("Champ(s) vide(s)", e.getMessage());
		}
	}
	
	@Test
	public void testNouveauJoueur5()
	{//necessite internet 
		
		String str= new Integer((int)(Math.random()*1000)).toString();
		try {
			Joueur.nouveauJoueur("test';--", "test"+str);
		} catch (BddException e) 
		{

			Assert.assertEquals("injection sql detectee et bloquee", e.getMessage());
		}
	}
	
	@Test
	public void testNouveauJoueur6()
	{//necessite internet 
		
		String str= new Integer((int)(Math.random()*1000)).toString();
		try {
			Joueur.nouveauJoueur("test"+str, "test';Delete form table");
		} catch (BddException e) 
		{
			Assert.assertEquals("injection sql detectee et bloquee", e.getMessage());
		}
	}
	
	@Test
	public void testSupprimerJoueur1()
	{//necessite internet
		Joueur j=null;
		try {
			j= new Joueur("bob","bob");
			Assert.assertTrue(j.supprimerJoueur());
		} catch (BddException e) 
		{
			throw new AssertionError("exeption inatendue:"+e.getMessage());
		}
		
		//on remet le joueur
		try {
			Joueur.nouveauJoueur("bob", "bob");
		} catch (BddException e) {
			
		}
	}
	
	@Test
	public void testVoirStats()
	{//necessite internet
		Joueur j=new Joueur();
		Assert.assertNotNull(j.voirStats());
	}
	
	@Test
	public void testSupprimerJoueur2()
	{
		Joueur j=null;
		try {
			j= new Joueur("bob","mauvais mdp");
			Assert.assertFalse(j.supprimerJoueur());
		} catch (BddException e) 
		{
			Assert.assertEquals("Mot de passe incorect", e.getMessage());
		}
	}
	
	@Test
	public void testGetArrayCarte()
	{//necessite internet 
		
		Joueur j= new Joueur();
		ArrayList<Carte> tab=new ArrayList<Carte>();
		
		j.setArrayCartes(tab);
		
		Assert.assertEquals(tab, j.getArrayCartes());
	}
	
	@Test
	public void testGetStats()
	{//necessite internet 
		
		Joueur j= new Joueur();
		Stats st=new Stats();		
		j.setStats(st);
		
		Assert.assertEquals(st, j.getStats());
	}
	
	@Test
	public void testGetNom()
	{//necessite internet 
		
		Joueur j= new Joueur();	
		j.setNom("bob");
		
		Assert.assertEquals("bob", j.getNom());
	}
	
	@Test
	public void testGetMdp()
	{//necessite internet 
		
		Joueur j= new Joueur();	
		j.setMdp("bob");
		
		Assert.assertEquals("bob", j.getMdp());
	}
	
	@Test
	public void testGetScore()
	{//necessite internet 
		
		Joueur j= new Joueur();	
		j.setScore(269);
		
		Assert.assertEquals(269, j.getScore());
	}
	
	@Test
	public void testSauvegarderStats()
	{//necessite internet 
		
		Joueur j=null;
		try {
			j = new Joueur("bob","bob");
			j.setScore(269);
			Assert.assertTrue(j.sauvegarderStats());
		} catch (BddException e) {
			throw new AssertionError(e.getMessage());
		}	
	}
}
