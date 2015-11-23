package dobble;

import java.util.Scanner;

import dobble.Stats.BddException;

public class TestStats {

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
		
		System.out.println("s.equals(new Chronometer()): "+s.equals(new Chronometer()));
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
}
