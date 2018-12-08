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
	
	SudokuState state = SudokuState.hasEmptyEntry;
	TextBox message = new TextBox("welcome to Sudoku");
	
	static Color Ruby = new Color(220,240,250);	
	static Color Nox = new Color(200,220,230);
	static Color Nene = new Color(230,220,225);
	static Color Yvonne = new Color(220,250,240);
	
	/*
	 * this is the data structure representing the Sudoku board
	 */
	List<List<SudokuCell>> rows = new ArrayList<>();
	List<List<SudokuCell>> colums = new ArrayList<>();
	List<List<SudokuCell>> box = new ArrayList<>();
	
	/*
	 * this list has all the SudokuCells 
	 */
	List<SudokuCell> sudokuCells = new ArrayList<>();
	
	//mark the current cell(the cell we just clicked)
	SudokuCell clicked = null;
		
	static class SudokuCell{
		boolean mouseHover;
		Rectangle2D area;
		int number = 0;
		TextBox display;
		boolean gotClicked=false;
		
		public SudokuCell(int x, int y, int w, int h) {
			this.area = new Rectangle2D.Double(x,y,w,h);
			this.mouseHover=false;
			display =new TextBox(" ");
		}
		
		public void draw(Graphics2D g) {
			if(mouseHover) {
				g.setColor(Nene);				
			}else if(gotClicked){
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
		
				
		for(SudokuCell cell : this.sudokuCells) {
			cell.mouseHover=cell.contains(mouse);
			if(cell.contains(click)) {
				this.clicked = cell;
				this.clicked.gotClicked = true;
			}
		}

		for(int i=0;i<9;i++) {
			if(keyInput[i]) {
				this.clicked.number = i+1;
			}
		}

		
		//set message base on the gaming status 
		switch(this.state) {
		case allValid:
			this.message.setString("You win");
			break;
		case hasEmptyEntry:
			this.message.setString("waiting for input...");
		case hasInvalidEntry:
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
