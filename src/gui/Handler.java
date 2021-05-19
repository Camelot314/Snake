package gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Map;

import model.Cell;
import model.GameModel;
import model.GameObject;

/**
 * Updates and render all objects in the game. It communicates between the 
 * different pages in the GUI. It also is the center of the state changes for 
 * the game. Singleton class.
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public class Handler implements Displayable {
	private static Handler instance = null;
	
	private Map<Cell, GameObject> objectMap;
	private Game game;
	private Menu menu;
	private Controls controlsPage;
	private HighScorePage highScorePage;
	private GameModel model;
	private OverHead hud, popUp;
	private GameState prevState;
	
	
	private int width, height;
	private boolean firstPress = false, highScore;
	
	/**
	 * Private constructor for single design.
	 * @param width
	 * @param height
	 * @param game
	 */
	private Handler(int width, int height, Game game, boolean twoPlayer) {
		if (game == null) {
			throw new IllegalArgumentException("null input for game");
		}
		
		this.width = width;
		this.height = height;
		this.game = game;
		controlsPage = Controls.getInstance();
		
		prevState = GameState.MENU;
		
	}
	
	/**
	 * Static getInstance method for singleton design.
	 * @param width
	 * @param height
	 * @param game
	 * @return only instance of the handler class
	 */
	public static Handler getInstance(int width, int height, Game game, boolean twoPlayer) {
		if (instance == null) {
			instance = new Handler(width, height, game, twoPlayer);
			instance.hud = HUD.getInstance(instance);
			instance.popUp = PopUp.getInstance(instance);
			instance.menu = Menu.getInstance(game, instance);
			instance.highScorePage = HighScorePage.getInstance(instance);
			instance.highScorePage.setScoreList(GameModel.getHighScores());
		}
		return instance;
	}
	
	/**
	 * returns the menu
	 * @return menu
	 */
	public Menu getMenu() {
		return menu;
	}
	
	/**
	 * Getter for the width
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Getter for the height
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	
	
	/**
	 * Returns the current game state
	 * @return GameState
	 */
	public GameState getGameState() {
		return game.getState();
	}
	
	/**
	 * Returns text that will be displayed when the game ends. 
	 * @return
	 */
	public String getGameOverText() {
		String answer = "";
		if (model.isGameOver()) {
			if (model.isTwoPlayer()) {
				answer += model.getLosingPlayer() == 1 ? "Player 2" : "Player 1";
				answer += " wins ";
			} else {
				answer += "You lose. You get ";
				answer += "\n" + model.getScore() + " points.";
			}
			
		}
		return answer;
	}
	
	/**
	 * Getter for previous state.
	 * @return GameState
	 */
	public GameState getPrevState() {
		return prevState;
	}
	
	/**
	 * Returns true if the current game was a high score. 
	 * @return boolean
	 */
	public boolean isHighScore() {
		return highScore;
	}
	
	
	
	/**
	 * Method called by the Key input when a key is pressed. It will then call
	 * the model.ChangeHeading() method. If it the first time movement key has been 
	 * pressed then it will start game. If the escape key is pressed at any time
	 * Then the game will change state to the menu.
	 * @param keyPress
	 */
	public void pressKey(int keyPress) {
		if (keyPress == KeyEvent.VK_ESCAPE) {
			showMenu();
		}
		
		if (model != null) {
			if (model.isTwoPlayer()) {
				firstPress = firstPress || keyPress == KeyEvent.VK_SPACE;
				model.changeHeading(keyPress);
			} else {
				firstPress = model.changeHeading(keyPress) || firstPress;
			}
			
		}
	}
	
	/**
	 * performs the logic for the next frame. If the direction key has never
	 * been pressed then it will perform nothing. Once a key is pressed the game
	 * logic will start. If the model says the game is over the handler will 
	 * stop the game loop.
	 */
	public void tick() {
		if (game.getState() == GameState.ONEPLAYER || game.getState() == GameState.TWOPLAYER) {
			if (firstPress) {
				model.nextAnimationStep();
			}
			if (model.isGameOver()) {
				prevState = game.getState();
				game.setState(GameState.PAUSED);
				highScore = model.addHighScore();
			}
		}
		
	}
	
	
	/**
	 * Renders all the game objects  in the object map using the graphics object
	 * g when game state is GAME using the formation about each game object.
	 * @param g
	 */
	@Override
	public void render(Graphics g) {
		hud.render(g);
		switch(game.getState()) {
			case ONEPLAYER : renderGame(g);
				break;
			case TWOPLAYER : renderGame(g);
				break;
			case MENU : renderMenu(g);
				break;
			case PAUSED : renderPaused(g);
				break;
			case CONTROLS: renderControls(g);
				break;
			case SCORES: renderScores(g);
				break;
			default:
				break;
		}
	}
	
	public void setState(GameState state) {
		GameState temp = prevState;
		prevState = game.getState();
		switch(state) {
		case ONEPLAYER : restartGame(false);
			break;
		case TWOPLAYER : restartGame(true);
			break;
		case MENU : showMenu();
			break;
		case CONTROLS: showControls();
			break;
		case SCORES: showScores();
			break;
		default: prevState = temp;
			break;
		}
	}
	
	/**
	 * Restarts the game from the previous status
	 */
	private void restartGame(boolean twoPlayer) {
		startGame(twoPlayer);
	}
	
	/**
	 * Will reset the game and everything
	 */
	private void resetGame() {
		highScore = false;
		game.setTickRate(60);
		hud.setVisible(true);
		GameModel.clearInstnaces();
		model = null;
		objectMap = null;
		firstPress = false;
	}
	
	/**
	 * Starts the game loop
	 * @param twoPlayer
	 */
	private synchronized void startGame(boolean twoPlayer) {
		highScore = false;
		game.setTickRate(15.0);
		hud.setVisible(true);
		GameModel.clearInstnaces();
		model = GameModel.getInstance(width, height, twoPlayer);
		objectMap = model.getUsedCells();
		firstPress = false;
		game.setState(twoPlayer ? GameState.TWOPLAYER : GameState.ONEPLAYER);
	}
	
	/**
	 * Goes to the menu page. The game if on will be lost.
	 */
	private void showMenu() {
		game.setTickRate(60);
		hud.setVisible(false);
		GameModel.clearInstnaces();
		model = null;
		objectMap = null;
		game.setState(GameState.MENU);
		firstPress = false;
	}
	
	/**
	 * Will change the game state to control info state. 
	 */
	private void showControls() {
		resetGame();
		game.setState(GameState.CONTROLS);
	}

	
	
	/**
	 * Will change the game state to high score state.
	 */
	private void showScores() {
		resetGame();
		game.setState(GameState.SCORES);
	}
	
	
	/**
	 * Stops the game and renders the control info page
	 * @param g
	 */
	private void renderControls(Graphics g) {
		hud.render(g);
		if (game.getState() == GameState.CONTROLS) {
			controlsPage.render(g);
		}
	}
	
	/**
	 * Renders all the objects in the game.
	 * @param g
	 */
	private void renderGame(Graphics g) {
		int x, y;
		for (Map.Entry<Cell, GameObject> entry : objectMap.entrySet()) {
			Cell cell = entry.getKey();
			GameObject gameObject = entry.getValue();
			
			x = cell.getXCoord() + gameObject.getOffset();
			y = cell.getYCoord() + gameObject.getOffset();

			g.setColor(entry.getValue().getColor());
			g.fillRect(x, y, gameObject.getRectSize(), gameObject.getRectSize());
		}
	}
	
	/**
	 * Renders the game and the lose pop up
	 * @param g
	 */
	private void renderPaused(Graphics g) {
		renderGame(g);
		popUp.setVisible(true);
		popUp.render(g);
	}

	/**
	 * Renders the menu
	 * @param g
	 */
	private void renderMenu(Graphics g) {
		menu.render(g);
	}

	
	
	/**
	 * Stops the game and renders the high score list;
	 * @param g
	 */
	private void renderScores(Graphics g) {
		hud.render(g);
		highScorePage.setScoreList(GameModel.getHighScores());
		highScorePage.render(g);
	}
}
