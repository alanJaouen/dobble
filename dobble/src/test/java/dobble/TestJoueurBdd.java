package dobble;

import java.util.Scanner;

import dobble.Stats.BddException;

public class TestJoueurBdd {

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
}
