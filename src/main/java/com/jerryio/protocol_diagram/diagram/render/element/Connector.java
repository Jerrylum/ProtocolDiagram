package com.jerryio.protocol_diagram.diagram.render.element;

import com.jerryio.protocol_diagram.diagram.render.IVisible;
import com.jerryio.protocol_diagram.diagram.render.Matrix;

public class Connector extends Element {
    // bit mask
    public static final byte TOP = 0b1000; // 8
    public static final byte RIGHT = 0b0100; // 4
    public static final byte BOTTOM = 0b0010; // 2
    public static final byte LEFT = 0b0001; // 1

    private byte value = 0;
    private boolean individual = false;

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public boolean isIndividual() {
        return individual;
    }

    public boolean isConnected(Element e) {
        if (e == null)
            return false;
        else if (e instanceof Connector c)
            return !c.isIndividual();
        else if (e instanceof IVisible v)
            return v.isVisible();
        else
            return false;
    }

    @Override
    public void process(Matrix m, int x, int y) {
        value = 0;
        value |= isConnected(m.get(x, y - 1)) ? TOP : 0;
        value |= isConnected(m.get(x + 1, y)) ? RIGHT : 0;
        value |= isConnected(m.get(x, y + 1)) ? BOTTOM : 0;
        value |= isConnected(m.get(x - 1, y)) ? LEFT : 0;

        // Special case

        // if a row tail is on the left hand side, then the connector should be
        // individual
        if (m.get(x - 1, y) instanceof RowTail t) {
            individual = true;
            if (!t.isVisible())
                value = 0;
        }
    }

}
