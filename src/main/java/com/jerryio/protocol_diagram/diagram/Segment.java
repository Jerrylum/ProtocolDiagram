package com.jerryio.protocol_diagram.diagram;

public class Segment extends AbstractSegment {

	private final int length;

	public Segment(int length, Field parent) {
		super(parent);
		this.length = length;
	}

	public Segment(Field parent) {
		super(parent);
		this.length = parent.getLength();
	}

	public int getLength() {
		return this.length;
	}

	/**
	 * return a chunked segment, e.g. given a Segment[bit=8], seg.chunk(5) -> new Segment[bit=5]
	 * @param bit
	 * @return
	 */
	public Segment chunk(int bit) {
		if (bit == 0) {
			return null;
		}

		return new Segment(bit, this.parent);
	}

	/**
	 * return a sliced segment, e.g. given a Segment[bit=8], seg.slice(5) -> new Segment[bit=3]
	 * @param bit
	 * @return
	 */
	public Segment slice(int bit) {
		if (bit == 0) {
			return this;
		}

		if (bit >= getLength()) {
			return null;
		}

		return new Segment(this.length - bit, this.parent);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Segment ");
		sb.append("[ ");
		sb.append("length: " + this.getLength());
		sb.append(", parent: " + this.parent.toString().split("@")[1]);
		sb.append(" ]");

		return sb.toString();
	}

}
