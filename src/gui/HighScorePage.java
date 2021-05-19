package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

import model.FileHandler;
import model.GameModel;
import model.HighScore;

/**
 * Class that displays the High scores.
 * @author Jaraad Kamal
 *
 */
public class HighScorePage implements Displayable {
	private static HighScorePage instance;
	private LinkedList<HighScore> scoreList;
	private Box box;
	private Handler handler;
	
	private HighScorePage (Handler handler) {
		box = new Box(350, 500, 200, 50);
		GameState[] activeState = {GameState.SCORES};
		this.handler = handler;
		Button clearScores = new Button(box, this.handler, ButtonID.CLEAR, activeState, 
				(handlerToUse) -> {
					FileHandler.deleteFile(GameModel.HIGH_SCORE_LOCATION);
					scoreList = GameModel.getHighScores();
				});
		MouseInput.getInstance().addButton(clearScores);
	}
	
	/**
	 * Get instance method for singleton class
	 * @return HighScorePage.
	 */
	public static HighScorePage getInstance(Handler handler) {
		if (instance == null) {
			instance = new HighScorePage(handler);
		}
		return instance;
	}
	
	/**
	 * Sets the score list to the given parameter. 
	 * @param scoreList
	 */
	public void setScoreList(LinkedList<HighScore> scoreList) {
		this.scoreList = scoreList;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 1, 50));
		g.drawString("HighScores", 50, 150);
		g.setFont(new Font("arial", 1, 20));
		for (int i = 0; i < scoreList.size(); i ++) {
			HighScore score = scoreList.get(i);
			g.drawString("" + i + ". " + score.toString(), 275, 225 + 25 * i);
		}
		g.drawRect(box.getX(), box.getY(), box.getWidth(),
				box.getHeight());
		g.drawString("Clear all scores", box.getX() + 25, box.getY() + 30);
		
		
	}

}
