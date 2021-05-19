package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Menu class. This displays the main menu ands adds the buttons to the 
 * mouseInput class. Singleton class.
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public class Menu  implements Displayable {
	private static Menu instance;
	private Handler handler;
	private Box onePlayer, twoPlayer, scores, controls;
	
	/**
	 * Private constructor for singleton design
	 * @param game
	 * @param handler
	 * @param invisibles
	 */
	private Menu(Game game, Handler handler) {
		this.handler = handler;
		if (game.getState() == GameState.MENU) {
			game.setTickRate(60.0);
		}
		int x, y, rectWidth, rectHeight;
	
		/*
		 * player 1 button
		 */
		x = handler.getWidth() * 13 / 18;
		y = handler.getHeight() * 5 / 8;
		rectWidth = handler.getWidth() / 4;
		rectHeight = handler.getHeight() / 9;
		onePlayer = new Box(x, y, rectWidth, rectHeight);
		addButton(onePlayer, GameState.ONEPLAYER, ButtonID.ONE);
		
		/*
		 * player 2 button. 
		 */
		
		x = handler.getWidth() * 13 / 18;
		y = handler.getHeight() * 13 / 16;
		rectWidth = handler.getWidth() / 4;
		rectHeight = handler.getHeight() / 9;
		
		twoPlayer = new Box(x, y, rectWidth, rectHeight);
		addButton(twoPlayer, GameState.TWOPLAYER, ButtonID.TWO);
		
		/*
		 * Scores button 
		 */
		x = handler.getWidth() - onePlayer.getRightBound();
		y = handler.getHeight() * 5 / 8;
		rectWidth = handler.getWidth() / 4;
		rectHeight = handler.getHeight() / 9;
		scores = new Box(x, y, rectWidth, rectHeight);
		addButton(scores, GameState.SCORES, ButtonID.SCORES);
		
		/*
		 * Controls button
		 */
		x = handler.getWidth() - onePlayer.getRightBound();
		y = handler.getHeight() * 13 / 16; 
		rectWidth = handler.getWidth() / 4;
		rectHeight = handler.getHeight() / 9;
		controls = new Box(x, y, rectWidth, rectHeight);
		addButton(controls, GameState.CONTROLS, ButtonID.CONTROLS);
	}
	
	/**
	 * Gets the single instance of the class. This class is a singleton.
	 * @param game
	 * @param handler
	 * @param invisibles
	 * @return Menu instance
	 */
	public static Menu getInstance(Game game, Handler handler) {
		if (instance == null) {
			instance = new Menu(game, handler);
		}
		return instance;
	}

	@Override
	public void render(Graphics g) {
		int y, rectHeight;
		
		/*
		 * Drawing main name
		 */
		g.setColor(Color.WHITE);
		y = handler.getHeight() / 5;
		rectHeight = handler.getHeight() / 5;
		
		Font headingFont = new Font("arial", 1, 80);
		g.setFont(headingFont);
		g.drawString("Snake", handler.getWidth() / 2 - 120, y + rectHeight - 30);
		
		
		
		/*
		 * Drawing all the boxes
		 */
		g.setFont(new Font("arial", 1, 25));
		drawRectangle(g, onePlayer, "One Player");
		drawRectangle(g, twoPlayer, "Two Player");
		drawRectangle(g, scores, "High Scores");
		drawRectangle(g, controls, "Controls");
	}

	/**
	 * Will draw the rectangles with the given parameters when rendering the 
	 * menu.
	 * @param g
	 */
	private static void drawRectangle(Graphics g, Box box, String text) {
		g.drawRect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
		g.drawString(text, box.getX() + 20, box.getY() + 45);
	}
	
	/**
	 * Adds a given button to the mouseListner class.
	 * @param box
	 * @param toSend
	 * @param id
	 */
	private void addButton(Box box, GameState toSend, ButtonID id) {
		GameState actState[] = {GameState.MENU};
		ButtonAction action = (handler) -> {
			handler.setState(toSend);
		};
		MouseInput.getInstance().addButton(new Button(box, this.handler, id, actState, action));
	}
	
}
