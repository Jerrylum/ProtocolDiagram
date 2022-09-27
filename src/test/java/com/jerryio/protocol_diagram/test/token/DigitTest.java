package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Digit;

public class DigitTest {
    @Test
    public void testDigitValid() {
        assertEquals(Digit.parse(new CodePointBuffer("0")), new Digit('0'));
        assertEquals(Digit.parse(new CodePointBuffer("1")), new Digit('1'));
        assertEquals(Digit.parse(new CodePointBuffer("2")), new Digit('2'));
        assertEquals(Digit.parse(new CodePointBuffer("3")), new Digit('3'));
        assertEquals(Digit.parse(new CodePointBuffer("4")), new Digit('4'));
        assertEquals(Digit.parse(new CodePointBuffer("5")), new Digit('5'));
        assertEquals(Digit.parse(new CodePointBuffer("6")), new Digit('6'));
        assertEquals(Digit.parse(new CodePointBuffer("7")), new Digit('7'));
        assertEquals(Digit.parse(new CodePointBuffer("8")), new Digit('8'));
        assertEquals(Digit.parse(new CodePointBuffer("9")), new Digit('9'));
        assertEquals(Digit.parse(new CodePointBuffer("1 ")), new Digit('1'));
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
