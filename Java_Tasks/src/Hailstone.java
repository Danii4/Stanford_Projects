/*
 * The purpose of this program is to use specific math operations to reach the number one
 * when the user enters a positive integer. First, the user is asked to enter a number.
 * Then, based on whether it is is even or odd, the number is plugged into a math equation
 * until it reaches one. The program also tells the user how many steps it takes to reach
 * one. After this, the user is asked if they want to enter another number. Based on their
 * response, the program continues or is ended.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	
	public void run() {
		println("This program computes Hailstone sequences.");
		println();
		
		boolean resume = true; //if true, the program with allow the user to ask again
		
		while(resume) {
			hailstoneSeq();
			
			//asking the user if they want to start again
			boolean startAgain = readBoolean("Run again? ", "y", "n");
			println();
			//when the user says yes
			if(startAgain) { 
				resume = startAgain;
			}
			//When the user says no
			if(startAgain == false) { 
				resume = false;
				println("Thanks for using Hailstone.");
			}
		}
	}

/*
 * This method asks the user to enter in a number. With modulo, that number undergoes 
 * specific math operations depending on whether it is even or odd in a loop. The entire
 * math process is printed to the user, as well. The number of times these math operations 
 * is counted as well by added how many even and odd steps occur. When one is reached, the 
 * loop breaks.
 */
	private void hailstoneSeq() {
		int evenCount = 0; //number of times the even operation is used
		int oddCount = 0; //number of times the of operation is used
		
		int num = readInt("Enter a number:");
		
		while(num != 1) {
			//even numbers
			if(num % 2 == 0) { 
				println(num + " is even, so I take half: " + (num/2));
				num = num / 2;
				evenCount += 1;
				
				if(num == 1) { 
					break;
				}	
			}
			//odd numbers
			if(num % 2 != 0) { 
				println(num + " is odd, so I make 3n + 1: " + (3 * num + 1));
				num = 3 * num + 1;
				oddCount += 1;
				
				if(num == 1) {
					break;
				}	
			}
		}
		
		//determining the number of steps to reach number 1
		int totalCount = evenCount + oddCount;
		println("It took " + totalCount + " steps to reach 1.");
	}
	
}
