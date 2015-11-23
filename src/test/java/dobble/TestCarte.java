package dobble;

import java.util.Scanner;

public class TestCarte {

	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		while(true) {
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
					Carte carte = new Carte(5);
					System.out.println("Carte générée:\n" + carte);
					break;
		
			case 3: System.out.println("TEST METHODE GETSYMBOLECOMMUN");
					Carte carte1 = new Carte(1);
					Carte carte2 = new Carte(50);
			
					System.out.println("Carte1 :\n" + carte1 + "\nCarte2:\n" + carte2);
					System.out.println("Symbole commun: " + Carte.getSymboleCommun(carte1, carte2));
					break;
			case 4:
				scan.close();
				System.exit(0);
		}
		}
	}
}
