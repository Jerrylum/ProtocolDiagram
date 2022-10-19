package com.jerryio.protocol_diagram.diagram;

public abstract class AbstractSegment implements ISegment {

	protected final Field parent;
	
	public AbstractSegment(Field parent) {
		this.parent = parent;
	}

	/**
	 * @param other
	 * @return
	 */
	public boolean equals(AbstractSegment other) {
		return other == null ? false : this.parent == other.parent;
	}
	
}
