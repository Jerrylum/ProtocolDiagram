package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.List;

public class Matrix {

	protected final int width;
	protected final List<Row> list;

	public Matrix(int width) {
		this.list = new ArrayList<>();
		this.width = width;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.list.size();
	}

	public Row getLastRow() {
		return this.list.get(this.list.size() - 1);
	}

	public void add(Field field) {
		if (this.list.size() == 0) {
			this.list.add(new Row(width));
		}

		for (Segment seg = new Segment(field); seg != null;) {
			if (this.getLastRow().isFull()) {
				this.list.add(new Row(this.width));
			}

			final int len = Math.min(width - this.getLastRow().getLength(), seg.getLength());
			this.getLastRow().add(seg.chunk(len));
			seg = seg.slice(len);
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

		for (Row e: this.list) {
			sb.append("\n" + e.toString());
		}

		return sb.toString();
	}
	
}
