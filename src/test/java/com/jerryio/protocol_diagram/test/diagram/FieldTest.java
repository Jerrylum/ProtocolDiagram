package com.jerryio.protocol_diagram.test.diagram;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.Pair;

public class FieldTest {
    @Test
    public void testFieldConstructor() {
        Field f = new Field("test", 1);
        assertEquals(f.getName(), "test");
        assertEquals(f.getLength(), 1);
    }

    @Test
    public void testFieldPairConstructor() {
        Field f = new Field(new Pair<String, Integer>("test", 1));
        assertEquals(f.getName(), "test");
        assertEquals(f.getLength(), 1);
    }

    @Test
    public void testSetName() {
        Field f = new Field("test", 1);
        assertEquals(f.getName(), "test");
        f.setName("test2");
        assertEquals(f.getName(), "test2");
    }

    @Test
    public void testSetLength() {
        Field f = new Field("test", 1);
        assertEquals(f.getLength(), 1);
        f.setLength(2);
        assertEquals(f.getLength(), 2);
    }
}
