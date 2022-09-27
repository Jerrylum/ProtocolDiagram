package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.SingleQuoteString;

public class SingleQuoteStringTest {
    @Test
    public void testBooleanTValid() {
        SingleQuoteString s = new SingleQuoteString("hello", "world");
        assertEquals(s.value(), "hello");
        assertEquals(s.content(), "world");

        assertEquals(SingleQuoteString.parse(
                new CodePointBuffer("'\\\\'")),
                new SingleQuoteString("'\\\\'", "\\")); // \
        assertEquals(SingleQuoteString.parse(
                new CodePointBuffer("'\\''")),
                new SingleQuoteString("'\\''", "'")); // '
        assertEquals(SingleQuoteString.parse(
                new CodePointBuffer("'\\\\\\''")),
                new SingleQuoteString("'\\\\\\''", "\\'")); // \'
        assertEquals(SingleQuoteString.parse(
                new CodePointBuffer("'\\\\\\\\'")),
                new SingleQuoteString("'\\\\\\\\'", "\\\\")); // \\
        assertEquals(SingleQuoteString.parse(
                new CodePointBuffer("'test\\\\'")),
                new SingleQuoteString("'test\\\\'", "test\\")); // test\
        assertEquals(SingleQuoteString.parse(
                new CodePointBuffer("'test\\''")),
                new SingleQuoteString("'test\\''", "test'")); // test'
        assertEquals(SingleQuoteString.parse(
                new CodePointBuffer("'test\\\\\\''")),
                new SingleQuoteString("'test\\\\\\''", "test\\'")); // test\'
        assertEquals(SingleQuoteString.parse(
                new CodePointBuffer("''")),
                new SingleQuoteString("''", "")); // empty
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
