package serveur;

import java.io.*;
import java.net.*;
import java.util.Scanner;



public class Client {

	public static Socket socket = null;
	public static Thread t1;
	
	public static void main(String[] args) {
	
		
	try {
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir l'IP :");
		String str = sc.nextLine();     
		System.out.println("Veuillez saisir le port :");
		int str2 = sc.nextInt(); 
		
		System.out.println(str+"     "+str2);
		
		socket = new Socket(str,str2);
		
		System.out.println("Connexion établie avec le serveur"); // Si le message s'affiche c'est que je suis connecté
		
		t1 = new Thread( new Chat_ClientServeur(socket, "serveur"));
		t1.start();
		
		
		
	} catch (UnknownHostException e) {
	  System.err.println("Impossible de se connecter à l'adresse "+socket.getLocalAddress());
	} catch (IOException e) {
	  System.err.println("Aucun serveur à l'écoute du port "+socket.getLocalPort());
	  e.printStackTrace();
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	
	

	}

}
