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
        assertEquals(i.params().get(0).first(), "test");
        assertEquals(i.params().get(0).second(), Integer.valueOf(123));

        assertEquals(OneLineInput.parse(new CodePointBuffer("")).getClass(), OneLineInput.class);
        assertEquals(OneLineInput.parse(new CodePointBuffer("test:123")).getClass(), OneLineInput.class);
        assertEquals(OneLineInput.parse(new CodePointBuffer("test test:123")).getClass(), OneLineInput.class);
        assertEquals(OneLineInput.parse(new CodePointBuffer("test:123,")).getClass(), OneLineInput.class);
        assertEquals(OneLineInput.parse(new CodePointBuffer("test:123, ")).getClass(), OneLineInput.class);
        assertEquals(OneLineInput.parse(new CodePointBuffer("test : 123,test2:456")).getClass(), OneLineInput.class);
        assertEquals(OneLineInput.parse(new CodePointBuffer("test : 123, test2: 456,")).getClass(), OneLineInput.class);
        assertEquals(OneLineInput.parse(new CodePointBuffer(" test:123")).getClass(), OneLineInput.class);
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
