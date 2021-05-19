package model;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * This is the class that houses all the cells, gameObjects and game algorithms.
 * The cells are set up like a graph with designated neighbors. The list of 
 * total cell is one large array. When the next step is called then it will
 * determine the next cell the player is moving to by the player heading and the
 * cell neighbors. It will then move the player and remove any unused cells. 
 * Copyright (c) 2021. 
 * @author Jaraad Kamal
 *
 */
public class GameModel {
	private static final int CELL_SIZE = 25;
	public static final String HIGH_SCORE_LOCATION = "Assets/HighScoresFolder/highScores.bin";
	private static GameModel onePlayerInstance, twoPlayerInstance;
	private static LinkedList<HighScore> highScores = new LinkedList<>();
	private final  Random random;
	private Map<Cell, GameObject> usedCells;
	private LinkedList<Cell> unusedCells;
	private Cell[] allCells;
	private Map<Direction, Integer> cellNeighbors;
	private Player player1, player2;
	private Apple apple;
	private int gridWidth, gridHeight, losingIndex = 0;
	private boolean twoPlayer, gameOver, needToCallNextP1, needToCallNextP2;
	
	/**
	 * Static initialization block that sets up the highScores list.
	 */
	static {
		synchronized(highScores) {
			highScores = FileHandler.readList(HIGH_SCORE_LOCATION);
		}
	}
	
	
	/**
	 * Constructor that uses the provided random object. This is used for testing
	 * and debugging.
	 * @param totalWidth
	 * @param totalHeight
	 * @param twoPlayer
	 * @param random
	 */
	private GameModel(int totalWidth, int totalHeight, boolean twoPlayer, Random random) {
		gridWidth = totalWidth / CELL_SIZE;
		gridHeight = totalHeight / CELL_SIZE;		
		this.twoPlayer = twoPlayer;
		if (random != null) {
			this.random = random;
		} else {
			this.random = random = new Random();
		}
		
		if (gridWidth < 4 || gridHeight < 4) {
			throw new IllegalArgumentException("grid is too small");
		}
		Cell.setUp(CELL_SIZE, gridWidth, gridHeight);
		usedCells = new HashMap<>();
		unusedCells = new LinkedList<>();
		cellNeighbors = new HashMap<>();
		
		
		fillLists();		
		createPlayerAndApple(totalWidth, totalHeight, twoPlayer, random);
	}

	

	/**
	 * Generates the players and apple
	 * @param totalWidth
	 * @param totalHeight
	 * @param twoPlayer
	 * @param random
	 */
	private void createPlayerAndApple(int totalWidth, int totalHeight, boolean twoPlayer, Random random) {
		/*
		 * Creating the random player spawn location.
		 */
		int player1XSpawn = this.random.nextInt(totalWidth - 2 * CELL_SIZE) + CELL_SIZE;
		int player1YSpawn = this.random.nextInt( totalHeight - 2 * CELL_SIZE) + CELL_SIZE;
		
		
		/*
		 * getting the cells for both the player spawn and the apple spawn. 
		 */
		Cell player1Cell = allCells[Cell.getCellId(player1XSpawn, player1YSpawn)];
		Cell appleCell = unusedCells.get(random.nextInt(unusedCells.size()));
		
		player1 = new Player(player1Cell, Color.WHITE, 1);
		useCell(player1Cell, player1);
		apple = new Apple(appleCell);
		useCell(appleCell, apple);
		
		if (twoPlayer) {
			Cell player2Cell = unusedCells.get(random.nextInt(unusedCells.size()));
			player2 = new Player(player2Cell, Color.BLUE, 2);
			useCell(player2Cell, player2);
		}
		
		needToCallNextP1 = false;
		needToCallNextP2 = false;
	}

