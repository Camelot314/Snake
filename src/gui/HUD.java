package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Displays the menu button at the top of the page in every page except the Menu.
 * This is a Heads Up Display. Singleton class
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public class HUD implements OverHead {
	private static HUD instance;
	private boolean visible;
	private Box hudBox;
	private Handler handler;
	
	
	/**
	 * Private for singleton class.
	 * @param handler
	 */
	private HUD(Handler handler) {	
		this.handler = handler;
		hudBox = new Box(0, 0, 70, 40);
	}
	
	/**
	 * Getter for visible.
	 * @return will return true if it is visible.
	 */
	@Override
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Will set the visibility parameter.
	 * @param visible
	 */
	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Static get instance method to have singleton class.
	 * @param handler
	 * @return
	 */
	public static HUD getInstance(Handler handler) {
		if (instance == null) {
			instance = new HUD(handler);
			GameState activeStates[] = {
					GameState.CONTROLS, GameState.ONEPLAYER,
					GameState.TWOPLAYER, GameState.PAUSED, GameState.SCORES
			};
			Button button = new Button(instance.hudBox, instance.handler, ButtonID.HUD, 
					activeStates, (handlerToUse) -> {
				handlerToUse.setState(GameState.MENU);
			});
			MouseInput.getInstance().addButton(button);
		}
		return instance;
	}

	@Override
	public void render(Graphics g) {
		if (visible) {
			g.setFont(new Font("arial", 1, 15));
			g.setColor(Color.WHITE);
			g.drawString("Menu", 15, 25);
		}
	}
	
	@Override
	public Box getBox() {
		return hudBox;
	}

}
