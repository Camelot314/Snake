package model;

import java.util.Random;

/**
 * The four cardinal directions in the game. This also contains some utilities
 * methods that are useful in the game algorithms.
 * Copyright (c) 2021. 
 * @author Jaraad Kamal
 *
 */
public enum Direction {
	NORTH, EAST, SOUTH, WEST;
	private static final Random RANDOM = new Random();
	
	/**
	 * Will return the opposite direction of the given. 
	 * @param initial
	 * @return Direction in opposite direction
	 */
	public static Direction getOpposite(Direction initial) {
		return getDirection(initial.ordinal() + 2);
	}
	
	/**
	 * Will return true if the given direction is a vertical direction. False 
	 * otherwise.
	 * @param direction
	 * @return boolean.
	 */
	public static boolean isVeritcal(Direction direction) {
		return direction.ordinal() % 2 == 0;
	}
	
	/**
	 * Will return a random direction object. 
	 * @return Direction
	 */
	public static Direction getRandom() {
		return getDirection(RANDOM.nextInt(4));
	}
	
	/**
	 * Returns a direction based off an integer ordinal.
	 * @param oridnal
	 * @return Direction.
	 */
	private static Direction getDirection(int oridnal) {
		int key = Math.abs(oridnal % 4);
		
		switch (key) {
			case 0 : return NORTH;
			case 1 : return EAST;
			case 2 : return SOUTH;
			case 3 : return WEST;
			default : return NORTH;
		}
	}
}
