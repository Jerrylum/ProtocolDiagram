package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Int;

public class IntTest {
    @Test
    public void testIntValid() {
        assertEquals(new Int("0"), Int.parse(new CodePointBuffer("0")));
        assertEquals(new Int("123"), Int.parse(new CodePointBuffer("123")));
        assertEquals(new Int("0"), Int.parse(new CodePointBuffer("0 ")));
        assertEquals(new Int("123"), Int.parse(new CodePointBuffer("123 ")));
        assertEquals(new Int("0"), Int.parse(new CodePointBuffer("0.16")));
        assertEquals(new Int("3"), Int.parse(new CodePointBuffer("3.14")));
    }

    @Test
    public void testIntNull() {
        assertNull(Int.parse(new CodePointBuffer("abc")));
        assertNull(Int.parse(new CodePointBuffer("")));
        assertNull(Int.parse(new CodePointBuffer(" ")));
        assertNull(Int.parse(new CodePointBuffer(".14")));
    }
}
