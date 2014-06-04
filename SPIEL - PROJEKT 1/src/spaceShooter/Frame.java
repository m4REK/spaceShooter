/* 		*** RELEASE INFORMATION ***
 * 		Wintersemester 2013/14
 * 		HAW Hamburg
 * 		Projekt A
 * 		Johannes Bagge & Marko Vukadinovic
 * 		spaceShooter
 * 		2D Side Scroller Game
 */


package spaceShooter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.List;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	
	final Player player;
	final Background bg;
	private BufferStrategy strat;		// die Klasse BufferStrategy verhindert flickering und tiering durch page-flipping
	private List<Bullets> bullets;
	private List<Enemy> enemys;
	private int score = 0;
	private static final Color scoreColor = Color.white;
	private static final Font scoreFont = new Font("Comic Sans", Font.BOLD, 48);
	private static String playerName;
	private static HighscoreList scoreboard;
	
	// Konstruktor
	public Frame(Player player, Background bg, List<Bullets> bullets, List<Enemy> enemys, String playerName, HighscoreList scoreboard){
		// WindowName
		super("SpaceShooter");
		// KeyListener
		addKeyListener(new Keyboard());
		this.player = player;
		this.bg = bg;
		this.bullets = bullets;
		this.enemys = enemys;
		// playerName und Scoreboard ben�tigt f�r die Highscore-Liste
		Frame.playerName = playerName;
		Frame.scoreboard = scoreboard;
	}
	
	// BufferStrategy
	public void makeStrat(){
		createBufferStrategy(2);	// Anzahl der pages beim PageFlipping (hier 2)
		strat = getBufferStrategy();
	}
	
	// neuen Frame berechnen
	public void repaintScreen(){
		Graphics g = strat.getDrawGraphics();		// Zeichenfl�che aus der BufferStrategy holen
		draw(g);									// das eigentliche Zeichnen
		g.dispose();								// Graphics-Objekt wegwerfen (Performance)
		strat.show();								// das Gezeichnete anzeigen
	}
	
	// Score erh�hen
	public void increaseScore() {
		score++;
	}
	
	// hier wird auf der BufferStrategy gezeichnet, nicht auf einem JPanel!
	private void draw(Graphics g){
			// Background zeichnen
			g.drawImage(bg.getLook(), bg.getX(), 0, null);								// Background einmal zeichnen
			g.drawImage(bg.getLook(), bg.getX()+bg.getLook().getWidth(), 0, null);		// Background am Ende vom 1. BG-Pic noch einmal zeichnen
			
			// Bullets zeichnen
			for (int i=0; i<bullets.size(); i++){
				Bullets b = bullets.get(i);
				g.drawImage(Bullets.getLook(), b.getBounding().x, b.getBounding().y, null);
			}
			
			// Enemies zeichnen
			for(int i=0; i<enemys.size(); i++){
				Enemy e = enemys.get(i);
				g.drawImage(e.getLook(), e.getBounding().x, e.getBounding().y, null);
			}
			
			// Score zeichnen
			g.setColor(scoreColor);
			g.setFont(scoreFont);
			g.drawString(Integer.toString(score), 50, 80);
		
			// Player an Position vom Bounding zeichnen
			g.drawImage(player.getLook(), player.getBounding().x, player.getBounding().y, null);
			
			if(!player.isAlive()) {		// Spieler ist tot
				// Game Over Text anzeigen
				g.setColor(scoreColor);
				g.setFont(scoreFont);
				g.drawString("Game Over", 280, 300);
				g.setFont(new Font("Comic Sans", Font.BOLD, 13));
				g.drawString("Press ESC to exit", 350, 340);
				// Highscore-Liste aktualisieren
				scoreboard.refreshHighscores(score, playerName);
			}
	}
}