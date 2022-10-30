package com.jerryio.protocol_diagram.test.diagram;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.jerryio.protocol_diagram.diagram.render.Matrix;
import com.jerryio.protocol_diagram.diagram.render.element.Element;
import com.jerryio.protocol_diagram.diagram.style.AsciiStyle;
import com.jerryio.protocol_diagram.diagram.style.AsciiVerboseStyle;
import com.jerryio.protocol_diagram.diagram.style.UTF8CornerStyle;
import com.jerryio.protocol_diagram.diagram.style.UTF8HeaderStyle;
import com.jerryio.protocol_diagram.diagram.style.UTF8Style;

public class StyleTest {

    @Test
    public void testStyleEdgeCase() {
        class UnknownElement extends Element {
            public String toString() {
                return "UnknownElement";
            }

            @Override
            public void process(Matrix m, int x, int y) {
                // Nothing to do
            }
        }

        String expected = "UnknownElement";

        String actual = new UTF8Style(new ArrayList<>() {
            {
                add(new UnknownElement());
            }
        }).output();

        assertEquals(expected, actual);

        new UTF8Style(new ArrayList<>());
        new UTF8HeaderStyle(new ArrayList<>());
        new UTF8CornerStyle(new ArrayList<>());
        new AsciiStyle(new ArrayList<>());
        new AsciiVerboseStyle(new ArrayList<>());
    }
}
