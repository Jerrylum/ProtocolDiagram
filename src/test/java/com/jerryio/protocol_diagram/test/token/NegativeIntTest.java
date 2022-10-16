package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.NegativeInt;

public class NegativeIntTest {
    @Test
    public void testNegativeIntValid() {
        assertEquals(new NegativeInt("-123"), NegativeInt.parse(new CodePointBuffer("-123")));
        assertEquals(new NegativeInt("-123"), NegativeInt.parse(new CodePointBuffer("-123abc")));
        assertEquals(new NegativeInt("-123"), NegativeInt.parse(new CodePointBuffer("-123 ")));
        assertEquals(new NegativeInt("-123"), NegativeInt.parse(new CodePointBuffer("-123 456")));
        assertEquals(new NegativeInt("-3"), NegativeInt.parse(new CodePointBuffer("-3.14")));
    }

    @Test
    public void testNegativeIntNull() {
        assertNull(NegativeInt.parse(new CodePointBuffer("abc")));
        assertNull(NegativeInt.parse(new CodePointBuffer("")));
        assertNull(NegativeInt.parse(new CodePointBuffer("123")));
        assertNull(NegativeInt.parse(new CodePointBuffer("0123")));
        assertNull(NegativeInt.parse(new CodePointBuffer(".14")));
    }
}
