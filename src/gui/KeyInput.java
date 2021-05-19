package gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class that listens for key presses. Singleton class
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	private static KeyInput instance = null;
	
	/**
	 * private constructor for singleton class.
	 * @param handler
	 */
	private KeyInput(Handler handler) {
		if (handler == null) {
			throw new IllegalArgumentException("null input for handler");
		}
		this.handler = handler;
	}
	
	/**
	 * Gets the one instance of this class.
	 * @param handler
	 * @return KeyInput instance. 
	 */
	public static KeyInput getInstance (Handler handler) {
		if (instance == null) {
			instance = new KeyInput(handler);
		}
		return instance;
	}
	
	/**
	 * Will cause the handler to tell the model that a key is pressed.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		handler.pressKey(key);
	}
}
