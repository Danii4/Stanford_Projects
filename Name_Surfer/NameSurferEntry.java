/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	private String name;
	private int[] ranksOfName;

	/**
	 * Constructor: NameSurferEntry(line)
	 * Creates a new NameSurferEntry from a data line as it appears
	 * in the data file.  Each line begins with the name, which is
	 * followed by integers giving the rank of that name for each
	 * decade.
	 */
	public NameSurferEntry(String line) {
		this.ranksOfName = new int[NDECADES];
		ArrayList<String> parts = split(line);
		this.name = parts.get(0);
		
		for(int i = 1; i < parts.size(); i++) {
			String numberString = parts.get(i);
			int number = Integer.parseInt(numberString); 
			ranksOfName[i - 1] = number;
		}
	}

	/**
	 * Public Method: getName()
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Public Method: getRank(decade)
	 * Returns the rank associated with an entry for a particular
	 * decade. The decade value is an integer indicating how many
	 * decades have passed since the first year in the database,
	 * which is given by the constant START_DECADE.  If a name does
	 * not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		if (decade < 0 || decade > (NDECADES - 1)) {
			return -1;
		}
		return this.ranksOfName[decade];
	}

	/**
	 * Public Method: toString()
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
	public String toString() {
		String result = this.name + " [" + this.ranksOfName[0];
		for (int i = 1; i < this.ranksOfName.length; i++) {
			result += ", " + this.ranksOfName[i];
		}
		result += "]";
		return result;
	}

	private ArrayList<String> split(String line) {
		ArrayList<String> parts = new ArrayList<>();
		String currentStr = "";
		
		for(int i = 0; i < line.length(); i++) {
			char currentChar = line.charAt(i);
			if (currentChar != ' ') {
				currentStr += currentChar;
			} else {
				parts.add(currentStr);
				currentStr = "";
			}
		}
		parts.add(currentStr);
		return parts;
	}
}
