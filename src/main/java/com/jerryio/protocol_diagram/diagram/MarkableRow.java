package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.List;

public class MarkableRow extends Row {

	private final List<MarkableSegment> children;

	public MarkableRow(Row row) {
		super(row.getWidth());
		this.children = new ArrayList<MarkableSegment>();

		for (int i = 0, length = 0; length < row.getLength(); i++) {
			final MarkableSegment seg = new MarkableSegment(this.get(i), false);

			this.children.add(seg);
			length += seg.getLength();
		}
	}

	public void add(MarkableSegment seg) {
		assert seg != null;
		assert seg.getLength() + this.getLength() <= this.getWidth();

		this.children.add(seg);
	}

	public MarkableSegment get(int index) {
		if (index >= this.children.size()) {
			return null;
		}

		return this.children.get(index);
	}
	
}
