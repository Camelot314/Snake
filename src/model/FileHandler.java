package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * A utilities class that does the writing, reading, and deleting of the 
 * highScore data.
 * Copyright (c) 2021. 
 * @author Jaraad Kamal
 *
 */
public class FileHandler {
	/**
	 * Writes the given list to a file.
	 */
	public static void writeList(LinkedList<HighScore> list, String fileLocation) {
		synchronized(list) {
			File file = new File(fileLocation);
			try {
				if (!file.exists()) {
					file.createNewFile();
				}
				ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file));
				objOut.writeObject(list);
				objOut.flush();
				objOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Reads and creates a list from a file. If the file does not exist then
	 * it will return a list 10 default highScores.
	 */
	@SuppressWarnings("unchecked")
	public static LinkedList<HighScore> readList(String fileLocation) {
		File file = new File(fileLocation);
		LinkedList<HighScore> list;
		if (file.exists()) {
			try {
				ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(file));
				list = (LinkedList<HighScore>) objIn.readObject();
				objIn.close();
				return list;
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		list = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			list.add(new HighScore());
		}

		return list;
	}
	
	/**
	 * Will delete the specified file
	 * @param fileLocation
	 */
	public static void deleteFile(String fileLocation) {
		File file = new File(fileLocation);
		if (file.exists()) {
			try {
				file.delete();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		GameModel.recheckList();
	}
}
