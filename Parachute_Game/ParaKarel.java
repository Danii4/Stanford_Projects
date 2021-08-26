/*
 * File: ParaKarel.java
 * ------------------
 * This program will eventually play the ParaKarel game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.util.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParaKarel extends ConsoleProgram {

	/***********************************************************
	 *              CONSTANTS                                  *
	 ***********************************************************/
	
	/* The number of guesses in one game of ParaKarel */
	private static final int N_GUESSES = 7;
	/* The width and the height to make the karel image */
	private static final int KAREL_SIZE = 150;
	/* The y-location to display karel */
	private static final int KAREL_Y = 230;
	/* The width and the height to make the parachute image */
	private static final int PARACHUTE_WIDTH = 300;
	private static final int PARACHUTE_HEIGHT = 130;
	/* The y-location to display the parachute */
	private static final int PARACHUTE_Y = 50;
	/* The y-location to display the partially guessed string */
	private static final int PARTIALLY_GUESSED_Y = 430;
	/* The y-location to display the incorrectly guessed letters */
	private static final int INCORRECT_GUESSES_Y = 460;
	/* The fonts of both labels */
	private static final String PARTIALLY_GUESSED_FONT = "Courier-36";
	private static final String INCORRECT_GUESSES_FONT = "Courier-26";
	
	// Animation delay or pause time 
		public static final double DELAY = 1000.0 / 60.0;
	
	/***********************************************************
	 *              Instance Variables                         *
	 ***********************************************************/
	
	/* An object that can produce pseudo random numbers */
	private RandomGenerator rg = new RandomGenerator();
	
	private GCanvas canvas = new GCanvas();
	
	
	/***********************************************************
	 *                    Methods                              *
	 ***********************************************************/
	
	public void init() {
		add(canvas);
		drawBackground(); 
	}

	public void run() {
		println("Welcome to ParaKarel");
		
		//list of all the words in a file
		ArrayList<String> wordList = new ArrayList<String>();
		scannerStuff(wordList);
		
		GImage karel = null;
		karel = karel(karel);
		
		parachute();
		
		// strings for the parachute
		GLine strings = null;
		//list of all the x positions of the strings
		ArrayList<Integer> stringXLocations = new ArrayList<Integer>();
		
		drawStrings(strings, stringXLocations);
		console(karel, strings, stringXLocations, wordList);	
	}

	/**
	 * Method: Get Random Word
	 * -------------------------
	 * This method returns a word to use in the ParaKarel game. It randomly 
	 * selects from among 10 choices.
	 */
	private String getRandomWord() {
		int index = rg.nextInt(10);
		if(index == 0) return "BUOY";
		if(index == 1) return "COMPUTER";
		if(index == 2) return "CONNOISSEUR";
		if(index == 3) return "DEHYDRATE";
		if(index == 4) return "FUZZY";
		if(index == 5) return "HUBBUB";
		if(index == 6) return "KEYHOLE";
		if(index == 7) return "QUAGMIRE";
		if(index == 8) return "SLITHER";
		if(index == 9) return "ZIRCON";
		throw new ErrorException("getWord: Illegal index");
	}

	//this method creates the background
	private void drawBackground() {
		 GImage bg = new GImage("background.jpg");
		 bg.setSize(canvas.getWidth(), canvas.getHeight());
		 canvas.add(bg, 0, 0);
	}
	
	//this method scans a file so the user can have a word from a bigger list
	private void scannerStuff(ArrayList<String> wordList) {
		try {
			Scanner input = new Scanner(new File("ParaKarelLexicon.txt"));
			while(input.hasNextLine()){
				String line = input.nextLine();
				wordList.add(line);	 
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//this method creates Karel
	private GImage karel(GImage karel) {
		karel = new GImage("karel.png");
		karel.setSize(KAREL_SIZE, KAREL_SIZE);
		int karelX = canvas.getWidth() / 2 - KAREL_SIZE / 2;
		canvas.add(karel, karelX, KAREL_Y);
		
		return karel;
	}
		
	//this method creates the parachute
	private GImage parachute() {
		GImage parachute = new GImage("parachute.png");
		parachute.setSize(PARACHUTE_WIDTH, PARACHUTE_HEIGHT);
		int parachuteX = canvas.getWidth() / 2 - PARACHUTE_WIDTH / 2;
		canvas.add(parachute, parachuteX, PARACHUTE_Y);	
		return parachute;
	}
	
	//this method creates the strings and adds each string's x value to a list
	private GLine drawStrings(GLine strings, ArrayList<Integer> stringXLocations) {
		int stringXO = canvas.getWidth() / 2 - PARACHUTE_WIDTH / 2;
		int stringYO = PARACHUTE_Y + PARACHUTE_HEIGHT;
		int stringX1 = canvas.getWidth() / 2;
		int stringY1 = KAREL_Y;
		
		strings = new GLine(stringXO, stringYO, stringX1, stringY1);
		stringXLocations.add(stringXO);
		canvas.add(strings);
		
		int spacing = PARACHUTE_WIDTH / (N_GUESSES - 1);
		
		for(int i = 1; i < N_GUESSES; i++) {
			strings = new GLine(stringXO + spacing, stringYO, stringX1, stringY1);
			stringXLocations.add(stringXO + spacing);
			canvas.add(strings);
			spacing += (PARACHUTE_WIDTH / (N_GUESSES - 1));	
		}		
		return strings;
	}
	
	/*
	 * This method takes care of the console portion of the program. It asks the
	 * user for an input (a string) and it converts it to a char. The char is then
	 * compared to every character in the word and if they match, that spot on the
	 * blank is replaced with the char. The user's guesses decrease every time they
	 * guess the wrong letter.
	 */
	private void console(GImage karel, GLine strings, ArrayList stringXLocations, ArrayList wordList) {
		int index = rg.nextInt(wordList.size());
		String word =  (String) wordList.get(index);
		
		String blankDashes = "";
		int guesses = N_GUESSES;
		
		//printing the initial blank dashes
		for(int i = 0; i < word.length(); i++) {
			blankDashes += "-";	
		}
		
		//creating the partially guessed label
		GLabel dashes = new GLabel(blankDashes);
		dashes.setFont(PARTIALLY_GUESSED_FONT);
		double dashesX = canvas.getWidth() / 2 - dashes.getWidth() / 2;
		canvas.add(dashes, dashesX, PARTIALLY_GUESSED_Y);
		
		//creating the incorrect guesses letters label
		GLabel incorrectGuesses = new GLabel("");
		double incorrectGuessesX = canvas.getWidth() / 2 - dashes.getWidth() / 2;
		//distance between incorrect guesses
		int spacing = 0;
		
		println("Your word now looks like this: " + blankDashes);
		println("You have " + guesses + " guesses left.");
		
		//keeps track of the index for the stringXLocations list
		int indexStrings = 0;
		
		while(guesses >= 0) {
			//asking the user for a letter
			String userInput = readLine("Your guess: ");
			userInput = userInput.toUpperCase();
			if(userInput.length() != 1) {
				userInput = readLine("This guess is illegal. Your guess: ");
				userInput = userInput.toUpperCase();
			}
			
			//converting the letter to a char
			char userGuess = userInput.charAt(0);
		
			// when the user's guess is correct
			for(int i = 0; i < word.length(); i++) {
				if(userGuess == word.charAt(i)) {
					blankDashes = blankDashes.substring(0, i) + userInput + blankDashes.substring(i + 1);
					
					canvas.remove(dashes);
					dashes = new GLabel(blankDashes.substring(0, i) + userInput + blankDashes.substring(i + 1));
					dashes.setFont(PARTIALLY_GUESSED_FONT);
					canvas.add(dashes, dashesX, PARTIALLY_GUESSED_Y);
									
					println("That guess is correct.");
					println("Your word now looks like this: " + blankDashes);
					println("You have " + guesses + " guesses left");
				}
			}
			
			//when the user has completed the word
			if(blankDashes.equals(word)) {
				println("You win.");
				println("The word was: " + word);
				break;
			}
		
			//when the user's guess is incorrect
			if(word.contains(userInput) == false) {
				guesses--;		
				
				//updating the incorrect guess label
				incorrectGuesses = new GLabel(userInput);
				userInput = userInput.toUpperCase();
				incorrectGuesses.setFont(INCORRECT_GUESSES_FONT);
				canvas.add(incorrectGuesses,incorrectGuessesX + spacing, INCORRECT_GUESSES_Y);
				spacing += 20;
				
				//removing the parachute strings
				GObject stringLocator = canvas.getElementAt( (int) stringXLocations.get(indexStrings), PARACHUTE_Y + PARACHUTE_HEIGHT);
				canvas.remove(stringLocator);
				indexStrings++;
					
				println("There are no " + userInput + "'s in this word.");
				println("Your word now looks like this: " + blankDashes);
				println("You have " + guesses + " guesses left");
				
				//when there are no more guesses left
				if(guesses == 0) {
					println("You're completely hung.");
					println("The word was: " + word);
					fallingKarel(karel, word);
					break;
				}
			}
		}
	}
	
	//this method removes the Karel image and replaces it with the flipped image
	private void fallingKarel(GImage karel, String word) {
		canvas.remove(karel);
		
		GImage fallingKarel = new GImage("karelFlipped.png");
		fallingKarel.setSize(KAREL_SIZE, KAREL_SIZE);
		int karelX = canvas.getWidth() / 2 - KAREL_SIZE / 2;
		canvas.add(fallingKarel, karelX, KAREL_Y);
		
		String message = "The word was " + word + " :(";
		GLabel actualWord = new GLabel(message, 30, 30);
		actualWord.setFont("Courier-15");
		actualWord.setColor(Color.WHITE);
		canvas.add(actualWord);
		
		while(true) {
			fallingKarel.move(0, 1);
			pause(DELAY);
		}
	}

}
