package com.IX.Cobo.Engine.Render;

public class Render {
	
	private int width, height;	
	public int[] pixels;
	
	int xtime = 10, ytime = 10;
	int counter = 0;
	
	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height];
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void render() {
		counter++;
		if(counter % 10 == 0) xtime++;
		if(counter % 80 == 0) ytime++;
		
		for(int y = 0; y < height; y++) {
			if(ytime >= height) break;
			for(int x = 0; x < width; x++) {
				if(xtime >= width) break;
				pixels[xtime + ytime * width] = 0xFF00FF;
			}
		}
	}

}
