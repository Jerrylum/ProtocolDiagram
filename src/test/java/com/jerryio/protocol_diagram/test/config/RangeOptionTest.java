package com.jerryio.protocol_diagram.test.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jerryio.protocol_diagram.config.RangeOption;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class RangeOptionTest {
    @Test
    public void testRangeOptionGet() {
        RangeOption ro = new RangeOption("test", 1, 0, 10);
        assertEquals((Integer) 1, ro.getDefault());
        assertEquals((Integer) 1, ro.getValue());
        assertEquals("min:0 max:10 default:1", ro.getUsageDescription());
        assertEquals("test", ro.getKey());

    }

    @Test
    public void testRangeOptionSetInteger() {
        RangeOption ro = new RangeOption("test", 1, 0, 10);
        assertTrue(ro.setValue(5).success());
        assertEquals((Integer) 5, ro.getValue());
        assertEquals((Integer) 1, ro.getDefault());
        assertFalse(ro.setValue(5).success());
        assertEquals("It is already 5.", ro.setValue(5).message());
        assertEquals("Set \"test\" from 5 to 0.", ro.setValue(0).message());
        assertEquals((Integer) 0, ro.getValue());
        assertEquals((Integer) 1, ro.getDefault());
        assertEquals("Set \"test\" from 0 to 10.", ro.setValue(10).message());
        assertEquals((Integer) 10, ro.getValue());
        assertEquals((Integer) 1, ro.getDefault());
        assertEquals("Set \"test\" from 10 to 1.", ro.setValue(1).message());
        assertEquals((Integer) 1, ro.getValue());
        assertEquals((Integer) 1, ro.getDefault());
        assertEquals("Set \"test\" from 1 to 9.", ro.setValue(9).message());
        assertEquals((Integer) 9, ro.getValue());
        assertEquals((Integer) 1, ro.getDefault());
        assertFalse(ro.setValue(-1).success());
        assertEquals("The value must be between 0 and 10.", ro.setValue(-1).message());
        assertFalse(ro.setValue(11).success());
        assertEquals("The value must be between 0 and 10.", ro.setValue(11).message());
    }

    @Test
    public void testRangeOptionSetParameter() {
        RangeOption ro = new RangeOption("test", 1, 0, 10);
        assertTrue(ro.setValue(Parameter.parse(new CodePointBuffer("5"))).success());
        assertEquals((Integer) 5, ro.getValue());
        assertEquals((Integer) 1, ro.getDefault());
        assertFalse(ro.setValue(Parameter.parse(new CodePointBuffer("5"))).success());
        assertEquals("It is already 5.", ro.setValue(Parameter.parse(new CodePointBuffer("5"))).message());
        assertEquals("Set \"test\" from 5 to 0.", ro.setValue(Parameter.parse(new CodePointBuffer("0"))).message());
        assertEquals((Integer) 0, ro.getValue());
        assertEquals((Integer) 1, ro.getDefault());
        assertEquals("Set \"test\" from 0 to 10.", ro.setValue(Parameter.parse(new CodePointBuffer("10"))).message());
        assertEquals((Integer) 10, ro.getValue());
        assertEquals((Integer) 1, ro.getDefault());
        assertEquals("Set \"test\" from 10 to 1.", ro.setValue(Parameter.parse(new CodePointBuffer("1"))).message());
        assertEquals((Integer) 1, ro.getValue());
        assertEquals((Integer) 1, ro.getDefault());
        assertEquals("Set \"test\" from 1 to 9.", ro.setValue(Parameter.parse(new CodePointBuffer("9"))).message());
        assertEquals((Integer) 9, ro.getValue());
        assertEquals((Integer) 1, ro.getDefault());
        assertFalse(ro.setValue(Parameter.parse(new CodePointBuffer("-1"))).success());
        assertEquals("The value must be between 0 and 10.", ro.setValue(Parameter.parse(new CodePointBuffer("-1"))).message());
        assertFalse(ro.setValue(Parameter.parse(new CodePointBuffer("11"))).success());
        assertEquals("The value must be between 0 and 10.", ro.setValue(Parameter.parse(new CodePointBuffer("11"))).message());
        assertFalse(ro.setValue(Parameter.parse(new CodePointBuffer("1.1"))).success());
        assertEquals("The value must be an integer.", ro.setValue(Parameter.parse(new CodePointBuffer("1.1"))).message());
        assertFalse(ro.setValue(Parameter.parse(new CodePointBuffer("a"))).success());
        assertEquals("The value must be an integer.", ro.setValue(Parameter.parse(new CodePointBuffer("a"))).message());
    }
}
