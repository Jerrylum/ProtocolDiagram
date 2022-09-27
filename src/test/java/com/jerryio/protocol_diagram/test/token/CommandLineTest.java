package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import java.util.*;
import com.jerryio.protocol_diagram.token.Parameter;
import com.jerryio.protocol_diagram.token.StringT;
import com.jerryio.protocol_diagram.token.NumberT;
import com.jerryio.protocol_diagram.token.BooleanT;

public class CommandLineTest {
    @Test
    public void testCommandLineValid() {
        List<Parameter> params = new ArrayList<>();
        assertEquals(CommandLine.parse(new CodePointBuffer("target")).name(), "target");
        assertEquals(CommandLine.parse(new CodePointBuffer("target ")).name(), "target");
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
    }
}
