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
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

// MAIN KLASSE

public final class spaceShooter implements Runnable{
	
	// Bullets und Enemies werden in LinkedLists gehalten
	public static List<Bullets> bullets = new LinkedList<Bullets>();
	public static List<Enemy> enemys = new LinkedList<Enemy>();
	
	//Player und Background erstellen
	public static Player player = new Player(300,300,800,600, bullets, enemys);
	public static Background bg = new Background(50, player);
	
	private static boolean fullScreen;
	public static Random r = new Random();
	
	// !!! DIESE WERTE KOMMEN VOM MENU !!!
	@SuppressWarnings("unused")
	private String playerName;
	@SuppressWarnings("unused")
	private static HighscoreList scoreboard = new HighscoreList(10);
	// Bewegungsmuster der Gegner wählen (
	//	0: keine Bewegung zum Ziel, 
	//	1: Achsenbasierte Bewegung, 
	//	2: Vektorbasierte Bewegung)
	private static int movePattern;
	
	public Frame f;
	
	
	//Konstruktor
	public spaceShooter(boolean fullscreen, int movePattern, String playerName, HighscoreList scoreboard) {

		f = new Frame(player, bg, bullets, enemys, playerName, scoreboard);
		spaceShooter.fullScreen = fullscreen;
		spaceShooter.movePattern = movePattern;
		this.playerName = playerName;
		spaceShooter.scoreboard = scoreboard;
		
		//Frame initialisieren
		if(!fullscreen){
			f.setSize(800, 600);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(!fullScreen){f.setLocationRelativeTo(null);}		// zentriert positionieren
		if(!fullScreen){f.setVisible(true);};				// nicht n�tig bei fullscreen
		f.setResizable(false);
		}
		
		// ersten Enemy spawnen
		enemySpawn(f);
		
		// fullscreen device w�hlen
		if(fullScreen){
			
			f.setUndecorated(true);
			f.setAutoRequestFocus(true);
		
			GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice device = environment.getDefaultScreenDevice();	// Default Screen w�hlen
			device.setFullScreenWindow(f);
			device.setDisplayMode(new DisplayMode(800,600,32,60));  // (width, height, bitDepth, refreshRate)
			
		}
		
		// BufferStrategy initialisieren
		f.makeStrat();
	}
	
	@Override
	public void run() {
		
		
		// Frequenz mit der neue Enemies spawnen
		final float enemySpawnTime = 2f;
		float timeSinceLastEnemySpawn =0;
		
		// Gespeicherte Zeit des letzten Frames
		long lastFrame = System.currentTimeMillis();
		
		// nach dem Tod soll nur noch ein neuer Frame gezeichnet werden
		boolean repaint = true;
		
		// Haupt-Spiel-Schleife!
		while(true){
			// Spiel abbrechen
			if (Keyboard.isKeyDown(KeyEvent.VK_ESCAPE)) System.exit(0);
			
			// Aktuelle Systemzeit speichern
			long thisFrame = System.currentTimeMillis();
			// Delta(t) des letzten und des aktuellen Frames
			float timeSinceLastFrame = ((float)(thisFrame - lastFrame))/1000f; // Umrechnung in Sekunden (float, damit nicht immer auf 0 gerundet wird)
			// n�chsten Durchgang vorbereiten
			lastFrame = thisFrame;
			
			// Enemies gem�� gesetzter Frequenz spawnen
			timeSinceLastEnemySpawn += timeSinceLastFrame;
			if(timeSinceLastEnemySpawn > enemySpawnTime){
				timeSinceLastEnemySpawn -= enemySpawnTime;
				enemySpawn(f);
			}
			
			// Player updaten
			player.update(timeSinceLastFrame);
			
			// Background updaten
			bg.update(timeSinceLastFrame);
			
			// neuen Frame zeichnen
			if(repaint == true) {
				f.repaintScreen();
				if(!player.isAlive()) {
					repaint = false;
				}
			}
			
			// Bullets updaten
			for(int i=0; i<bullets.size(); i++){
				bullets.get(i).update(timeSinceLastFrame);
			}
			
			// Enemies updaten
			for(int i=0; i<enemys.size(); i++){
				enemys.get(i).update(timeSinceLastFrame);
			}
			
			// Verhindern, dass so viele Frames berechnet werden wie die CPU aush�lt
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	// Enemy an random position erstellen
	public static void enemySpawn(Frame f){
		enemys.add(new Enemy(800, r.nextInt(600 - Enemy.getHeight()),bullets, player, movePattern, f));
	}

}
