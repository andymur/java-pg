package com.andymur.pg.java.chessboard;

public class ChessBoardRunner {

	static final int COL_NUM = 8;
	static final int ROW_NUM = 8;

	public static final String BLACK_CELL = " X";
	public static final String WHITE_CELL = " O";

	public static void main(String[] args) {
		printBoard();
	}

	static void printBoard() {
		for (int i = 0; i < COL_NUM; i++) {
			String cell = i % 2 == 0 ? BLACK_CELL : WHITE_CELL;

			for (int j = 0; j < ROW_NUM; j++) {
				System.out.print(cell);
				cell = nextCell(cell);
			}
			System.out.print("\n");
		}
	}

	static String nextCell(String currentCell) {
		return currentCell.equals(WHITE_CELL) ? BLACK_CELL : WHITE_CELL;
	}

}