	/*
	 * Creating the unused and all cells lists.
	 */
	private void fillLists() {
		unusedCells = new LinkedList<>();
		allCells = new Cell[gridWidth * gridHeight];
		int offSet = CELL_SIZE / 2;
		
		Cell toAdd = null;
		
		for (int row = 0; row < gridHeight; row ++) {
			for (int col = 0; col < gridWidth; col ++) {
				int xPos = offSet + col * CELL_SIZE;
				int yPos = offSet + row * CELL_SIZE;
				int id = Cell.getCellId(xPos, yPos);
				
				toAdd = new Cell(xPos,yPos, CellState.EMPTY , id);
				allCells[id] = toAdd;
				unusedCells.add(toAdd);
			}
		}
		
		/*
		 * Creating the map of calculations to get to neighbor;
		 */
		int[] calc = { -1 * gridWidth, 1, gridWidth, -1};
		Direction[] directions = Direction.values();
		for (int i = 0; i < 4; i ++) {
			cellNeighbors.put(directions[i], calc[i]);
		}
	}
	
	/**
	 * Private Constructor that does not use the provided random object. To
	 * only allow one instance of the class. This is the constructor used when 
	 * not debugging. 
	 * @param totalWidth
	 * @param totalHeight
	 * @param twoPlayer
	 */
	private GameModel(int totalWidth, int totalHeight, boolean twoPlayer) {
		this (totalWidth, totalHeight, twoPlayer, null);
	}

	
	/**
	 * Static method that gets the one instance of the class. There is one instance
	 * for 1 player and a separate instance for 2 player. 
	 * @param totalWidth
	 * @param totalHeight
	 * @param twoPlayer
	 * @return instance of GameModel. 
	 */
	public static GameModel getInstance(int totalWidth, int totalHeight, boolean twoPlayer) {
		return getInstance(totalWidth, totalHeight, twoPlayer, null);
	}
	
	/**
	 * Static method that gets the one instance for one player and one instance 
	 * for two player. This is the method used when debugging to get predictable
	 *  random values. 
	 * @param totalWidth
	 * @param totalHeight
	 * @param twoPlayer
	 * @param random
	 * @return instance of GameModel.
	 */
	public static GameModel getInstance(int totalWidth, int totalHeight,
			boolean twoPlayer, Random random) {
		
		GameModel instance = twoPlayer ? twoPlayerInstance : onePlayerInstance;
		if (instance == null) {
			instance = new GameModel(totalWidth, totalHeight, twoPlayer, random);
			if (twoPlayer) {
				twoPlayerInstance = instance;
			} else {
				onePlayerInstance = instance;
			}
		}
		return instance;
	}
	
	/**
	 * Gets the highScores list.
	 * @return LinkedList<HighScores> highScores;
	 */
	public static LinkedList<HighScore> getHighScores() {
		return highScores;
	}

	/**
	 * Returns the cell size.
	 * @return int cell size.
	 */
	public static int getCellSize() {
		return CELL_SIZE;
	}

	/**
	 * Returns the number of cells it has in the width direction.
	 * @return int gridWidth.
	 */
	public int getGridWidth() {
		return gridWidth;
	}

	/**
	 * Returns the number of cells it has in the height direction.
	 * @return int gridHeight
	 */
	public int getGridHeight() {
		return gridHeight;
	}
	
	/**
	 * Will return true if game is over
	 * @return boolean
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	
	/**
	 * Rechecks the list with the file
	 */
	public static void recheckList() {
		synchronized(highScores) {
			highScores = FileHandler.readList(HIGH_SCORE_LOCATION);
		}
	}

