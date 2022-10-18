package com.jerryio.protocol_diagram.diagram;

public class MarkableDivider extends Divider implements IMarkable {

	private final String name;

	public MarkableDivider(Divider divider, String name) {
		super(divider.getLength(), divider.isDisplay());
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
}
