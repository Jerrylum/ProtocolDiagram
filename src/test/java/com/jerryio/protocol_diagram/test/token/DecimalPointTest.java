package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.DecimalPoint;

public class DecimalPointTest {
    @Test
    public void testDecimalPointValid() {
        assertEquals(new DecimalPoint('.'), DecimalPoint.parse(new CodePointBuffer(".")));
        assertEquals(new DecimalPoint('.'), DecimalPoint.parse(new CodePointBuffer(". ")));
        assertEquals(new DecimalPoint('.'), DecimalPoint.parse(new CodePointBuffer(".a")));
        assertEquals(new DecimalPoint('.'), DecimalPoint.parse(new CodePointBuffer(".123")));
    }

    @Test
    public void testDecimalPointNull() {
        assertNull(DecimalPoint.parse(new CodePointBuffer(" .")));
        assertNull(DecimalPoint.parse(new CodePointBuffer("0")));
        assertNull(DecimalPoint.parse(new CodePointBuffer(" ")));
        assertNull(DecimalPoint.parse(new CodePointBuffer("a")));
        assertNull(DecimalPoint.parse(new CodePointBuffer("")));
        assertNull(DecimalPoint.parse(new CodePointBuffer("1 ")));
        assertNull(DecimalPoint.parse(new CodePointBuffer("-1")));
        assertNull(DecimalPoint.parse(new CodePointBuffer("1")));
    }
}
