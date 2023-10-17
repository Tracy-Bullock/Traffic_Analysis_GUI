/**
 * @author Tracy Bullock
 * CMSC 335 7382 Object-Oriented and Concurrent Programming (2218)
 * Final Project
 * December 12, 2021
 * Created with Eclipse IDE
 * 
 * TrafficLight.java - A class that creates each traffic light for 
 * the intersections, updates the overall road length when intersections
 * are added, and signals for the simulation to be paused or stopped.
 */

package tBullock_CMSC335_Final;

import javax.swing.*;

public class TrafficLight extends JLabel {
	
	private static final long serialVersionUID = 1L;
	int roadLength = 1000, position;
	String name, color;
	boolean isPaused, isStopped;
	
	/**
	 * TrafficLight - parameterized constructor used to initialize 
	 * the light information and set the overall road length in the
	 * main.
	 * 
	 * @param intersectionCount of type int.
	 */
	public TrafficLight(int intersectionCount) {
		name = "Intersection " + intersectionCount;
		position = intersectionCount * roadLength;
		isPaused = false;
		isStopped = false;
		TrafficAnalysisGUI.roadLength += roadLength;
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
	 * setColor - method used to set the color of the traffic light.
	 * 
	 * @param color of type string.
	 */
	synchronized void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * getIntersectionName - method used to return the intersection
	 * name.
	 * 
	 * @return name of type String.
	 */
	synchronized String getIntersectionName() {
		return name;
	}
	
	/**
	 * getColor - method used to return the traffic light
	 * color.
	 * 
	 * @return color of type String.
	 */
	synchronized String getColor() {
		return color;
	}
	
	/**
	 * getPosition - method used to return the traffic light
	 * position. 
	 * 
	 * @return position of type int.
	 */
	synchronized int getPosition() {
		return position;
	}
}
