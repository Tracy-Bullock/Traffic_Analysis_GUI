/**
 * @author Tracy Bullock
 * CMSC 335 7382 Object-Oriented and Concurrent Programming (2218)
 * Final Project
 * December 12, 2021
 * Created with Eclipse IDE
 * 
 * Car.java - A class that creates a car for the simulation, tracks
 * the car's name, speed, position, and status, and displays that 
 * information on the carInfoPane. Each car runs in its own thread.
 */

package tBullock_CMSC335_Final;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Car {
	
	JPanel infoPane, positionPane;
	JTextField txtName, txtStatus, txtSpeed, txtXposition, txtYposition;
	int speed, xPosition, yPosition = 0;
	String carName, status;
	boolean isPaused, isStopped;
	
	/**
	 * Car - parameterized constructor used to initialize all of the 
	 * cars information and create a new thread for the car to run in.
	 * 
	 * @param carCount of type int.
	 * @param carInfoPane of type JPanel.
	 */
	public Car(int carCount, JPanel carInfoPane) {
		isPaused = false;
		isStopped = false;
		infoPane = carInfoPane;
		carName = "Car " + carCount;
		
		Runnable speedAndPosition = new CarSpeedAndPosition(this);
		Thread driving = new Thread(speedAndPosition, "Car " + carCount);
		driving.start();
		addInfo();
	}
	
	/**
	 * addInfo - method used to create all of the vehicles information
	 * and add it to the infoPane.
	 */
	private void addInfo() {
		
		LineBorder border = new LineBorder(Color.BLACK);
		
		txtName = new JTextField(carName);
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setBorder(border);
		txtName.setEditable(false);
		infoPane.add(txtName);
		
		txtStatus = new JTextField(status);
		txtStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatus.setEditable(false);
		infoPane.add(txtStatus);
		
		txtSpeed = new JTextField(Integer.toString(speed));
		txtSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		txtSpeed.setEditable(false);
		infoPane.add(txtSpeed);
		
		positionPane = new JPanel();
		positionPane.setLayout(new GridLayout(2,1));
		infoPane.add(positionPane);
		
		txtXposition = new JTextField("X: " + xPosition);
		txtXposition.setHorizontalAlignment(SwingConstants.CENTER);
		txtXposition.setEditable(false);
		positionPane.add(txtXposition);
		
		txtYposition = new JTextField("Y: " + yPosition);
		txtYposition.setHorizontalAlignment(SwingConstants.CENTER);
		txtYposition.setEditable(false);
		positionPane.add(txtYposition);
	}

	/**
	 * setSpeed - method used to set the speed of the car.
	 * 
	 * @param speed of type int.
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * setxPosition - method to set the x position of the car.
	 * 
	 * @param xPosition of type int.
	 */
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * setStatus - method used to set the status of the car.
	 * 
	 * @param status of type string.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * isPaused - method to signal that the simulation has
	 * been paused.
	 * 
	 * @return isPaused of type boolean.
	 */
	public boolean isPaused() {
		return isPaused;
	}

	/**
	 * setPaused - method to set the value of isPaused.
	 * 
	 * @param isPaused of type boolean.
	 */
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	/**
	 * isStopped - method to signal that the simulation has 
	 * been stopped.
	 * 
	 * @return isStopped of type boolean.
	 */
	public boolean isStopped() {
		return isStopped;
	}

	/**
	 * setStopped - method to set the value of isStopped.
	 * 
	 * @param isStopped of type boolean.
	 */
	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}

	/**
	 * updateInfo - method to update the output of the car's
	 * information.
	 */
	public void updateInfo() {
		txtStatus.setText(status);
		txtSpeed.setText(Integer.toString(speed) + " KM/H");
		txtXposition.setText( "X: " + Integer.toString(xPosition));
	}
}
