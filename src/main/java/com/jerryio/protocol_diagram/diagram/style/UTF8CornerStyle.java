package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.element.Corner;
import com.jerryio.protocol_diagram.diagram.element.Element;

public class UTF8CornerStyle extends UTF8Style {

    public UTF8CornerStyle(List<Element> elements) {
        super(elements);
    }

    @Override
    public String output(Corner e) {
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
