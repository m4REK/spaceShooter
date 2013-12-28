/* 		*** RELEASE INFORMATION ***
 * 		Wintersemester 2013/14
 * 		HAW Hamburg
 * 		Projekt A
 * 		Johannes Bagge & Marko Vukadinovic
 * 		spaceShooter
 * 		2D Side Scroller Game
 */


package spaceShooter;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class Bullets {
	private static BufferedImage look;
	
	// statischer Block (wird bei Erstbenutzung aufgerufen)
	static{
		try {
			look = ImageIO.read(Bullets.class.getClassLoader().getResourceAsStream("gfx/schuss.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private float f_posX;
	private float f_posY;
	private float f_speedX;
	private float f_speedY;
	private Rectangle bounding;
	private List<Bullets> bullets;
	private float bulletTimeAlive;
	private final float timeToLive = 5.0f;

	
	public Bullets (float x, float y, float speedX, float speedY, List<Bullets> bullets){
		this.f_posX = x;
		this.f_posY = y;
		this.f_speedX = speedX;
		this.f_speedY = speedY;
		bounding = new Rectangle((int)x, (int)y, look.getWidth(), look.getHeight());
		// die Bullets-Liste wird benötigt, um Bullets nach einer Zeit daraus entfernen zu können
		this.bullets = bullets;
	}
	
	// Bullets updaten (gleiche Weise wie beim Player)
	public void update(float timeSinceLastFrame){
		// Bullet nach einer Weile zerstören
		bulletTimeAlive += timeSinceLastFrame;
		if(bulletTimeAlive > timeToLive){
			bullets.remove(this);
		}
		
		// Bullet bewegen
		f_posX += f_speedX * timeSinceLastFrame;
		f_posY += f_speedY * timeSinceLastFrame;
		bounding.x = (int)f_posX;
		bounding.y = (int)f_posY;
	}
	
	public Rectangle getBounding(){
		return bounding;
	}
	
	public static BufferedImage getLook(){
		return look;
	}

}
