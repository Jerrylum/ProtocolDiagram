package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.element.Corner;
import com.jerryio.protocol_diagram.diagram.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.element.Element;
import com.jerryio.protocol_diagram.diagram.element.RowTail;
import com.jerryio.protocol_diagram.diagram.element.Segment;

public class UTF8Style extends Style {

    public UTF8Style(List<Element> elements) {
        super(elements);
    }

    public String output(Corner e) {
        return new char[] {
                ' ', // 0
                ' ', // 1
                ' ', // 2
                '┐', // 3
                ' ', // 4
                '─', // 5
                '┌', // 6
                '┬', // 7
                ' ', // 8
                '┘', // 9
                '│', // 10
                '┤', // 11
                '└', // 12
                '┴', // 13
                '├', // 14
                '┼' // 15
        }[e.getValue()] + "";
    }

    @Override
    public String output(DividerSegment e) {
        if (e.isDisplayName())
            return output((Segment) e);

        char c = e.isVisible() ? '─' : ' ';

        return output(c, c, e.getLength());
    }

    @Override
    public String output(RowTail e) {
        return output("Reserved", '─', e.getLength());
    }

}