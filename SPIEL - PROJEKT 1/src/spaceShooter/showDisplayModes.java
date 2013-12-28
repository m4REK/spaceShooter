/* 		*** RELEASE INFORMATION ***
 * 		Wintersemester 2013/14
 * 		HAW Hamburg
 * 		Projekt A
 * 		Johannes Bagge & Marko Vukadinovic
 * 		spaceShooter
 * 		2D Side Scroller Game
 */


package spaceShooter;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

// Diese Klasse zeigt die für den fullscreen-mode möglichen DisplayModes des Gerätes

public class showDisplayModes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();
		
		DisplayMode[] ds = device.getDisplayModes();
		
		for(int i=0; i<ds.length; i++){
			System.out.println(ds[i].getWidth()+" "+ds[i].getHeight()+" "+ds[i].getBitDepth()+" "+ds[i].getRefreshRate());
		}
		
		

	}

}
