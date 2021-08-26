/*
 * The goal of this program is to find the real roots of a quadratic equation. This program 
 * asks the user for 3 numbers corresponding to the quadratic equation: a, b, and c.
 * Using these numbers, the program calculates the discriminant, which determines how
 * many roots a quadratic equation has. Based on the value of the discriminant, the 
 * program calculates the appropriate roots for the numbers if there are any.
 * Finally, the program displays the value of the roots to the user.
 */

import acm.program.*;

public class QuadraticEquation extends ConsoleProgram {
	public void run() {
		println("CS 106A Quadratic Solver!");
		
		//asking the user for values
		double a = readDouble("Enter a: ");
		double b = readDouble("Enter b: ");
		double c = readDouble("Enter c: ");
		
		//calculating the discriminant
		double discriminant = (b * b) - (4 * a * c);
		
		//finding all real roots by plugging the discriminant into the quadratic formula
		double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
		double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
		//one root
		if(discriminant == 0) { 
			println("One root: " + root1);
		}
		//two roots
		if(discriminant > 0) { 
			println("Two roots: " + root1 + " and " + root2);
		}
		//no real roots
		if(discriminant < 0){ 
			println("No real roots");
		}
	}
	
}
