package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.List;

public class MixedMatrix extends Matrix {

	private List<Row<AbstractElement>> list;

	private MixedMatrix(int width) {
		super(width);
		this.list = new ArrayList<>();
	}

	public static MixedMatrix create(Matrix m) {
		MixedMatrix ret = new MixedMatrix(m.getWidth());

		for (int rowidx = 0; rowidx < m.getHeight(); rowidx++) {
			ret.list.add(m.get(rowidx));
		}

		final int size = ret.list.size();

		for (int i = 0, idx = 1; i < size - 1; i++, idx += 2) {
			// divider row
			final Row<AbstractElement> dividerRow = new Row<AbstractElement>(ret.getWidth());
			// the adjacency rows, top and bottom
			final Row<AbstractElement> top = ret.list.get(idx - 1);
			final Row<AbstractElement> bottom = ret.list.get(idx);
			// the length of the divider has to be drawn
			final int length = Math.max(top.getLength(), bottom.getLength());

			for (int tidx = 0, tstart = 0, tend = 0, bidx = 0, bstart = 0, bend = 0; dividerRow.getLength() < length;) {
				final AbstractElement ts = top.get(tidx);
				final AbstractElement bs = bottom.get(bidx);

				// indexing based on present-length-only segment
				tstart = tend;
				bstart = bend;
				final int local_tend = ts != null ? tstart + ts.getLength() : length;
				final int local_bend = bs != null ? bstart + bs.getLength() : length;

				int start = Math.max(tstart, bstart);
				int end = Math.min(local_tend, local_bend);

				dividerRow.add(new Divider(end - start, (ts == null || bs == null) ? true : !ts.equals(bs)));

				if (local_tend <= local_bend) {
					tidx++;
					tend = local_tend;
				}
				if (local_tend >= local_bend) {
					bidx++;
					bend = local_bend;
				}
			}

			ret.list.add(idx, dividerRow);
		}

		// for top boundary
		// divider row
		final Row<AbstractElement> head = ret.list.get(0);
		final Row<AbstractElement> tail = ret.list.get(ret.list.size() - 1);

		final Row<AbstractElement> topBoundary = new Row<AbstractElement>(ret.getWidth());
		final Row<AbstractElement> bottomBoundary = new Row<AbstractElement>(ret.getWidth());

		// for bottom boundary
		for (int i = 0, length = 0; length < head.getLength(); i++) {
			final AbstractElement seg = head.get(i);
			topBoundary.add(new Divider(seg.getLength(), true));
			length += seg.getLength();
		}

		// for bottom boundary
		for (int i = 0, length = 0; length < tail.getLength(); i++) {
			final AbstractElement seg = tail.get(i);
			bottomBoundary.add(new Divider(seg.getLength(), true));
			length += seg.getLength();
		}

		ret.list.add(0, topBoundary);
		ret.list.add(bottomBoundary);

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

	@Override
	public Row<AbstractElement> get(int i) {
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

		for (Row<AbstractElement> e: this.list) {
			sb.append("\n" + e.toString());
		}

		return sb.toString();
	}

}
