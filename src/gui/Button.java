package gui;

import java.awt.event.MouseEvent;

/**
 * Class that represents a button.
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public class Button {
	private ButtonAction lambda;
	private Handler handler;
	private Box box;
	private GameState activeStates[];
	private ButtonID id;
	
	
	/**
	 * Constructor that sets up the button.
	 * @param box
	 * @param handler
	 * @param id
	 * @param activeStates
	 * @param lambda
	 */
	public Button(Box box, Handler handler, ButtonID id,GameState activeStates[], ButtonAction lambda) {
		if (handler == null) {
			throw new IllegalArgumentException("null handler for button");
		}
		this.handler = handler;
		this.box = box;
		this.activeStates = activeStates;
		this.lambda = lambda;
		this.id = id;
//		MouseInput.getInstance().addButton(this);
	}
	
	@Override
	public String toString() {
		return "" + id;
	}
	
	/**
	 * Presses the button. If the game state is one of the active states then
	 * it will check to see if the click happened inside the button box.
	 * It it did, then it will execute the lambda expression.
	 * @param e
	 */
	public void pressButton(MouseEvent e) {
		for (GameState state : activeStates) {
			if (state == handler.getGameState() && box.isInside(e.getX(), e.getY())) {
				lambda.pressButton(handler);
				break;
			}
		}
	}
		
	
}
