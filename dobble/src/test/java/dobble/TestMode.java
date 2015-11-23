package dobble;

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
}
