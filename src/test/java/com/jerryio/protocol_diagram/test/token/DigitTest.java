package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Digit;

public class DigitTest {
    @Test
    public void testDigitValid() {
        assertEquals(new Digit('0'), Digit.parse(new CodePointBuffer("0")));
        assertEquals(new Digit('1'), Digit.parse(new CodePointBuffer("1")));
        assertEquals(new Digit('2'), Digit.parse(new CodePointBuffer("2")));
        assertEquals(new Digit('3'), Digit.parse(new CodePointBuffer("3")));
        assertEquals(new Digit('4'), Digit.parse(new CodePointBuffer("4")));
        assertEquals(new Digit('5'), Digit.parse(new CodePointBuffer("5")));
        assertEquals(new Digit('6'), Digit.parse(new CodePointBuffer("6")));
        assertEquals(new Digit('7'), Digit.parse(new CodePointBuffer("7")));
        assertEquals(new Digit('8'), Digit.parse(new CodePointBuffer("8")));
        assertEquals(new Digit('9'), Digit.parse(new CodePointBuffer("9")));
        assertEquals(new Digit('1'), Digit.parse(new CodePointBuffer("1 ")));
    }

    @Test
    public void testDigitNull() {
        assertNull(Digit.parse(new CodePointBuffer("")));
        assertNull(Digit.parse(new CodePointBuffer("-1")));
        assertNull(Digit.parse(new CodePointBuffer("a")));
        assertNull(Digit.parse(new CodePointBuffer("A")));
        assertNull(Digit.parse(new CodePointBuffer(" ")));
        assertNull(Digit.parse(new CodePointBuffer(" 1")));
        assertNull(Digit.parse(new CodePointBuffer(".22")));
    }
}
