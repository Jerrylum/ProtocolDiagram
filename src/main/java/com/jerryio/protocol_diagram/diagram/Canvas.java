package com.jerryio.protocol_diagram.diagram;

public class Canvas {

	private final int width;
	private final int height;
	private char[][] canvas;

	public Canvas(int width, int height) {
			this.height = height * 2 + 1;
			this.width = width * 2 + 1;
			this.canvas = new char[this.height][this.width];
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public char getChar(Coordinate c) {
		return this.canvas[c.y()][c.x()];
	}

	public void setChar(Coordinate c, char content) {
		this.canvas[c.y()][c.x()] = content;
	}

	public void reset() {
		for (int i = 0; i < this.height; i++) {
				for (int j = 0; j < this.width; j ++) {
						this.canvas[i][j] = ' ';
				}
		}
	}

	public void drawRectangle(int x, int y, int length) {
    // draw top border
    for (int i = x + 1; i < x + length; i++) {
			if ((int) this.canvas[y][i] == 0) {
				this.canvas[y][i] = GridType.HORIZONTAL.toChar();
			}
    }

    // draw bottom border
    for (int i = x + 1; i < x + length; i++) {
			if ((int) this.canvas[y + 2][i] == 0) {
				this.canvas[y + 2][i] = GridType.HORIZONTAL.toChar();
			}
    }

		// draw left and right border
    this.canvas[y + 1][x] = GridType.VERTICAL.toChar();
    this.canvas[y + 1][x + length] = GridType.VERTICAL.toChar();
	}

	public void drawText(int x, int y, int length, String str) {
		final String trimmed = str.substring(0, Math.min(str.length(), length - 1));
		final int pivot = x + (length - trimmed.length() - 1) / 2;

		for (int i = 0; i < trimmed.length(); i++) {
			this.canvas[y][pivot + i + 1] = trimmed.charAt(i);
		}
	}

	public void generateCorners() {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				final boolean top = this.canvas[Math.max(0, i - 1)][j] == GridType.VERTICAL.toChar();
				final boolean bottom = this.canvas[Math.min(this.height - 1, i + 1)][j] == GridType.VERTICAL.toChar();
				final boolean left = this.canvas[i][Math.max(0, j - 1)] == GridType.HORIZONTAL.toChar();
				final boolean right = this.canvas[i][Math.min(this.width - 1, j + 1)] == GridType.HORIZONTAL.toChar();

				if (top && right && bottom && left) {
					this.canvas[i][j] = GridType.ALL.toChar();
				} else if (top && right && bottom) {
					this.canvas[i][j] = GridType.NON_LEFT.toChar();
				} else if (right && bottom && left) {
					this.canvas[i][j] = GridType.NON_TOP.toChar();
				} else if (bottom && left && top) {
					this.canvas[i][j] = GridType.NON_RIGHT.toChar();
				} else if (left && top && right) {
					this.canvas[i][j] = GridType.NON_BOTTOM.toChar();
				} else if (top && right) {
					this.canvas[i][j] = GridType.BOTTOM_LEFT.toChar();
				} else if (right && bottom) {
					this.canvas[i][j] = GridType.TOP_LEFT.toChar();
				} else if (bottom && left) {
					this.canvas[i][j] = GridType.TOP_RIGHT.toChar();
				} else if (left && top) {
					this.canvas[i][j] = GridType.BOTTOM_RIGHT.toChar();
				} else if (top && bottom) {
					this.canvas[i][j] = GridType.VERTICAL.toChar();
				} else if (left && right) {
					this.canvas[i][j] = GridType.HORIZONTAL.toChar();
				}
			}
		}
	}

	public void eliminateBorder(int x, int y, int length) {
    for (int i = x + 1; i < x + length; i++) {
			if ((int) this.canvas[y + 2][i] == GridType.HORIZONTAL.toChar()) {
				this.canvas[y + 2][i] = GridType.EMPTY.toChar();
			}
    }
	}

	public String toString() {
		final StringBuilder s = new StringBuilder();

		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				s.append(this.canvas[i][j] == 0 ? " " : this.canvas[i][j]);
			}
			s.append("\n");
		}

		return s.toString();
	}
	
}
