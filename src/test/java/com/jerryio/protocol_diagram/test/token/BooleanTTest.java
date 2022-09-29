package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.BooleanT;

public class BooleanTTest {
    @Test
    public void testBooleanTValid() {
        BooleanT t = new BooleanT("True", true);
        assertEquals(t.value(), "True");
        assertEquals(t.bool(), true);

        assertEquals(BooleanT.parse(new CodePointBuffer("True")), new BooleanT("True", true));
        assertEquals(BooleanT.parse(new CodePointBuffer("true")), new BooleanT("true", true));
        assertEquals(BooleanT.parse(new CodePointBuffer("False")), new BooleanT("False", false));
        assertEquals(BooleanT.parse(new CodePointBuffer("false")), new BooleanT("false", false));
        assertEquals(BooleanT.parse(new CodePointBuffer("True ")), new BooleanT("True", true));
        assertEquals(BooleanT.parse(new CodePointBuffer("TrUe ")), new BooleanT("TrUe", true));
        assertEquals(BooleanT.parse(new CodePointBuffer("fAlse")), new BooleanT("fAlse", false));
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