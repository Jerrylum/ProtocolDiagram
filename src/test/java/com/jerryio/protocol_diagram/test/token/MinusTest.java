package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Minus;

public class MinusTest {
    @Test
    public void testMinusParsing() {
        assertEquals(Minus.parse(new CodePointBuffer("")), null);
        assertEquals(Minus.parse(new CodePointBuffer("0")), null);
        assertEquals(Minus.parse(new CodePointBuffer(" -")), null);
        assertEquals(Minus.parse(new CodePointBuffer("-")).getClass(), Minus.class);
        assertEquals(Minus.parse(new CodePointBuffer("- ")).getClass(), Minus.class);
    }
}
