/*
 * The purpose of this program is to determine certain aspects of temperatures
 * based on user input. The user enters in as many temperature values they desire 
 * until they enter the sentinel value. The program records the total number of 
 * temperatures entered in, as well as their sum to find the average temperature. 
 * It compares all the temperature values with each other to determine the hottest 
 * and coldest temperatures. If any temperature entered in is less than 50 degrees, 
 * then the program will count the number of times this occurs to determine the 
 * number of cold days. Additionally, if no temperatures are entered in, then the 
 * program takes that into account as well. All these values are then printed for
 * the user to see.
 */

import acm.program.*;

public class Weather extends ConsoleProgram {
	private static final int SENTINEL = -1;
	
	public void run() {
		println("CS 106A \"Weather Master 4000\"!");
	
		double totalTemp = 0; //temperature sum
		double num = 0; //number of temperatures entered
		int coldDays = 0; //number of cold days
		
		int temp = readInt("Next temperature (or " + SENTINEL + " to quit)?");
		
		int highTemp = temp; //highest temperature
		int lowTemp = temp; //lowest temperature
		
		while (temp != SENTINEL) {
			//counting the number of cold days
			if(temp <= 50) { 
				coldDays += 1;
			}
			
			//determining the highest temperature
			if(temp > highTemp) { 
				highTemp = temp;
			}
			
			//determining the lowest temperature
			if(temp < lowTemp) { 
				lowTemp = temp;
			}
			
			//counting the number of days and calculating the temperature sum
			num += 1; 
			totalTemp += temp;
			
			temp = readInt("Next temperature (or " + SENTINEL + " to quit)?");
		}
		//if no temperature is entered
		if(num == 0) {
			println("No temperatures were entered.");
				
		}
		
		//printing the values
		if(num != 0) {
			//highest temperature
			println("Highest temperature = " + highTemp);
			
			//lowest temperature
			println("Lowest temperature = " + lowTemp);
		
			//calculating the average temperature
			double avg = totalTemp / num;
			println("Average = " + avg);
		
			//number of cold days
			println(coldDays + " cold day(s).");
		}	
	}
	
}
