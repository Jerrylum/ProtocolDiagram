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

        assertEquals(new SafeString("test"), SafeString.parse(new CodePointBuffer("test"))); // test
        assertEquals(new SafeString("test test"), SafeString.parse(new CodePointBuffer("test test"))); // test
        assertEquals(new SafeString("test"), SafeString.parse(new CodePointBuffer("test:123"))); // test123
        assertEquals(new SafeString("test"), SafeString.parse(new CodePointBuffer("test,123"))); // test123
        assertEquals(new SafeString("test"), SafeString.parse(new CodePointBuffer("test:123,456"))); // test123456
        assertEquals(new SafeString("test"), SafeString.parse(new CodePointBuffer("test,123:456"))); // test123456
        assertEquals(new SafeString("test"), SafeString.parse(new CodePointBuffer("test:123,456:789"))); // test123456789
    }

    @Test
    public void testSafeStringNull() {
        assertNull(SafeString.parse(new CodePointBuffer(""))); // test
        assertNull(SafeString.parse(new CodePointBuffer(":"))); // test
        assertNull(SafeString.parse(new CodePointBuffer(","))); // test
        assertNull(SafeString.parse(new CodePointBuffer(" "))); // test
    }
}
