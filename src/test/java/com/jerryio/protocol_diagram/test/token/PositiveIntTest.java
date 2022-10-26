package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.PositiveInt;

public class PositiveIntTest {
    @Test
    public void testPositiveIntValid() {
        assertEquals(new PositiveInt("123"), PositiveInt.parse(new CodePointBuffer("123")));
        assertEquals(new PositiveInt("123"), PositiveInt.parse(new CodePointBuffer("123abc")));
        assertEquals(new PositiveInt("123"), PositiveInt.parse(new CodePointBuffer("123 ")));
        assertEquals(new PositiveInt("123"), PositiveInt.parse(new CodePointBuffer("123 456")));
        assertEquals(new PositiveInt("3"), PositiveInt.parse(new CodePointBuffer("3.14")));
    }

    @Test
    public void testPositiveIntNull() {
        assertNull(PositiveInt.parse(new CodePointBuffer("abc")));
        assertNull(PositiveInt.parse(new CodePointBuffer("")));
        assertNull(PositiveInt.parse(new CodePointBuffer("-1")));
        assertNull(PositiveInt.parse(new CodePointBuffer("0123")));
        assertNull(PositiveInt.parse(new CodePointBuffer(".14")));
    }
}
