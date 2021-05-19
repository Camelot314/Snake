package gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * Creates the java display window to show the game. This is a singletonClass.
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public class Window extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7446192599263749848L;
	public static Window instance;
	private Game game;
	private JFrame frame;

	/**
	 * Private constructor for generating single instace of class.
	 * @param width
	 * @param height
	 * @param title
	 * @param game
	 */
	private Window(int width, int height, String title, Game game) {
		this.game = game;
		frame = new JFrame(title);

		Image icon = Toolkit.getDefaultToolkit().getImage("Assets/Icon.png");  
		frame.setIconImage(icon);  
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		
	}
	
	/**
	 * Static method that gets the one instance of the class
	 * @param width
	 * @param height
	 * @param title
	 * @param game
	 * @return Window instance.
	 */
	public static Window getInstance(int width, int height, String title, Game game) {
		if (instance == null) {
			instance = new Window(width, height, title, game);
		}
		return instance;
	}
	
	
	/**
	 * Method that makes the window visible and adds the game. This will start
	 * everything up.
	 */
	public void addGame() {
		frame.add(game);
		frame.setVisible(true);
	}
	
}
