package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Frac;

public class FracTest {
    @Test
    public void testFracValid() {
        assertEquals(new Frac(".14"), Frac.parse(new CodePointBuffer(".14")));
        assertEquals(new Frac(".14"), Frac.parse(new CodePointBuffer(".14.15")));
        assertEquals(new Frac(".14"), Frac.parse(new CodePointBuffer(".14 ")));
        assertEquals(new Frac(".14"), Frac.parse(new CodePointBuffer(".14abc")));
        assertEquals(new Frac(".14"), Frac.parse(new CodePointBuffer(".14\\")));
        assertEquals(new Frac(".14"), Frac.parse(new CodePointBuffer(".14\'")));
    }

    @Test
    public void testFracNull() {
        assertNull(Frac.parse(new CodePointBuffer(".")));
        assertNull(Frac.parse(new CodePointBuffer("14")));
        assertNull(Frac.parse(new CodePointBuffer("")));
        assertNull(Frac.parse(new CodePointBuffer(" ")));
        assertNull(Frac.parse(new CodePointBuffer("abc")));
        assertNull(Frac.parse(new CodePointBuffer("-123")));
        assertNull(Frac.parse(new CodePointBuffer("3.14")));
    }
}
