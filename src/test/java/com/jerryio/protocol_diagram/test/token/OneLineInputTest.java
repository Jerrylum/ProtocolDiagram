package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.OneLineInput;

public class OneLineInputTest {
    @Test
    public void testOneLineInputVaild() {
        assertEquals(OneLineInput.parse(new CodePointBuffer("test:123")).getClass(), OneLineInput.class);
        assertEquals(OneLineInput.parse(new CodePointBuffer("test:123,")).getClass(), OneLineInput.class);
        assertEquals(OneLineInput.parse(new CodePointBuffer("test:123,test2:456")).getClass(), OneLineInput.class);
        assertEquals(OneLineInput.parse(new CodePointBuffer("test:123,test2:456,")).getClass(), OneLineInput.class);
        assertEquals(OneLineInput.parse(new CodePointBuffer(" test:123")).getClass(), OneLineInput.class);

    }

    @Test
    public void testOneLineInputNull() {

    }
}
