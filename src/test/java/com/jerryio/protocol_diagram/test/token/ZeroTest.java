package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Zero;

public class ZeroTest {
    @Test
    public void testZeroParsing() {
        assertEquals(Zero.parse(new CodePointBuffer("")), null);
        assertEquals(Zero.parse(new CodePointBuffer("-")), null);
        assertEquals(Zero.parse(new CodePointBuffer(" 0")), null);
        assertEquals(Zero.parse(new CodePointBuffer("0")).getClass(), Zero.class);
        assertEquals(Zero.parse(new CodePointBuffer("0 ")).getClass(), Zero.class);
    }
}
