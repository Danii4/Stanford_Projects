/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import acm.util.*;
import java.util.*;
import java.io.*;

public class NameSurferDataBase implements NameSurferConstants {

	private ArrayList<NameSurferEntry> entries;

	/**
	 * Constructor: NameSurferDataBase(filename)
	 * Creates a new NameSurferDataBase and initializes it using the
	 * data in the specified file.  The constructor throws an error
	 * exception if the requested file does not exist or if an error
	 * occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		this.entries = new ArrayList<>();
		try {
			Scanner input = new Scanner(new File(filename));
			while(input.hasNextLine()){
				String line = input.nextLine();
				NameSurferEntry entry = new NameSurferEntry(line);
				entries.add(entry);
			}
			input.close();
		} catch (FileNotFoundException ex) {
			System.out.println("The error is: " + ex);
		}
	}

	/**
	 * Public Method: findEntry(name)
	 * Returns the NameSurferEntry associated with this name, if one
	 * exists.  If the name does not appear in the database, this
	 * method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		String requiredNameLowerCase = name.toLowerCase();
		for(int i = 0; i < entries.size(); i++) {
			NameSurferEntry entry = entries.get(i);
			String entryName = entry.getName();
			String entryNameLowerCase = entryName.toLowerCase();
			if (requiredNameLowerCase.equals(entryNameLowerCase)) {
				// entry with matching name found
				return entry;
			}
		}
		// entry not found
		return null;
	}
}
