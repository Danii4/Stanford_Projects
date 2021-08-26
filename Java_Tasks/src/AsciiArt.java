import java.awt.Color;

import acm.program.*;

/*
 * This program creates ice cream Ascii Art. This masterpiece consists of a simple ice cream 
 * cone with one scoop of (organic) strawberry ice cream. The ice cream is topped with a cherry
 * to make it somewhat healthy.
 */

public class AsciiArt extends ConsoleProgram {
	
	public void run() {
		println("CS 106A ASCII Art by Dania Hanif", Color.BLUE);
		println();
		
		topping();
		iceCream();
		cone();
	}

/*
 *  This method creates the topping, which is a cherry. It is divided into two parts:
 *  the stem and the cherry.
 */
	private void topping() {
		stem();
		cherry();
	}

/*
 * This method creates the stem of the cherry by first printing spaces, the tip of 
 * the stem, and the bottom portion.
 */
	private void stem() {
		for(int i = 1; i <= 7; i++) {
			print(" ");
		}
		println("/|", Color.GREEN);
		for(int i = 1; i <= 8; i++) {
			print(" ");
		}
		println("|", Color.GREEN);
		
	}

// This method creates the cherry part by placing spaces and using *.
	private void cherry() {
		for(int i = 1; i <= 2; i++) { 
			for(int k = 1; k <= 8; k++) {
			print(" ");
			}
			for(int k = 1; k <= 2; k++) {
			print("*", Color.RED);
			}
			println();
		}
	}
	
/*
 * This method creates a scoop of strawberry ice cream by layering the = in a increasing
 * pattern.
 */
	private void iceCream() {
		for(int i = 1; i <= 4; i++) {
			for(int j = 1; j <= 4 - i; j++) {
				print("  ");
			}
			for(int k = 1; k <= 4 * i + 2; k++) {
				print("=", Color.MAGENTA);
			}
			println();
		}
	}
	
/*
 * This method creates the ice cream cone, which is an inverted triangle. Therefore,
 * the spaces muse increase as they are going down.
 */
	private void cone() {
		for(int i = 9; i > 0; i--) {
			for(int j = 1; j <= 9 - i; j++) {
				print(" ");
			}
			for(int k = 0; k <= i - 1; k++) {
				print("\\/", Color.ORANGE);
			}
			println();
		}	
	}
	
}
