/**
 * @author Tracy Bullock
 * CMSC 335 7382 Object-Oriented and Concurrent Programming (2218)
 * Final Project
 * December 12, 2021
 * Created with Eclipse IDE
 * 
 * TrafficAnalysisGUI.java - the main class of an application that provides
 * traffic information such as real time traffic light display, current time,
 * vehicle speed and location. The program begins with three vehicles and three
 * main intersections, but both can be added by clicking the appropriate buttons.
 * Vehicles travel at speeds measured in Kilometers per hour and the intersections
 * are 1000 meters apart. The simulation can be paused, continued, stopped, and 
 * started new. Each traffic light, each vehicle, and the time runs in its own thread.
 * When cars reach the end of the road, they loop back to the begining.
 */

package tBullock_CMSC335_Final;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TrafficAnalysisGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JButton btnStart, btnPause, btnStop, btnContinue, btnAddCar, btnAddIntersection;
	JPanel contentPane, buttonPane, trafficLightPane, lightLabelsPane, lightIconsPane, vehicleInfoPane;
	JTextField txtVehicle, txtStatus, txtSpeed, txtPosition;
	JLabel lblTimeStamp;
	TrafficLight trafficLight;
	static ArrayList<TrafficLight> tLights;
	CarInfoPane carInfo;
	int intersectionCount;
	public static int roadLength = 0;

	/**
	 * main - the main method that intiates the frame.
	 * @param args
	 */
	public static void main(String[] args) {
		
		TrafficAnalysisGUI frame = new TrafficAnalysisGUI();
		frame.setVisible(true);

	}
	
	/**
	 * TrafficAnalysisGUI - the default constructor that iniates
	 * the program and threads for the clock and traffic lights.
	 */
	public TrafficAnalysisGUI() {
		
		tLights = new ArrayList<TrafficLight>();
		intersectionCount = 1;
		initComponents();
		
		
		
		Runnable currentTime = new CurrentTime(contentPane);
		Thread time = new Thread(currentTime, "Time Thread");
		time.start();
		
		for (TrafficLight light: tLights) {
			Runnable trafficLight = new TrafficLightDisplay(light);
			Thread intersection = new Thread(trafficLight, "Intersection " + intersectionCount);
			intersection.start();
		}
		
		
	}
	
	/**
	 * initComponents - method to iniate the components of the
	 * swing application.
	 */
	private void initComponents() {
		
		setTitle("Traffic Analysis Program");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 900, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		
		try {
			Image shapeImg = ImageIO.read(this.getClass().getResource("TrafficLight.png"));
			setIconImage(shapeImg);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An error has occured with the logo!", 
					"LOGO ERROR", JOptionPane.WARNING_MESSAGE);
		}
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblTrafficIcon = new JLabel();
		lblTrafficIcon.setBounds(45, 100, 800, 211);
		contentPane.add(lblTrafficIcon);
		ImageIcon trafficIcon = new ImageIcon(TrafficAnalysisGUI.class.getResource("Traffic.png"));
		lblTrafficIcon.setIcon(trafficIcon);
		
		createTrafficLights();
		
		carInfo = new CarInfoPane();
		contentPane.add(carInfo);
		
		buttonPane = new JPanel();
		buttonPane.setBounds(45, 631, 800, 23);
		buttonPane.setBackground(Color.LIGHT_GRAY);
		contentPane.add(buttonPane);
		buttonPane.setLayout(new GridLayout(1, 0, 5, 0));
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(this);
		btnStart.setEnabled(false);
		buttonPane.add(btnStart);
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(this);
		buttonPane.add(btnStop);
		
		btnPause = new JButton("Pause");
		btnPause.addActionListener(this);
		buttonPane.add(btnPause);
		
		btnContinue = new JButton("Continue");
		btnContinue.addActionListener(this);
		btnContinue.setEnabled(false);
		buttonPane.add(btnContinue);
		
		btnAddCar = new JButton("Add Car");
		btnAddCar.addActionListener(this);
		buttonPane.add(btnAddCar);
		
		btnAddIntersection = new JButton("Add Intersection");
		btnAddIntersection.addActionListener(this);
		buttonPane.add(btnAddIntersection);
		
	}
	
	/**
	 * createTrafficLights - method to create panels for the
	 * traffic lights and calls method to create intersections
	 */
	public void createTrafficLights() {
		
		trafficLightPane = new JPanel();
		trafficLightPane.setBounds(100, 320, 700, 69);
		trafficLightPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(trafficLightPane);
		
		lightLabelsPane = new JPanel();
		lightLabelsPane.setBackground(Color.LIGHT_GRAY);
		lightLabelsPane.setLayout(new GridLayout(1,0,20, 0));
		trafficLightPane.add(lightLabelsPane, BorderLayout.NORTH);
		
		lightIconsPane = new JPanel();
		lightIconsPane.setBackground(Color.LIGHT_GRAY);
		lightIconsPane.setLayout(new GridLayout(1,0,20, 0));
		trafficLightPane.add(lightIconsPane, BorderLayout.CENTER);
		
		for (int i = 0; i < 3; i++) {
			createIntersections();
		}
	}
	
	/**
	 * createIntersections - method to create intersections with their
	 * own threads.
	 */
	public void createIntersections() {
		lightLabelsPane.add(new JLabel("Intersection " + intersectionCount));
		TrafficLight tl = new TrafficLight(intersectionCount);
		lightIconsPane.add(tl);
		tLights.add(tl);
		
		intersectionCount++;
		
		Runnable trafficLight = new TrafficLightDisplay(tl);
		Thread intersection = new Thread(trafficLight, "Intersection " + intersectionCount);
		intersection.start();
		
		if (intersectionCount == 8) {
			btnAddIntersection.setEnabled(false);
		}
	}
	
	/**
	 * createCars - method to create cars for the simulation.
	 */
	public void createCars() {
		carInfo.addCar();
		if (carInfo.carCount > 6) {
			btnAddCar.setEnabled(false);
		}
	}
	
	/**
	 * pauseSim - method used to send signals to the individual 
	 * threads to pause the simulation.
	 */
	public void pauseSim() {
		for (TrafficLight light: tLights) {
			light.setPaused(true);
		}
		for (Car car: carInfo.cars) {
			car.setPaused(true);
		}
		btnStop.setEnabled(false);
		btnPause.setEnabled(false);
		btnContinue.setEnabled(true);
		btnAddCar.setEnabled(false);
		btnAddIntersection.setEnabled(false);
	}
	
	/**
	 * resumeSim - method used to send signals to the individual 
	 * threads to continue the simulation.
	 */
	public void resumeSim() {
		for (TrafficLight light: tLights) {
			light.setPaused(false);
		}
		for (Car car: carInfo.cars) {
			car.setPaused(false);
		}
		btnStop.setEnabled(true);
		btnContinue.setEnabled(false);
		btnPause.setEnabled(true);
		btnAddCar.setEnabled(true);
		btnAddIntersection.setEnabled(true);
	}
	
	/**
	 * getRoadLength - method used to help threads monitor the
	 * length of the road.
	 * 
	 * @return roadLength of type int.
	 */
	public static synchronized int getRoadLength() {
		return roadLength;
	}
	
	/**
	 * getTrafficSignals - method used to help threads monitor the 
	 * traffic signals.
	 * 
	 * @return tLights of type ArrayList<TrafficLight>.
	 */
	public static synchronized ArrayList<TrafficLight> getTrafficSignals() {
		return tLights;
	}
	
	/**
	 * startSim - method used to start a new simulation.
	 */
	public void startSim() {
		intersectionCount = 1;
		roadLength = 0;
		tLights.clear();
		createTrafficLights();
		carInfo = new CarInfoPane();
		contentPane.add(carInfo);
		btnStart.setEnabled(false);
		btnStop.setEnabled(true);
		btnPause.setEnabled(true);
		btnAddCar.setEnabled(true);
		btnAddIntersection.setEnabled(true);
	}
	
	/**
	 * stopSim - method used to end the current simulation.
	 */
	public void stopSim() {
		for (Car car: carInfo.cars) {
			car.setStopped(true);
		}
		
		for (TrafficLight light: tLights) {
			light.setStopped(true);
		}

		contentPane.remove(trafficLightPane);
		contentPane.repaint();
		carInfo.stopSim();
		btnStop.setEnabled(false);
		btnStart.setEnabled(true);
		btnPause.setEnabled(false);
		btnAddCar.setEnabled(false);
		btnAddIntersection.setEnabled(false);
	}

	/**
	 * actionPerformed - overrides the original method in 
	 * actionListener and is used to control actions of 
	 * the buttons.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(btnStart)) {
			startSim();

		} else if (e.getSource().equals(btnStop)) {
			stopSim();

		} else if (e.getSource().equals(btnPause)) {
			pauseSim();

		} else if (e.getSource().equals(btnContinue)) {
			resumeSim();

		} else if (e.getSource().equals(btnAddCar)) {
			createCars();

		} else {
			createIntersections();

		}
		
	}

}
