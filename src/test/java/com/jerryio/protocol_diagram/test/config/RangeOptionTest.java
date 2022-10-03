package com.jerryio.protocol_diagram.test.config;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jerryio.protocol_diagram.config.RangeOption;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class RangeOptionTest {
    @Test
    public void testRangeOptionGet(){
        RangeOption ro = new RangeOption("test", 1, 0, 10);
        assertEquals(ro.getDefault(), (Integer)1);
        assertEquals(ro.getValue(), (Integer)1);
        assertEquals(ro.getUsageDescription(), "min:0 max:10 default:1");
        assertEquals(ro.getKey(), "test");

    }
    @Test
    public void testRangeOptionSetint(){
        RangeOption ro = new RangeOption("test", 1, 0, 10);
        assertEquals(ro.setValue(5).success(), true);
        assertEquals(ro.getValue(), (Integer)5);
        assertEquals(ro.getDefault(), (Integer)1);
        assertEquals(ro.setValue(5).success(), false);
        assertEquals(ro.setValue(5).message(), "It is already 5.");
        assertEquals(ro.setValue(-1).success(), false);
        assertEquals(ro.setValue(-1).message(), "The value must be between 0 and 10.");
        assertEquals(ro.setValue(11).success(), false);
        assertEquals(ro.setValue(11).message(), "The value must be between 0 and 10.");
    }
    @Test
    public void testRangeOptionSetParameter(){
        RangeOption ro = new RangeOption("test", 1, 0, 10);
        assertEquals(ro.setValue(Parameter.parse(new CodePointBuffer("5"))).success(), true);
        assertEquals(ro.getValue(), (Integer)5);
        assertEquals(ro.getDefault(), (Integer)1);
        assertEquals(ro.setValue(Parameter.parse(new CodePointBuffer("5"))).success(), false);
        assertEquals(ro.setValue(Parameter.parse(new CodePointBuffer("5"))).message(), "It is already 5.");
        assertEquals(ro.setValue(Parameter.parse(new CodePointBuffer("-1"))).success(), false);
        assertEquals(ro.setValue(Parameter.parse(new CodePointBuffer("-1"))).message(), "The value must be between 0 and 10.");
        assertEquals(ro.setValue(Parameter.parse(new CodePointBuffer("11"))).success(), false);
        assertEquals(ro.setValue(Parameter.parse(new CodePointBuffer("11"))).message(), "The value must be between 0 and 10.");
        assertEquals(ro.setValue(Parameter.parse(new CodePointBuffer("1.1"))).success(), false);
        assertEquals(ro.setValue(Parameter.parse(new CodePointBuffer("1.1"))).message(), "The value must be an integer.");
    }
}
