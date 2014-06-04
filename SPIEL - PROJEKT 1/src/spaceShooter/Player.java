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
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class Player {
	private Rectangle bounding;
	private float f_posX;
	private float f_posY;
	private int worldSize_x;
	private int worldSize_y;
	private BufferedImage look;
	private List<Bullets> bullets;
	private List<Enemy> enemys;
	private boolean alive = true;
	private BufferedImage lookDead;
	private float timeSinceLastShot =0;
	private float schussFREQ = 0.1f;
	private audioClip aClip;

	
	// Konstruktor
	public Player(int x, int y,int worldSize_x, int worldSize_y, List<Bullets> bullets, List<Enemy> enemys){
		// PlayerPicture laden
		try {
			look = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/raumschiffchen.png"));
			lookDead = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/raumschiff_kaputt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// BoundingBox entsprechend den Bildattributen setzen
		bounding = new Rectangle (x,y, look.getWidth(), look.getHeight());
		f_posX = x;
		f_posY = y;
		this.worldSize_x = worldSize_x;
		this.worldSize_y = worldSize_y;
		this.bullets = bullets;
		this.enemys = enemys;
		
	}
	
	// PlayerPosition und BoundingBox updaten
	public void update(float timeSinceLastFrame){
		
		// wenn der player tot ist funktionieren keine Kommandos mehr
		if(!alive) return;
		
		// in jedem Frame wird die Zeit, die seit dem letzten Frame vergangen ist, zur Zeit, die seit dem letzten Schuss vergangen ist, hinzuaddiert
		timeSinceLastShot += timeSinceLastFrame;
		
		// Spieler-Bewegung (300 Pixel/Sekunde)
		if (Keyboard.isKeyDown(KeyEvent.VK_UP))f_posY -=300*timeSinceLastFrame;
		if (Keyboard.isKeyDown(KeyEvent.VK_DOWN))f_posY +=300*timeSinceLastFrame;
		if (Keyboard.isKeyDown(KeyEvent.VK_RIGHT))f_posX +=300*timeSinceLastFrame;
		if (Keyboard.isKeyDown(KeyEvent.VK_LEFT))f_posX -=300*timeSinceLastFrame;
		
		// schießen
		if(timeSinceLastShot > schussFREQ && Keyboard.isKeyDown(KeyEvent.VK_SPACE)){
			timeSinceLastShot = 0;
			bullets.add(new Bullets(f_posX+look.getWidth()/2 - Bullets.getLook().getWidth()/2, f_posY+look.getHeight()/2 - Bullets.getLook().getHeight()/2, 500, 0, bullets));
			
			// Audio
			aClip = new audioClip("./src/audio/lasersound.wav");
			aClip.playClip();
		}
		
		bounding.x=(int)f_posX;
		bounding.y=(int)f_posY;
		
		// auf Kollision mit Enemy prüfen
		for (int i=0; i<enemys.size(); i++){
			Enemy e = enemys.get(i);
			if(e.isAlive() && bounding.intersects(e.getBounding())) alive = false;
		}
		
		//Spieler bleibt im Fenster!!!
		if (f_posX < 0) f_posX =0;
		if (f_posY < 0) f_posY =0;
		if (f_posX > worldSize_x-bounding.width) f_posX=worldSize_x-bounding.width; 
		if (f_posY > worldSize_y-bounding.height) f_posY=worldSize_y-bounding.height; 
		
	}
	
	public Rectangle getBounding(){
		return bounding;
	}
	
	// Abfrage für Bewegungsmuster
	public float getX(){
		return f_posX;
	}
	
	public float getY(){
		return f_posY;
	}
	
	// aktuellen Look zurückgeben (wird von Frame aufgerufen)
	public BufferedImage getLook(){
		if(alive) return look;
		else return lookDead;
	}
	
	public boolean isAlive(){
		return alive;
	}
}
