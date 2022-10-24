package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import java.util.*;
import com.jerryio.protocol_diagram.token.Parameter;

public class CommandLineTest {
    @Test
    public void testCommandLineValid() {
        List<Parameter> params = new ArrayList<>();
        assertEquals("target", CommandLine.parse(new CodePointBuffer("target")).name());
        assertEquals("target", CommandLine.parse(new CodePointBuffer("target ")).name());
        assertEquals("target", CommandLine.parse(new CodePointBuffer("  target   ")).name());

        assertTrue(CommandLine.parse(new CodePointBuffer("target")).params().equals(params));

        params.add(Parameter.parse(new CodePointBuffer("value1")));
        assertTrue(CommandLine.parse(new CodePointBuffer("target value1")).params().equals(params));

        params.add(Parameter.parse(new CodePointBuffer("'value2'")));
        assertTrue(CommandLine.parse(new CodePointBuffer("target value1 'value2'")).params().equals(params));

        params.add(Parameter.parse(new CodePointBuffer("\"value3\"")));
        assertTrue(CommandLine.parse(new CodePointBuffer("target value1 'value2' \"value3\"")).params().equals(params));

        params.add(Parameter.parse(new CodePointBuffer("True")));
        assertTrue(CommandLine.parse(new CodePointBuffer("target value1 'value2' \"value3\" True")).params().equals(params));

        params.add(Parameter.parse(new CodePointBuffer("False")));
        assertTrue(CommandLine.parse(new CodePointBuffer("target value1 'value2' \"value3\" True False")).params().equals(params));

        params.add(Parameter.parse(new CodePointBuffer("123")));
        assertTrue(CommandLine.parse(new CodePointBuffer("target value1 'value2' \"value3\" True False 123")).params().equals(params));

        params.add(Parameter.parse(new CodePointBuffer("123.456")));
        assertTrue(CommandLine.parse(new CodePointBuffer("target value1 'value2' \"value3\" True False 123 123.456")).params().equals(params));

        params.add(Parameter.parse(new CodePointBuffer("-123.456")));
        assertTrue(CommandLine.parse(new CodePointBuffer("target value1 'value2' \"value3\" True False 123 123.456 -123.456")).params().equals(params));

        params.add(Parameter.parse(new CodePointBuffer("-123")));
        assertTrue(CommandLine.parse(new CodePointBuffer("target value1 'value2' \"value3\" True False 123 123.456 -123.456 -123")).params().equals(params));
    }

    @Test
    public void testCommandLineNull() {
        assertNull(CommandLine.parse(new CodePointBuffer("")));
        assertNull(CommandLine.parse(new CodePointBuffer("target '")));
    }
}
