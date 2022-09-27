package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Int;

public class IntTest {
    @Test
    public void testIntValid() {
        assertEquals(Int.parse(new CodePointBuffer("0")), new Int("0"));
        assertEquals(Int.parse(new CodePointBuffer("123")), new Int("123"));
        assertEquals(Int.parse(new CodePointBuffer("0 ")), new Int("0"));
        assertEquals(Int.parse(new CodePointBuffer("123 ")), new Int("123"));
        assertEquals(Int.parse(new CodePointBuffer("0.16")), new Int("0"));
        assertEquals(Int.parse(new CodePointBuffer("3.14")), new Int("3"));
    }

    @Test
    public void testIntNull() {
        assertNull(Int.parse(new CodePointBuffer("abc")));
        assertNull(Int.parse(new CodePointBuffer("")));
        assertNull(Int.parse(new CodePointBuffer(" ")));
        assertNull(Int.parse(new CodePointBuffer(".14")));
    }
}
