package com.jerryio.protocol_diagram.test.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jerryio.protocol_diagram.config.EnumOption;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class EnumOptionTest {
    @Test
    public void testEnumOptionGet() {
        EnumOption eo = new EnumOption("test", "a", "a", "b", "c");
        assertEquals(eo.getDefault(), "a");
        assertEquals(eo.getValue(), "a");
        assertEquals(eo.getUsageDescription(), "A | b | c");
        assertEquals(eo.getKey(), "test");
    }

    @Test
    public void testEnumOptionSetString() {
        EnumOption eo = new EnumOption("test", "aaa", "aaa", "bbb", "ccc", "abc");
        assertEquals(eo.setValue("bbb").success(), true);
        assertEquals(eo.getValue(), "bbb");
        assertEquals(eo.getDefault(), "aaa");
        assertEquals(eo.setValue("bbb").success(), false);
        assertEquals(eo.setValue("bbb").message(), "It is already \"bbb\".");
        assertEquals(eo.setValue("a").success(), false);
        assertEquals(eo.setValue("a").message(), "Ambiguous value \"a\".");
        assertEquals(eo.setValue("ddd").success(), false);
        assertEquals(eo.setValue("ddd").message(), "Unknown value \"ddd\".");
        assertEquals(eo.setValue("abc").message(), "Set \"test\" from \"bbb\" to \"abc\".");
        assertEquals(eo.setValue("bb").message(), "Set \"test\" from \"abc\" to \"bbb\".");
    }

    @Test
    public void testEnumOptionSetParameter() {
        EnumOption eo = new EnumOption("test", "aaa", "aaa", "bbb", "ccc", "abc");
        assertEquals(eo.setValue(Parameter.parse(new CodePointBuffer("bbb"))).success(), true);
        assertEquals(eo.getValue(), "bbb");
        assertEquals(eo.getDefault(), "aaa");
        assertEquals(eo.setValue(Parameter.parse(new CodePointBuffer("bbb"))).success(), false);
        assertEquals(eo.setValue(Parameter.parse(new CodePointBuffer("bbb"))).message(), "It is already \"bbb\".");
        assertEquals(eo.setValue(Parameter.parse(new CodePointBuffer("a"))).success(), false);
        assertEquals(eo.setValue(Parameter.parse(new CodePointBuffer("a"))).message(), "Ambiguous value \"a\".");
        assertEquals(eo.setValue(Parameter.parse(new CodePointBuffer("ddd"))).success(), false);
        assertEquals(eo.setValue(Parameter.parse(new CodePointBuffer("ddd"))).message(), "Unknown value \"ddd\".");
        assertEquals(eo.setValue(Parameter.parse(new CodePointBuffer("abc"))).message(),
                "Set \"test\" from \"bbb\" to \"abc\".");
        assertEquals(eo.setValue(Parameter.parse(new CodePointBuffer("5"))).success(), false);
        assertEquals(eo.setValue(Parameter.parse(new CodePointBuffer("5"))).message(),
                "The value \"5\" is not accepted.");
    }
}
