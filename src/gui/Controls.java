package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Controls information page
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public class Controls implements Displayable {
	private static Controls instance;
	
	/**
	 * Private Constructor for singleton class;
	 * @param handler
	 */
	private Controls() {
		
	}
	
	public static Controls getInstance() {
		if (instance == null) {
			instance = new Controls();
		}
		return instance;
	}

	@Override
	public void render(Graphics g) {
		Font heading2 = new Font("arial", 2, 20);
		Font text = new Font("arial", 1, 15);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 1, 50));
		g.drawString("Controls", 50, 150);
		
		g.setFont(heading2);
		g.drawString("The Goal:", 50, 200);
		g.drawString("The Controls:", 50, 310);
		g.setFont(text);
		g.drawString("The object of the game is to move around and collect the red apples.", 50, 225);
		g.drawString("The more apples you get, the longer you get. You loose if you hit anything other than an apple", 50, 250);
		
		g.drawString("For 1 Player: Use W, A, S, and D controls to move", 50, 335);
		g.drawString("For 2 Players: Player 1 is white with W, A, S, and D controls", 50, 375);
		g.drawString("Player 2 is blue with Up, Down, Left, and Right controls", 160, 400);
	}

}
