package com.jerryio.protocol_diagram.diagram;

public abstract class AbstractElement implements IElement {

	protected final Field parent;
	
	public AbstractElement(Field parent) {
		this.parent = parent;
	}

	/**
	 * @param other
	 * @return
	 */
	public boolean equals(AbstractElement other) {
		return other == null ? false : this.parent == other.parent;
	}
	
}
