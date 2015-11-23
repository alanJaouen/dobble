package dobble;

public class TestSymbole {

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

}
