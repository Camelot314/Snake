package model;

import java.awt.Color;

/**
 * Abstract class that represents a game object. Each object has a cell type
 * either breakable or unbreakable. It also has a color. The two abstract methods
 * are the getRectSize() which returns the size of the rectangle to be drawn in 
 * the GUI. And the getOffset() which returns the integer offset from the cell
 * coordinate when drawing the object in the GUI. 
 * Copyright (c) 2021. 
 * @author Jaraad Kamal
 *
 */
public abstract class GameObject {
	private Cell cell;
	private CellState objectType;
	private Color color;
	
	/**
	 * Constructor. It will set the cell to the given cell and the cell
	 * state to the given. The xLocal and yLocal come from the current cell. 
	 * @param cell
	 * @param state
	 */
	protected GameObject(Cell cell, CellState state, Color color) {
		this.cell = cell;
		if (state == CellState.EMPTY) {
			throw new IllegalArgumentException("given state is emtpy");
		}
		objectType = state;
		this.cell.setState(state);
		this.color = color;
		
		
	}

	/**
	 * Gets the cell object
	 * @return
	 */
	public Cell getCell() {
		return cell;
	}
	
	/**
	 * Sets the cell object to the given parameter. 
	 * @param cell
	 */
	public void setCell(Cell cell) {
		this.cell = cell;
		cell.setState(objectType);
	}

	/**
	 * Gets the x coordinate of the cell in cell space.
	 * @return int x local coordinate. 
	 */
	public int getXLocal() {
		return cell.getXLocal();
	}

	/**
	 * Gets the y coordinate of the cell in cell space. 
	 * @return int y local coordinate. 
	 */
	public int getYLocal() {
		return cell.getYLocal();
	}

	/**
	 * Gets the object type. 
	 * @return an enumerator, breakable or unbreakable.
	 */
	public CellState getObjectType() {
		return objectType;
	}
	
	
	/**
	 * Gets the color used for displaying.
	 * @return color of object.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Will return the size of the square to draw when rendering object. 
	 * @return int length of square size.
	 */
	public abstract int getRectSize();
	
	/**
	 * Will return the offset needed to display the object because the rectangle
	 * size is less than the cell size. 
	 * @return int that is the offset.
	 */
	public abstract int getOffset();
	
}
