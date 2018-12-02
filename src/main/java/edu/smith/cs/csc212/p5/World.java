package edu.smith.cs.csc212.p5;

import java.util.*;

public class World {
	/**
	 * this is the starting place of the game
	 */
	Place startingPlace = new Place();

	private int width;
	private int height;
	 
	public World(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	
	/**
	 * how big is the world we model?
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * 
	 * @return the height 
	 */
	public int getHeight() {
		return height;
	}
}
