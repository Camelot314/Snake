package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import model.Cell;
import model.CellState;
import model.FileHandler;
import model.GameModel;
import model.HighScore;

/**
 * Some tests that were created as the program was being written.
 * Copyright (c) 2021. 
 * @author Jaraad Kamal
 *
 */
class Tests {

	@Test
	public void test() {
		Cell.setUp(2, 10, 10);
		LinkedList<Cell> unusedCells = new LinkedList<>();
		
		
		for (int row = 0; row < 10; row ++ ) {
			for (int col = 0; col < 10; col ++ ) {
				unusedCells.add(new Cell(row, col, CellState.EMPTY, row));
			}
		}
		
		System.out.println(unusedCells);
		Collections.shuffle(unusedCells);
		System.out.println("========");
		System.out.println(unusedCells);
	}
	
	@Test
	public  void mappingTest() {
		int cellSize = 10, totalWidth = 20, totalHeight = 20;
		
		int gridWidth = totalWidth / cellSize, gridHeight = totalHeight / cellSize;
		
		Cell.setUp(cellSize, gridWidth, gridHeight);
		LinkedList<Cell> unusedCells = new LinkedList<>();
		CellState emp = CellState.EMPTY;
		int offSet = cellSize / 2, id = 0;
		
		for (int row = 0; row < gridHeight; row ++) {
			for (int col = 0; col < gridWidth; col ++) {
				int xPos = offSet + col * cellSize;
				int yPos = offSet + row * cellSize;
				
				
				unusedCells.add(new Cell(xPos,yPos, emp,  id++));
			}
		}
		System.out.println(unusedCells);
	}
	
	@Test
	public void highScore() {
		System.out.println(new HighScore());
		System.out.println(new HighScore( 200, System.currentTimeMillis()));
		System.out.println(new HighScore( 1, System.currentTimeMillis()));
		System.out.println(new HighScore( 12, System.currentTimeMillis()));
		System.out.println(new HighScore(1000, System.currentTimeMillis()));
	}
	
	@Test
	public void testRead() {
		LinkedList<HighScore> list = FileHandler.readList(GameModel.HIGH_SCORE_LOCATION);
		printList(list);
		System.out.println();
		System.out.println();
		list.add(new HighScore(200, System.currentTimeMillis()));
		list.add(new HighScore(1, System.currentTimeMillis()));
		list.add(new HighScore(12, System.currentTimeMillis()));
		Collections.sort(list);
		list.pollLast();
		list.pollLast();
		list.pollLast();
		printList(list);
		System.out.println();
		System.out.println("=======");
		File file = new File("Assets");
		System.out.println(file.exists());
		assertTrue(file.exists());
//		FileHandler.writeList(list, GameModel.HIGH_SCORE_LOCATION);
	}
	
	private static void printList(Iterable<?> list) {
		for (Object obj : list) {
			System.out.println(obj);
		}
	}
	


}
