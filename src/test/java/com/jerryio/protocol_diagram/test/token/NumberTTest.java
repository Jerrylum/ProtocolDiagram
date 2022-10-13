package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.NumberT;

public class NumberTTest {
    @Test
    public void testNumberTValid() {
        NumberT number = NumberT.parse(new CodePointBuffer("0"));
        assertEquals(number.value(), "0");
        assertEquals(number.isPositive(), true);
        assertEquals(number.isDouble(), false);
        assertEquals(NumberT.parse(new CodePointBuffer("14a")).toInt(), 14);
        assertEquals(NumberT.parse(new CodePointBuffer("0")), new NumberT("0", true, false));
        assertEquals(NumberT.parse(new CodePointBuffer("-14")), new NumberT("-14", false, false));
        assertEquals(NumberT.parse(new CodePointBuffer("14")), new NumberT("14", true, false));
        assertEquals(NumberT.parse(new CodePointBuffer("3.14 ")), new NumberT("3.14", true, true));
        assertEquals(NumberT.parse(new CodePointBuffer("-3.14")), new NumberT("-3.14", false, true));
        assertEquals(NumberT.parse(new CodePointBuffer("14\\")), new NumberT("14", true, false));
        assertEquals(NumberT.parse(new CodePointBuffer("14\'")), new NumberT("14", true, false));
        assertEquals(NumberT.parse(new CodePointBuffer("14\"")), new NumberT("14", true, false));
        assertEquals(NumberT.parse(new CodePointBuffer("14 ")), new NumberT("14", true, false));
        assertEquals(NumberT.parse(new CodePointBuffer("-14\\")), new NumberT("-14", false, false));
        assertEquals(NumberT.parse(new CodePointBuffer("-14\'")), new NumberT("-14", false, false));
        assertEquals(NumberT.parse(new CodePointBuffer("-14\"")), new NumberT("-14", false, false));
        assertEquals(NumberT.parse(new CodePointBuffer("-14 ")), new NumberT("-14", false, false));
        assertEquals(NumberT.parse(new CodePointBuffer("3.14\\")), new NumberT("3.14", true, true));
        assertEquals(NumberT.parse(new CodePointBuffer("3.14\'")), new NumberT("3.14", true, true));
        assertEquals(NumberT.parse(new CodePointBuffer("3.14\"")), new NumberT("3.14", true, true));
        assertEquals(NumberT.parse(new CodePointBuffer("3.14 ")), new NumberT("3.14", true, true));
        assertEquals(NumberT.parse(new CodePointBuffer("-3.14\\")), new NumberT("-3.14", false, true));
        assertEquals(NumberT.parse(new CodePointBuffer("-3.14\'")), new NumberT("-3.14", false, true));
        assertEquals(NumberT.parse(new CodePointBuffer("-3.14\"")), new NumberT("-3.14", false, true));
        assertEquals(NumberT.parse(new CodePointBuffer("-3.14 ")), new NumberT("-3.14", false, true));
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
