package com.jerryio.protocol_diagram.diagram;

public class Element extends AbstractElement {

	private final int length;

	public Element(int length, Field parent) {
		super(parent);
		this.length = length;
	}

	public int getLength() {
		return this.length;
	}

	/**
	 * return a chunked segment, e.g. given a Element[bit=8], seg.chunk(5) -> new Element[bit=5]
	 * @param bit
	 * @return
	 */
	public IElement chunk(int bit) {
		if (bit == 0) {
			return null;
		}

		return new Element(bit, this.parent);
	}

	/**
	 * return a sliced segment, e.g. given a Element[bit=8], seg.slice(5) -> new Element[bit=3]
	 * @param bit
	 * @return
	 */
	public IElement slice(int bit) {
		if (bit == 0) {
			return this;
		}

		if (bit >= getLength()) {
			return null;
		}

		return new Element(this.length - bit, this.parent);
	}
	
	@Override
	public String toString() {
		String tmp;
		if (this.parent == null) tmp = "null";
		else tmp = this.parent.toString().split("@")[1];

		StringBuilder sb = new StringBuilder();
		sb.append("Element ");
		sb.append("[ ");
		sb.append("length: " + this.getLength());
		sb.append(", parent: " + tmp);
		sb.append(" ]");

		return sb.toString();
	}

}
