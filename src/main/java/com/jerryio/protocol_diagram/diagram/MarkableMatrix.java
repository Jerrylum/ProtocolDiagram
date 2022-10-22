package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkableMatrix extends Matrix {

	private final List<Row<IConcreteMarkable>> list;

	private MarkableMatrix(int width) {
		super(width);
		this.list = new ArrayList<>();
	}

	public static MarkableMatrix create(Matrix m) {

		MarkableMatrix ret = new MarkableMatrix(m.getWidth());
		AbstractElement head = null;
		Map<AbstractElement, List<Integer>> lengthMap = new HashMap<>();
		IElement previous = null;

		// for any of the segments match, group them together with the key value of the first segment
		// such that Map<first segment, list of integers indicating the lengths>
		for (int rowidx = 0; rowidx < m.getHeight(); rowidx++) {
			final Row<AbstractElement> r = m.get(rowidx);

			for (int i = 0, length = 0; length < r.getLength(); i++) {	
				final AbstractElement current = r.get(i);
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
					lengthMap.get(head).add((previous.getLength() + current.getLength() - 1) % ret.getWidth() + 1);
				}

				// record all chunked length of the current parent
				lengthMap.get(head).add((previous = current).getLength());
			}
		}

		// get the each display index per field
		final var keys = lengthMap.keySet();
		Map<AbstractElement, Integer> indexMap = new HashMap<>();

		for (final var k: keys) {
			final var test = lengthMap.get(k);
			final var max = test.stream().mapToInt(Integer::intValue).max().getAsInt();
			final var filtered = test.stream().filter((e) -> e == max).toArray();
			final var index = (int) Math.ceil((double) filtered.length / 2) - 1;
			indexMap.put(k, index);
		}

		// init objects
		AbstractElement newHead = null;
		int idx = 0;
		for (int rowidx = 0; rowidx < m.getHeight(); rowidx++) {
			final Row<AbstractElement> r = m.get(rowidx);
			final var newRow = new Row<IConcreteMarkable>(ret.getWidth());

			for (int i = 0, length = 0; length < r.getLength(); i++) {
				final AbstractElement current = r.get(i);
				length += current.getLength();

				if (!current.equals(newHead) && !(current instanceof Divider)) {
					newHead = current;
					idx = 0;
				}

				// special case: null for prepended divider
				if (rowidx == 0 || rowidx == m.getHeight() - 1) {
					newRow.add(new MarkableDivider((Divider) current, "", true));
					continue;
				}

				boolean isDisplay = idx++ == indexMap.get(newHead).intValue();

				if (current instanceof Divider) {
					// we create a markable segment just for getting the name of the segment,
					// so the passed argument "is display" does not really matter in the first context
					final var segment = new MarkableSegment((Segment) newHead, false);
					newRow.add(new MarkableDivider((Divider) current, segment.getName(), isDisplay));
				}
			 	if (current instanceof Segment) {
					newRow.add(new MarkableSegment((Segment) current, isDisplay));
				}
			}

			ret.list.add(newRow);
		}

		return ret;
	}

	/**
	 * instance variable accessor
	 */
	@Override
	public int getWidth() {
		return super.getWidth();
	}

	@Override
	public int getHeight() {
		return this.list.size();
	}

	// cannot override with explicitly stating the generic type as the return type is different
	@Override
	public Row get(int i) {
		return this.list.get(i);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MarkableMatrix ");
		sb.append("[ ");
		sb.append("width: " + this.getWidth());
		sb.append(", height: " + this.getHeight());
		sb.append(" ]");

		for (Row<IConcreteMarkable> e: this.list) {
			sb.append("\n" + e.toString());
		}

		return sb.toString();
	}
	
}
