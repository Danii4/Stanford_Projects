/*
 * File: VoteCounter.java
 * ---------------------
 * A sandcastle program that uses collections to tally votes 
 */

import acm.program.*;
import java.util.*;

public class VoteCounter extends ConsoleProgram {
	public void run() {
		ArrayList<String> votes = new ArrayList<String>();
		votes.add("Zaphod Beeblebrox");
		votes.add("Arthur Dent");
		votes.add("Trillian McMillian");
		votes.add("Zaphod Beeblebrox");
		votes.add("Marvin");
		votes.add("Mr. Zarniwoop");
		votes.add("Trillian McMillian");
		votes.add("Zaphod Beeblebrox");
		printVoteCounts(votes);
		
	}
	
	/*
	 * Your job is to implement this method according to 
	 * the problem specification. 
	 */
	private void printVoteCounts(ArrayList<String> votes) {
		HashMap<String, Integer> voteMap = new HashMap<String, Integer>();
		String nameKey = ""; //key
		int voteTotal = 1; //value
		
		//adding the names and votes to the Hash Map
		for(int i = 0; i < votes.size(); i++) {
			nameKey = votes.get(i);
			
			//when a person has more that 1 vote
			if(voteMap.containsKey(nameKey)) {
				int moreVotes = voteMap.get(nameKey);
				voteMap.put(nameKey, moreVotes + 1);
			}
			//when the person has only one vote
			else {
				voteMap.put(nameKey,voteTotal);
			}
		}
		
		//printing the names and their corresponding votes
		for(String i : voteMap.keySet()) { 
			println("Votes for " + i + ": " + voteMap.get(i));
		}		
	}
}
