package com.jerryio.protocol_diagram.diagram;

public class MarkableSegment extends MarkableElement {

	public MarkableSegment(Segment seg, boolean isDisplay) {
		super(seg.getLength(), seg.parent, isDisplay, seg.parent.getName());
	}

	@Override
	public String toString() {
		final String tmp = this.parent != null ? "null" : this.parent.toString().split("@")[1];

		StringBuilder sb = new StringBuilder();
		sb.append("MarkableSegment ");
		sb.append("[ ");
		sb.append("length: " + this.getLength());
		sb.append(", parent: " + tmp);
		sb.append(", is display: " + this.isTextDisplay());
		sb.append(", name: " + this.getName());
		sb.append(" ]");

		return sb.toString();
	}
	
}
