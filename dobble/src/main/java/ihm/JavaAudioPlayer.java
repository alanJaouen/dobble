package ihm;

import java.io.*;

import dobble.Symbole;
import sun.audio.*;
 
/**
 * A simple Java sound file example (i.e., Java code to play a sound file).
 * AudioStream and AudioPlayer code comes from a javaworld.com example.
 * @author alvin alexander, devdaily.com.
 */
public class JavaAudioPlayer extends Thread
{
	private int id;
	private int idJoueur;
	
	public JavaAudioPlayer(int id, int idJoueur)
	{
		this.id=id;
		this.idJoueur = idJoueur;
	}
	
	public void run()
	{
		String fichier = "sons/";
		if(this.idJoueur == 0) //Si l'ID correspond au joueur
			fichier += "moi";
		else //Si c'est l'adversaire qui a joue
			{
				if(Symbole.lecture("param.txt", 5).equals("1.0")) //Si le joueur a choisi Alan, la voix par defaut
				{
					fichier += "son";
				}
				else if(Symbole.lecture("param.txt", 5).equals("2.0")) //Si le joueur a choisi Moussa
				{
					fichier += "moussa";
				}
			}
		
		
		fichier += id + ".wav";
		InputStream in=null;
		try {
			in = new FileInputStream(fichier);
		} catch (FileNotFoundException e) {
		}
		 
	    // create an audiostream from the inputstream
	    AudioStream audioStream=null;
		try {
			audioStream = new AudioStream(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
	    // play the audio clip with the audioplayer class
	    AudioPlayer.player.start(audioStream);
		this.stop();
	}
}