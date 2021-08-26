/* 
 * Note: these methods are public in order for them to be used by other files
 * in this assignment; DO NOT change them to private.  You may add additional
 * private methods to implement required functionality if you would like.
 * 
 * You should remove the stub lines from each method and replace them with your
 * implementation that returns an updated image.
 */

// TODO: comment this file explaining its behavior

import java.awt.event.MouseEvent;

import acm.graphics.*;

public class ImageShopAlgorithms implements ImageShopAlgorithmsInterface {

	public GImage rotateLeft(GImage source) {
		int[][] pixels = source.getPixelArray();
		int numRow = pixels.length;
		int numCol = pixels[0].length;
		
		int[][] rotatedLeftImg = new int[numCol][numRow];
		
		for (int r = 0; r < numCol; r++) {
			for (int c = 0; c < numRow; c++) {
				int rSwitch = c;
				int cSwitch = (numCol - 1) - r;
				 
				rotatedLeftImg[r][c] = pixels[rSwitch][cSwitch];
			}
		}
		
		GImage newImg = new GImage(rotatedLeftImg);
		return newImg;
	}

	public GImage rotateRight(GImage source) {
		int[][] pixels = source.getPixelArray();
		int numRow = pixels.length;
		int numCol = pixels[0].length;
		
		int[][] rotatedRightImg = new int[numCol][numRow];
		
		for (int r = 0; r < numCol; r++) {
			for (int c = 0; c < numRow; c++) {
				int rSwitch = (numRow - 1) - c; 
				int cSwitch = r;
				
				rotatedRightImg[r][c] = pixels[rSwitch][cSwitch];
			}
		}
		
		GImage newImg = new GImage(rotatedRightImg);
		return newImg;
	}

	public GImage flipHorizontal(GImage source) {
		int[][] pixels = source.getPixelArray();
		int numRow = pixels.length;
		int numCol = pixels[0].length;
		
		int[][] flipHorizontalImg = new int[numRow][numCol];
		
		int counter = 0;
		//swapping the column values in each row
		for (int r = 0; r < numRow; r++) {
			for (int c = 0; c < numCol; c++) { 
				int cSwitch = (numCol - 1) - counter;
				flipHorizontalImg[r][c] = pixels[r][cSwitch];
				counter++;
			}
			counter = 0;
		}
		
		GImage newImg = new GImage(flipHorizontalImg);
		return newImg;
	}

	public GImage negative(GImage source) {
		int[][] pixels = source.getPixelArray();
		int numRow = pixels.length;
		int numCol = pixels[0].length;
		
		int[][] negativeImg = new int[numRow][numCol];
		
		//subtracting the rgb values from 255 to get the negative value
		for (int r = 0; r < numRow; r++) {
			for (int c = 0; c < numCol; c++) { 
				int red = 255 - GImage.getRed(pixels[r][c]); 
				int green = 255 - GImage.getGreen(pixels[r][c]); 
				int blue = 255 - GImage.getBlue(pixels[r][c]); 
				
				negativeImg[r][c] = GImage.createRGBPixel(red, green, blue);
			}	
		}
		
		GImage newImg = new GImage(negativeImg);
		return newImg;
	}

	public GImage greenScreen(GImage source) {
		int[][] pixels = source.getPixelArray();
		int numRow = pixels.length;
		int numCol = pixels[0].length;
		
		int[][] greenScrImg = new int[numRow][numCol];
		for (int r = 0; r < numRow; r++) {
			for (int c = 0; c < numCol; c++) { 
				int red = GImage.getRed(pixels[r][c]); 
				int green = GImage.getGreen(pixels[r][c]); 
				int blue = GImage.getBlue(pixels[r][c]); 
				
				int bigger = 2 * Math.max(red, blue);
				
				//removing the green if green is greater than or equal to bigger
				if(green >= bigger) {
					greenScrImg[r][c] = GImage.createRGBPixel(red, green, blue, 0);
				}
				else {
					greenScrImg[r][c] = pixels[r][c];
				}
			}	
		}
		
		GImage newImg = new GImage(greenScrImg);
		return newImg;
	}

