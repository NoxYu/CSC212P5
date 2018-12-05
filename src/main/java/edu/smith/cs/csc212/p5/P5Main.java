package edu.smith.cs.csc212.p5;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
 import me.jjfoley.gfx.GFX;
import me.jjfoley.gfx.IntPoint;
import me.jjfoley.gfx.TextBox;

/**
 * This class is like class PlayFish in P2
 */

public class P5Main extends GFX {
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(200,210,230));
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Game Started!");
		GFX app = new P5Main();
		app.start();
	}
  
}