package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.render.element.Connector;
import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.Element;
import com.jerryio.protocol_diagram.diagram.render.element.NextLine;
import com.jerryio.protocol_diagram.diagram.render.element.RowSegment;
import com.jerryio.protocol_diagram.diagram.render.element.RowTail;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;


/**
 * an abstract class that handled a lot of style transforming logic, the descendant of this class
 * once want to apply changes, could be easily done by implementing only few necessary methods
 */
public abstract class Style {
    /**
     * a list of elements in the diagram
     */
    protected List<Element> elements;

    /**
     * assign the elements into the instance variable
     * 
     * @param elements a list of elements in the diagram
     */
    public Style(List<Element> elements) {
        this.elements = elements;
    }

    /**
     * a method that transform the RowTail instance to string
     * 
     * @param e a connector
     * @return String
     */
    public abstract String output(Connector e);

    /**
     * a method that transform the DividerSegment instance to string
     * 
     * @param e a divider segment
     * @return String
     */
    public abstract String output(DividerSegment e);

    /**
     * a method that transform the RowTail instance to string
     * 
     * @param e a row tail
     * @return String
     */
    public abstract String output(RowTail e);

    /**
     * a method that transform the NextLine instance to string
     * 
     * @param e a next line
     * @return String
     */
    public String output(NextLine e) {
        return "\n";
    }

    /**
     * a method that transforms the RowSegment instance to string
     * 
     * @param e a row segment
     * @return String
     */
    public String output(RowSegment e) {
        return output((Segment) e);
    }

    /**
     * a method that transforms the Segment instance to string
     * 
     * @param e a segment
     * @return String
     */
    public String output(Segment e) {
        return output(e.isDisplayName() ? e.getRepresent().getName() : " ", ' ', e.getLength());
    }

    /**
     * a method that slices the logic of toString by pattern matching for instanceof
     * to distribute the computation work to different methods
     * 
     * @param e an element
     * @return String
     */
    public String output(Element e) {
        if (e instanceof Connector f)
            return output(f);
        else if (e instanceof DividerSegment f)
            return output(f);
        else if (e instanceof RowTail f)
            return output(f);
        else if (e instanceof NextLine f)
            return output(f);
        else if (e instanceof RowSegment f)
            return output(f);
        else
            return e.toString();
    }

    /**
     * a low-level method that takes three parameters, `name`, `placeholder` and `bitLength`,
     * and generate a string with length `bitLength`, centered text with name and filled with
     * `placeholder` in the background
     * 
     * @param name the name of the field
     * @param placeholder the background character of the field
     * @param bitLength the length of the field
     * @return String
     */
    public String output(String name, char placeholder, int bitLength) {
        StringBuilder sb = new StringBuilder();
        int length = bitLength * 2 - 1;
        int nameLength = name.length();
        int nameIdx = (length - nameLength) / 2 + ((nameLength + 1) % 2);
        if (nameLength > length) {
            name = name.substring(0, length);
            nameIdx = 0;
        }
        for (int i = 0; i < length; i++) {
            if (i == nameIdx) {
                sb.append(name);
                i += name.length() - 1;
            } else {
                sb.append(placeholder);
            }
        }
        return sb.toString();
    }

    /**
     * a low-level method that takes three parameters, `odd`, `even` and `bitLength`,
     * and generate a string with length `bitLength`, and interleave with char `odd` and `even`
     * base on the index.
     * 
     * @param odd the character will be applied when the index is odd
     * @param even the character will be applied when the index is even
     * @param bitLength the length of the field
     * @return String
     */
    public String output(char odd, char even, int bitLength) {
        StringBuilder sb = new StringBuilder();
        sb.append(odd);
        for (int i = 1; i < bitLength; i++) {
            sb.append(even);
            sb.append(odd);
        }
        return sb.toString();
    }

    /**
     * a method for converting the list of elements to string
     * 
     * @return String
     */
    public String output() {
        StringBuilder sb = new StringBuilder();
        Element last = null;
        for (Element e : elements) {
            if (last != e) {
                sb.append(output(last = e));
            }
        }
        return sb.toString();
    }
}
