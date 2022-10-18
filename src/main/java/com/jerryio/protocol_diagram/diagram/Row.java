package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.List;

public class Row {

	private final int width;
	private final List<AbstractSegment> children;

	public Row(int width) {
		this.width = width;
		this.children = new ArrayList<>();
	}

	public int getWidth() {
		return this.width;
	}

	public void add(AbstractSegment seg) {
		assert seg != null;
		assert seg.getLength() + this.getLength() <= width;

		this.children.add(seg);
	}

	public AbstractSegment get(int index) {
		if (index >= this.children.size()) {
			return null;
		}

		return this.children.get(index);
	}

	public boolean isFull() {
		return this.getLength() == width;
	}

	public int getLength() {
		return this.children.stream().map((e) -> e.getLength()).reduce(0, (a, b) -> a + b);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Row ");
		sb.append("[ ");
		sb.append("length: " + this.getLength());
		sb.append(" ]");

		for (ISegment e: this.children) {
			sb.append("\n" + e.toString());
		}

		return sb.toString();
	}
	
}
