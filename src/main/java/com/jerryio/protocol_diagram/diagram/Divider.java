package com.jerryio.protocol_diagram.diagram;

public class Divider extends Element implements IMembraneDisplayable {

	private final boolean isDisplay;

	public Divider(int width, boolean isDisplay) {
		super(width, null);
		this.isDisplay = isDisplay;
	}

	public boolean isMembraneDisplay() {
		return this.isDisplay;
	}

	@Override
	public IElement chunk(int bit) {
		// super must be element
		final Element origin = (Element) super.chunk(bit);

		if (origin == null) {
			return null;
		}

		return new Divider(origin.getLength(), this.isDisplay);
	}

	@Override
	public IElement slice(int bit) {
		// super must be element
		final Element origin = (Element) super.slice(bit);

		if (origin == null) {
			return null;
		}

		return new Divider(origin.getLength(), this.isDisplay);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Divider ");
		sb.append("[ ");
		sb.append("length: " + this.getLength());
		sb.append(", membrane: " + this.isMembraneDisplay());
		sb.append(" ]");

		return sb.toString();
	}
	
}
