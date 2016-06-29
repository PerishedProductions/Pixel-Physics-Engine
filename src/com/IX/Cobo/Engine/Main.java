package com.IX.Cobo.Engine;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static int width = 1280;
	public static int height = 720;

	public boolean running = false;

	Thread thread;
	private JFrame frame;

	public Main() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		
	    frame = new JFrame();
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (running) {
			System.out.println("Running");
		}
	}

	public static void main(String[] args) {
		Main main = new Main();
		
		main.frame = new JFrame();
		main.frame.setResizable(false);
		main.frame.setTitle("Pixel Physics Engine");
		main.frame.pack();
		main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.frame.setLocationRelativeTo(null);
		main.frame.setVisible(true);

	}
}
