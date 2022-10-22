package com.jerryio.protocol_diagram.diagram;

public interface IElement {

	public int getLength();

	public IElement slice(int bit);
	public IElement chunk(int bit);

}
