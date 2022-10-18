package com.jerryio.protocol_diagram.diagram;

public class Divider extends Segment implements IDisplayable {

	private final boolean isDisplay;

	public Divider(int width, boolean isDisplay) {
		super(width, null);
		this.isDisplay = isDisplay;
	}

	public boolean isDisplay() {
		return this.isDisplay;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Divider ");
		sb.append("[ ");
		sb.append("length: " + this.getLength());
		sb.append(", is display: " + this.isDisplay());
		sb.append(" ]");

		return sb.toString();
	}
	
}
