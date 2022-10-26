package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.StringT;

public class StringTTest {
    @Test
    public void testBooleanTValid() {
        assertEquals(new StringT("test"), StringT.parse(new CodePointBuffer("test")));// test
        assertEquals(new StringT(""), StringT.parse(new CodePointBuffer(" ")));// empty
        assertEquals(new StringT("test"), StringT.parse(new CodePointBuffer("test test")));// test
        assertEquals(new StringT("test"), StringT.parse(new CodePointBuffer("'test'"))); // 'test'
        assertEquals(new StringT("test"), StringT.parse(new CodePointBuffer("\"test\""))); // "test"
        assertEquals(new StringT("\\"), StringT.parse(new CodePointBuffer("'\\\\'"))); // '\\'
        assertEquals(new StringT("'"), StringT.parse(new CodePointBuffer("'\\''"))); // '\''
        assertEquals(new StringT("\\"), StringT.parse(new CodePointBuffer("\"\\\\\""))); // "\\"
        assertEquals(new StringT("\""), StringT.parse(new CodePointBuffer("'\\\"'"))); // '\"'
        assertEquals(new StringT("\\test"), StringT.parse(new CodePointBuffer("'\\\\test'"))); // '\\test'
        assertEquals(new StringT("\\test"), StringT.parse(new CodePointBuffer("\"\\\\test\""))); // "\\test"
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
