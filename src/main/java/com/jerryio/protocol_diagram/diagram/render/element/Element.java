package com.jerryio.protocol_diagram.diagram.render.element;

import com.jerryio.protocol_diagram.diagram.render.Matrix;

public abstract class Element {
    public abstract void process(Matrix m, int x, int y);
}
