package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.render.element.Connector;
import com.jerryio.protocol_diagram.diagram.render.element.Element;

/**
 * this class is a extended style of UTF8, it changed the corner that present
 * a variety of direction into only cross symbol
 */
public class UTF8CornerStyle extends UTF8Style {
    /**
     * this constructor takes a list of elements and assigns it into private instance variable
     * 
     * @param elements a list of elements in the diagram
     */
    public UTF8CornerStyle(List<Element> elements) {
        super(elements);
    }

    @Override
    public String output(Connector e) {
        return new char[] {
                ' ', // 0
                ' ', // 1
                ' ', // 2
                '┼', // 3
                ' ', // 4
                '─', // 5
                '┼', // 6
                '┼', // 7
                ' ', // 8
                '┼', // 9
                '│', // 10
                '┼', // 11
                '┼', // 12
                '┼', // 13
                '┼', // 14
                '┼' // 15
        }[e.getValue()] + "";
    }

}
