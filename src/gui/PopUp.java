package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * This is the display dialog box. It will show up when the game has ended. It
 * has a button that is the try again button. This is a singleton class.
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 *
 */
public class PopUp implements OverHead {
	private static PopUp instance;
	private boolean visible;
	private Box box;
	private Handler handler;
	
	/**
	 * Private for singleton class.
	 * @param handler
	 */
	private PopUp(Handler handler) {		
		if (handler == null) {
			throw new IllegalArgumentException("null handler in hud");
		}
		this.handler = handler;
		int x, y, width, height;
		x = handler.getWidth() / 6;
		y =  handler.getHeight() / 3;
		width =  handler.getWidth() * 2 / 3;
		height =handler.getHeight() / 3; 
		box = new Box(x, y, width, height);
	}
	
	/**
	 * Static get instance method to have singleton class.
	 * @param handler
	 * @return
	 */
	public static PopUp getInstance(Handler handler) {
		if (instance == null) {
			instance = new PopUp(handler);
			GameState activeStates[] = {
					GameState.PAUSED
			};
			Button button = new Button(instance.box, handler, ButtonID.AGAIN, 
					activeStates, (handlerToUse) -> {
						handlerToUse.setState(handlerToUse.getPrevState());
					});
			MouseInput.getInstance().addButton(button);
			
		}
		return instance;
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

	@Override
	public void render(Graphics g) {
		if (visible) {
			g.setColor(Color.WHITE);
			g.fillRect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
			g.setColor(Color.BLACK);
			g.setFont(new Font("arial", 1, 50));
			g.drawString("GAME OVER", box.getX() + 20, box.getY() + 50);
			g.setFont(new Font("arial", 1, 25));
			g.drawString(handler.getGameOverText(), box.getX() + 20, box.getY() + 100);
			if (handler.isHighScore()) {
				g.drawString("New Record Score!!", box.getX() + 20, box.getY() + 130);
			}
			g.drawString("Try Again", box.getX() + 250, box.getY() + 175);
		}
	}
	
	@Override
	public Box getBox() {
		return box;
	}
}