	/**
	 * Will write the highScore if it is one to a file.
	 */
	public boolean addHighScore() {
		if (gameOver && !twoPlayer) {
			for (int i = 0; i < highScores.size(); i ++) {
				int score = highScores.get(i).getScore();
				if (score < player1.getLength()) {
					addScore();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Will add a high score to the list if the current single player score is 
	 * higher than any of the 10 current scores. Tt will also immediately write 
	 * it to a file.
	 */
	private synchronized void addScore() {
		highScores.add(new HighScore(player1.getLength(), System.currentTimeMillis()));
		Collections.sort(highScores);
		highScores.pollLast();		
		FileHandler.writeList(highScores, HIGH_SCORE_LOCATION);
	}


	
	/**
	 * Returns 1 if player 1 lost, 2 if player 2 lost, or 0 if game still going.
	 * @return Integer
	 */
	public int getLosingPlayer() {
		if (!gameOver) {
			return 0;
		}
		if (!twoPlayer) {
			return 1;
		}
		return losingIndex;
	}
	
	/**
	 * Returns the score of the winning player (or player 1 if single player)
	 * score is determined by the length of the player.
	 * @return int score
	 */
	public int getScore() {
		int toReturn = 0;
		switch(getLosingPlayer()) {
			case 1 : toReturn =  twoPlayer ? player2.getLength() : player1.getLength();
				break;
			case 2 : toReturn =  player1.getLength();
				break;
			default : return 0;
		}
		return toReturn;
	}
	
	/**
	 * Returns a map of the game objects. 
	 * @return Map of game objects.
	 */
	public Map<Cell, GameObject> getUsedCells() {
		return usedCells;
	}
	
	/**
	 * Changes the heading of the two players given an integer corresponding to
	 * as keyPress. Will return true if both player 1 and (if present) player 2
	 * pressed a key. The need to call next step is that that the game will not
	 * change the heading twice per player before rendering out the next frame.
	 * This prevents the player from going backwards when pressing both the back
	 * and up key at the same time. (Or down and left.)
	 * @param keyPress
	 * @return boolean.
	 */
	public boolean changeHeading(int keyPress) {
		
		if (needToCallNextP1 || needToCallNextP2) {
			return false;
		}
		
		needToCallNextP1 = true;
		boolean changed = true;
		switch(keyPress) {
			case KeyEvent.VK_W : player1.setHeading(Direction.NORTH);
				break;
			case KeyEvent.VK_A : player1.setHeading(Direction.WEST);
				break;
			case KeyEvent.VK_S : player1.setHeading(Direction.SOUTH);
				break;
			case KeyEvent.VK_D : player1.setHeading(Direction.EAST);
				break;
			default : changed = false; needToCallNextP1 = false;
		}
		
		if (twoPlayer) {
			needToCallNextP2 = true;
			switch(keyPress) {
				case KeyEvent.VK_UP : player2.setHeading(Direction.NORTH);
					break;
				case KeyEvent.VK_LEFT : player2.setHeading(Direction.WEST);
					break;
				case KeyEvent.VK_DOWN : player2.setHeading(Direction.SOUTH);
					break;
				case KeyEvent.VK_RIGHT : player2.setHeading(Direction.EAST);
					break;
				default : needToCallNextP2 = false;
			}
		}
		if (twoPlayer) {
			return keyPress == KeyEvent.VK_SPACE;
		}
		return changed;
	}
	
	/**
	 * This method performs the game logic on each step. First it will check if 
	 * the next cell player 1 will go to is open. If it is then it will move the 
	 * player. If it is not, then it will check to see if the filled cell is 
	 * breakable or not. If the cell is not breakable then it will set the game
	 * to over and end the game. If the cell is breakable (it is an apple) then 
	 * it will lengthen the player and add another apple. 
	 * @return Map of all the current game objects that need to be displayed. 
	 */
	public Map<Cell, GameObject> nextAnimationStep() {
		if (twoPlayer) {
			animatePlayer(player2);
		}
		needToCallNextP1 = false;
		needToCallNextP2 = false;
		return animatePlayer(player1);
		
		
	}
	
	/**
	 * returns true if it is two player
	 * @return boolean.
	 */
	public boolean isTwoPlayer() {
		return twoPlayer;
	}
	
	/**
	 * this will clear all instances of the class. This is used when making 
	 * a new game or restarting a game.
	 */
	public static void clearInstnaces() {
		onePlayerInstance = null;
		twoPlayerInstance = null;
	}

	/**
	 * Animates the given player object.
	 * @return a map of all the used cells
	 */
	private Map<Cell, GameObject> animatePlayer(Player player) {
		Cell playerCurrCell, playerNextCell;
		playerCurrCell = player.getCell();
		int idOfNext = playerCurrCell.getId() + cellNeighbors.get(player.getHeading());
		
		if (!isValidId(idOfNext,playerCurrCell.getId(), player.getHeading())) {
			synchronized(this) {
				losingIndex = player.getId();
				gameOver = true;
			}
			return usedCells;
		}
		
		
		
		playerNextCell = allCells[idOfNext];
		if (!useCell(playerNextCell, player)) {
			synchronized(this) {
				returnCell(player.move(playerNextCell).orElse(null));
			}
		} else {
			if (playerNextCell.getState() == CellState.BREAKABLE) {
				useApple(player, playerNextCell);
			} else {
				synchronized(this) {
					gameOver = true;
				}
			}
		}
		return usedCells;
	}
	
	/**
	 * Method called when the player hits the apple. It will assign the apple 
	 * to another random cell and increase the player length. It will then also
	 * continue the animation so the player keeps moving forward.
	 * @param player
	 * @param oldAppleCell
	 */
	private void useApple(Player player, Cell oldAppleCell) {
		synchronized(player) {
			player.grow();
		}
		int nextCellIndex = random.nextInt(unusedCells.size());
		synchronized(this) {
			returnCell(player.move(oldAppleCell).orElse(null));
			
			// re-purposing apple previous cell for next player cell
			usedCells.put(oldAppleCell, player);
			
			
			
			Cell nextAppleCell = unusedCells.get(nextCellIndex); 
			apple.setCell(nextAppleCell);
			unusedCells.remove(nextAppleCell);
			usedCells.put(nextAppleCell, apple);
		}
		
	}
	
	
	/**
	 * Will return true if the next id is a valid id for the list of cells.
	 * It will return false if the id is out of the range of the array (this 
	 * detects top and bottom collisions). It will then return false if the 
	 * direction is a horizontal direction and the next id is on a different row
	 * then the previous id (this detects horizontal collisions with walls).
	 * @param idNext
	 * @param idCurr
	 * @param direction
	 * @return
	 */
	private boolean isValidId(int idNext, int idCurr, Direction direction) {
		boolean correctRow = idNext / gridWidth == idCurr / gridWidth 
				|| Direction.isVeritcal(direction);
		
		return idNext >= 0 && idNext < allCells.length && correctRow;
		
	}
	

	
	/**
	 * Moves the cell to the used cells list and removes it from the unused cells
	 * list. If the gameObject parameter is null then it will do nothing and return
	 * false. If the cell parameter is null then it will pick a random cell from
	 * the unused (this is used when placing an apple). The method will return true
	 * if there was a collision false otherwise.
	 * @param cell
	 * @param gameObject
	 * @return will return false if gameObject is null or there was no collision
	 */
	private boolean useCell(Cell cell, GameObject gameObject) {
		if (gameObject != null) {
			if (cell != null && !unusedCells.contains(cell)) {
				return true;
			}
			Cell inQuestion = cell == null ? getRandomUnusedCell().orElse(null) : cell;
			
			if (cell == null) {
				return true;
			}
			synchronized(unusedCells) {
				unusedCells.remove(inQuestion);
				cell.setState(gameObject.getObjectType());
				usedCells.put(inQuestion, gameObject);
			}
			
			
			
			return false;
		}
		return false;
	}
	
	/**
	 * Will return an optional. The optional will either contain a random unused
	 * cell or nothing if there were no unused cells left. 
	 * @return
	 */
	private Optional<Cell> getRandomUnusedCell() {
		if (unusedCells.size() != 0) {
			return Optional.of(unusedCells.get(random.nextInt(unusedCells.size())));
		}
		
		return Optional.empty();
	}
	
	/**
	 * Returns a used cell back to empty. Adds it to the unusedCells list. Will 
	 * do nothing if the parameter is null.
	 * @param cell
	 */
	private void returnCell(Cell cell) {
		if (cell != null) {
			usedCells.remove(cell);
			cell.setState(CellState.EMPTY);
			unusedCells.add(cell);
			
		}
	}
	
}
