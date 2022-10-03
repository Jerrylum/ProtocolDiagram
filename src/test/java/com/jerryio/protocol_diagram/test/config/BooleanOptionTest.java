package com.jerryio.protocol_diagram.test.config;
import static org.junit.Assert.assertEquals;

import javax.management.openmbean.OpenDataException;

import org.junit.Test;

import com.jerryio.protocol_diagram.config.BooleanOption;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;


public class BooleanOptionTest {
    @Test
    public void testBooleanOptionGet() {
        BooleanOption option = new BooleanOption("test", true);
        assertEquals(option.getKey(), "test");
        assertEquals(option.getDefault(), true);
        assertEquals(option.getValue(), true);
        assertEquals(option.getUsageDescription(), "TRUE | false");
        BooleanOption option2 = new BooleanOption("test", false);
        assertEquals(option2.getDefault(), false);
        assertEquals(option2.getValue(), false);
        assertEquals(option2.getUsageDescription(), "true | FALSE");
    }
    @Test
    public void testBooleanOptionSetBoolean() {
        BooleanOption option = new BooleanOption("test", true);
        assertEquals(option.setValue(true).success(), false);
        assertEquals(option.setValue(true).message(), "It is already true.");
        assertEquals(option.getUsageDescription(), "TRUE | false");
        assertEquals(option.setValue(false).success(), true);
        assertEquals(option.setValue(false).success(), false);
        assertEquals(option.setValue(false).message(), "It is already false.");
        assertEquals(option.setValue(true).success(), true);
    }
    @Test
    public void testBooleanOptionSetParameter() throws OpenDataException {
        BooleanOption option = new BooleanOption("test", true);
        assertEquals(option.setValue(Parameter.parse(new CodePointBuffer("true"))).success(), false);
        assertEquals(option.setValue(Parameter.parse(new CodePointBuffer("true"))).message(), "It is already true.");
        assertEquals(option.setValue(Parameter.parse(new CodePointBuffer("false"))).success(), true);
        assertEquals(option.setValue(Parameter.parse(new CodePointBuffer("false"))).success(), false);
        assertEquals(option.setValue(Parameter.parse(new CodePointBuffer("false"))).message(), "It is already false.");
        assertEquals(option.setValue(Parameter.parse(new CodePointBuffer("true"))).success(), true);
        assertEquals(option.setValue(Parameter.parse(new CodePointBuffer("1"))).success(), false);
        assertEquals(option.setValue(Parameter.parse(new CodePointBuffer("1"))).message(), "The value must be a boolean.");
        assertEquals(option.setValue(Parameter.parse(new CodePointBuffer("0"))).success(), false);
        assertEquals(option.setValue(Parameter.parse(new CodePointBuffer("0"))).message(), "The value must be a boolean.");
    }

}
