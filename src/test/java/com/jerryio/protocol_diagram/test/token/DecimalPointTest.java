package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.DecimalPoint;

public class DecimalPointTest {
    @Test
    public void testDigit1To9Vaild() {
        assertEquals(DecimalPoint.parse(new CodePointBuffer(".123")), new DecimalPoint('.'));
    }

    @Test
    public void testDigit1To9Null(){
        assertNull(DecimalPoint.parse(new CodePointBuffer("0")));
        assertNull(DecimalPoint.parse(new CodePointBuffer(" ")));
        assertNull(DecimalPoint.parse(new CodePointBuffer("a")));
        assertNull(DecimalPoint.parse(new CodePointBuffer("")));
        assertNull(DecimalPoint.parse(new CodePointBuffer("1 ")));
        assertNull(DecimalPoint.parse(new CodePointBuffer("-1")));
        assertNull(DecimalPoint.parse(new CodePointBuffer("10")));
    }
}
