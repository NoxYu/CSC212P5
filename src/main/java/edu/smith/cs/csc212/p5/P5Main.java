package edu.smith.cs.csc212.p5;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import me.jjfoley.gfx.GFX;
import me.jjfoley.gfx.TextBox;

/**
 * This class is like class PlayFish in P2
 */

public class P5Main extends GFX {
   
	public static int VISUAL_GRID_SIZE= 400;
	public static int LOGICAL_GRID_SIZE = 15;
	public static int DIALOGUE_WINDOW_SIZE = 130;

	/**
	 * this is where the game logic lives 
	 */
	Game game;
	/**
	 * This TextBox wraps up making fonts and centering text.
	 */
	TextBox gameState = new TextBox("");
	/**
	 * This is the dialogue window.
	 */
	Rectangle2D dialogueWindow;

	/**
	 * set color for the grid frame.
	 */
	public static Color GRID_COLOR = new Color(255, 170, 245);
	
	
	public P5Main() {
		super(VISUAL_GRID_SIZE, VISUAL_GRID_SIZE + DIALOGUE_WINDOW_SIZE);
		game = new Game(LOGICAL_GRID_SIZE, LOGICAL_GRID_SIZE);		
		dialogueWindow = new Rectangle2D.Double(0,0,getWidth(),DIALOGUE_WINDOW_SIZE);
	}
	
	/**
	 * how big is a tile?
	 * @return the width of a tile 
	 */
	public int getTileW() {
		return VISUAL_GRID_SIZE/game.world.getWidth();
	}
	public int getTileH() {
		return VISUAL_GRID_SIZE/game.world.getHeight();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0,0,getWidth(), getHeight());
		
		World world = game.world;
		
		// Use the tile-sizes.  
		int tw = getTileW();
		int th = getTileH(); 

		// set color of all grids.
		g.setColor(new Color(5,5,5));
		g.fillRect(0, 0, tw * world.getWidth(), th * world.getHeight());
		// Draw a grid to better picture how the game works.
		g.setColor(GRID_COLOR);
		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				g.drawRect(x * tw, y * th, tw, th);
			}
		}
		
	}	
  
	public static void main(String[] args) {
		System.out.println("Game Started!");
		GFX app = new P5Main();
		app.start();
	}
  
}
