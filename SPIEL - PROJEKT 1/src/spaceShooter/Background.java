/* 		*** RELEASE INFORMATION ***
 * 		Wintersemester 2013/14
 * 		HAW Hamburg
 * 		Projekt A
 * 		Johannes Bagge & Marko Vukadinovic
 * 		spaceShooter
 * 		2D Side Scroller Game
 */


package spaceShooter;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	private float f_posX;
	// Scroll-Geschwindigkeit
	private float f_speed;
	private BufferedImage look;
	private Player player;
	audioClip BG;
	audioClip crash;
	
	public Background(float f_speed, Player player){
		this.f_speed = f_speed;
		// Player wird ben�tigt, um den Hintergrund anzuhalten, wenn der Player tot ist
		this.player = player;
		
		try {
			look = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/weltraum.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BG = new audioClip("./src/audio/imperialMarch.wav"); 
		crash = new audioClip("./src/audio/boom.wav");
	}
	
	// Background updaten
	public void update (float timeSinceLastFrame){
		if(player.isAlive()) {			// nur, wenn Player lebt
			BG.loopClip();
			f_posX -= f_speed*timeSinceLastFrame;
			// Background resetten
			if(f_posX < -look.getWidth()) f_posX += look.getWidth();
		}else {
			BG.stopClip();
			crash.playClip();
		}
	}
	
	// nur die X-Koordinate ist interessant, weil der Hintergrund nur in eine Richtung scrollt
	public int getX(){
		return (int)f_posX;
	}
	
	public BufferedImage getLook(){
		return look;
	}

}
