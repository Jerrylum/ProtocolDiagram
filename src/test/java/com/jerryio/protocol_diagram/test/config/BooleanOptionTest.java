package com.jerryio.protocol_diagram.test.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.management.openmbean.OpenDataException;

import org.junit.Test;

import com.jerryio.protocol_diagram.config.BooleanOption;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class BooleanOptionTest {
    @Test
    public void testBooleanOptionGet() {
        BooleanOption option = new BooleanOption("test", true);
        assertEquals("test", option.getKey());
        assertTrue(option.getDefault());
        assertTrue(option.getValue());
        assertEquals("TRUE | false", option.getUsageDescription());
        BooleanOption option2 = new BooleanOption("test", false);
        assertFalse(option2.getDefault());
        assertFalse(option2.getValue());
        assertEquals("true | FALSE", option2.getUsageDescription());
    }

    @Test
    public void testBooleanOptionSetBoolean() {
        BooleanOption option = new BooleanOption("test", true);
        assertFalse(option.setValue(true).success());
        assertEquals("It is already true.", option.setValue(true).message());
        assertEquals("TRUE | false", option.getUsageDescription());
        assertTrue(option.setValue(false).success());
        assertFalse(option.setValue(false).success());
        assertEquals("It is already false.", option.setValue(false).message());
        assertTrue(option.setValue(true).success());
    }

    @Test
    public void testBooleanOptionSetParameter() throws OpenDataException {
        BooleanOption option = new BooleanOption("test", true);
        assertFalse(option.setValue(Parameter.parse(new CodePointBuffer("true"))).success());
        assertEquals("It is already true.", option.setValue(Parameter.parse(new CodePointBuffer("true"))).message()); 
        assertTrue(option.setValue(Parameter.parse(new CodePointBuffer("false"))).success());
        assertFalse(option.setValue(Parameter.parse(new CodePointBuffer("false"))).success());
        assertEquals("It is already false.", option.setValue(Parameter.parse(new CodePointBuffer("false"))).message());
        assertTrue(option.setValue(Parameter.parse(new CodePointBuffer("true"))).success());
        assertFalse(option.setValue(Parameter.parse(new CodePointBuffer("1"))).success());
        assertEquals("The value must be a boolean.", option.setValue(Parameter.parse(new CodePointBuffer("1"))).message());
        assertFalse(option.setValue(Parameter.parse(new CodePointBuffer("0"))).success());
        assertEquals("The value must be a boolean.", option.setValue(Parameter.parse(new CodePointBuffer("0"))).message());
    }

}
