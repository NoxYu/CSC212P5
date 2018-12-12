package edu.smith.cs.csc212.p5;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import me.jjfoley.gfx.*;

public class Sudoku extends GFX{
	
	public Sudoku() {
		setupGame();
	}
	
	TextBox message = new TextBox("welcome to Sudoku");
	
	static Color Ruby = new Color(220,240,250);	
	static Color Nox = new Color(200,220,230);
	static Color Nene = new Color(230,220,225);
	static Color Yvonne = new Color(220,250,240);
	
	AnswerButton ab;
	
	static int[][] KEY = new int[9][9];
	static int[][] BOARD = new int[9][9];
	
	GameLogic gameLogic;
	
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
			if(mouseHover&&canChange) {
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
	
	static class AnswerButton{
		
		TextBox display;
		boolean mouseHover;
		Rectangle2D area;
		boolean showAnswer;
		
		public AnswerButton(int x, int y, int w, int h) {
			this.area = new Rectangle2D.Double(x,y,w,h);
			this.mouseHover=false;
			display =new TextBox("Show Answer");
			showAnswer = false;
		}		
		
		public void draw(Graphics2D g) {
			if(mouseHover) {
				g.setColor(Yvonne);				
			}else {
				g.setColor(Nene);
			}
			g.fill(this.area);

			this.display.centerInside(this.area);
			this.display.setFontSize(12);
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
		int x=size/2, y=0;
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
			x=size/2;
		}
		
		int buttonW = getWidth()/11;
		int buttonH = (getHeight()/11)*2;
		ab = new AnswerButton(size/2, size*10-5, buttonH, buttonW);
		
		
				
		/*
		 * transfer a 2D array from GameLogic to sudokuCells
		 */
		BOARD = GameLogic.Easy;
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				SudokuCell current = sudokuCells[i][j];
				if(BOARD[i][j]!=0) {
					//grids that already have a number in them cannot be changed
					current.canChange = false;
				}				
				current.number = BOARD[i][j];
			}
		}
		
		/*
		 *  set up an answer key
		 */
		gameLogic = new GameLogic();
		KEY = BOARD;
		gameLogic.backtracking(KEY);
		gameLogic.display(KEY);
		
	}
	
	/**
	 * check if the sudoku board has any empty grid
	 * @return
	 */
	public boolean hasEmpty() {
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(sudokuCells[i][j].number==0) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return true - if all inputs to the sudoku board are correct
	 */
	public boolean correct() {
		int[][] cellNums = new int[9][9];
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				cellNums[i][j] = sudokuCells[i][j].number;
			}
		}
		return gameLogic.correct(cellNums);
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

		ab.mouseHover = ab.contains(mouse);
		if(ab.contains(click)) {
			if(ab.showAnswer) {
				//display a solution to the current sudoku board
				for(int i=0;i<9;i++) {
					for(int j=0;j<9;j++){
						if(sudokuCells[i][j].canChange) {
							sudokuCells[i][j].number = KEY[i][j];
						}
			 		}
				}				
				ab.showAnswer = false;
				ab.display.setString("Hide Answer");
			}else {
				//hide all answers 
				for(int i=0;i<9;i++) {
					for(int j=0;j<9;j++) {
						if(sudokuCells[i][j].canChange) {
							sudokuCells[i][j].number = 0;
						}
					}
				}
				
				ab.showAnswer= true;
				ab.display.setString("Show Answer");
			}
		}
		
		
		for(int i=0;i<9;i++) {
			if(keyInput[i]&&clicked.canChange) {
				this.clicked.number = i+1;
			}
		}
		
		if(!hasEmpty()) {
			if(this.correct()) { 
				message.setString("You win!");
			}else {
				message.setString("Your Answer is not fully correct");				
			}
		}else {
			message.setString("Welcome to Sudoku");
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Ruby);
		g.fillRect(0,0,this.getWidth(), this.getHeight());
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				sudokuCells[i][j].display.setColor(Nox.darker().darker());
				sudokuCells[i][j].draw(g);
			}
		}	
		
		//draw the Show-Answer button
		ab.display.setColor(Nox.darker().darker());
		ab.draw(g);
				
		
		Rectangle2D centerText = new Rectangle2D.Double(0,this.getHeight()*9/10 -5,
				this.getWidth(),this.getWidth()/11);
		this.message.setFontSize(20.0);
		this.message.setColor(Nox.darker().darker());
		this.message.centerInside(centerText);
		this.message.draw(g);		
	}
	
	
	public static void main(String[] args) {
		Sudoku app = new Sudoku();
		app.start();
	}
}
