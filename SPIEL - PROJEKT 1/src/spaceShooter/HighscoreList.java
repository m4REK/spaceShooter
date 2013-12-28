/* 		*** RELEASE INFORMATION ***
 * 		Wintersemester 2013/14
 * 		HAW Hamburg
 * 		Projekt A
 * 		Johannes Bagge & Marko Vukadinovic
 * 		spaceShooter
 * 		2D Side Scroller Game
 */


package spaceShooter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class HighscoreList {
	
	@SuppressWarnings("unused")
	private static String playerName;
	private static int maxScoresToSave;
	static HighscoreEntry[] highscoreArray = null;
	File scoreFile = null;
	
	// Konstruktor
	public HighscoreList(int maxScoresToSave) {
		HighscoreList.maxScoresToSave = maxScoresToSave;
		// Datei anlegen
		scoreFile = new File("scoreList.txt");
		try {
			scoreFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		highscoreArray = new HighscoreEntry[maxScoresToSave];
		
		// alte ScoreList auslesen
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(scoreFile));
			// Array f�llen
			for(int i = 0; i < maxScoresToSave; i++) {
				String lineFromList = bufferedReader.readLine();
				if(lineFromList == null) {			// falls ein Eintrag nicht gef�llt war wird er mit "Score = 0" und "Name = " - "" gef�llt
					lineFromList = "0: - ";
				}
				String[] lineFromListSeperated = lineFromList.split(":");
				int scoreFromList = Integer.parseInt(lineFromListSeperated[0]);
				String nameFromList = lineFromListSeperated[1];
				highscoreArray[i] = new HighscoreEntry(scoreFromList, nameFromList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {bufferedReader.close();} catch (Exception e) {}
		}
	}
	
	// HighscoreList anzeigen
	public static HighscoreEntry[] getArray () {
		return highscoreArray;
	}

	// HighscoreList erneuern
	public void refreshHighscores(int newScore, String playerName) {
		HighscoreList.playerName = playerName;
		
		// wenn der neue Highscore nicht auf der Liste erscheint ist nichts zu tun
		if(newScore > highscoreArray[maxScoresToSave-1].getScore()) {
			HighscoreEntry newEntry = new HighscoreEntry(newScore, playerName);
			
			// neuen Eintrag einf�gen und Array sortieren
			highscoreArray[maxScoresToSave-1] = newEntry;
			java.util.Arrays.sort(highscoreArray);				// hier macht man sich das implementierte Comparable-Interface zunutze
			
			// neue ScoreList speichern
			BufferedWriter bufferedWriter = null;
			try {
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(scoreFile), "utf-8"));
				for(int i = 0; i < maxScoresToSave; i++) {
			    	bufferedWriter.write(highscoreArray[i].getScore() + ":" + highscoreArray[i].getName());
			    	bufferedWriter.newLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			   try {bufferedWriter.close();} catch (Exception e) {}
			}	
		}
	}
	
}
