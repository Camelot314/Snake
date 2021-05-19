package model;

import java.awt.Color;

/**
 * Game object class that is an apple
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public class Apple extends GameObject{
	
	/**
	 * Constructor.
	 * @param cell
	 */
	public Apple(Cell cell) {
		super(cell, CellState.BREAKABLE, Color.RED);
	}
	
	@Override
	public int getRectSize() {
		return Cell.getCellSize() - 10;
	}
	
	@Override
	public int getOffset() {
		return 5;
	}
	
	@Override
	public String toString() {
		return "Apple";
	}
}
