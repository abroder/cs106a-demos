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

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import acm.graphics.GLabel;
import acm.program.*;

public class QuieTalk extends GraphicsProgram {
	private static final int TEXT_FIELD_LENGTH = 20;
	
	private JTextField textField;
	
	public void init() {
		JLabel textLabel = new JLabel("Enter Text:");
		textField = new JTextField(TEXT_FIELD_LENGTH);
		add(textLabel, NORTH);
		add(textField, NORTH);
		
		textField.addActionListener(this);
	}
	
	private void drawCenteredText(String text) {
		GLabel centeredLabel = new GLabel(text);
		centeredLabel.setFont("TimesNewRoman-50");
		double x = getWidth()/2 - centeredLabel.getWidth()/2;
		double y = getHeight()/2 + centeredLabel.getAscent()/2;
		add(centeredLabel, x, y);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == textField) {
			removeAll();
			drawCenteredText(textField.getText());
			textField.setText("");
		}
	}	
}	
