package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.StringT;

public class StringTTest {
    @Test
    public void testBooleanTValid() {
        assertEquals(StringT.parse(new CodePointBuffer("test")), new StringT("test"));// test
        assertEquals(StringT.parse(new CodePointBuffer(" ")), new StringT(""));// empty
        assertEquals(StringT.parse(new CodePointBuffer("test test")), new StringT("test"));// test
        assertEquals(StringT.parse(new CodePointBuffer("'test'")), new StringT("test")); // 'test'
        assertEquals(StringT.parse(new CodePointBuffer("\"test\"")), new StringT("test")); // "test"
        assertEquals(StringT.parse(new CodePointBuffer("'\\\\'")), new StringT("\\")); // '\\'
        assertEquals(StringT.parse(new CodePointBuffer("'\\''")), new StringT("'")); // '\''
        assertEquals(StringT.parse(new CodePointBuffer("\"\\\\\"")), new StringT("\\")); // "\\"
        assertEquals(StringT.parse(new CodePointBuffer("'\\\"'")), new StringT("\"")); // '\"'
        assertEquals(StringT.parse(new CodePointBuffer("'\\\\test'")), new StringT("\\test")); // '\\test'
        assertEquals(StringT.parse(new CodePointBuffer("\"\\\\test\"")), new StringT("\\test")); // "\\test"
    }

    @Test
    public void testBooleanTNull() {
        assertNull(StringT.parse(new CodePointBuffer(""))); // empty
        assertNull(StringT.parse(new CodePointBuffer("'"))); // '
        assertNull(StringT.parse(new CodePointBuffer("\""))); // "
        assertNull(StringT.parse(new CodePointBuffer("'test")));// 'test
        assertNull(StringT.parse(new CodePointBuffer("\"test")));// "test
        assertNull(StringT.parse(new CodePointBuffer("'test\"")));// 'test"
        assertNull(StringT.parse(new CodePointBuffer("\"test'")));// "test'
    }
}
