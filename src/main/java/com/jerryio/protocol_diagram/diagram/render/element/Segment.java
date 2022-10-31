package com.jerryio.protocol_diagram.diagram.render.element;

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

    /**
     * a getter method that returns the originated field of this segment.
     * 
     * @return Field
     */
    public Field getRepresent() {
        return represent;
    }

    /**
     * a getter methods returns a start index of the segment, this value will
     * be useful for holding the context information of the matrix
     * 
     * @return int
     */
    public int getStartIndex() {
        return start;
    }

    /**
     * a getter methods returns a derived value end index of the segment,
     * this value will be useful for holding the context information of the matrix
     * 
     * @return int
     */
    public int getEndIndex() { // exclusive
        return start + length;
    }

    /**
     * a getter method returns the length of the current segment
     * 
     * @return int
     */
    public int getLength() {
        return length;
    }

    /**
     * a setter method that decides whether the segment should display name
     * 
     * @param displayName
     */
    public void setDisplayName(boolean displayName) {
        this.displayName = displayName;
    }

    /**
     * a getter method that returns a value to determine whether the segment will
     * display name
     * 
     * @return boolean
     */
    public boolean isDisplayName() {
        return displayName;
    }

}
