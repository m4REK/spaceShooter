/* 		*** RELEASE INFORMATION ***
 * 		Wintersemester 2013/14
 * 		HAW Hamburg
 * 		Projekt A
 * 		Johannes Bagge & Marko Vukadinovic
 * 		spaceShooter
 * 		2D Side Scroller Game
 */


package spaceShooter;

public class HighscoreEntry implements Comparable<HighscoreEntry> {
	
	private int score;
	private String name;
	
	public HighscoreEntry(int score, String name) {
		this.score = score;
		this.name = name;
	}
	
	@Override
	public int compareTo(HighscoreEntry otherEntry) {
		if(this.score > otherEntry.score) {
			return -1;
		}
		else if(this.score < otherEntry.score) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
}
