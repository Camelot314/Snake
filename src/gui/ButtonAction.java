package gui;

/**
 * Functional interface of the action that is performed when a button is pressed
 * Copyright (c) 2021.
 * @author Jaraad Kamal
 */
@FunctionalInterface
public interface ButtonAction {
	public void pressButton (Handler handler);
}
