package com.jerryio.protocol_diagram.test.diagram;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.Pair;
import com.jerryio.protocol_diagram.diagram.Diagram;

public class addFieldtest {
    @Test
    public void testFieldValid() {
        Field field = new Field("test", 1);
        assertEquals(field.getName(), "test");   
        assertEquals(field.getLength(), 1);
        field.setLength(10);
        assertEquals(field.getLength(), 10);
        field.setName("test2");
        assertEquals(field.getName(), "test2");
        Field field2 = new Field(new Pair<String,Integer>("test3", 12));
        assertEquals(field2.getName(), "test3");
        assertEquals(field2.getLength(), 12);
        field2.setLength(11);
        assertEquals(field2.getLength(), 11);
        field2.setName("test4");
        assertEquals(field2.getName(), "test4");
    }
}
