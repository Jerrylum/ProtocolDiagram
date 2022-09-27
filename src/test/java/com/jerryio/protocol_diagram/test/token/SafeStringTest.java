package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.SafeString;

public class SafeStringTest {
    @Test
    public void testSafeStringValid() {
        SafeString s = new SafeString("test");
        assertEquals(s.content(), "test");

        assertEquals(SafeString.parse(new CodePointBuffer("test")), new SafeString("test")); // test
        assertEquals(SafeString.parse(new CodePointBuffer("test test")), new SafeString("test test")); // test
        assertEquals(SafeString.parse(new CodePointBuffer("test:123")), new SafeString("test")); // test123
        assertEquals(SafeString.parse(new CodePointBuffer("test,123")), new SafeString("test")); // test123
        assertEquals(SafeString.parse(new CodePointBuffer("test:123,456")), new SafeString("test")); // test123456
        assertEquals(SafeString.parse(new CodePointBuffer("test,123:456")), new SafeString("test")); // test123456
        assertEquals(SafeString.parse(new CodePointBuffer("test:123,456:789")), new SafeString("test")); // test123456789
    }

    @Test
    public void testSafeStringNull() {
        assertNull(SafeString.parse(new CodePointBuffer(""))); // test
        assertNull(SafeString.parse(new CodePointBuffer(":"))); // test
        assertNull(SafeString.parse(new CodePointBuffer(","))); // test
        assertNull(SafeString.parse(new CodePointBuffer(" "))); // test
    }
}
