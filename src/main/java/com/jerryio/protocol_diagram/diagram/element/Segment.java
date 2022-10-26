package com.jerryio.protocol_diagram.diagram.element;

import com.jerryio.protocol_diagram.diagram.Field;

public abstract class Segment extends Element {
    private Field represent;
    private int start;
    private int length;
    private boolean displayName;

    public Segment(Field represent, int start, int length) {
        this.represent = represent;
        this.start = start;
        this.length = length;
        this.displayName = false;
    }

    public Field getRepresent() {
        return represent;
    }

    public int getStartIndex() {
        return start;
    }

    public int getEndIndex() { // exclusive
        return start + length;
    }

    public int getLength() {
        return length;
    }

    public void setDisplayName(boolean displayName) {
        this.displayName = displayName;
    }

    public boolean isDisplayName() {
        return displayName;
    }


}
