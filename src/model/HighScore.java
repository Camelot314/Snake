package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
/**
 * Class that keeps track of all the information about the high score (except 
 * the rank of the score).
 * Copyright (c) 2021. 
 * @author Jaraad Kamal
 *
 */
public class HighScore implements Serializable, Comparable<HighScore> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2356896020854831058L;
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("HH:mm:ss MM-dd-yyyy");
	private String dateStr;
	private int score;
	private long milliseconds;
	
	/**
	 * Constructor. This takes in an int for the score and a long for the time
	 * in milliseconds. The date string is either "NO_DATE" if the time is 
	 * negative or the formated date if it is a positive number.
	 * @param score
	 * @param milliseconds
	 */
	public HighScore(int score, long milliseconds) {
		this.score = score;
		this.milliseconds = milliseconds;
		if (milliseconds > 0) {
			dateStr = FORMATTER.format(milliseconds);
		} else {
			dateStr = "NO_DATE";
		}
		
	}

	/**
	 * Default constructor. This make the score 0 and makes the date -1. When
	 * the date is -1 then the display date is "NO_DATE"
	 */
	public HighScore() {
		this(0, -1);
	}
	
	@Override
	public String toString() {
		String scoreSpaces = "", answer;
		if (score == 0) {
			scoreSpaces = "   ";
		} else {
			for (int i = 0; i < 3 - Math.round((Math.log(score) / Math.log(10))); i ++) {
				scoreSpaces += " ";
			}
		}
		
		answer =  "    Score: ";
		answer += score + scoreSpaces + "    Date: ";
		answer += dateStr + "    ";
		return answer;
	}
	
	/**
	 * CompareTo method. This sorts objects first by the score with highest 
	 * appearing first. Then if the scores are the same it will put the 
	 * object with the earlier date first.
	 */
	@Override
	public int compareTo(HighScore other) {
		int initial = other.score - score;
		if (initial != 0) {
			return initial;
		}
		if (milliseconds >= 0 && other.milliseconds >= 0) {
			return Long.compare(milliseconds, other.milliseconds);
		}
		
		return milliseconds < 0 ? -1 : 1;
	}
	
	/**
	 * Returns the dateString
	 * @return String dateString
	 */
	public String getDateStr() {
		return dateStr;
	}

	/**
	 * Returns the score associated with the high score.
	 * @return int score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Returns the time in milliseconds of when the high score object was 
	 * originally made. 
	 * @return long milliseconds.
	 */
	public long getMilliseconds() {
		return milliseconds;
	}
}
