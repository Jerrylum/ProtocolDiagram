package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.DoubleQuoteString;

public class DoubleQuoteStringTest {
    @Test
    public void testDoubleQuoteStringValid() {
        DoubleQuoteString t = new DoubleQuoteString("\"test\"", "test");
        assertEquals(t.value(), "\"test\"");
        assertEquals(t.content(), "test");

        assertEquals(DoubleQuoteString.parse(new CodePointBuffer("\"\\\\\"")), new DoubleQuoteString("\"\\\\\"", "\\")); // \
        assertEquals(DoubleQuoteString.parse(new CodePointBuffer("\"\\\"\"")), new DoubleQuoteString("\"\\\"\"", "\"")); // \"
        assertEquals(DoubleQuoteString.parse(new CodePointBuffer("\"\\\\\\\"\"")),
                new DoubleQuoteString("\"\\\\\\\"\"", "\\\"")); // \\"
        assertEquals(DoubleQuoteString.parse(new CodePointBuffer("\"\\\\\\\\\"")),
                new DoubleQuoteString("\"\\\\\\\\\"", "\\\\")); // \\
        assertEquals(DoubleQuoteString.parse(new CodePointBuffer("\"test\\\\\"")),
                new DoubleQuoteString("\"test\\\\\"", "test\\")); // test\
        assertEquals(DoubleQuoteString.parse(new CodePointBuffer("\"test\\\"\"")),
                new DoubleQuoteString("\"test\\\"\"", "test\"")); // test\"
        assertEquals(DoubleQuoteString.parse(new CodePointBuffer("\"test\\\\\\\"\"")),
                new DoubleQuoteString("\"test\\\\\\\"\"", "test\\\"")); // test\\"
        assertEquals(DoubleQuoteString.parse(new CodePointBuffer("\"\"")), new DoubleQuoteString("\"\"", "")); // empty
    }

    @Test
    public void testDoubleQuoteStringTNull() {
        assertNull(DoubleQuoteString.parse(new CodePointBuffer("test"))); // no quote
        assertNull(DoubleQuoteString.parse(new CodePointBuffer("")));
        assertNull(DoubleQuoteString.parse(new CodePointBuffer("234")));
        assertNull(DoubleQuoteString.parse(new CodePointBuffer("\""))); // missing end quote
        assertNull(DoubleQuoteString.parse(new CodePointBuffer("\"test")));
        assertNull(DoubleQuoteString.parse(new CodePointBuffer("\"234")));
    }
}
