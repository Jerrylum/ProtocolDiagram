package com.jerryio.protocol_diagram.test.config;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jerryio.protocol_diagram.config.*;
import com.jerryio.protocol_diagram.token.Parameter;
import com.jerryio.protocol_diagram.token.CodePointBuffer;

public class ConfigurationTest {
    @Test
    public void testConfigurationGet() {
        BooleanOption bo = new BooleanOption("test", true);
        BooleanOption bo2 = new BooleanOption("test2", false);
        EnumOption eo = new EnumOption("test3", "test", "test", "test2");
        RangeOption ro = new RangeOption("test4", 1, 1, 10);
        RangeOption ro2 = new RangeOption("key", -1, -1, 10);
        List<Option> list = new ArrayList<Option>();
        list.add(bo);
        list.add(bo2);
        list.add(eo);
        list.add(ro);
        list.add(ro2);
        Configuration c = new Configuration(bo, bo2, eo, ro, ro2);
        assertEquals(c.getOptions(), list);
        assertEquals(c.getValue("test"), true);
        assertEquals(c.getValue("test2"), false);
        assertEquals(c.getValue("test3"), "test");
        assertEquals(c.getValue("test4"), 1);
        assertEquals(c.getValue("key"), -1);
        assertEquals(c.getValue("null"), null);
        assertEquals(c.getOption("test"), bo);
        assertEquals(c.getOption("test2"), bo2);
        assertEquals(c.getOption("test3"), eo);
        assertEquals(c.getOption("test4"), ro);
        assertEquals(c.getOption("null"), null);
        assertEquals(c.getOption("ke"), ro2);
        assertEquals(c.getOption("tes"), null);
    }

    @Test
    public void testConfigurationSet() {
        BooleanOption bo = new BooleanOption("test", true);
        BooleanOption bo2 = new BooleanOption("test2", false);
        EnumOption eo = new EnumOption("test3", "test", "test", "test2");
        RangeOption ro = new RangeOption("test4", 1, 1, 10);
        Configuration c = new Configuration(bo, bo2, eo, ro);
        assertEquals(c.setValue("test", Parameter.parse(new CodePointBuffer("false"))).success(), true);
        assertEquals(c.setValue("test2", Parameter.parse(new CodePointBuffer("true"))).success(), true);
        assertEquals(c.setValue("test3", Parameter.parse(new CodePointBuffer("test2"))).success(), true);
        assertEquals(c.setValue("test4", Parameter.parse(new CodePointBuffer("5"))).success(), true);
        assertEquals(c.setValue("null", Parameter.parse(new CodePointBuffer("5"))).success(), false);
    }

}
