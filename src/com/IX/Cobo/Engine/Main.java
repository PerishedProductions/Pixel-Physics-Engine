package com.IX.Cobo.Engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.IX.Cobo.Engine.Render.Render;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static int width = 960;
	public static int height = width / 16 * 9;

	public boolean running = false;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Render render;
	
	Thread thread;
	private JFrame frame;

	public Main() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		
		render = new Render(width, height);
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
			long lastTime = System.nanoTime();
			double ns = 1000000000.0 / 60.0;
			double delta = 0;
			
		    while (running) {
		    	long now = System.nanoTime();
		    	
		    	delta += (now - lastTime) / ns;
		    	lastTime = now;
		    	
		    	while(delta >= 1) {
		    		update();
		    		delta--;
		    	}
		    	
		        render();
		    }
		    
		    stop();
		}
	}

	public void update() {
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		render.clear();
		render.render();

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = render.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Main main = new Main();

		main.frame = new JFrame();
		main.frame.setResizable(false);
		main.frame.setTitle("Pixel Physics Engine");
		main.frame.add(main);
		main.frame.pack();
		main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.frame.setLocationRelativeTo(null);
		main.frame.setVisible(true);
		
		main.start();

	}
}
