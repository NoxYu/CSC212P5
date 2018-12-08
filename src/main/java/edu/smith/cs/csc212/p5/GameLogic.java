package edu.smith.cs.csc212.p5;
// Reference: https://medium.com/@ssaurel/build-a-sudoku-solver-in-java-part-1-c308bd511481
// Reference: https://dingo.sbs.arizona.edu/~sandiway/sudoku/examples.html

public class GameLogic {
	// a grid to solve
	public static int [][]GRID_TO_SOLVE = {
			{9,0,0,1,0,0,0,0,5},
			{0,0,5,0,9,0,2,0,1},
			{8,0,0,0,4,0,0,0,0},
			{0,0,0,0,8,0,0,0,0},
			{0,0,0,7,0,0,0,0,0},
			{0,0,0,0,2,6,0,0,9},
			{2,0,0,3,0,0,0,0,6},
			{0,0,0,2,0,0,9,0,0},
			{0,0,1,9,0,4,5,7,0},
	};
	
	public static int [][]Easy = {
			{1,0,0,4,8,9,0,0,6},
			{7,3,0,0,0,0,0,4,0},
			{0,0,0,0,0,1,2,9,5},
			{0,0,7,1,2,0,6,0,0},
			{5,0,0,7,0,3,0,0,8},
			{0,0,6,0,9,5,7,0,0},
			{9,1,4,6,0,0,0,0,6},
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
	
	private int[][]board;
	
	public GameLogic(int[][] board) {
		this.board = new int[9][9];
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.board[i][j] = board[i][j];
			}
		}
	}
	
	private boolean isInRow(int row, int number) {
		for (int i = 0; i < 9; i++) {
			if(board[row][i] == number) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isInCol(int col, int number) {
		for (int i = 0; i < 9; i++) {
			if(board[i][col] == number) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isInBox(int row, int col, int number) {
		int r = row - row % 3;
		int c = col - col % 3;
		
		for (int i = r; i < r + 3; i++) {
			for (int j = c; j < c + 3; j++) {
				if(board[i][j] == number) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isOk(int row, int col, int number) {
		return !isInRow(row, number) && !isInCol(col, number) && !isInBox(row, col, number);
	}
	
	//public boolean solve() {
		//for (int row = 0; row < Size; row++) {
			//for (int col = 0; col < Size; col++) {
				//if(board[row][col] == Empty) {
					//input;
					//if (isOk(row, col, input)) {
						//board[row][col] = input;
					//}
				//}
			//}
		//}
	//}
	
	private boolean correct(int row, int col, int number) {
		for (row = 0; row < 9; row++) {
			for (col = 0; col < 9; col++){
				if(board[row][col] != 0 && isOk(row, col, number) == true) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean backtracking() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++){
				if(board[row][col] == 0) {
					for (int input = 1; input <= 9; input++) {
						if(isOk(row,col,input)) {
							board[row][col] = input;
							if (backtracking() == true) {
								return true;
							} else {
								board[row][col] = 0;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}
	
	public void display() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(" "+ board[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		GameLogic sudoku = new GameLogic(GRID_TO_SOLVE);
		System.out.println("Time for Sudoku");
		sudoku.display();
		if (sudoku.backtracking() == true){
			sudoku.display();
		} else{
			System.out.println("not solvable");
		}
	}
}