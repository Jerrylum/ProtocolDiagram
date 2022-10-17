package com.jerryio.protocol_diagram.test.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
        assertEquals(true, c.getValue("test"));
        assertEquals(false, c.getValue("test2"));
        assertEquals("test", c.getValue("test3"));
        assertEquals(1, c.getValue("test4"));
        assertEquals(-1, c.getValue("key"));
        assertNull(c.getValue("null"));
        assertEquals(bo, c.getOption("test"));
        assertEquals(bo2, c.getOption("test2"));
        assertEquals(eo, c.getOption("test3"));
        assertEquals(ro, c.getOption("test4"));
        assertNull(c.getOption("null"));
        assertEquals(ro2, c.getOption("ke"));
        assertNull(c.getOption("tes"));
    }

    @Test
    public void testConfigurationSet() {
        BooleanOption bo = new BooleanOption("test", true);
        BooleanOption bo2 = new BooleanOption("test2", false);
        EnumOption eo = new EnumOption("test3", "test", "test", "test2");
        RangeOption ro = new RangeOption("test4", 1, 1, 10);
        Configuration c = new Configuration(bo, bo2, eo, ro);
        assertTrue(c.setValue("test", Parameter.parse(new CodePointBuffer("false"))).success());
        assertTrue(c.setValue("test2", Parameter.parse(new CodePointBuffer("true"))).success());
        assertTrue(c.setValue("test3", Parameter.parse(new CodePointBuffer("test2"))).success());
        assertTrue(c.setValue("test4", Parameter.parse(new CodePointBuffer("5"))).success());
        assertFalse(c.setValue("null", Parameter.parse(new CodePointBuffer("5"))).success());
    }

}
