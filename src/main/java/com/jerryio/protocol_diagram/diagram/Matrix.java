package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.List;

public class Matrix {

	private final int width;
	private final List<Row<AbstractElement>> list;

	public Matrix(int width) {
		this.list = new ArrayList<>();
		this.width = width;
	}

	/**
	 * utility functions, for getting the last row of a matrix
	 */
	private static Row<AbstractElement> getLastRow(Matrix m) {
		return m.list.get(m.list.size() - 1);
	}

	/**
	 * instance variable accessor
	 */
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.list.size();
	}

	public Row<AbstractElement> get(int i) {
		return this.list.get(i);
	}

	public void add(Field field) {
		if (this.list.size() == 0) {
			this.list.add(new Row<AbstractElement>(width));
		}

		for (Element seg = new Segment(field); seg != null;) {
			if (getLastRow(this).isFull()) {
				this.list.add(new Row<AbstractElement>(this.width));
			}

			final int len = Math.min(width - getLastRow(this).getLength(), seg.getLength());
			getLastRow(this).add((Segment) seg.chunk(len));
			seg = (Segment) seg.slice(len);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Matrix ");
		sb.append("[ ");
		sb.append("width: " + this.getWidth());
		sb.append(", height: " + this.getHeight());
		sb.append(" ]");

		for (Row<AbstractElement> e: this.list) {
			sb.append("\n" + e.toString());
		}

		return sb.toString();
	}
	
}
