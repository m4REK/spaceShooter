/* 		*** RELEASE INFORMATION ***
 * 		Wintersemester 2013/14
 * 		HAW Hamburg
 * 		Projekt A
 * 		Johannes Bagge & Marko Vukadinovic
 * 		spaceShooter
 * 		2D Side Scroller Game
 */


package spaceShooter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	// Array mit KeyCodes
	private static boolean[] keys = new boolean[1024];
	
	public static boolean isKeyDown(int keyCode){
		if(keyCode >=0 && keyCode<keys.length)return keys[keyCode];
		else return false;
	}
	
	// KeyCode im Array auf true setzen, wenn entsprechender Key gedrückt
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode >=0 && keyCode< keys.length) keys[keyCode]=true;
	}
	
	// KeyCodes wieder auf false setzen
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode >=0 && keyCode< keys.length) keys[keyCode]=false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
