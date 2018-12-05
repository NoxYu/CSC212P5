package edu.smith.cs.csc212.p5;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import me.jjfoley.gfx.GFX;
import me.jjfoley.gfx.IntPoint;
import me.jjfoley.gfx.TextBox;

public class Sudoku extends GFX{
	
	public Sudoku() {
		setupGame();
	}
	
	SudokuState state = SudokuState.Correct;
	TextBox message = new TextBox("welcome to Sudoku");
	
	static Color Ruby = new Color(220,240,250);	
	static Color Nox = new Color(200,220,230);
	static Color Nene = new Color(230,220,225);
	
	/*
	 * this is the data structure representing the Sudoku board
	 * it has 29 ArrayLists - 9 columns, 9 rows, 9 groups of 9 grids, 2 diagonals  
	 */
	List<List<SudokuCell>> sudokuStructure= new ArrayList<>();
	List<SudokuCell> sudokuCells = new ArrayList<>();
		
	static class SudokuCell{
		boolean mouseHover;
		Rectangle2D area;
		int number;
		
		public SudokuCell(int x, int y, int w, int h) {
			this.area = new Rectangle2D.Double(x,y,w,h);
			this.mouseHover=false;
		}
		
		public void draw(Graphics2D g) {
			if(mouseHover) {
				g.setColor(Nene);				
			}else {
				g.setColor(Nox);
			}
			g.fill(this.area);
		}

		public boolean contains(IntPoint mouse) {
			if(mouse==null) {
				return false;
			}
			return this.area.contains(mouse);
		}
	}
	
	public void setupGame() {
		int size = getWidth()/11;
		int x=0, y=0;
		for(int i=0;i<9;i++) {
			if(i%3==0) {
				y+=5;
			}
			for(int j=1;j<=9;j++) {
				sudokuCells.add(new SudokuCell(x,y,size,size));
				x+=size+2;
				if(j%3==0) {
					x+=5;
				}
			}
			y+=size+2;
			x=0;
		}
	}
	
	@Override
	public void update(double time) {
		IntPoint mouse =this.getMouseLocation();
		
		for(SudokuCell cell : this.sudokuCells) {
			cell.mouseHover=cell.contains(mouse);
		}
		//set message base on the gaming status 
		switch(this.state) {
		case Correct:
			this.message.setString("Your Input is Correct");
			break;
		case Incorrect:
			this.message.setString("Your Input is not quit right");
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Ruby);
		g.fillRect(0,0,this.getWidth(), this.getHeight());
		for(SudokuCell cell : this.sudokuCells) {
			cell.draw(g);
		}
		
		Rectangle2D centerText = new Rectangle2D.Double(0,this.getHeight()*9/10,
				this.getWidth(),this.getWidth()/11);
		this.message.setFontSize(30.0);
		this.message.setColor(Nox.darker());
		this.message.centerInside(centerText);
		this.message.draw(g);
		
	}
	
	
	public static void main(String[] args) {
		Sudoku app = new Sudoku();
		app.start();
	}
}
