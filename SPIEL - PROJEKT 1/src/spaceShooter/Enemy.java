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
import java.util.Random;
import javax.imageio.ImageIO;
import spaceShooter.Bullets;


public class Enemy {
	private static BufferedImage look[] = new BufferedImage[4];		// Array, weil die Animation aus mehreren Bildern besteht
	private static BufferedImage lookDead;
	private final static float neededAnimationTime = 1.0f;
	private final Random r = new Random();
	private float aniTime =0;
	private float posX;
	private float posY;
	private Rectangle bounding;
	private Player player;
	private List<Bullets> bullets;
	private boolean alive = true;
	private int movePattern;
	private final int maxMovePattern = 2;
	private final float enemySpeed;
	private Frame f;
	
	// statischer Block (wird bei Erstbenutzung aufgerufen)
	static{
		try {
			look[0] = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("gfx/enemy1.png"));
			look[1] = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("gfx/enemy2.png"));
			look[2] = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("gfx/enemy3.png"));
			look[3] = look[1];
			lookDead = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("gfx/enemy_kaputt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Enemy(float x, float y, List<Bullets> bullets, Player player, int movePattern, Frame f){
		this.posX = x;
		this.posY = y;
		bounding = new Rectangle ((int)x, (int)y, look[0].getWidth(), look[0].getHeight());
		this.bullets = bullets;
		// der Frame wird gebraucht, um den Score zu erhöhen
		this.f = f;
		// Player benötigt, um Bewegungsmuster umzusetzen
		this.player = player;
		// MovePattern wählen
		this.movePattern = movePattern;
		// enemySpeed random zwischen 50 und 100 wählen (vermeidet das clustern von enemies)
		enemySpeed = 50 + (r.nextInt(101));
	}
	
	// Enemies updaten
	public void update (float timeSinceLastFrame){
		if(player.isAlive()) {
			// Einzelbilder abhängig von der Zeit durchschalten
			aniTime += timeSinceLastFrame;
			if (aniTime > neededAnimationTime) aniTime = 0;
			
			// "Standard"-Bewegung (aktiv, wenn erweitertes Bewegungsmuster deaktiviert ist oder erw. Bewegungsmuster aktiviert
			//		und der Gegner tot ist (damit tote Gegner aus dem Bild verschwinden) oder ungültiges Bewegungsmuster gewählt ist)
			if((movePattern == 0) || ((movePattern == 1) && !alive) || ((movePattern == 2) && !alive) || (movePattern < 0) || (movePattern > maxMovePattern)) {
				// Gegner bewegen sich nur von rechts nach links
				posX -= enemySpeed * timeSinceLastFrame;
			}
			// Achsenbasiertes Bewegungsmuster
			else if(movePattern == 1) {
				if(alive) {
					// Spieler über Enemy?
					if((player.getY() - posY) < -10) {
						posY -= enemySpeed * timeSinceLastFrame;
					}
					// Spieler unter Enemy?
					else if((player.getY() - posY) > 10) {
						posY += enemySpeed * timeSinceLastFrame;
					}
					// Spieler links vom Enemy?
					if(player.getX() < posX) {
						posX -= enemySpeed * timeSinceLastFrame;
					}
					// Spieler rechts vom Enemy?
					else if(player.getX() > posX) {
						posX += enemySpeed * timeSinceLastFrame;
					}
				}
			}
			// Vektorbasiertes Bewegungsmuster
			else if(movePattern == 2) {
				if(alive) {
					// Vektor erstellen, der vom Enemy auf den Player zeigt
					float speedx = player.getX() - posX;
					float speedy = player.getY() - posY;
					
					// Satz des Pythagoras
					float speed = (float)Math.sqrt((speedx * speedx + speedy * speedy));
					
					if(speed != 0) {				// vermeidet das Teilen durch Null
						// Vektor normalisieren
						speedx /= speed;
						speedy /= speed;
						
						// normalisierten Vektor auf gewünschte Geschwindigkeit anheben
						speedx *= enemySpeed * timeSinceLastFrame;
						speedy *= enemySpeed * timeSinceLastFrame;
						
						// eigentliche Bewegung
						posX += speedx;
						posY += speedy;
					}
				}
			}
			
			// Enemies werden wieder nach rechts gesetzt, wenn sie links aus dem Bildschirm gewandert sind
			if(posX <= -getLook().getWidth()){
				posX = 800;
				posY = r.nextInt(600-getLook().getHeight());
				// tote Gegner "wiederbeleben"
				alive = true;
			}
			
			// prüfen, ob Enemy von Bullet getroffen
			for (int i = 0; i < bullets.size(); i++){
				Bullets b = bullets.get(i);
				if (alive && bounding.intersects(b.getBounding())){
					// Enemy tot
					alive = false;
					// Bullet entfernen
					bullets.remove(b);
					// Score erhöhen
					f.increaseScore();
				}
			}
			
			bounding.x = (int) posX;
			bounding.y = (int) posY;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public Rectangle getBounding(){
		return bounding;
	}
	
	public BufferedImage getLook(){
		if(!alive) return lookDead;
		else {
			if(look.length ==0) return null;	// dient nur der Sicherheit
			
			for (int i = 0; i<look.length; i++){
				if(aniTime< (float)(neededAnimationTime/look.length*(i+1))){
				return look[i];
				}
			}
			return look[look.length-1];
		}
	}
	
	public static int getWidth(){
		return look[0].getWidth();
	}
	
	
	public static int getHeight(){
		return look[0].getHeight();
	}
}
