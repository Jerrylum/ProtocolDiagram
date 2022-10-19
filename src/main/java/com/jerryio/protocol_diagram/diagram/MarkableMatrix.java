package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MarkableMatrix extends Matrix {

	protected final List<Row<IConcreteMarkable>> list;

	private MarkableMatrix(int width) {
		super(width);
		this.list = new ArrayList<>();
	}

	public static MarkableMatrix create(Matrix m) {

		MarkableMatrix ret = new MarkableMatrix(m.width);
		AbstractSegment head = null;
		Map<AbstractSegment, List<Integer>> lengthMap = new HashMap<>();
		ISegment previous = null;

		// for any of the segments match, group them together with the key value of the first segment
		// such that Map<first segment, list of integers indicating the lengths>
		for (final Row<AbstractSegment> r: m.list) {
			for (int i = 0, length = 0; length < r.getLength(); i++) {	
				final AbstractSegment current = r.get(i);
				length += current.getLength();

				// if current not having the same parent field, then start new list
				if (!current.equals(head) && current instanceof Divider) {
					continue;
				}

				// if not identical, change the head segment, else add the divider length between the last segment and the current segment
				if (!current.equals(head)) {
					head = current;
					lengthMap.put(current, new ArrayList<>());
				} else {
					lengthMap.get(head).add((previous.getLength() + current.getLength() - 1) % ret.width + 1);
				}

				// record all chunked length of the current parent
				lengthMap.get(head).add((previous = current).getLength());
			}
		}

		// get the each display index per field
		final var keys = lengthMap.keySet();
		Map<AbstractSegment, Integer> indexMap = new HashMap<>();

		for (final var k: keys) {
			final var test = lengthMap.get(k);
			final var max = test.stream().mapToInt(Integer::intValue).max().getAsInt();
			final var filtered = test.stream().filter((e) -> e == max).toArray();
			final var index = (int) Math.ceil((double) filtered.length / 2) - 1;
			indexMap.put(k, index);
		}

		// init objects
		AbstractSegment newHead = null;
		int idx = 0;
		for (final Row<AbstractSegment> r: m.list) {
			final var newRow = new Row<MarkableSegment>(ret.width);

			for (int i = 0, length = 0; length < r.getLength(); i++, idx++) {
				final AbstractSegment current = r.get(i);
				length += current.getLength();

				if (!current.equals(newHead) && current instanceof Segment) {
					newHead = current;
				}

				if (current instanceof Segment) {
					newRow.add(new MarkableSegment(current, false));
				}
				if (current instanceof Divider) {
					// newRow.add(new MarkableDivider((Divider) current, new MarkableSegment(newHead, false).getName()));
				}
			}
		}

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
