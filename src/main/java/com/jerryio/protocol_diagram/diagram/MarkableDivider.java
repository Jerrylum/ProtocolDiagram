package com.jerryio.protocol_diagram.diagram;

public class MarkableDivider extends MarkableElement implements IMembraneDisplayable {

	private final boolean isMembraneDisplay;

	public MarkableDivider(Divider divider, String name, boolean isTextDisplay) {
		super(divider.getLength(), null, isTextDisplay, name);
		this.isMembraneDisplay = divider.isMembraneDisplay();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MarkableDivider ");
		sb.append("[ ");
		sb.append("length: " + this.getLength());
		sb.append(", is display: " + this.isTextDisplay());
		sb.append(", name: " + this.getName());
		sb.append(" ]");

		return sb.toString();
	}

	@Override
	public boolean isMembraneDisplay() {
		return this.isMembraneDisplay;
	}

}
