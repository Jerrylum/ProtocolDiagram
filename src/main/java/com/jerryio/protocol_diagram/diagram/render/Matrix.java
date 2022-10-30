package com.jerryio.protocol_diagram.diagram.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jerryio.protocol_diagram.diagram.render.element.Connector;
import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.Element;
import com.jerryio.protocol_diagram.diagram.render.element.NextLine;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;

public class Matrix {
    private int width = 0;
    private int height = 1;
    private List<Element> matrix;

    public Matrix(List<Segment> segments) {
        matrix = new ArrayList<>();

        if (segments.size() < 3)
            return;

        boolean isDivider = true;
        for (Segment segment : segments) {
            if (segment instanceof DividerSegment != isDivider) {
                isDivider = !isDivider;
                matrix.add(new Connector());
                matrix.add(new NextLine());
                height++;
            }

            matrix.add(new Connector());
            matrix.add(segment);
            for (int i = 1; i < segment.getLength(); i++) {
                matrix.add(segment);
                matrix.add(segment);
            }
        }

        matrix.add(new Connector());
        matrix.add(new NextLine());
        width = matrix.size() / height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Element> getElements() {
        return Collections.unmodifiableList(matrix);
    }

    public Element get(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return null;
        return matrix.get(y * width + x);
    }

    public void process() {
        Element last = null;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Element e = get(x, y);
                if (last != e) {
                    e.process(this, x, y);
                    last = e;
                }
            }
        }
    }
}
