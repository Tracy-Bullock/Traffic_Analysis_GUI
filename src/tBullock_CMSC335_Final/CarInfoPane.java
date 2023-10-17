/**
 * @author Tracy Bullock
 * CMSC 335 7382 Object-Oriented and Concurrent Programming (2218)
 * Final Project
 * December 12, 2021
 * Created with Eclipse IDE
 * 
 * CarInfoPane.java - A class that creates a JPanel to create cars and
 * hold the car information of each car within the simulation.
 */

package tBullock_CMSC335_Final;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class CarInfoPane extends JPanel {

	private static final long serialVersionUID = 1L;
	JTextField txtVehicle, txtStatus, txtSpeed, txtPosition;
	Car car;
	ArrayList<Car> cars = new ArrayList<Car>();
	int carCount = 1;
	
	/**
	 * CarInfoPane - default constructor that iniates the JPanel
	 * components and creates the first 3 cars used for the 
	 * simulation.
	 */
	public CarInfoPane() {
		initComponents();
		
		for (int i = 0; i < 3; i++) {
			addCar();
		}
	}
	
	/**
	 * initComponents - method used to create and initialize all
	 * of the components of this JPanel.
	 */
	private void initComponents() {
		
		setBounds(45, 400, 800, 220);
		setLayout(new GridLayout(0,4));
		
		txtVehicle = new JTextField("VEHICLE INFO");
		txtVehicle.setHorizontalAlignment(SwingConstants.CENTER);
		txtVehicle.setEditable(false);
		add(txtVehicle);
		
		LineBorder border = new LineBorder(Color.BLACK);
		
		txtStatus = new JTextField("STATUS");
		txtStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txtStatus.setBorder(border);
		txtStatus.setEditable(false);
		add(txtStatus);
		
		txtSpeed = new JTextField("SPEED");
		txtSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		txtSpeed.setBorder(border);
		txtSpeed.setEditable(false);
		add(txtSpeed);
		
		txtPosition = new JTextField("POSITION");
		txtPosition.setHorizontalAlignment(SwingConstants.CENTER);
		txtPosition.setBorder(border);
		txtPosition.setEditable(false);
		add(txtPosition);
	}
	
	/**
	 * addCar - method used to create and add a new car to the
	 * simulation.
	 */
	public void addCar() {
		cars.add(new Car(carCount, this));
		carCount++;
	}
	
	/**
	 * stopSim - method used to signal that the simulation should
	 * be stopped and removed all of the information from the panel,
	 * displying that the simulation has been stopped.
	 */
	public void stopSim() {
		removeAll();
		setLayout(new BorderLayout(0, 0));
		JLabel stopped = new JLabel("SIMULATION STOPPED");
		stopped.setHorizontalAlignment(SwingConstants.CENTER);
		add(stopped, BorderLayout.CENTER);
	}
}
