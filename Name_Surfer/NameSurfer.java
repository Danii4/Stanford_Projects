/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.graphics.*;
import acm.program.*;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import acm.util.*;

public class NameSurfer extends GraphicsProgram implements NameSurferConstants {

	private NameSurferDataBase dataBase;
	private JTextField textField = new JTextField(TEXT_FIELD_WIDTH);
	private int colorCounter = 0;
	private GLine dataLine;

	public static void main(String[] args) {
		new NameSurfer().start(args);
	}

	/**
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window.
	 */
	public void init() {
		//loading the file
		this.dataBase = new NameSurferDataBase(NAMES_DATA_FILE);

		add(new JLabel("Name: "), NORTH);

		textField.addActionListener(this);
		textField.setActionCommand("Graph");
		add(textField, NORTH);

		add(new JButton("Graph"), NORTH);
		add(new JButton("Clear"), NORTH);

		addActionListeners();
	}

	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so you will have to define a method to respond to
	 * button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// You fill this in //
		String command = e.getActionCommand();

		if(command.equals("Graph") || e.getActionCommand().equals("Graph")){
			redraw();
			drawLines();
			colorCounter++;

		} else if (command.equals("Clear")) {
			removeAll();
			redraw();
			colorCounter = 0;
		}
	}

	/**
	 * This class is responsible for detecting when the the canvas
	 * is resized. This method is called on each resize!
	 */
	public void componentResized(ComponentEvent e) {
		removeAll();
		redraw();
		drawLines();
		colorCounter--;
	}

	/**
	 * A helper method that we *strongly* recommend. Redraw clears the
	 * entire display and repaints it. Consider calling it when you change
	 * anything about the display.
	 */
	private void redraw() {
		// You fill this in //
		createBG();
		createDecadeLabels();		
	}

	//this method creates the background grid lines
	private void createBG() {
		//creating the vertical lines
		GLine verticalLines = new GLine();
		int spacing = getWidth() / NDECADES;
		int xDistance = spacing;
		for(int i = 0; i < NDECADES - 1; i++) {
			verticalLines = new GLine(xDistance, 0, xDistance, getHeight());
			add(verticalLines);
			xDistance += spacing;
		}
		//creating the horizontal lines
		GLine topHorizontalLine = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		add(topHorizontalLine);
		int y = getHeight() - GRAPH_MARGIN_SIZE;
		GLine bottompHorizontalLine = new GLine(0, y, getWidth(), y);
		add(bottompHorizontalLine);	
	}
	
	//this method creates the decade labels at the bottom of the screen
	private void createDecadeLabels() {
		GLabel decadeLabel = new GLabel("");
		int decadeNum = START_DECADE;
		int spacing = getWidth() / NDECADES;
		int xDistance = 0;
		int y = getHeight() - DECADE_LABEL_MARGIN_SIZE;
		for(int i = 0; i < NDECADES; i++) {
			String currentDecade = Integer.toString(decadeNum);
			decadeLabel = new GLabel(currentDecade, xDistance, y);
			add(decadeLabel);
			decadeNum += 10;
			xDistance += spacing;
		}
	}
	
	//this method graphs the data lines and labels each point
	private void drawLines() {
		Color[] colorOrderArray = {Color.BLACK, Color.RED, Color.BLUE, Color.MAGENTA};
		
		ArrayList<Integer> ranks = new ArrayList<Integer>(); //list of ranks
		String text = textField.getText();
		NameSurferEntry entry = this.dataBase.findEntry(text);
		//adding rank values to the list
		for(int i = 0; i < NDECADES; i++) {
			ranks.add(entry.getRank(i));
		}
		String name = ""; //name used for the labels
		name = nameCaseConvertor(text, name);
				
		//GLine dataLine = new GLine();
		GLabel nameLabel = new GLabel("");
		int spacing = getWidth() / NDECADES;
		int x0Distance = 0; //x value for x0
		int x1Distance = spacing; //x value for x1
		double ySpacing = (double)(getHeight() - (2 * GRAPH_MARGIN_SIZE)) / MAX_RANK;
		
		for(int i = 0; i < NDECADES - 1; i++) {
			int rank0 = ranks.get(i); //y value for y0
			int rank1 = ranks.get(i + 1); //y value for y1
			
			//converting the ranks to Strings for the label
			String decadeMarker0 = name + " " + Integer.toString(rank0);
			String decadeMarker1 = name + " " + Integer.toString(rank1);
			
			//when the next rank is 0 and the current one is not 0
			if(ranks.get(i + 1) == 0 && ranks.get(i) != 0) {
				rank0 = ranks.get(i);
				rank1 = getHeight() - GRAPH_MARGIN_SIZE;
				decadeMarker0 = name + "*";
				
				dataLine = new GLine(x0Distance, (rank0 * ySpacing) + GRAPH_MARGIN_SIZE, x1Distance, rank1);
				dataLine.setColor(colorOrderArray[colorCounter % 4]);
				add(dataLine);
			
			//when the next rank is not 0 and the current one is 0
			} else if(ranks.get(i + 1) != 0 && ranks.get(i) == 0) {
				rank0 = getHeight() - GRAPH_MARGIN_SIZE;
				rank1 = ranks.get(i + 1);
				decadeMarker0 = name + "*";
				
				dataLine = new GLine(x0Distance, rank0, x1Distance, (rank1 * ySpacing) + GRAPH_MARGIN_SIZE);
				dataLine.setColor(colorOrderArray[colorCounter % 4]);
				add(dataLine);

				nameLabel = new GLabel(decadeMarker0, x0Distance, rank0);
				nameLabel.setColor(colorOrderArray[colorCounter % 4]);
				add(nameLabel);
				nameLabel = new GLabel(decadeMarker1, x1Distance, (rank1 * ySpacing) + GRAPH_MARGIN_SIZE);				
				nameLabel.setColor(colorOrderArray[colorCounter % 4]);
				add(nameLabel);
			
			//when one of both ranks is 0 and the next one is 0
			} else if((rank0 == 0 || rank1 == 0) && ranks.get(i + 1) == 0) {
				rank0 = getHeight() - GRAPH_MARGIN_SIZE;
				rank1 = getHeight() - GRAPH_MARGIN_SIZE;
				decadeMarker0 = name + "*";
				decadeMarker1 = name + "*";
				
				dataLine = new GLine(x0Distance, rank0, x1Distance, rank1);
				dataLine.setColor(colorOrderArray[colorCounter % 4]);
				add(dataLine);
				
				nameLabel = new GLabel(decadeMarker0, x0Distance, rank0);
				nameLabel.setColor(colorOrderArray[colorCounter % 4]);
				add(nameLabel);
				nameLabel = new GLabel(decadeMarker1, x1Distance, rank1);
				nameLabel.setColor(colorOrderArray[colorCounter % 4]);
				add(nameLabel);
			
			} else {
				dataLine = new GLine(x0Distance, (rank0 * ySpacing) + GRAPH_MARGIN_SIZE, x1Distance, (rank1 * ySpacing) + GRAPH_MARGIN_SIZE);
				dataLine.setColor(colorOrderArray[colorCounter % 4]);
				add(dataLine);
				
				nameLabel = new GLabel(decadeMarker0, x0Distance, (rank0 * ySpacing) + GRAPH_MARGIN_SIZE);
				nameLabel.setColor(colorOrderArray[colorCounter % 4]);
				add(nameLabel);
				nameLabel = new GLabel(decadeMarker1, x1Distance, (rank1 * ySpacing) + GRAPH_MARGIN_SIZE);				
				nameLabel.setColor(colorOrderArray[colorCounter % 4]);
				add(nameLabel);
			}
			//updating the x-distances
			x0Distance += spacing;
			x1Distance += spacing;
		}
	}
	
	//this method converts the text field's text. The first letter will be uppercase and the rest lowercase.
	private String nameCaseConvertor(String text, String name) {
		for(int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if(i == 0) {
				name = name + Character.toUpperCase(ch);
			} else {
				name = name + Character.toLowerCase(ch);
			}
		}
		return name;
	}		
}

