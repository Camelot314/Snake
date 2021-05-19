package gui;

import java.awt.Graphics;

/**
 * Interface for a displyable object. 
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public interface Displayable {
	
	/**
	 * Re renders the object.
	 */
	public void render(Graphics g);
}
