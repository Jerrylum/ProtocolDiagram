package com.jerryio.protocol_diagram.diagram.render.element;

import com.jerryio.protocol_diagram.diagram.render.Matrix;

public abstract class Element {
    /**
     * a callback method for shaping the element such that matching the context
     * of the matrix by the given matrix instance and the position of the current
     * element
     * 
     * @param m the matrix instance
     * @param x the x position of the current element
     * @param y the y position of the current element
     */
    public abstract void process(Matrix m, int x, int y);
}
