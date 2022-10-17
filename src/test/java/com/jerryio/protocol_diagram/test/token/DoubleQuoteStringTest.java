package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.DoubleQuoteString;

public class DoubleQuoteStringTest {
    @Test
    public void testDoubleQuoteStringValid() {
        DoubleQuoteString t = new DoubleQuoteString("\"test\"", "test");
        assertEquals("\"test\"", t.value());
        assertEquals("test", t.content());

        assertEquals(new DoubleQuoteString("\"\\\\\"", "\\"), DoubleQuoteString.parse(new CodePointBuffer("\"\\\\\""))); // \
        assertEquals(new DoubleQuoteString("\"\\\"\"", "\""), DoubleQuoteString.parse(new CodePointBuffer("\"\\\"\""))); // \"
        assertEquals(new DoubleQuoteString("\"\\\\\\\"\"", "\\\""), DoubleQuoteString.parse(new CodePointBuffer("\"\\\\\\\"\""))); // \\"
        assertEquals(new DoubleQuoteString("\"\\\\\\\\\"", "\\\\"), DoubleQuoteString.parse(new CodePointBuffer("\"\\\\\\\\\""))); // \\
        assertEquals(new DoubleQuoteString("\"test\\\\\"", "test\\"), DoubleQuoteString.parse(new CodePointBuffer("\"test\\\\\""))); // test\
        assertEquals(new DoubleQuoteString("\"test\\\"\"", "test\""), DoubleQuoteString.parse(new CodePointBuffer("\"test\\\"\""))); // test\"
        assertEquals(new DoubleQuoteString("\"test\\\\\\\"\"", "test\\\""), DoubleQuoteString.parse(new CodePointBuffer("\"test\\\\\\\"\""))); // test\\"
        assertEquals(new DoubleQuoteString("\"\"", ""), DoubleQuoteString.parse(new CodePointBuffer("\"\""))); // empty
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
