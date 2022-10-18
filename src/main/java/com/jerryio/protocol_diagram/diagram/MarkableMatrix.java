package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MarkableMatrix extends Matrix {

	protected final List<MarkableRow> list;

	private MarkableMatrix(int width) {
		super(width);
		this.list = new ArrayList<>();
	}

	public static MarkableMatrix create(Matrix m) {
		MarkableMatrix ret = new MarkableMatrix(m.width);
		AbstractSegment head = null;
		AbstractSegment previous = null;
		Map<AbstractSegment, List<Integer>> lengthMap = new HashMap<>();

		for (Row r: m.list) {
			for (int i = 0, length = 0; length < r.getLength(); i++) {
				final AbstractSegment current = r.get(i);
				length += current.getLength();

				// if current not having the same parent field, then start new list
				if (!current.equals(previous)) {
					head = current;
					lengthMap.put(current, new ArrayList<>());
				}
				// record all chunked length of the current parent
				lengthMap.get(head).add(current.getLength());
				previous = current;
			}
		}

		final Iterator<AbstractSegment> keys = lengthMap.keySet().iterator();
		while (keys.hasNext()) {
			final AbstractSegment s = keys.next();
			final List<Integer> lens = lengthMap.get(s);
			for (int i = 0; i < lens.size(); i++) {
				System.out.println(lens.get(i));
			}
		}

		// wip
		for (Row r: m.list) ret.list.add(new MarkableRow(r));

		return ret;
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
