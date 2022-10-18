package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.List;

public class MarkableMatrix extends Matrix {

	protected final List<MarkableRow> list;

	private MarkableMatrix(int width) {
		super(width);
		this.list = new ArrayList<>();
	}

	public static MarkableMatrix create(Matrix m) {
		MarkableMatrix ret = new MarkableMatrix(m.width);
		// wip
		AbstractSegment s = null;

		for (Row r: m.list) {
			for (int i = 0, length = 0; length < r.getLength(); i++) {
				final var _s = r.get(i);
				length += _s.getLength();

				if (_s.equals(s)) {
				}
			}
		}

		return ret;
	}
	
}
