package serveur;

import java.io.*;
import java.net.*;

import dobble.Joueur;
import dobble.MoteurJeu;

public class Serveur {
 public static ServerSocket ss = null;
 public static Thread t;
 public static MoteurJeu mj;
 
	public static void main(String[] args) {
		
		try {
			
			
			ss = new ServerSocket(0);
			int lePortLibre = ss.getLocalPort(); 
			InetAddress IP = InetAddress.getLocalHost();
			String MonIP = IP.getHostAddress();
			
			
			System.out.println("Le serveur est à l'écoute du port "+ lePortLibre);
			System.out.println("à l'IP "+MonIP);
			
			t = new Thread(new Accepter_connexion(ss));
			t.start();
			
			
			
			
		} catch (IOException e) {
			System.out.println("pb");
			System.err.println("Le port "+ss.getLocalPort()+" est déjà utilisé !");
		}
	
	}

	
	}

