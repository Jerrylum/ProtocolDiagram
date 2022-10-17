package com.jerryio.protocol_diagram.test.diagram;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.Pair;

public class FieldTest {
    @Test
    public void testFieldConstructor() {
        Field f = new Field("test", 1);
        assertEquals("test", f.getName());
        assertEquals(1, f.getLength());
    }

    @Test
    public void testFieldPairConstructor() {
        Field f = new Field(new Pair<String, Integer>("test", 1));
        assertEquals("test", f.getName());
        assertEquals(1, f.getLength());
    }

    @Test
    public void testSetName() {
        Field f = new Field("test", 1);
        assertEquals("test", f.getName());
        f.setName("test2");
        assertEquals("test2", f.getName());
    }

    @Test
    public void testSetLength() {
        Field f = new Field("test", 1);
        assertEquals(1, f.getLength());
        f.setLength(2);
        assertEquals(2, f.getLength());
    }
}
