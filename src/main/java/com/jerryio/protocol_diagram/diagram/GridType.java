package com.jerryio.protocol_diagram.diagram;

public enum GridType {
	EMPTY				('\u0000'),
	HORIZONTAL	('0'),
	VERTICAL		('1'),
	TOP_RIGHT		('2'),
	BOTTOM_RIGHT('3'),
	BOTTOM_LEFT	('4'),
	TOP_LEFT		('5'),
	NON_TOP			('6'),
	NON_RIGHT		('7'),
	NON_BOTTOM	('8'),
	NON_LEFT		('9'),
	ALL					('a');

	private final char index;

	GridType(char index) {
		this.index = index;
	}

	public char toChar() {
		return this.index;
	}

}
