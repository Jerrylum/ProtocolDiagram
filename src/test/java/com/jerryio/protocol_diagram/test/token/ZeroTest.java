package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Zero;

public class ZeroTest {
    @Test
    public void testZeroVaild() {
        assertEquals(Zero.parse(new CodePointBuffer("0")), new Zero('0'));
        assertEquals(Zero.parse(new CodePointBuffer("0 ")), new Zero('0'));
    }

    @Test
    public void testZeroNull() {
        assertNull(Zero.parse(new CodePointBuffer("1")));
        assertNull(Zero.parse(new CodePointBuffer("a")));
        assertNull(Zero.parse(new CodePointBuffer(" ")));
        assertNull(Zero.parse(new CodePointBuffer("")));
        assertNull(Zero.parse(new CodePointBuffer("-")));
        assertNull(Zero.parse(new CodePointBuffer(" 0")));
    }
}
