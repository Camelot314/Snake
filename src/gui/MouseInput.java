package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class that notifies when a mouse has been pressed. Singleton class.
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public class MouseInput extends MouseAdapter {
	private static MouseInput instance;
	private ArrayList<Button> buttons;
	
	/**
	 * Private constructor for singleton class.
	 * @param handler
	 */
	private MouseInput() {
		buttons = new ArrayList<>();
	}
	
	/**
	 * Get instance method because only one instance of this class is allowed.
	 * @param handler
	 * @return MouseInput Instance.
	 */
	public static MouseInput getInstance() {
		if (instance == null) {
			instance = new MouseInput();
		}
		return instance;
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		for (Button button : buttons) {
			button.pressButton(e);
		}
	}
	
	/**
	 * Adds a button to the list of all the buttons.
	 * @param button
	 */
	public void addButton(Button button) {
		buttons.add(button);
	}
}
