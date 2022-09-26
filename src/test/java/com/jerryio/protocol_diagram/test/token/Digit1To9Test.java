package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Digit1To9;

public class Digit1To9Test {
    @Test
    public void testDigit1To9Vaild() {
        assertEquals(Digit1To9.parse(new CodePointBuffer("1")), new Digit1To9('1'));
        assertEquals(Digit1To9.parse(new CodePointBuffer("2")), new Digit1To9('2'));
        assertEquals(Digit1To9.parse(new CodePointBuffer("3")), new Digit1To9('3'));
        assertEquals(Digit1To9.parse(new CodePointBuffer("4")), new Digit1To9('4'));
        assertEquals(Digit1To9.parse(new CodePointBuffer("5")), new Digit1To9('5'));
        assertEquals(Digit1To9.parse(new CodePointBuffer("6")), new Digit1To9('6'));
        assertEquals(Digit1To9.parse(new CodePointBuffer("7")), new Digit1To9('7'));
        assertEquals(Digit1To9.parse(new CodePointBuffer("8")), new Digit1To9('8'));
        assertEquals(Digit1To9.parse(new CodePointBuffer("9")), new Digit1To9('9'));
        assertEquals(Digit1To9.parse(new CodePointBuffer("10")), new Digit1To9('1'));
        assertEquals(Digit1To9.parse(new CodePointBuffer("1 ")), new Digit1To9('1'));
    }

    @Test
    public void testDigit1To9Null(){
        assertNull(Digit1To9.parse(new CodePointBuffer("0")));
        assertNull(Digit1To9.parse(new CodePointBuffer(" 1")));
        assertNull(Digit1To9.parse(new CodePointBuffer("a")));
        assertNull(Digit1To9.parse(new CodePointBuffer("A")));
        assertNull(Digit1To9.parse(new CodePointBuffer("")));
        assertNull(Digit1To9.parse(new CodePointBuffer("-1")));
        assertNull(Digit1To9.parse(new CodePointBuffer(".123")));
    }
}
