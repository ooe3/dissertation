package main;
import java.awt.EventQueue;
import gui.StartGUI;


public class Run {
	public static void main(String[] args) {
		StartGUI window = new StartGUI();
		double average = 0;
		average+=((double)1550/120);
		System.out.printf("%.3f",average);
	}

}
