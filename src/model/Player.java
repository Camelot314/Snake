package model;

import java.awt.Color;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Class the represents a player. The player has a heading which is the direction
 * it is traveling it. It also has an integer representing how many growth squares
 * it still has left. It also has an id. The player cells are put in a Deque
 * which represents the snake. To move you add a cell to the front of the Deque
 * and remove the last cell in the Deque.
 * Copyright (c) 2021. 
 * @author Jaraad Kamal
 *
 */
public class Player extends GameObject{
	private static final int INCREMENT_SIZE = 1;
	private Deque<Cell> cells;
	private Direction heading;
	private int length, growthLeft, id;
	
	/**
	 * Constructor. This makes the object type to unbreakable. It also sets up 
	 * the cell, the player color and the id for debugging.
	 * @param cell
	 * @param color
	 * @param id
	 */
	public Player(Cell cell, Color color, int id) {
		super(cell, CellState.UNBREAKABLE, color);
		this.heading = Direction.getRandom();
		this.id = id;
		length = 1;
		
		cells = new LinkedList<Cell>();
		cells.addFirst(getCell());
	}
	
	/**
	 * This will set the heading of the player. The player is not allowed to 
	 * change its heading to the complete opposite of its current heading. This
	 * is to prevent the snake from going backwards and hitting itself. The only
	 * exception to this rule is when the playerLength is 1. 
	 * @param direction
	 */
	public void setHeading(Direction direction) {
		if (Direction.getOpposite(heading) != direction) {
			heading = direction;
		} else if (length == 1) {
			heading = direction;
		}
	}
	
	/**
	 * Returns the id of the player
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the current heading.
	 * @return Direction
	 */
	public Direction getHeading() {
		return heading;
	}
	
	/**
	 * Gets the current length.
	 * @return
	 */
	public int getLength() {
		return length;
	}
	
	
	@Override
	public int getRectSize() {
		return Cell.getCellSize() - 2;
	}
	
	@Override
	public int getOffset() {
		return 1;
	}
	
	/**
	 * When called it will change the head of the queue to the next cell that the
	 * snake will reach. If it is not currently growing it will return the cell
	 * which was removed from the queue as it moved. This cell is then removed
	 * from the used cells list in the game model.
	 * @param nextCell
	 * @return Optional<Cell> the cell to remove or empty
	 */
	public Optional<Cell> move(Cell nextCell) {
		cells.addFirst(nextCell);
		setCell(nextCell);
		if (growthLeft == 0) {
			return Optional.of(cells.pollLast());
		}
		growthLeft --;
		length ++;
		return Optional.empty();
	}
	
	/**
	 * will start the grow process.
	 */
	public void grow() {
		growthLeft = INCREMENT_SIZE;
	}
	
	@Override
	public String toString() {
		return "player id: " + id;
	}
	
}
