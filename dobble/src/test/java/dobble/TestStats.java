package dobble;

import java.net.NetworkInterface;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import dobble.Stats.BddException;

public class TestStats {

	// test visuel
	public static void main(String[] args) {

		Scanner sc= new Scanner(System.in);
		while(true)
		{
			System.out.println("\n\n\n\n\nTest de la classe Stats\n\n"
					+ "\t1:lecture sur la bdd\n"
					+ "\t2:constructeur\n"
					+ "\t3:equals\n"
					+ "\t4:sauvegarde sur la bdd (fais appel a 1:lecture sur la bdd)\n"
					+ "\t5:quitter");
			switch(sc.nextInt())
			{
				case 1:
					bdd();
					break;
				case 2:
					constructeur();
					break;
				case 3:
					equals();
					break;
				case 4:
					sauvegarder();
					break;
				case 5:
					sc.close();
					System.exit(0);
			}
		}
		
	}
	
	
	
	private static void sleep()
	{
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
		}
	}

	
	private static void bdd()
	{
		Stats st=new Stats();
		String id="";
		String mdp="jambon";
		
		//test id vide
		
		
		System.out.println("\ntest de recuperation des stats avec id:"+id+" mdp:"+mdp);
		try {
			System.out.println(st.getBddStat(id, mdp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sleep();
		
		
		//test id faux
		id="marc";
		mdp="jambon";
		
		System.out.println("\ntest de recuperation des stats avec id:"+id+" mdp:"+mdp);
		try {
			System.out.println(st.getBddStat(id, mdp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sleep();
		
		//test mdp faux
		id="toto";
		mdp="jambon";
		
		System.out.println("\ntest de recupération des stats avec id:"+id+" mdp:"+mdp);
		try {
			System.out.println(st.getBddStat(id, mdp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sleep();
		//test id et mdp corrects
		id="bob";
		mdp="bob";
		
		System.out.println("\ntest de recupération des stats avec id:"+id+" mdp:"+mdp);
		try {
			System.out.println(st.getBddStat(id, mdp));
		} catch (Exception e) {
			e.printStackTrace();
		}

		//test injection sql
		id="toto';--";
		mdp="onsenfiche";
		
		System.out.println("\ntest de recuperation des stats avec id:"+id+" mdp:"+mdp+" (injection sql)");
		try {
			System.out.println(st.getBddStat(id, mdp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sleep();
		
		
	}

	private static void constructeur()
	{
		Stats st;
		//test avec constructeur bdd id faux
		String id="toto";
		String mdp="bob";
		System.out.println("\ntest du constructeur Stats("+id+","+mdp+")");
		try {
			st=new Stats(id,mdp);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		sleep();
		
		
		//test avec constructeur bdd id correct
		id="bob";
		mdp="bob ";
		System.out.println("\ntest du constructeur Stats("+id+","+mdp+")");
		try {
			st=new Stats(id,mdp);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		sleep();
		
		//test avec constructeur par defaut
		System.out.println("\ntest du constructeur Stats()");
		try {
			st=new Stats();
			System.out.println(st);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		sleep();
		
		//test avec constructeur champ a champ
		System.out.println("\ntest du constructeur Stats(2,54,12,5,6)");
		try {
			st=new Stats(2,54,12,5,6);
			System.out.println(st);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		sleep();
		
		//test avec constructeur champ a champ
		System.out.println("\ntest du constructeur Stats(-2,54,12,5,6)");
		try {
			st=new Stats(-2,54,12,5,6);
			System.out.println(st);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		sleep();
		
		//test avec constructeur par copie
		System.out.println("\ntest du constructeur Stats(new Stats(5,4,2,2,600))");
		try {
			st=new Stats(new Stats(5,4,2,2,600));
			System.out.println(st);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		sleep();
	}
	
	
	private static void equals()
	{
		
		Stats s=new Stats(1,2,3,4,5);
		System.out.println("\ns= "+s);
		
		System.out.println("s.equals(s): "+s.equals(s));
		
		System.out.println("s.equals(new Stats(1,2,3,4,5)): "+s.equals(new Stats(1,2,3,4,5)));
		
		System.out.println("s.equals(new Stats(1,2,3,4,0)): "+s.equals(new Stats(1,2,3,4,0)));
		
		System.out.println("s.equals(new Stats(0,2,3,4,5)): "+s.equals(new Stats(0,2,3,4,5)));
		
		System.out.println("s.equals(new Stats(1,7,98,45,7)): "+s.equals(new Stats(1,7,98,45,7)));
		
		System.out.println("s.equals(new Stats()): "+s.equals(new Stats()));
	}
	
	private static void sauvegarder()
	{
		String id="bob"; 
		String mdp="bob";
		Stats s1=new Stats(1,2,3,4,5);
		Stats s2=new Stats(28,27,30,15,500);
		
		System.out.println("\ns1= "+s1);
		System.out.println("s2= "+s2);
		
		//id corect s1
		try {
			System.out.println("\ntest de sauvegarde de s1 avec id:"+id+" mdp:"+mdp);
			s1.sauvegarder(id, mdp);
			System.out.println("résultat (lecture depuis bdd): ");
			System.out.print(s1.getBddStat(id, mdp));
			
		} catch (BddException e) {
			e.printStackTrace();
		}
		sleep();
		
		
		//id corect s2
		try {
			System.out.println("\n\n\ntest de sauvegarde de s2 avec id:"+id+" mdp:"+mdp);
			s2.sauvegarder(id, mdp);
			System.out.println("résultat (lecture depuis bdd): ");
			System.out.print(s2.getBddStat(id, mdp));
			
			
			
		} catch (BddException e) {
			e.printStackTrace();
		}
		sleep();
		
		//id incorect
		id="bob"; 
		mdp="pigeon";
		try {
			System.out.println("\n\n\ntest de sauvegarde de s2 avec id:"+id+" mdp:"+mdp);
			s2.sauvegarder(id, mdp);
			System.out.println("résultat (lecture depuis bdd): ");
			System.out.print(s2.getBddStat(id, mdp));
			
		} catch (BddException e) {
			e.printStackTrace();
		}
		sleep();
		
		//mot de passe avec des espaces
		mdp="   ";
		try {
			System.out.println("\n\n\ntest de sauvegarde de s2 avec id:"+id+" mdp:"+mdp);
			s2.sauvegarder(id, mdp);
			System.out.println("résultat (lecture depuis bdd): ");
			System.out.print(s2.getBddStat(id, mdp));
			
		} catch (BddException e) {
			e.printStackTrace();
		}
		sleep();
		
		//id avec des espaces
		mdp="bob";
		id="   ";
		try {
			System.out.println("\n\n\ntest de sauvegarde de s2 avec id:"+id+" mdp:"+mdp);
			s2.sauvegarder(id, mdp);
			System.out.println("résultat (lecture depuis bdd): ");
			System.out.print(s2.getBddStat(id, mdp));
			
		} catch (BddException e) {
			e.printStackTrace();
		}
		sleep();
		
		//injection SQL
		mdp="autre";
		id="bob';--";
		try {
			System.out.println("\n\n\ntest de sauvegarde de s2 avec id:"+id+" mdp:"+mdp+" (injection sql)");
			s1.sauvegarder(id, mdp);
			System.out.println("résultat (lecture depuis bdd): ");
			System.out.print(s2.getBddStat("bob", "bob"));
			
		} catch (BddException e) {
			e.printStackTrace();
		}
		sleep();
	}
	
	//test JUnit
	
	@Test
	public void testConstructeurDefaut()
	{
		Stats s=new Stats();
		Assert.assertNotNull(s);
	}
	
	@Test
	public void testConstructeurChampAChamp()
	{
		Stats s=new Stats(3,2,1,3,5);
		Assert.assertNotNull(s);
	}
	
	@Test
	public void testConstructeurStringString()
	{
		
		Stats s;
		try {
			s = new Stats("bob","bob");
			Assert.assertNotNull(s);
		} catch (BddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	@Test
	public void testConstructeurStringString2()
	{
		try {
			Stats s=new Stats("null","vide");
			Assert.assertNull(s);
			} catch (BddException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void testConstructeurStringString3()
	{
		try {
			Stats s=new Stats("","");
			Assert.assertNull(s);
			} catch (BddException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void testConstructeurStringString4()
	{
		try {
			Stats s=new Stats("bob","");
			Assert.assertNull(s);
			} catch (BddException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void testConstructeurStringString5()
	{
		try {
			Stats s=new Stats("","bob");
			Assert.assertNull(s);
			} catch (BddException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void testConstructeurStringString6()
	{
		try {
			Stats s=new Stats("bob';--","vide");
			Assert.assertNull(s);
			} catch (BddException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void testConstructeurStringString7()
	{
		try {
			Stats s=new Stats("bob","vide';--");
			Assert.assertNull(s);
			} catch (BddException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void testConstructeurStringString8()
	{
		try {
			Stats s=new Stats("bob","mauvais mdp");
			Assert.assertNull(s);
			} catch (BddException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void testConstructeurStringString9()
	{
		try {
			Stats s=new Stats("';","mauvais mdp");
			Assert.assertNull(s);
			} catch (BddException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void testConstructeurCopie()
	{
		Stats s=new Stats(3,2,1,3,5);
		Stats s2 = new Stats(s);
		Assert.assertEquals(s, s2);
	}
	
	@Test
	public void testToString()
	{
		Stats s=new Stats(3,2,1,3,5);

		Assert.assertEquals("Stats [niveau=3, exp=2, tempsDeJeu=1, tpsReaction="
				+"3, meilleurScore=5]", s.toString());
		
		 
	}
	
	@Test
	public void testSetNiveau1()
	{
		Stats s=new Stats(1,1,1,1,1);

		s.setNiveau(-3);
		
		Assert.assertEquals(1, s.getNiveau());

	}
	
	@Test
	public void testSetNiveau2()
	{
		Stats s=new Stats(1,1,1,1,1);

		s.setNiveau(7);
		
		Assert.assertEquals(7, s.getNiveau());

	}
	
	@Test
	public void testEquals1()
	{
		Stats c = new Stats();
		Assert.assertEquals(c, c);
	}
	
	@Test
	public void testEquals2()
	{
		Stats c = new Stats();
		Assert.assertNotEquals(c, null);
	}
	
	@Test
	public void testEquals3()
	{
		Stats c = new Stats(1,1,1,1,1);
		Stats c2 = new Stats(2,1,1,1,1);
		Assert.assertNotEquals(c, c2);
	}
	
	@Test
	public void testEquals4()
	{
		Stats c = new Stats(1,1,1,1,1);
		Stats c2 = new Stats(1,2,1,1,1);
		Assert.assertNotEquals(c, c2);
	}
	
	@Test
	public void testEquals5()
	{
		Stats c = new Stats();		
		Assert.assertNotEquals(c,new Integer(5));
	}
	
	@Test
	public void testEquals6()
	{
		Stats c = new Stats(1,1,1,1,1);
		Stats c2 = new Stats(1,1,2,1,1);
		Assert.assertNotEquals(c, c2);
	}
	
	@Test
	public void testEquals7()
	{
		Stats c = new Stats(1,1,1,1,1);
		Stats c2 = new Stats(1,1,1,2,1);
		Assert.assertNotEquals(c, c2);
	}
	
	@Test
	public void testEquals8()
	{
		Stats c = new Stats(1,1,1,1,1);
		Stats c2 = new Stats(1,1,1,1,2);
		Assert.assertNotEquals(c, c2);
	}
	
	
	@Test
	public void testSauvegarder1()
	{
		Stats s1=new Stats(1,2,3,4,5);
		try {
			s1.sauvegarder("bob", "bob");
		} catch (BddException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSauvegarder2()
	{
		Stats s1=new Stats(1,2,3,4,5);
		try {
			s1.sauvegarder("bob", "");
		} catch (BddException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSauvegarder3()
	{
		Stats s1=new Stats(1,2,3,4,5);
		try {
			s1.sauvegarder("", "bob");
		} catch (BddException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSauvegarder4()
	{
		Stats s1=new Stats(1,2,3,4,5);
		try {
			s1.sauvegarder("", "");
		} catch (BddException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSauvegarder5()
	{
		Stats s1=new Stats(1,2,3,4,5);
		try {
			s1.sauvegarder("bob", "';--");
		} catch (BddException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSauvegarder6()
	{
		Stats s1=new Stats(1,2,3,4,5);
		try {
			s1.sauvegarder("bob';--", "null");
		} catch (BddException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSauvegarder7()
	{
		Stats s1=new Stats(1,2,3,4,5);
		try {
			s1.sauvegarder("bob';--", "';--");
		} catch (BddException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSauvegarder8()
	{
		Stats s1=new Stats(1,2,3,4,5);
		try {
			s1.sauvegarder("karl", "Marx");
		} catch (BddException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSauvegarder9()
	{
		Stats s1=new Stats(1,2,3,4,5);
		try {
			s1.sauvegarder("bob'';'", "");
		} catch (BddException e) {
			e.printStackTrace();
		}
	}
	

}
