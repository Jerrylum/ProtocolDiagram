package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Digit1To9;

public class Digit1To9Test {
    @Test
    public void testDigit1To9Valid() {
        assertEquals(new Digit1To9('1'), Digit1To9.parse(new CodePointBuffer("1")));
        assertEquals(new Digit1To9('2'), Digit1To9.parse(new CodePointBuffer("2")));
        assertEquals(new Digit1To9('3'), Digit1To9.parse(new CodePointBuffer("3")));
        assertEquals(new Digit1To9('4'), Digit1To9.parse(new CodePointBuffer("4")));
        assertEquals(new Digit1To9('5'), Digit1To9.parse(new CodePointBuffer("5")));
        assertEquals(new Digit1To9('6'), Digit1To9.parse(new CodePointBuffer("6")));
        assertEquals(new Digit1To9('7'), Digit1To9.parse(new CodePointBuffer("7")));
        assertEquals(new Digit1To9('8'), Digit1To9.parse(new CodePointBuffer("8")));
        assertEquals(new Digit1To9('9'), Digit1To9.parse(new CodePointBuffer("9")));
        assertEquals(new Digit1To9('1'), Digit1To9.parse(new CodePointBuffer("10")));
        assertEquals(new Digit1To9('1'), Digit1To9.parse(new CodePointBuffer("1 ")));
    }

    @Test
    public void testDigit1To9Null() {
        assertNull(Digit1To9.parse(new CodePointBuffer("0")));
        assertNull(Digit1To9.parse(new CodePointBuffer(" 1")));
        assertNull(Digit1To9.parse(new CodePointBuffer("a")));
        assertNull(Digit1To9.parse(new CodePointBuffer("A")));
        assertNull(Digit1To9.parse(new CodePointBuffer("")));
        assertNull(Digit1To9.parse(new CodePointBuffer("-1")));
        assertNull(Digit1To9.parse(new CodePointBuffer(".123")));
    }
}
