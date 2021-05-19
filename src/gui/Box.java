package gui;
/**
 * Button box class that makes buttons and pop up boxes
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public class Box {
	private int x, y, width, height, rightBound, bottomBound;
	
	/**
	 * Constructor. This creaetes the box.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Box(int x, int y, int width, int height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		rightBound = x + width;
		bottomBound = y + height;
	}
	
	/**
	 * Will return true if the given coordinate is inside the box.
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean isInside(int x, int y) {
		return x >= this.x && y >= this.y && x <= rightBound && y <= bottomBound;
	}

	@Override
	public String toString() {
		return "Box [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", rightBound=" + rightBound
				+ ", bottomBound=" + bottomBound + "]";
	}

	/**
	 * Returns the x coordinate of the top left corner.
	 * @return int x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y coordinate of the top left corner
	 * @return int y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Returns the width of the rectangle.
	 * @return int width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the rectangle.
	 * @return int height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the x coordinate of the bottom right corner.
	 * @return int rightBound
	 */
	public int getRightBound() {
		return rightBound;
	}

	/**
	 * Returns the y coordinate of the bottom right corner.
	 * @return int bottomBound
	 */
	public int getBottomBound() {
		return bottomBound;
	}
}
