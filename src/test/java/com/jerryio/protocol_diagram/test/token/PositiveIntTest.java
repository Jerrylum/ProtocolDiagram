package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.PositiveInt;

public class PositiveIntTest {
    @Test
    public void testBooleanTVaild() {
        assertEquals(PositiveInt.parse(new CodePointBuffer("123")), new PositiveInt("123"));
        assertEquals(PositiveInt.parse(new CodePointBuffer("123abc")), new PositiveInt("123"));
        assertEquals(PositiveInt.parse(new CodePointBuffer("123 ")), new PositiveInt("123"));
        assertEquals(PositiveInt.parse(new CodePointBuffer("123 456")), new PositiveInt("123"));
        assertEquals(PositiveInt.parse(new CodePointBuffer("3.14")), new PositiveInt("3"));
    }

    @Test
    public void testBooleanTNull() {
        assertNull(PositiveInt.parse(new CodePointBuffer("abc")));
        assertNull(PositiveInt.parse(new CodePointBuffer("")));
        assertNull(PositiveInt.parse(new CodePointBuffer("-1")));
        assertNull(PositiveInt.parse(new CodePointBuffer("0123")));
        assertNull(PositiveInt.parse(new CodePointBuffer(".14")));
    }
}
