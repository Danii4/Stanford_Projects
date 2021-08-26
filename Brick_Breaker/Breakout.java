/*
 * File: Breakout.java
 * -------------------
 * Names: Kepa Neesen and Dania Hanif
 * Section Leader: Aditya
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	// Dimensions of the canvas, in pixels
	// These should be used when setting up the initial size of the game,
	// but in later calculations you should use getWidth() and getHeight()
	// rather than these constants for accurate size information.
	public static final double CANVAS_WIDTH = 420;
	public static final double CANVAS_HEIGHT = 600;

	// Number of bricks in each row
	public static final int NBRICK_COLUMNS = 10;

	// Number of rows of bricks
	public static final int NBRICK_ROWS = 10;

	// Separation between neighboring bricks, in pixels
	public static final double BRICK_SEP = 4;

	// Width of each brick, in pixels
	public static final double BRICK_WIDTH = Math.floor(
			(CANVAS_WIDTH - (NBRICK_COLUMNS + 1.0) * BRICK_SEP) / NBRICK_COLUMNS);

	// Height of each brick, in pixels
	public static final double BRICK_HEIGHT = 8;

	// Offset of the top brick row from the top, in pixels
	public static final double BRICK_Y_OFFSET = 70;

	// Dimensions of the paddle
	public static final double PADDLE_WIDTH = 60;
	public static final double PADDLE_HEIGHT = 10;

	// Offset of the paddle up from the bottom 
	public static final double PADDLE_Y_OFFSET = 30;

	// Radius of the ball in pixels
	public static final double BALL_RADIUS = 10;

	// The ball's vertical velocity.
	public static final double VELOCITY_Y = 3.0;

	// The ball's minimum and maximum horizontal velocity; the bounds of the
	// initial random velocity that you should choose (randomly +/-).
	public static final double VELOCITY_X_MIN = 1.0;
	public static final double VELOCITY_X_MAX = 3.0;

	// Animation delay or pause time between ball moves (ms)
	public static final double DELAY = 1000.0 / 60.0;

	// Number of turns 
	public static final int NTURNS = 3;
	
	//paddle
	private GRect paddle;
	
	//ball
	private GOval ball;
	
	//ball velocity
	private double vx, vy;
	
	//brick count
	private int brickCount;
	
	//random generator
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	
	public void run() {
		// Set the window's title bar text
		setTitle("CS 106A Breakout");

		// Set the canvas size.  In your code, remember to ALWAYS use getWidth()
		// and getHeight() to get the screen dimensions, not these constants!
		setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);	
		
		createBrick();
		createPaddle();
		
		brickCount = NBRICK_ROWS * NBRICK_COLUMNS;
		
		for(int i = NTURNS; i >= 1; i--){
			waitForClick();
			createBall();
			
			if(brickCount == 0) {
				remove(ball); 
				GLabel youWin = new GLabel("You Win!", getWidth() / 2, getHeight() / 2 );
				add(youWin);
				break;
			}	
			if(hasPlayerFailed(ball)) {
				remove(ball);
			}
			if(i <= 1) {
				GLabel gameOver = new GLabel("Game Over!", getWidth() / 2, getHeight() / 2 );
				add(gameOver);
			}
		}	
	}
	
	//this method creates the bricks and colors them
	private GRect createBrick() {
		double leftoverSpace = (getWidth() - (BRICK_WIDTH * NBRICK_COLUMNS) - ((BRICK_SEP * NBRICK_COLUMNS) - BRICK_SEP)) / 2;
		double brickY = BRICK_Y_OFFSET; 
		double brickX = leftoverSpace; 
		GRect brick = new GRect(BRICK_WIDTH,BRICK_HEIGHT);
				
		for(int i = 0; i < NBRICK_ROWS; i++) {	
			for(int j = 0; j < NBRICK_COLUMNS; j++) {
				brick = new GRect(BRICK_WIDTH,BRICK_HEIGHT);
				add(brick, brickX, brickY);	
				brick.setFilled(true);
			
				if(i < 2) {
					brick.setColor(Color.RED);
				} 
				else if (i < 4) {
					brick.setColor(Color.ORANGE);
				} 
				else if (i < 6) { 
					brick.setColor(Color.YELLOW);
				} 
				else if (i < 8) {
					brick.setColor(Color.GREEN);
				} 
				else if (i < 10) {
					brick.setColor(Color.CYAN);
				}		
				brickX += BRICK_WIDTH + BRICK_SEP;		
				}
				brickX = leftoverSpace;
				brickY += BRICK_HEIGHT + BRICK_SEP;	
			}
			return brick;
	}
	
	//this method creates the paddle
	private void createPaddle() {
		double paddleY = getHeight() - PADDLE_Y_OFFSET;
		paddle = new GRect( 0, paddleY, PADDLE_WIDTH,PADDLE_HEIGHT);
		paddle.setFilled(true);
			
	}
	
	//this method creates the ball and controls its movement
	private void createBall() {
		double ballX = (getWidth() / 2) - BALL_RADIUS;
		double ballY = (getHeight() / 2) - BALL_RADIUS;
		
		vx = rgen.nextDouble(1.0, 3.0);
		vy = VELOCITY_Y;
		if (rgen.nextBoolean(0.5)) vx = -vx;
		
		ball = new GOval(ballX, ballY,2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		add(ball);	
		
		while(!hasPlayerFailed(ball)) { 
			getCollidingObject(ball);
		
			ball.move(vx,vy);
			pause(DELAY);
			
			if(ball.getX() >= getWidth() - BALL_RADIUS * 2 || ball.getX() <= 0) {
				vx = -vx;	
			}
			if(ball.getY() <= 0) { 
				vy = -vy;
			}	
		}
	}
	
	//this boolean checks to see if the player let the ball hit the screen bottom
	private boolean hasPlayerFailed(GOval ball) {
		return ball.getY() > getHeight() - BALL_RADIUS * 2;
	}
	
	//this method checks if the ball has hit a paddle or a brick and removes the bricks
	private void getCollidingObject(GOval ball) {
		
		//top left corner
		GObject colliderTL = getElementAt(ball.getX(), ball.getY());
		//top right corner
		GObject colliderTR = getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY());
		//bottom left corner
		GObject colliderBL = getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2);
		//bottom right corner
		GObject colliderBR = getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2);
		
		if(colliderBL == paddle || colliderBR == paddle) {
			vy = -vy;
			pause(DELAY);
		} 
		else if (colliderTL != null) {
			remove(colliderTL);
			vy = -vy;
			pause(DELAY);
			brickCount--;
		} 
		else if (colliderTR != null) {
			remove(colliderTR);
			vy = -vy;
			pause(DELAY);	
			brickCount--;
		} 
		else if (colliderBL != null) {
			remove(colliderBL);
			vy = -vy;
			pause(DELAY);	
			brickCount--;
		} 
		else if (colliderBR != null) {
			remove(colliderBR);
			vy = -vy;
			pause(DELAY);	
			brickCount--;		
		}
	}
	
	//this method has the paddle follow the player's mouse
	public void mouseMoved(MouseEvent e) { 
		double mouseX = e.getX();
		
		double paddleX = mouseX - paddle.getWidth() / 2.0;
		double paddleY = getHeight() - PADDLE_Y_OFFSET;
		
		paddle.setLocation(paddleX, paddleY);
		add(paddle);
		
		if(paddle.getX() < 0) {
			paddle.setLocation(0, paddleY);
		}
		else if(paddle.getX() > getWidth() - PADDLE_WIDTH) {
			paddle.setLocation(getWidth() - PADDLE_WIDTH, paddleY);
		}
	}
}