	public GImage blur(GImage source) {
		int[][] pixels = source.getPixelArray();
		int numRow = pixels.length;
		int numCol = pixels[0].length;
		
		int[][] blurImg = new int[numRow][numCol];
		
		//blurring the image
		for (int r = 0; r < numRow; r++) {
			for (int c = 0; c < numCol; c++) { 
				int red = 0; 
				int green = 0; 
				int blue = 0; 
				//the number each color needs to be divided by
				int counter = 0;
				
				//checking the left and right sides of each pixel (-1 to 1) and the boundaries
				for(int x = -1; x <= 1; x++) {
					if (c + x >= 0 && c + x < numCol) {
						red += GImage.getRed(pixels[r][c + x]); 
						green += GImage.getGreen(pixels[r][c + x]); 
						blue += GImage.getBlue(pixels[r][c + x]); 
						counter++;
					}
				}
				
				//checking the top and bottom sides of each pixel (-1 to 1) and the boundaries
				for(int y = -1; y <= 1; y++) {
					if (r + y >= 0 && r + y < numRow) {
						red += GImage.getRed(pixels[r + y][c]); 
						green += GImage.getGreen(pixels[r + y][c]); 
						blue += GImage.getBlue(pixels[r + y][c]); 
						counter++;
					}
				}
				//creating the blurred image based on the averages of the rgb values
				blurImg[r][c] = GImage.createRGBPixel(red / counter, green / counter, blue / counter);
			}	
		}
		GImage newImg = new GImage(blurImg);
		return newImg;
	} 

	public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight) {
		//index for rows
		int indexR = 0;
		//index for columns
		int indexC = 0;

		int[][] pixels = source.getPixelArray();
		
		int[][] cropImg = new int[cropHeight][cropWidth];
		
		//cropping the image: the cropImg is assigned the source's pixels within the cropped region specified
		for (int r = cropY; r < cropHeight + cropY; r++) {
			for (int c = cropX; c < cropWidth + cropX; c++) { 					
				cropImg[indexR][indexC] = pixels[r][c];
				indexC++;
			}
			indexR++;
			indexC = 0;
		} 
		
		GImage newImg = new GImage(cropImg);
		return newImg; 
	}

	public GImage equalize(GImage source) {
		int[][] pixels = source.getPixelArray();
		int numRow = pixels.length;
		int numCol = pixels[0].length;
		
		//Computing the Luminosity Histogram
		int[] lumArray = new int[256];
		for (int r = 0; r < numRow; r++) {
			for (int c = 0; c < numCol; c++) { 
				int red = GImage.getRed(pixels[r][c]);
				int green = GImage.getGreen(pixels[r][c]);
				int blue = GImage.getBlue(pixels[r][c]); 
				
				lumArray[(computeLuminosity(red, green, blue))]++; 
			}
		}
		
		/* Computing the Cumulative Luminosity Histogram: The cumLumArray is assigned the 0 index value
		 * of lumArray. 1 is added to the cumLumArray's index and it is set to the equivalent index lumArray value
		 * plus the previous cumLumArray value. */
		int[] cumLumArray = new int[256];
		for(int i = 0; i < 256; i++) {
			if(i >= 1) {
				int total = cumLumArray[i - 1] + lumArray[i];
				cumLumArray[i] = total;
			}
			else {
				cumLumArray[i] = lumArray[i];
			}
		}
		
		//Modifying Each Pixel To Increase Contrast
		int[][] equalizeImg = new int[numRow][numCol];
		for (int r = 0; r < numRow; r++) {
			for (int c = 0; c < numCol; c++) {
				int red = GImage.getRed(pixels[r][c]);
				int green = GImage.getGreen(pixels[r][c]);
				int blue = GImage.getBlue(pixels[r][c]);
				
				/* Computing the pixel’s luminosity: the luminosity of the rgb values is calculated and plugged into
				 * the equation. the equalizeImg is then given these modified values */
				double equation = (255 * cumLumArray[computeLuminosity(red, green, blue)]) / (numRow * numCol);
				int modifiedValue = (int) equation;
				equalizeImg[r][c] = GImage.createRGBPixel(modifiedValue, modifiedValue, modifiedValue);
			}
		}
		
		GImage newImg = new GImage(equalizeImg);
		return newImg;
	}
	
	//this method swaps the rgb values for all the pixels every 3 lines to create a striped pattern
	public GImage colorSwap(GImage source) {
		int[][] pixels = source.getPixelArray();
		int numRow = pixels.length;
		int numCol = pixels[0].length;
		
		int[][] colorSwapImg = new int[numRow][numCol];
		
		for (int r = 0; r < numRow; r++) {
			for (int c = 0; c < numCol; c++) {	
				colorSwapImg[r][c] = pixels[r][c];
			}
		}
		
		for (int r = 0; r < numRow; r+= 3) {
			for (int c = 0; c < numCol; c++) {
				int red = GImage.getRed(pixels[r][c]);
				int green = GImage.getGreen(pixels[r][c]);
				int blue = GImage.getBlue(pixels[r][c]);	
				colorSwapImg[r][c] = GImage.createRGBPixel(green, blue, red);
			}
		}
		
		GImage newImg = new GImage(colorSwapImg);
		return newImg;
	
	}
}
	