package com.jerryio.protocol_diagram.diagram;

public interface ISegment {

	public int getLength();

	public ISegment slice(int bit);
	public ISegment chunk(int bit);

}
