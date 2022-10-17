package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class ParameterTest {
    @Test
    public void testParameterValid() {
        assertTrue(Parameter.parse(new CodePointBuffer("True")).getBoolean());
        assertFalse(Parameter.parse(new CodePointBuffer("False")).getBoolean());
        assertEquals(123, Parameter.parse(new CodePointBuffer("123")).getInt());
        assertEquals(123.456, Parameter.parse(new CodePointBuffer("123.456")).getDouble(), 0.0001);
        assertEquals("Hello", Parameter.parse(new CodePointBuffer("Hello World")).getString());
        assertEquals(0, Parameter.parse(new CodePointBuffer("0")).getInt());
        assertEquals(0.0, Parameter.parse(new CodePointBuffer("0.0")).getDouble(), 0.0001);
        assertEquals(-14, Parameter.parse(new CodePointBuffer("-14")).getInt());
        assertEquals(-14.0, Parameter.parse(new CodePointBuffer("-14.0")).getDouble(), 0.0001);
        assertEquals("14", Parameter.parse(new CodePointBuffer("\"14\"")).getString());
        assertEquals("14.0", Parameter.parse(new CodePointBuffer("\"14.0\"")).getString());
        assertEquals("-14", Parameter.parse(new CodePointBuffer("\"-14\"")).getString());
        assertEquals("-14.0", Parameter.parse(new CodePointBuffer("\"-14.0\"")).getString());
        assertEquals("14", (Parameter.parse(new CodePointBuffer("'14'")).getString()));
        assertEquals("14.0", Parameter.parse(new CodePointBuffer("'14.0'")).getString());
        assertEquals("-14", Parameter.parse(new CodePointBuffer("'-14'")).getString());
        assertEquals("-14.0", Parameter.parse(new CodePointBuffer("'-14.0'")).getString());
        assertEquals("3.14h", Parameter.parse(new CodePointBuffer("3.14h")).toString());
    }

    @Test
    public void testParameterNull() {
        assertNull(Parameter.parse(new CodePointBuffer("")));
    }

    @Test
    public void testParameterEquals() {
        Parameter p1 = Parameter.parse(new CodePointBuffer("True"));
        Parameter p2 = Parameter.parse(new CodePointBuffer("True"));

        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1)); // symmetric
        assertTrue(p1.equals(p1)); // reflexive
        assertTrue(p2.equals(p2)); // reflexive
        assertFalse(p1.equals(null));
        assertFalse(p1.equals(new Object()));
        assertFalse(p1.equals(123));
        assertFalse(p1.equals(true));
        assertFalse(p1.equals(Parameter.parse(new CodePointBuffer("False"))));
        assertFalse(p1.equals(Parameter.parse(new CodePointBuffer("123"))));
        assertFalse(p1.equals(Parameter.parse(new CodePointBuffer("Hello"))));

        p1 = Parameter.parse(new CodePointBuffer("123"));

        assertFalse(p1.equals(Parameter.parse(new CodePointBuffer("False"))));
        assertTrue(p1.equals(Parameter.parse(new CodePointBuffer("123"))));
        assertFalse(p1.equals(Parameter.parse(new CodePointBuffer("Hello"))));

        p1 = Parameter.parse(new CodePointBuffer("Hello"));

        assertFalse(p1.equals(Parameter.parse(new CodePointBuffer("False"))));
        assertFalse(p1.equals(Parameter.parse(new CodePointBuffer("123"))));
        assertTrue(p1.equals(Parameter.parse(new CodePointBuffer("Hello"))));
    }

    @Test
    public void testParameterMethods() {
        Parameter p = Parameter.parse(new CodePointBuffer("True"));

        assertTrue(p.isBoolean());
        assertFalse(p.isNumber());
        assertFalse(p.isDouble());
        assertFalse(p.isString());
        assertEquals("true", p.toString());

        p = Parameter.parse(new CodePointBuffer("123"));

        assertFalse(p.isBoolean());
        assertTrue(p.isNumber());
        assertFalse(p.isDouble());
        assertFalse(p.isString());
        assertEquals("123", p.toString());

        p = Parameter.parse(new CodePointBuffer("123.456"));

        assertFalse(p.isBoolean());
        assertTrue(p.isNumber());
        assertTrue(p.isDouble());
        assertFalse(p.isString());
        assertEquals("123.456", p.toString());

        p = Parameter.parse(new CodePointBuffer("'Hello'"));

        assertFalse(p.isBoolean());
        assertFalse(p.isNumber());
        assertFalse(p.isDouble());
        assertTrue(p.isString());
        assertEquals("Hello", p.toString());
    }
}
