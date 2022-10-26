package com.jerryio.protocol_diagram.test.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jerryio.protocol_diagram.config.EnumOption;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class EnumOptionTest {
    @Test
    public void testEnumOptionGet() {
        EnumOption eo = new EnumOption("test", "a", "a", "b", "c");
        assertEquals("a", eo.getDefault());
        assertEquals("a", eo.getValue());
        assertEquals("A | b | c", eo.getUsageDescription());
        assertEquals("test", eo.getKey());
    }

    @Test
    public void testEnumOptionSetString() {
        EnumOption eo = new EnumOption("test", "aaa", "aaa", "bbb", "ccc", "abc");
        assertTrue(eo.setValue("bbb").success());
        assertEquals("bbb", eo.getValue());
        assertEquals("aaa", eo.getDefault());
        assertFalse(eo.setValue("bbb").success());
        assertEquals("It is already \"bbb\".", eo.setValue("bbb").message());
        assertFalse(eo.setValue("a").success());
        assertEquals("Ambiguous value \"a\".", eo.setValue("a").message());
        assertFalse(eo.setValue("ddd").success());
        assertEquals("Unknown value \"ddd\".", eo.setValue("ddd").message());
        assertEquals("Set \"test\" from \"bbb\" to \"abc\".", eo.setValue("abc").message());
        assertEquals("Set \"test\" from \"abc\" to \"bbb\".", eo.setValue("bb").message());
    }

    @Test
    public void testEnumOptionSetParameter() {
        EnumOption eo = new EnumOption("test", "aaa", "aaa", "bbb", "ccc", "abc");
        assertTrue(eo.setValue(Parameter.parse(new CodePointBuffer("bbb"))).success());
        assertEquals("bbb", eo.getValue());
        assertEquals("aaa", eo.getDefault());
        assertFalse(eo.setValue(Parameter.parse(new CodePointBuffer("bbb"))).success());
        assertEquals("It is already \"bbb\".", eo.setValue(Parameter.parse(new CodePointBuffer("bbb"))).message());
        assertFalse(eo.setValue(Parameter.parse(new CodePointBuffer("a"))).success());
        assertEquals("Ambiguous value \"a\".", eo.setValue(Parameter.parse(new CodePointBuffer("a"))).message());
        assertFalse(eo.setValue(Parameter.parse(new CodePointBuffer("ddd"))).success());
        assertEquals("Unknown value \"ddd\".", eo.setValue(Parameter.parse(new CodePointBuffer("ddd"))).message());
        assertEquals("Set \"test\" from \"bbb\" to \"abc\".", eo.setValue(Parameter.parse(new CodePointBuffer("abc"))).message());
        assertFalse(eo.setValue(Parameter.parse(new CodePointBuffer("5"))).success());
        assertEquals("The value \"5\" is not accepted.", eo.setValue(Parameter.parse(new CodePointBuffer("5"))).message());
    }
}
