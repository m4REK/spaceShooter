/* 		*** RELEASE INFORMATION ***
 * 		Wintersemester 2013/14
 * 		HAW Hamburg
 * 		Projekt A
 * 		Johannes Bagge & Marko Vukadinovic
 * 		spaceShooter
 * 		2D Side Scroller Game
 */


package spaceShooter;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener {
	private JButton start_btn;
	private JButton end_btn;
	private JCheckBox fullscreen_chbx;
	private boolean fullscreen = false;
	private static final int menuWidth = 600;
	private static final int menuHeight = 550;
	private static final int btnWidth = 160;
	private static final int btnHeight = 40;
	private BufferedImage img;
	private JLabel imgLabel;
	private final JLabel headlineLabel = new JLabel("SpaceShooter");
	private final JLabel copyrightLabel = new JLabel("Copyright \u00a9 2013");
	private final JRadioButton movePattern1_btn = new JRadioButton("MovePattern 1", true);
	private final JRadioButton movePattern2_btn = new JRadioButton("MovePattern 2");
	private final JRadioButton movePattern3_btn = new JRadioButton("MovePattern 3");
	private final JButton movePatternExp_btn = new JButton("What's that?");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private int movePattern = 0;
	private final JPanel movePatternPanel = new JPanel();
	private final JTextField nameField = new JTextField();
	private final JLabel nameLabel = new JLabel("Name:");
	private static final int maxScoresToSave = 10;
	private static HighscoreList scoreboard = new HighscoreList(maxScoresToSave);
	private final JLabel highscoreLabel = new JLabel("<html><u>Highscores</u></<html>");
	
	
	public static void main(String[] args) {
		Menu menu = new Menu("Space Shooter Menu");
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(menuWidth,menuHeight);
		menu.setLocationRelativeTo(null);
		menu.setLayout(null);
		menu.setResizable(false);
		menu.setVisible(true);
	}
	
	public Menu(String title) {
		super(title);
		
		// Headline
		headlineLabel.setFont(new Font("Comic Sans", Font.BOLD + Font.ITALIC, 24));
		headlineLabel.setForeground(Color.blue);
		headlineLabel.setBounds(menuWidth/2-80, 20, 400, 40);
		add(headlineLabel);
		
		// Bild
		try {
			img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/raumschiffchen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		imgLabel = new JLabel(new ImageIcon(img));
		imgLabel.setBounds(menuWidth/2-img.getWidth()/2, 80, img.getWidth(), img.getHeight());
		add(imgLabel);
		
		// Fullscreen Checkbox
		fullscreen_chbx = new JCheckBox("Toggle Fullscreen", false);
		fullscreen_chbx.setBounds(menuWidth/4-65, 160, btnWidth, btnHeight);
		fullscreen_chbx.addActionListener(this);
		add(fullscreen_chbx);
		
		// MovePattern
		buttonGroup.add(movePattern1_btn);
		buttonGroup.add(movePattern2_btn);
		buttonGroup.add(movePattern3_btn);
		movePattern1_btn.addActionListener(this);
		movePattern2_btn.addActionListener(this);
		movePattern3_btn.addActionListener(this);
		movePatternPanel.add(movePattern1_btn);
		movePatternPanel.add(movePattern2_btn);
		movePatternPanel.add(movePattern3_btn);
		movePatternExp_btn.setBorderPainted(false); 
		movePatternExp_btn.setContentAreaFilled(false); 
		movePatternExp_btn.setFocusPainted(false); 
		movePatternExp_btn.setOpaque(false);
		movePatternExp_btn.setFont(new Font("Arial", Font.ITALIC, 12));
		movePatternExp_btn.setForeground(Color.blue);
		movePatternExp_btn.addActionListener(this);
		movePatternPanel.add(movePatternExp_btn);
		movePatternPanel.setBounds(menuWidth/4-60, 200, 110, 120);
		add(movePatternPanel);
		
		// Eingabefeld PlayerName
		nameLabel.setBounds(menuWidth/4-80, 340, 40, 20);
		nameField.setBounds(menuWidth/4-30, 340, 100, 20);
		add(nameLabel);
		add(nameField);
		
		// StartButton
		start_btn = new JButton("Spiel starten");
		start_btn.setBounds(menuWidth/4-btnWidth/2, 380, btnWidth, btnHeight);
		start_btn.addActionListener(this);
		add(start_btn);
		
		// EndButton
		end_btn = new JButton("Ende");
		end_btn.setBounds(menuWidth/4-btnWidth/2, 440, btnWidth, btnHeight);
		end_btn.addActionListener(this);
		add(end_btn);
		
		// HighscoreList
		highscoreLabel.setForeground(Color.black);
		highscoreLabel.setBounds(menuWidth/4+menuWidth/2-35, 160, 70, 20);
		add(highscoreLabel);
		for(int i = 0; i < maxScoresToSave; i++) {
			// Name
			JLabel highscoreEntryNameLabel = new JLabel();
			highscoreEntryNameLabel.setText(HighscoreList.getArray()[i].getName());
			highscoreEntryNameLabel.setBounds(menuWidth/4+menuWidth/3+20, 190+(i*30), 120, 20);
			add(highscoreEntryNameLabel);
			// Score
			JLabel highscoreEntryScoreLabel = new JLabel();
			if(HighscoreList.getArray()[i].getScore() < 10) {
				highscoreEntryScoreLabel.setText("0" + String.valueOf(HighscoreList.getArray()[i].getScore()));
			} else {
				highscoreEntryScoreLabel.setText(String.valueOf(HighscoreList.getArray()[i].getScore()));
			}
			highscoreEntryScoreLabel.setBounds(menuWidth/4+menuWidth/2+60, 190+(i*30), 30, 20);
			add(highscoreEntryScoreLabel);
		}
		
		
		// Copyright
		copyrightLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		copyrightLabel.setForeground(Color.black);
		copyrightLabel.setBounds(menuWidth-100, menuHeight-70, 100, 40);
		add(copyrightLabel);
	}
	
	// Listener
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == start_btn) {
			if(nameField.getText().equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(this, "Please enter a name first!", "Missing Name", JOptionPane.WARNING_MESSAGE);
			} else {
				spaceShooter game = new spaceShooter(fullscreen, movePattern, nameField.getText(), scoreboard);
				new Thread(game).start();
			}
		}
		
		if(e.getSource() == end_btn) {
			System.exit(0);
		}
		
		if(e.getSource() == fullscreen_chbx) {
			if(fullscreen_chbx.isSelected()) {
				fullscreen = true;
			} else {
				fullscreen = false;
			}
		}
		
		if(e.getSource() == movePattern1_btn) {
			movePattern = 0;
		}
		
		if(e.getSource() == movePattern2_btn) {
			movePattern = 1;
		}
		
		if(e.getSource() == movePattern3_btn) {
			movePattern = 2;
		}
		
		if(e.getSource() == movePatternExp_btn) {
			JOptionPane.showMessageDialog(this, "MovePatterns change the way your enemies behave:\n"
					+ "1: Enemies don't follow the player.\n"
					+ "2: Enemies use an axis-based move-pattern to follow the player.\n"
					+ "3: Enemies use a vector-based move-pattern to follow the player.");
		}
	}
}