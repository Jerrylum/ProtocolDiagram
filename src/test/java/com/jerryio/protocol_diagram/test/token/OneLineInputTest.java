package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.OneLineInput;
import com.jerryio.protocol_diagram.token.Pair;

public class OneLineInputTest {
    @Test
    public void testOneLineInputValid() {
        OneLineInput i = new OneLineInput(Arrays.asList(new Pair<String, Integer>("test", 123)));
        assertEquals("test", i.params().get(0).first());
        assertEquals(Integer.valueOf(123), i.params().get(0).second());

        assertEquals(OneLineInput.class, OneLineInput.parse(new CodePointBuffer("")).getClass());
        assertEquals(OneLineInput.class, OneLineInput.parse(new CodePointBuffer("test:123")).getClass());
        assertEquals(OneLineInput.class, OneLineInput.parse(new CodePointBuffer("test test:123")).getClass());
        assertEquals(OneLineInput.class, OneLineInput.parse(new CodePointBuffer("test:123,")).getClass());
        assertEquals(OneLineInput.class, OneLineInput.parse(new CodePointBuffer("test:123, ")).getClass());
        assertEquals(OneLineInput.class, OneLineInput.parse(new CodePointBuffer("test : 123,test2:456")).getClass());
        assertEquals(OneLineInput.class, OneLineInput.parse(new CodePointBuffer("test : 123, test2: 456,")).getClass());
        assertEquals(OneLineInput.class, OneLineInput.parse(new CodePointBuffer(" test:123")).getClass());
    }

    @Test
    public void testOneLineInputNull() {
        assertNull(OneLineInput.parse(new CodePointBuffer("test")));
        assertNull(OneLineInput.parse(new CodePointBuffer("test:")));
        assertNull(OneLineInput.parse(new CodePointBuffer(":123")));
        assertNull(OneLineInput.parse(new CodePointBuffer("test:123 ")));
        assertNull(OneLineInput.parse(new CodePointBuffer(",test")));
        assertNull(OneLineInput.parse(new CodePointBuffer(",test:123")));
        assertNull(OneLineInput.parse(new CodePointBuffer("test:,")));
        assertNull(OneLineInput.parse(new CodePointBuffer("test,test")));
        assertNull(OneLineInput.parse(new CodePointBuffer("test:")));
    }
}
