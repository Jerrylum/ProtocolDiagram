package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.BooleanT;

public class BooleanTTest {
    @Test
    public void testBooleanTValid() {
        BooleanT t = new BooleanT("True", true);
        assertEquals("True", t.value());
        assertEquals(true, t.bool());

        assertEquals(new BooleanT("True", true), BooleanT.parse(new CodePointBuffer("True")));
        assertEquals(new BooleanT("true", true), BooleanT.parse(new CodePointBuffer("true")));
        assertEquals(new BooleanT("False", false), BooleanT.parse(new CodePointBuffer("False")));
        assertEquals(new BooleanT("false", false), BooleanT.parse(new CodePointBuffer("false")));
        assertEquals(new BooleanT("True", true), BooleanT.parse(new CodePointBuffer("True ")));
        assertEquals(new BooleanT("TrUe", true), BooleanT.parse(new CodePointBuffer("TrUe ")));
        assertEquals(new BooleanT("fAlse", false), BooleanT.parse(new CodePointBuffer("fAlse")));
    }

    @Test
    public void testBooleanTNull() {
        assertNull(BooleanT.parse(new CodePointBuffer("")));
        assertNull(BooleanT.parse(new CodePointBuffer("1")));
        assertNull(BooleanT.parse(new CodePointBuffer("0")));
        assertNull(BooleanT.parse(new CodePointBuffer(" ")));
        assertNull(BooleanT.parse(new CodePointBuffer(" True")));
        assertNull(BooleanT.parse(new CodePointBuffer("")));

    }
}
