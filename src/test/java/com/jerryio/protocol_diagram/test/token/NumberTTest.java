package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.NumberT;

public class NumberTTest {
    @Test
    public void testNumberTValid() {
        NumberT number = NumberT.parse(new CodePointBuffer("0"));
        assertEquals("0", number.value());
        assertTrue(number.isPositive());
        assertFalse(number.isDouble());
        assertEquals(14, NumberT.parse(new CodePointBuffer("14a")).toInt());
        assertEquals(new NumberT("0", true, false), NumberT.parse(new CodePointBuffer("0")));
        assertEquals(new NumberT("-14", false, false), NumberT.parse(new CodePointBuffer("-14")));
        assertEquals(new NumberT("14", true, false), NumberT.parse(new CodePointBuffer("14")));
        assertEquals(new NumberT("3.14", true, true), NumberT.parse(new CodePointBuffer("3.14 ")));
        assertEquals(new NumberT("-3.14", false, true), NumberT.parse(new CodePointBuffer("-3.14")));
        assertEquals(new NumberT("14", true, false), NumberT.parse(new CodePointBuffer("14\\")));
        assertEquals(new NumberT("14", true, false), NumberT.parse(new CodePointBuffer("14\'")));
        assertEquals(new NumberT("14", true, false), NumberT.parse(new CodePointBuffer("14\"")));
        assertEquals(new NumberT("14", true, false), NumberT.parse(new CodePointBuffer("14 ")));
        assertEquals(new NumberT("-14", false, false), NumberT.parse(new CodePointBuffer("-14\\")));
        assertEquals(new NumberT("-14", false, false), NumberT.parse(new CodePointBuffer("-14\'")));
        assertEquals(new NumberT("-14", false, false), NumberT.parse(new CodePointBuffer("-14\"")));
        assertEquals(new NumberT("-14", false, false), NumberT.parse(new CodePointBuffer("-14 ")));
        assertEquals(new NumberT("3.14", true, true), NumberT.parse(new CodePointBuffer("3.14\\")));
        assertEquals(new NumberT("3.14", true, true), NumberT.parse(new CodePointBuffer("3.14\'")));
        assertEquals(new NumberT("3.14", true, true), NumberT.parse(new CodePointBuffer("3.14\"")));
        assertEquals(new NumberT("3.14", true, true), NumberT.parse(new CodePointBuffer("3.14 ")));
        assertEquals(new NumberT("-3.14", false, true), NumberT.parse(new CodePointBuffer("-3.14\\")));
        assertEquals(new NumberT("-3.14", false, true), NumberT.parse(new CodePointBuffer("-3.14\'")));
        assertEquals(new NumberT("-3.14", false, true), NumberT.parse(new CodePointBuffer("-3.14\"")));
        assertEquals(new NumberT("-3.14", false, true), NumberT.parse(new CodePointBuffer("-3.14 ")));
    }

    @Test
    public void testNumberTNull() {
        assertNull(NumberT.parse(new CodePointBuffer("-")));
        assertNull(NumberT.parse(new CodePointBuffer("")));
        assertNull(NumberT.parse(new CodePointBuffer(" ")));
        assertNull(NumberT.parse(new CodePointBuffer("a")));
        assertNull(NumberT.parse(new CodePointBuffer("a14")));
        assertNull(NumberT.parse(new CodePointBuffer("a3.14")));
        assertNull(NumberT.parse(new CodePointBuffer("a-14")));
        assertNull(NumberT.parse(new CodePointBuffer("a-3.14")));
        assertNull(NumberT.parse(new CodePointBuffer("a14 ")));
        assertNull(NumberT.parse(new CodePointBuffer("a3.14 ")));
        assertNull(NumberT.parse(new CodePointBuffer("a-14 ")));
        assertNull(NumberT.parse(new CodePointBuffer("a-3.14 ")));
    }
}
