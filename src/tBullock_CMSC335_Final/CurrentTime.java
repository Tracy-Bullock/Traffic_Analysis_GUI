/**
 * @author Tracy Bullock
 * CMSC 335 7382 Object-Oriented and Concurrent Programming (2218)
 * Final Project
 * December 12, 2021
 * Created with Eclipse IDE
 * 
 * CurrentTime.java - A runnable class to control the current time 
 * count, giving a current time stamp in 1 second intervals.
 */

package tBullock_CMSC335_Final;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

public class CurrentTime implements Runnable {
	
	SimpleDateFormat df = new SimpleDateFormat("H:m:s");
	Date now;
	JLabel lblTimeStamp;
	
	/**
	 * CurrentTime - parameterized constructor that initializes a
	 * label to hold the current time and adds it to the content
	 * pane passed into it.
	 * 
	 * @param contentPane of type JPanel.
	 */
	public CurrentTime(JPanel contentPane) {
		
		lblTimeStamp = new JLabel();
		lblTimeStamp.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeStamp.setBounds(300, 47, 300, 14);
		contentPane.add(lblTimeStamp);
	}

	/**
	 * run - overrides the original run method in Runnable and constantly
	 * updates the current time in 1 second intervals.
	 */
	@Override
	public void run() {
		while (true) {
			now = new Date();
			lblTimeStamp.setText("Current time: " + df.format(now));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "An error has occurred", "THREAD ERROR", JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}

}
