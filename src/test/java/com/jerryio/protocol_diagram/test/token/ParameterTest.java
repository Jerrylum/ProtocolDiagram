package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.BooleanT;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class ParameterTest {
    @Test
    public void testParameterVaild() {
        assertEquals(Parameter.parse(new CodePointBuffer("True")).getBoolean(), true);
        assertEquals(Parameter.parse(new CodePointBuffer("False")).getBoolean(), false);
        assertEquals(Parameter.parse(new CodePointBuffer("123")).getInt(), 123);
        assertEquals(Parameter.parse(new CodePointBuffer("123.456")).getDouble(), 123.456, 0.0001);
        assertEquals(Parameter.parse(new CodePointBuffer("Hello World")).getString(), "Hello");
        // assertEquals(Parameter.parse(new CodePointBuffer("0")).getInt(), 0);
        // assertEquals(Parameter.parse(new CodePointBuffer("0.0")).getDouble(), 0.0, 0.0001);
        // assertEquals(Parameter.parse(new CodePointBuffer("-14")).getInt(), -14);
        // assertEquals(Parameter.parse(new CodePointBuffer("-14.0")).getDouble(), -14.0, 0.0001);
    }


    @Test
    public void testParameterNull() {  

    }
}
