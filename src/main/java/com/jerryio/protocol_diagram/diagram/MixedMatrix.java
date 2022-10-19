package com.jerryio.protocol_diagram.diagram;

public class MixedMatrix extends Matrix {

	private MixedMatrix(int width) {
		super(width);
	}

	public static MixedMatrix create(Matrix m) {
		MixedMatrix e = new MixedMatrix(m.width);

		for (Row<AbstractSegment> r: m.list) {
			e.list.add(r);
		}

		final int size = e.list.size();

		for (int i = 0, idx = 1; i < size - 1; i++, idx += 2) {
			// divider row
			final Row<AbstractSegment> dividerRow = new Row<AbstractSegment>(e.width);
			// the adjacency rows, top and bottom
			final Row<AbstractSegment> top = e.list.get(idx - 1);
			final Row<AbstractSegment> bottom = e.list.get(idx);
			// the length of the divider has to be drawn
			final int length = Math.max(top.getLength(), bottom.getLength());

			for (int tidx = 0, tstart = 0, tend = 0, bidx = 0, bstart = 0, bend = 0; dividerRow.getLength() < length;) {
				final AbstractSegment ts = top.get(tidx);
				final AbstractSegment bs = bottom.get(bidx);

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

			e.list.add(idx, dividerRow);
		}

		return e;
	}

}
