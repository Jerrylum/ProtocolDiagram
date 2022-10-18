package com.jerryio.protocol_diagram.diagram;

public class MarkableSegment extends Segment implements IDisplayable, IMarkable {

	private final boolean isDisplay;

	public MarkableSegment(AbstractSegment seg, boolean isDisplay) {
		super(seg.parent);
		this.isDisplay = false;
	}

	@Override
	public String getName() {
		return this.parent.getName();
	}

	@Override
	public boolean isDisplay() {
		return this.isDisplay;
	}
	
}
