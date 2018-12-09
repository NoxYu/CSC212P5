package edu.smith.cs.csc212.p5;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import me.jjfoley.gfx.GFX;
import me.jjfoley.gfx.IntPoint;
import me.jjfoley.gfx.TextBox;

public class Sudoku extends GFX{
	
	public Sudoku() {
		setupGame();
	}
	
	TextBox message = new TextBox("welcome to Sudoku");
	
	static Color Ruby = new Color(220,240,250);	
	static Color Nox = new Color(200,220,230);
	static Color Nene = new Color(230,220,225);
	static Color Yvonne = new Color(220,250,240);
	
	
	/*
	 * this list has all the SudokuCells 
	 */
	public static SudokuCell sudokuCells[][] = new SudokuCell[9][9];
	
	//mark the current cell(the cell we just clicked)
	SudokuCell clicked = null;
		
	static class SudokuCell{
		boolean canChange;
		boolean mouseHover;
		Rectangle2D area;
		int number;
		TextBox display;
		boolean gotClicked;
		
		public SudokuCell(int x, int y, int w, int h) {
			this.area = new Rectangle2D.Double(x,y,w,h);
			this.mouseHover=false;
			display =new TextBox(" ");
			number = 0;
			canChange = true;
			gotClicked = false;
		}
		
		public void draw(Graphics2D g) {
			if(mouseHover) {
				g.setColor(Nene);				
			}else if(gotClicked&&canChange){
				g.setColor(Yvonne);
			}else {
				g.setColor(Nox);
			}
			g.fill(this.area);

			if(number == 0) {
				this.display.setString(" ");
			}else {
				this.display.setString(Integer.toString(number));				
			}
			this.display.centerInside(this.area);
			this.display.setFontSize(30);
			g.setColor(Nox.darker());
			this.display.draw(g);			
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
				sudokuCells[i][j-1] = new SudokuCell(x,y,size,size);
				x+=size+2;
				if(j%3==0) {
					x+=5;
				}
			}
			y+=size+2;
			x=0;
		}
		
		/*
		 * transfer a 2D array from GameLogic to the 3 Lists representing 
		 * the Sudoku board 
		 */
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				SudokuCell current = sudokuCells[i][j];
				if(GameLogic.Easy[i][j]!=0) {
					current.canChange = false;
				}				
				current.number = GameLogic.Easy[i][j];				
			}
		}		
	}
	
	@Override
	public void update(double time) {
		IntPoint mouse =this.getMouseLocation();
		IntPoint click = this.processClick();
		
		boolean[] keyInput = new boolean[9];
		
		keyInput[0] = processKey(KeyEvent.VK_1);
		keyInput[1] = processKey(KeyEvent.VK_2);
		keyInput[2] = processKey(KeyEvent.VK_3);
		keyInput[3] = processKey(KeyEvent.VK_4);
		keyInput[4] = processKey(KeyEvent.VK_5);
		keyInput[5] = processKey(KeyEvent.VK_6);
		keyInput[6] = processKey(KeyEvent.VK_7);
		keyInput[7] = processKey(KeyEvent.VK_8);
		keyInput[8] = processKey(KeyEvent.VK_9);
		
				
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				SudokuCell cell = sudokuCells[i][j];
				cell.mouseHover=cell.contains(mouse);
				if(cell.contains(click)) {
					this.clicked = cell;
					this.clicked.gotClicked = true;
				}
			}
		}

		for(int i=0;i<9;i++) {
			if(keyInput[i]&&clicked.canChange) {
				this.clicked.number = i+1;
			}
		}
				
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Ruby);
		g.fillRect(0,0,this.getWidth(), this.getHeight());
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				sudokuCells[i][j].draw(g);
			}
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
