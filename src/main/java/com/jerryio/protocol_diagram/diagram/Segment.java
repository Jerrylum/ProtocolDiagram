package com.jerryio.protocol_diagram.diagram;

public class Segment extends Element {

	public Segment(int length, Field parent) {
		super(length, parent);
	}

	public Segment(Field parent) {
		super(parent.getLength(), parent);
	}

	@Override
	public Element chunk(int bit) {
		// super must be element
		final Element origin = (Element) super.chunk(bit);

		if (origin == null) {
			return null;
		}

		return new Segment(origin.getLength(), origin.parent);
	}

	@Override
	public Element slice(int bit) {
		// super must be element
		final Element origin = (Element) super.slice(bit);

		if (origin == null) {
			return null;
		}

		return new Segment(origin.getLength(), origin.parent);
	}

}
