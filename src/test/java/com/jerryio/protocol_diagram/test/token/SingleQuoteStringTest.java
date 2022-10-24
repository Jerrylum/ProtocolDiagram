package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.SingleQuoteString;

public class SingleQuoteStringTest {
    @Test
    public void testBooleanTValid() {
        SingleQuoteString s = new SingleQuoteString("hello", "world");
        assertEquals("hello", s.value());
        assertEquals("world", s.content());

        assertEquals(new SingleQuoteString("'\\\\'", "\\"), SingleQuoteString.parse(new CodePointBuffer("'\\\\'"))); // \
        assertEquals(new SingleQuoteString("'\\''", "'"), SingleQuoteString.parse(new CodePointBuffer("'\\''"))); // '
        assertEquals(new SingleQuoteString("'\\\\\\''", "\\'"), SingleQuoteString.parse(new CodePointBuffer("'\\\\\\''"))); // \'
        assertEquals(new SingleQuoteString("'\\\\\\\\'", "\\\\"), SingleQuoteString.parse(new CodePointBuffer("'\\\\\\\\'"))); // \\
        assertEquals(new SingleQuoteString("'test\\\\'", "test\\"), SingleQuoteString.parse(new CodePointBuffer("'test\\\\'"))); // test\
        assertEquals(new SingleQuoteString("'test\\''", "test'"), SingleQuoteString.parse(new CodePointBuffer("'test\\''"))); // test'
        assertEquals(new SingleQuoteString("'test\\\\\\''", "test\\'"), SingleQuoteString.parse(new CodePointBuffer("'test\\\\\\''"))); // test\'
        assertEquals(new SingleQuoteString("''", ""), SingleQuoteString.parse(new CodePointBuffer("''"))); // empty
    }

    @Test
    public void testBooleanTNull() {
        assertNull(SingleQuoteString.parse(new CodePointBuffer("test"))); // no quote
        assertNull(SingleQuoteString.parse(new CodePointBuffer("")));
        assertNull(SingleQuoteString.parse(new CodePointBuffer("234")));
        assertNull(SingleQuoteString.parse(new CodePointBuffer("'"))); // missing end quote
        assertNull(SingleQuoteString.parse(new CodePointBuffer("'test")));
        assertNull(SingleQuoteString.parse(new CodePointBuffer("'234")));
    }
}
