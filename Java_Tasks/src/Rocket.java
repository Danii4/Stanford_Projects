/*
 * This program create Ascii Art in the shape of a rocket. The rocket is divided
 * into three components: the top, middle, and bottom. The top and bottom are the 
 * same.The middle forms a diamond shape, which can be divided into two parts: the 
 * top half of the diamond and the bottom half of the diamond. The first and last 
 * rows of the middle are separate.
 */

import acm.program.*;

public class Rocket extends ConsoleProgram {
	private static final int SIZE = 5;
	
	public void run() {
		topBottom();
		middle();
		topBottom();
	}

/*
 *  This method builds the top and bottom triangle portions of the rocket because both
 *  are the same. The spaces are printed first and decrease as the loop continues to create
 *  the triangle look. The \ are placed right after the / since the program runs one
 *  line at a time from left to right.
 */
	private void topBottom() {
		for(int i = 1; i <= SIZE; i++) {
			for(int j = 0; j <= SIZE - i; j++) {
				print(" ");
			}
			
			for(int k = 0; k <= i - 1; k++) {
				print("/");
			}
			
			for(int k = 0; k <= i - 1; k++) {
				print("\\");
			}
			println();
		}
	}

/*
 * This method builds the big rectangle in the middle of the rocket. It can be drawn
 * in 3 parts: the start and end lines, the top triangle design, and the bottom 
 * triangle design.
 */
	private void middle() {
		startEndLine();
		topHalf();
		bottomHalf();
		startEndLine();
	}
	
/*
 * This method creates the first and last row of the rectangular portion since they are
 * the same. The + is printed first and occurs twice in one line: start and end. Right 
 * after that,the = is printed twice the value of the SIZE constant. 
 */
	private void startEndLine() {
		print("+");
		for(int i = 1; i <= 2 * SIZE; i++) {
			print("=");
		}
		print("+");
		println();	
	}

/*
 * This method builds the top half of the rectangle excluding the first row, which was
 * taken care of in the startEndLine(). The | is printed first. The value of i is increasing
 * (i++), which creates the triangular shape. The • is printed in a decreasing pattern on 
 * both sides of the top half as it goes through the loop. Next, the /\ patter is printed 
 * and its frequency increases as it goes down each row to form half of a diamond. This 
 * ends with another |.
 */
	private void topHalf() {
		for(int i = 1; i <= SIZE; i++) {
			print("|");
			
			for(int j = 1; j <= SIZE - i; j++) {
				print("•");
			}
			
			for(int k = 0; k <= i - 1; k++) {
				print("/\\");
			}
			
			for(int j = 1; j <= SIZE - i; j++) {
				print("•");
			}
			print("|");
			
			println();
		}	
	}
	
/*
 * This method creates the bottom half of the rectangle excluding the bottom row. This
 * is the same as the topHalf() method, but in reverse and using \/ instead. For topHalf(), 
 * i was increasing (i++) to create the triangular pattern. However, for this method, an inverted 
 * triangular shape is needed, which is why the value of i is decreasing (i--). 
 */
	private void bottomHalf() {
		for(int i = SIZE; i > 0; i--) {
			print("|");
			
			for(int j = 1; j <= SIZE - i; j++) {
				print("•");
			}
			
			for(int k = 0; k <= i - 1; k++) {
				print("\\/");
			}
			
			for(int j = 1; j <= SIZE - i; j++) {
				print("•");
			}
			
			print("|");
			
			println();
		}	
	}
	
}
