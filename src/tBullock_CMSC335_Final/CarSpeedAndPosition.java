/**
 * @author Tracy Bullock
 * CMSC 335 7382 Object-Oriented and Concurrent Programming (2218)
 * Final Project
 * December 12, 2021
 * Created with Eclipse IDE
 * 
 * CarSpeedAndPosition.java - A runnable class to control the speed and
 * positions of the cars in relation to the traffic light signals. Cars
 * will slow down for yellow lights, drive for green lights, and stop 
 * for red lights. Car speeds are in kilometers per hour and distance is
 * in meters. Once a car reaches the last intersection, it will loop back 
 * to the first one. It is assumed that these cars can stop on a dime if 
 * they do not slow down enough.
 */

package tBullock_CMSC335_Final;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

public class CarSpeedAndPosition implements Runnable {
	
	Car car;
	int speed, position, roadLength;
	String status;
	int focusLightIndex;
	private volatile Boolean isRunning, isStopped;
	ArrayList<TrafficLight> tLights;
	
	/**
	 * CarSpeedAndPosition - parameterized constructor that initializes
	 * all of the information for the car passed into it and monitors
	 * traffic signals.
	 * 
	 * @param car of type Car.
	 */
	public CarSpeedAndPosition(Car car) {
		
		this.car = car;
		isStopped = false;
		initStartValue();
		updateTrafficSignals();
		setFocusLightIndex();
		
	}

	/**
	 * run - overrides the original run method in Runnable. It controls
	 * the speed of the car and constantly monitors traffic signals.
	 */
	@Override
	public void run() {
		
		while (!isStopped) {
			updateTrafficSignals();
			roadLength = TrafficAnalysisGUI.getRoadLength();
			checkIfPaused();
			
			if (isRunning) {
				try {
					
					Thread.sleep(500);
					if (position > roadLength) {
						position = 0;
						setFocusLightIndex();
					}
					reactToLights();
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, "An error has occurred", "THREAD ERROR", JOptionPane.WARNING_MESSAGE);
				}
			}
			checkIfStopped();
		}
	}
	
	/**
	 * initStartValue - method to initiate all start values.
	 */
	private void initStartValue() {
		roadLength = TrafficAnalysisGUI.getRoadLength();
		speed = ThreadLocalRandom.current().nextInt(35, 65);
		position = ThreadLocalRandom.current().nextInt(0, roadLength);
		status = "Driving";
	}
	
	/**
	 * setInfo - method used to set the speed, position, and status of 
	 * the car and update the car information panel.
	 */
	private void setInfo() {
		car.setSpeed(speed);
		car.setxPosition(position);
		car.setStatus(status);
		car.updateInfo();
	}
	
	/**
	 * checkIfPaused - method that checks to see if the
	 * user has paused the simulation and signals for the
	 * simulation to be paused.
	 */
	private void checkIfPaused() {
		
		if (car.isPaused) {
			isRunning = false;
			status = "Paused";
			setInfo();
		} else {
			isRunning = true;
		}
	}
	
	/**
	 * checkIfStopped - method used to check if the user has
	 * stopped the simulation and signals for the simulation
	 * to be terminated.
	 */
	private void checkIfStopped() {
		isStopped = car.isStopped();
		if (isStopped) {
			speed = 0;
			position = 0;
			status = "STOPPED";
			setInfo();
		}
	}
	
	/**
	 * updateTrafficSignals - method to update the traffic lights when
	 * intersections are added so that it can continue to monitor signals 
	 * for each light the car approaches.
	 */
	private void updateTrafficSignals() {
		tLights = TrafficAnalysisGUI.getTrafficSignals();
	}
	
	/**
	 * reactToLights - method that monitors the traffic light the car is
	 * approaching and makes the car reach appropriately to that light.
	 */
	private void reactToLights() {
		
		String tlColor = tLights.get(focusLightIndex).getColor();
		String tlName = tLights.get(focusLightIndex).getIntersectionName();

		if (tlColor.equalsIgnoreCase("yellow")) {
			if (speed > 5) {
				speed -= 5;
			} else {
				speed = 20;
			}
			position += speed * .5;
			focusLightIndex = position/1000;
			status = "Slowing for " + tlName;

		} else if (tlColor.equalsIgnoreCase("red")) {
			speed = 0;
			position = (focusLightIndex+1)*1000;
			status = "Stopped at " +  tlName;

		} else {
			speed = ThreadLocalRandom.current().nextInt(35, 65);
			position += speed * .5;
			focusLightIndex = position/1000;
			status = "Driving";
		}
		setInfo();
	}
	
	/**
	 * setFocusLightIndex - method that focuses in on the light
	 * that the car is approaching based off its current position.
	 */
	private void setFocusLightIndex() {
		focusLightIndex = position / 1000;
		if (focusLightIndex > 0) {
			focusLightIndex -= 1;
		}
	}
}
