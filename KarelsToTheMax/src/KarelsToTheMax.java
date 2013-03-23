/*
 * File: KarelsToTheMax.java
 * ---------------------
 * This problem, inspired by the Subdivision problem from the Winter 2012 midterm and
 * Koalas to the Max (http://www.koalastothemax.com/), draws a giant circle on the
 * screen, and as you continue to move your mouse over it, it resolves into an image.
 * 
 * This is intended to be a review of basic graphics and MouseEvents, and the first
 * substantial use of representing images as multidimensional arrays and using color values.
 */

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GDimension;
import acm.graphics.GImage;
import acm.graphics.GOval;
import acm.program.*;
import acm.util.RandomGenerator;

public class KarelsToTheMax extends GraphicsProgram {
	public static final int APPLICATION_HEIGHT = 500;
	public static final int APPLICATION_WIDTH = 500;
	
	RandomGenerator rgen = RandomGenerator.getInstance();

	// photo courtesy of Rachel Fenichel
	GImage image = new GImage("karels.jpg");

	// the pixel data is an instance variable because loading it in every time
	// the mouse moves slows the program down
	int[][] pixelData = image.getPixelArray();
	
	public void run() {
		addMouseListeners();
		addKeyListeners();

		image.setSize(new GDimension(getWidth(), getHeight()));
		reset();
	}
	
	private void reset() {
		removeAll();
		makeCircle(0, 0, getWidth());
	}
	
	// makes a circle at the given x, y point with radius r
	private void makeCircle(double x, double y, double radius) {
		GOval oval = new GOval(x, y, radius, radius);
		oval.setFilled(true);

		Color c = getAverageColor((int)x, (int)y, (int)(x + radius), (int)(y + radius));

		oval.setColor(c);
		oval.setFillColor(c);
		add(oval);
	}
	
	// finds the average color of an image given the opposite corners
	// of a bounding box
	private Color getAverageColor(int x1, int y1, int x2, int y2) {
		int avgRed = 0;
		int avgGreen = 0;
		int avgBlue = 0;
		
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				avgRed += GImage.getRed(pixelData[y][x]);
				avgGreen += GImage.getGreen(pixelData[y][x]);
				avgBlue += GImage.getBlue(pixelData[y][x]);
			}
		}
		
    // because we're doing integer division, there's a loss of precision; but 
    // the human eye generally isn't sensitive enough to notice the minor differences
		avgRed /= ((x2 - x1) * (y2 - y1));
		avgGreen /= ((x2 - x1) * (y2 - y1));
		avgBlue /= ((x2 - x1) * (y2 - y1));
		
		return new Color(avgRed, avgGreen, avgBlue);
	}
	
	// removes and oval and replaces it with four smaller ones when
	// the mouse moves over one
	public void mouseMoved(MouseEvent e) {
		GOval oval = (GOval) getElementAt(e.getX(), e.getY());
		if (oval != null) {
      // we need a cut off so that we don't try to average the color of half a pixel
			if (oval.getWidth()/2 < 1) return;

			remove(oval);
			makeCircle(oval.getX(), oval.getY(), oval.getWidth()/2);
			makeCircle(oval.getX() + oval.getWidth()/2, oval.getY(), oval.getWidth()/2);
			makeCircle(oval.getX(), oval.getY() + oval.getWidth()/2, oval.getWidth()/2);
			makeCircle(oval.getX() + oval.getWidth()/2, oval.getY() + oval.getWidth()/2, oval.getWidth()/2);
		}
	}
}	
