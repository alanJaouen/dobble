package serveur;

import java.net.Socket;

public class JoueurServeur extends Joueur {
private Serveur serveur;


	public JoueurServeur(Socket socket, int numero, Serveur serveur) {
		super(socket, numero);
		this.serveur = serveur;
	}

}
