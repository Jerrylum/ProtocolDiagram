package com.jerryio.protocol_diagram.diagram;

public class MarkableElement extends Element implements IConcreteMarkable {

	private final boolean isDisplay;
	private final String name;

	public MarkableElement(int length, Field parent, boolean isDisplay, String name) {
		super(length, parent);
		this.isDisplay = isDisplay;
		this.name = name;
	}

	@Override
	public boolean isTextDisplay() {
		return this.isDisplay;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
