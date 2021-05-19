package gui;

/**
 * Interface for classes that will appear on top of the already displayed image.
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public interface OverHead extends Displayable{
	/**
	 * Returns the size of the box button to be pressed.
	 * @return Box
	 */
	public Box getBox();
	
	/**
	 * Returns true if it is visible;
	 * @return
	 */
	public boolean isVisible();
	
	
	/**
	 * Sets the visibility parameter
	 * @param visible
	 */
	public void setVisible(boolean visible);
}
