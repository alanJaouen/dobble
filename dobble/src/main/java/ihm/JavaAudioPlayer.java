package ihm;

import java.io.*;
import sun.audio.*;
 
/**
 * A simple Java sound file example (i.e., Java code to play a sound file).
 * AudioStream and AudioPlayer code comes from a javaworld.com example.
 * @author alvin alexander, devdaily.com.
 */
public class JavaAudioPlayer
{
	
	/**
	 * Lit un fichier audio decrivant un symbole
	 * @param id l'ID du symbole
	 */
	public static void voix(int id) throws Exception //FIXME ne doit pas ralentir tout le jeu...
	{
		String fichier = "sons/son" + id + ".wav";
		InputStream in = new FileInputStream(fichier);
		 
	    // create an audiostream from the inputstream
	    AudioStream audioStream = new AudioStream(in);
	 
	    // play the audio clip with the audioplayer class
	    AudioPlayer.player.start(audioStream);
	}
	
  /*public static void main(String[] args) 
  throws Exception
  {
	  voix(47);
  }*/
}