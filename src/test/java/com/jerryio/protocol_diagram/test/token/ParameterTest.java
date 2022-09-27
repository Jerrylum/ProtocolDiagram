package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class ParameterTest {
    @Test
    public void testParameterValid() {
        assertEquals(Parameter.parse(new CodePointBuffer("True")).getBoolean(), true);
        assertEquals(Parameter.parse(new CodePointBuffer("False")).getBoolean(), false);
        assertEquals(Parameter.parse(new CodePointBuffer("123")).getInt(), 123);
        assertEquals(Parameter.parse(new CodePointBuffer("123.456")).getDouble(), 123.456, 0.0001);
        assertEquals(Parameter.parse(new CodePointBuffer("Hello World")).getString(), "Hello");
        assertEquals(Parameter.parse(new CodePointBuffer("0")).getInt(), 0);
        assertEquals(Parameter.parse(new CodePointBuffer("0.0")).getDouble(), 0.0, 0.0001);
        assertEquals(Parameter.parse(new CodePointBuffer("-14")).getInt(), -14);
        assertEquals(Parameter.parse(new CodePointBuffer("-14.0")).getDouble(), -14.0, 0.0001);
        assertEquals(Parameter.parse(new CodePointBuffer("\"14\"")).getString(), "14");
        assertEquals(Parameter.parse(new CodePointBuffer("\"14.0\"")).getString(), "14.0");
        assertEquals(Parameter.parse(new CodePointBuffer("\"-14\"")).getString(), "-14");
        assertEquals(Parameter.parse(new CodePointBuffer("\"-14.0\"")).getString(), "-14.0");
        assertEquals(Parameter.parse(new CodePointBuffer("'14'")).getString(), "14");
        assertEquals(Parameter.parse(new CodePointBuffer("'14.0'")).getString(), "14.0");
        assertEquals(Parameter.parse(new CodePointBuffer("'-14'")).getString(), "-14");
        assertEquals(Parameter.parse(new CodePointBuffer("'-14.0'")).getString(), "-14.0");
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
        assertEquals(p.toString(), "true");

        p = Parameter.parse(new CodePointBuffer("123"));

        assertFalse(p.isBoolean());
        assertTrue(p.isNumber());
        assertFalse(p.isDouble());
        assertFalse(p.isString());
        assertEquals(p.toString(), "123");

        p = Parameter.parse(new CodePointBuffer("123.456"));

        assertFalse(p.isBoolean());
        assertTrue(p.isNumber());
        assertTrue(p.isDouble());
        assertFalse(p.isString());
        assertEquals(p.toString(), "123.456");

        p = Parameter.parse(new CodePointBuffer("'Hello'"));

        assertFalse(p.isBoolean());
        assertFalse(p.isNumber());
        assertFalse(p.isDouble());
        assertTrue(p.isString());
        assertEquals(p.toString(), "Hello");
    }
}
