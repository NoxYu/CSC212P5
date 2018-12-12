package edu.smith.cs.csc212.p5;

import java.util.*;
import edu.smith.cs.csc212.p5.Sudoku.*;

// Reference: https://medium.com/@ssaurel/build-a-sudoku-solver-in-java-part-1-c308bd511481
// Reference: https://dingo.sbs.arizona.edu/~sandiway/sudoku/examples.html
/** 
 * Rules for Sudoku: no duplicate in row/column/box
 * @author Ruby Ru, Nox Yu
 *
 */
public class GameLogic {
	/*
	 *  grids available
	 */	
	public static int [][]Test = {
		{0,5,2,4,8,9,3,7,6},
		{7,3,9,2,5,6,8,4,1},
		{4,6,8,3,7,1,2,9,5},
		{3,8,7,1,2,4,6,5,9},
		{5,9,1,7,6,3,4,2,8},
		{2,4,6,8,9,5,7,1,3},
		{9,1,4,6,3,7,5,8,2},
		{6,2,5,9,4,8,1,3,7},
		{8,7,3,5,1,2,9,6,4},
	};
			
			
	public static int [][]Easy = {
			{1,0,0,4,8,9,0,0,6},
			{7,3,0,0,0,0,0,4,0},
			{0,0,0,0,0,1,2,9,5},
			{0,0,7,1,2,0,6,0,0},
			{5,0,0,7,0,3,0,0,8},
			{0,0,6,0,9,5,7,0,0},
			{9,1,4,6,0,0,0,0,0},
			{0,2,0,0,0,0,0,3,7},
			{8,0,0,5,1,2,0,0,4},
	};
	
	public static int [][]Intermediate = {
			{0,2,0,6,0,8,0,0,0},
			{5,8,0,0,0,9,7,0,0},
			{0,0,0,0,4,0,0,0,0},
			{3,7,0,0,0,0,5,0,0},
			{6,0,0,0,0,0,0,0,4},
			{0,0,8,0,0,0,0,1,3},
			{0,0,0,0,2,0,0,0,0},
			{0,0,9,8,0,0,0,3,6},
			{0,0,0,3,0,6,0,9,0},
	};
	
	public static int [][]Hard = {
			{0,0,0,6,0,0,4,0,0},
			{7,0,0,0,0,3,6,0,0},
			{0,0,0,0,9,1,0,8,0},
			{0,0,0,0,0,0,0,0,0},
			{0,5,0,1,8,0,0,0,3},
			{0,0,0,3,0,6,0,4,5},
			{0,4,0,2,0,0,0,6,0},
			{9,0,3,0,0,0,0,0,0},
			{0,2,0,0,0,0,1,0,0},
	};
	

	public int[][] board = new int[9][9];
	
	/*
	 * randomly generate a sudoku board from the available options 
	 */
	static Random rn = new Random();
	public static ArrayList<int [][]> mode = new ArrayList<>(Arrays.asList(Easy,Intermediate,Hard));
	public static int[][]level = mode.get(rn.nextInt(3));

	/*
	 * copy the number each sudoku cell contains to GameLogic
	 */
	public GameLogic() {
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				board[i][j] = Sudoku.sudokuCells[i][j].number;
			}
		}
	}
	 
	/**
	 * check if there is a duplicate number in this row
	 * @param row - the row number of the sudoku cell to-be-checked
	 * @param number - the number represented by the sudoku cell to-be-checked
	 * @return true - there is a duplicate number in the same row
	 */
	public boolean rowHasDup(int[][] board, int row, int col) {
		int currentNumber = board[row][col];
		for (int i = 0; i < 9; i++) {
			if(i!=col&&board[row][i] == currentNumber) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * check if there is a duplicate number in this column
	 * @param col - the column number of the sudoku cell to-be-checked
	 * @param number - the number represented by the sudoku cell to-be-checked
	 * @return true - there is a duplicate number in the same column
	 */	
	public boolean colHasDup(int[][] board, int row, int col) {
		int currentNumber = board[row][col];
		for (int i = 0; i < 9; i++) {
			if(i!=row&&board[i][col]== currentNumber) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * check if there is a duplicate number in this box
	 * @param row - the row number of the sudoku cell to-be-checked
	 * @param col - the column number of the sudoku cell to-be-checked
	 * @param number - the number represented by the sudoku cell to-be-checked
	 * @return true - there is a duplicate number in the same box
	 */
	public boolean boxHasDup(int[][] board, int row, int col) {
		int r = row - row % 3;
		int c = col - col % 3;
		
		int currentNumber = board[row][col];
		for (int i = r; i < r + 3; i++) {
			for (int j = c; j < c + 3; j++) {
				if(i!=row&&j!=col&&board[i][j]== currentNumber) {
					return true;
				}
			}
		}
		return false; 
	}
	
	/**
	 * 
	 * @param row - row number of this sudoku cell
	 * @param col - column number of this sudoku cell 
	 * @return whether input at this sudoku cell is valid
	 */
	public boolean isOk(int[][] board, int row, int col) {
		return !rowHasDup(board, row, col) && !colHasDup(board, row, col) && !boxHasDup(board, row, col);
	}
	

	
	
	/**
	 * 
	 * @return whether all inputs of the sudoku board are valid 
	 */
	public boolean correct(int[][] board) {
		int count = 0;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++){
				int number = board[row][col];
				if(!isOk(board, row, col)) {
					return false;
				}
			}
		} 
		return true;
	}
	
	
	/**
	 * 
	 * @return true: the sudoku is solvable; vice versa
	 */
	public boolean backtracking(int[][] board) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++){
				if(board[row][col]== 0) {
					for (int input = 1; input <= 9; input++) {
						board[row][col] = input;
						if(isOk(board, row,col) && backtracking(board) == true) {
							return true;
						} else {
							board[row][col] = 0;
						}
					}
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * helper method - displays the entire 2D array
	 */
	public void display(int[][] board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(" "+ board[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
}