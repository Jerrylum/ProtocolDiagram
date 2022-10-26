package com.jerryio.protocol_diagram.diagram.element;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private int width = 0;
    private int height = 1;
    private List<Element> matrix;

    public Matrix(List<Segment> segments) {
        matrix = new ArrayList<>();

        assert segments.size() > 0;

        boolean isDivider = true;
        for (Segment segment : segments) {
            if (segment instanceof DividerSegment != isDivider) {
                isDivider = !isDivider;
                matrix.add(new Corner());
                matrix.add(new NextLine());
                height++;
            }

            matrix.add(new Corner());
            for (int i = 0; i < segment.getLength(); i++)
                matrix.add(segment);
        }

        matrix.add(new Corner());
        matrix.add(new NextLine());
        width = matrix.size() / height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Element get(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return null;
        return matrix.get(y * width + x);
    }

    public void process() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                get(x, y).process(this, x, y);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Element e : matrix) {
            if (e instanceof NextLine)
                sb.append("\n");
            else if (e instanceof Corner)
                sb.append("c");
            else if (e instanceof DividerSegment)
                sb.append("d");
            else if (e instanceof RowTail)
                sb.append("t");
            else if (e instanceof RowSegment)
                sb.append("r");
            else
                sb.append("?");
        }
        return sb.toString();
    }
}
