package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Minus;

public class MinusTest {
    @Test
    public void testMinusValid() {

        assertEquals(Minus.class, Minus.parse(new CodePointBuffer("-")).getClass());
        assertEquals(Minus.class, Minus.parse(new CodePointBuffer("- ")).getClass());
    }

    @Test
    public void testMinusNull() {
        assertNull(Minus.parse(new CodePointBuffer("")));
        assertNull(Minus.parse(new CodePointBuffer("0")));
        assertNull(Minus.parse(new CodePointBuffer(" -")));
        assertNull(Minus.parse(new CodePointBuffer("a")));
        assertNull(Minus.parse(new CodePointBuffer("1")));
        assertNull(Minus.parse(new CodePointBuffer("a-")));
        assertNull(Minus.parse(new CodePointBuffer("1-")));
    }
}
