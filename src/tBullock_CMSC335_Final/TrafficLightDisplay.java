/**
 * @author Tracy Bullock
 * CMSC 335 7382 Object-Oriented and Concurrent Programming (2218)
 * Final Project
 * December 12, 2021
 * Created with Eclipse IDE
 * 
 * TrafficLightDisplay.java - A runnable class to control the traffic 
 * lights. The traffic lights begin with a random color and time delays
 * of the green and red lights are set to be randomized as well to prevent 
 * all lights from being the same color at once.
 */

package tBullock_CMSC335_Final;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;

public class TrafficLightDisplay implements Runnable {
	
	LightColors lColor;
	TrafficLight tLight;
	ImageIcon lightImg;
	int redWaitTime, greenWaitTime;
	
	private volatile Boolean isRunning, isStopped;
	
	/**
	 * TrafficLightDisplay - parameterized construcor that sets the 
	 * color and icon of the traffic light passed into it.
	 * 
	 * @param tLight of type TrafficLight.
	 */
	public TrafficLightDisplay(TrafficLight tLight) {
		
		isRunning = true;
		isStopped = false;
		redWaitTime = ThreadLocalRandom.current().nextInt(8000, 10000);
		greenWaitTime = ThreadLocalRandom.current().nextInt(11000, 16000);
		this.tLight = tLight;
		setlColor();
		
		setLightImage();
		
	}

	/**
	 * run - overrides the runnable run method and loops to control
	 * the light color changes.
	 */
	@Override
	public void run() {
		
		while (!isStopped) {
			checkIfPaused();
			if (isRunning) {
				changeColor();
				try {
					if (lColor.equals(LightColors.GREEN)) {
						Thread.sleep(greenWaitTime);
					} else if (lColor.equals(LightColors.YELLOW)) {
						Thread.sleep(4000);
					} else {
						Thread.sleep(redWaitTime);
					}
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, "An error has occurred", "THREAD ERROR", JOptionPane.WARNING_MESSAGE);
				}
				
			}
			checkIfStopped();
		}
		
	}
	
	/**
	 * changeColor - method to change the color of the 
	 * traffic light and set the image.
	 */
	private void changeColor() {
		
		if (lColor.equals(LightColors.GREEN)) {
			lColor = LightColors.YELLOW;
		} else if (lColor.equals(LightColors.YELLOW)) {
			lColor = LightColors.RED;
		} else {
			lColor = LightColors.GREEN;
		}
		setLightImage();
		updateColor();
		
	}

	/**
	 * setLightImage - method to set the image of the traffic
	 * light based on the color of the light.
	 */
	private void setLightImage() {
		
		switch (lColor) {
			case GREEN:
				lightImg = new ImageIcon(TrafficAnalysisGUI.class.getResource("GreenLight.png"));
				break;
			case YELLOW:
				lightImg = new ImageIcon(TrafficAnalysisGUI.class.getResource("YellowLight.png"));
				break;
			case RED:
				lightImg = new ImageIcon(TrafficAnalysisGUI.class.getResource("redLight.png"));
				break;
		}
		tLight.setIcon(lightImg);
	}
	
	/**
	 * checkIfPaused - method used to check if the simulation
	 * has been paused and signals for the lights to pause.
	 */
	private void checkIfPaused() {
		if (tLight.isPaused) {
			isRunning = false;
		} else {
			isRunning = true;
		}
	}
	
	/**
	 * checkIfStopped - method to check if the simulation
	 * has been stopped and signals to stop the traffic lights.
	 */
	private void checkIfStopped() {
		isStopped = tLight.isStopped();
		if (isStopped) {
			tLight.setIcon(null);
		}
	}

	/**
	 * setlColor - method to set the traffic light to a 
	 * random color and update the application with the
	 * information.
	 */
	private void setlColor() {
		lColor = LightColors.getRandomColor();
		updateColor();
	}
	
	/**
	 * updateColor - method used to update the rest of the program 
	 * of the color of the traffic light.
	 */
	private void updateColor() {
		switch (lColor) {
			case GREEN:
				tLight.setColor("green");
				break;
			case YELLOW:
				tLight.setColor("yellow");
				break;
			case RED:
				tLight.setColor("red");
				break;
		}
	}
	
	/**
	 * LightColors - used to set a random light color.
	 * 
	 * @author Tracy
	 *
	 */
	enum LightColors {
		GREEN, YELLOW, RED;
		
		public static LightColors getRandomColor() {
			Random random = new Random();
			return values()[random.nextInt(values().length)];
		}
	}

}
