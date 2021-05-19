package model;

/**
 * A cell is a specific location on the game map. A cell can either be full
 * or empty. If a cell is full then the object inside is either breakable (filled
 * with an apple) or unbreakable (filled with a player). Each cell has a given 
 * size, id, and an xy local coordinate that indicates its position in cell
 * relative space.  
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public final class Cell implements Comparable<Cell> {
	private static int cellSize = 0;
	private static int maxHeight, maxWidth;
	private static boolean hasInstances;
	
	private int xLocal, yLocal, id;
	private CellState state;
	
	/**
	 * Cell constructor. Takes in a x and y coordinate and maps it to a cell. The
	 * cell state parameter indicates the state. 
	 * @param xCoord
	 * @param yCoord
	 * @param state
	 * @param id
	 */
	public Cell(int xCoord, int yCoord, CellState state, int id) {
		if (cellSize == 0) {
			throw new IllegalArgumentException("set the cell size first");
		}
		this.id = id;
		xLocal = xCoord / cellSize;
		yLocal = yCoord / cellSize;
		
		if (!validLocal(xLocal, yLocal)) {
			throw new IndexOutOfBoundsException("the provided location is not on graph");
		}
		this.state = state;
		
	}
	
	/**
	 * Returns the id
	 * @return int id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the x coordinate in Cell local space.
	 * @return int x
	 */
	public int getXLocal() {
		return xLocal;
	}

	/**
	 * Returns the y coordinate in cell local space. 
	 * @return int y
	 */
	public int getYLocal() {
		return yLocal;
	}
	
	/**
	 * Returns the x coordinate in absolute space. This is scaled up from the 
	 * local x coordinate of cell space and the cellSize. 
	 * @return x coordinate. 
	 */
	public int getXCoord() {
		return cellSize * xLocal;
	}
	
	/**
	 * Returns the y coordinate in absolute space. This is scaled up from the 
	 * local y coordinate of cell space and the cellSize.
	 * @return
	 */
	public int getYCoord() {
		return cellSize * yLocal;
	}

	/**
	 * Returns the state of the cell. Either empty breakable or unbreakable. 
	 * @return Enumerator cellState.
	 */
	public CellState getState() {
		return state;
	}	
	
	/**
	 * Sets the cell state. It can either be empty, breakable or unbreakable.
	 * @param state
	 */
	public void setState(CellState state) {
		if (state != null) {
			this.state = state;
		} else {
			this.state = CellState.EMPTY;
		}
	}
	
	
	@Override
	public String toString() {
		return "Cell" + id +" (" + xLocal + "," + yLocal + ") " + state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xLocal;
		result = prime * result + yLocal;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Cell)) {
			return false;
		}
		Cell other = (Cell) obj;
		return other.xLocal == xLocal && other.yLocal == yLocal && id == other.id;
	}
	
	/**
	 * Cells are compared by id number.
	 */
	@Override
	public int compareTo(Cell other) {
		return id - other.id;
	}

	/**
	 * Returns the cell size
	 * @return int static cellSize.
	 */
	public static int getCellSize() {
		return cellSize;
	}
	
	/**
	 * Given an x and y coordinate it will retrieve the corresponding id of the 
	 * cell for that region.
	 * @param x
	 * @param y
	 * @return
	 */
	public static int getCellId(int x, int y) {
		int scale = cellSize == 0 ? 1 : cellSize;
		int xScaled = x / scale, yScaled = y / scale;
		
		return xScaled + maxWidth * yScaled;
	}
	
	/**
	 * Changes the CellSize, maxHeight and maxWidth. 
	 * Only modification of vars can happen once before classes is instantiated.
	 * Calling the method after the class has been instantiated does nothing.
	 * @param cellSize
	 * @param maxHeight
	 * @param maxWidth
	 */
	public static void setUp(int cellSize,  int maxWidth, int maxHeight) {
		
		if (hasInstances || Cell.cellSize != 0 || cellSize <= 0 || maxHeight <= 0 || maxWidth <= 0) {
			return;
		}
		Cell.cellSize = cellSize;
		Cell.maxHeight = maxHeight;
		Cell.maxWidth = maxWidth;
	}
	
	
	/**
	 * Checks if the location parameters are valid
	 * @param x
	 * @param y
	 * @return true if valid false otherwise.
	 */
	public static boolean validLocal(int x, int y) {
		return x >= 0 && x < maxWidth && y >= 0 && y < maxHeight;
	}
}
