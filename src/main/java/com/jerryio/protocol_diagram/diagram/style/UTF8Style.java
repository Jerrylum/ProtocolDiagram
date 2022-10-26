package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.element.Corner;
import com.jerryio.protocol_diagram.diagram.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.element.Element;
import com.jerryio.protocol_diagram.diagram.element.RowTail;

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
        char c = e.isVisible() ? '─' : ' ';

        StringBuilder sb = new StringBuilder();
        sb.append(c);
        for (int i = 1; i < e.getLength(); i++) {
            sb.append(c);
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public String output(RowTail e) {
        char c = 'A';

        StringBuilder sb = new StringBuilder();
        sb.append(c);
        for (int i = 1; i < e.getLength(); i++) {
            sb.append(c);
            sb.append(c);
        }
        return sb.toString();
    }

}