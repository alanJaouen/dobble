package ihm;

import java.io.*;
import sun.audio.*;
 
/**
 * A simple Java sound file example (i.e., Java code to play a sound file).
 * AudioStream and AudioPlayer code comes from a javaworld.com example.
 * @author alvin alexander, devdaily.com.
 */
public class JavaAudioPlayer extends Thread
{
	private int id;
	
	public JavaAudioPlayer(int id)
	{
		this.id=id;
	}
	
	public void run()
	{
		String fichier = "sons/son" + id + ".wav";
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