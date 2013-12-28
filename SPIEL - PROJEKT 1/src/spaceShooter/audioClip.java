/* 		*** RELEASE INFORMATION ***
 * 		Wintersemester 2013/14
 * 		HAW Hamburg
 * 		Projekt A
 * 		Johannes Bagge & Marko Vukadinovic
 * 		spaceShooter
 * 		2D Side Scroller Game
 */


package spaceShooter;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class audioClip {
	private File audioDatei;
	private Clip clip;
	
	public audioClip (String path) {
		audioDatei = new File(path);
		
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioDatei);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
				}
	}
	
	public void playClip(){			//play
		clip.start();
	}
	public void loopClip(){			//looping
		clip.loop(30); 
	}
	public void stopClip(){			//stop
		clip.stop();
	}
}
