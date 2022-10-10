package com.jerryio.protocol_diagram.diagram;

public class Corner {
    // bit mask
    public static final byte TOP = 0b1000;
    public static final byte RIGHT = 0b0100;
    public static final byte BOTTOM = 0b0010;
    public static final byte LEFT = 0b0001;

    public byte value;

    public Corner(byte value) {
        this.value = value;
    }
}
