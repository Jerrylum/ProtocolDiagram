package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jerryio.protocol_diagram.command.ConfigCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;


public class ConfigCommandTest {
    @Test
    public void testConfigCommandHandleSuccess() {
        ConfigCommand cc = new ConfigCommand();
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config Bit 1"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config bIt 128"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config biT 32"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("CONFIG BIT 64"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config diagram-style utf8-HEADER"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config diagram-style UTF8-corner"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config diagram-style ASCII"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config DIAGRAM-style ascii-verbose"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config diagram-STYLE utf8"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config HEADER-style none"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config header-STYLE FULL"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config header-style trim"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config left-space-placeholder true"))).success(), true);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config left-space-placeholder false"))).success(), true);
    }

    @Test
    public void testConfigCommandHandleFail() {
        ConfigCommand cc = new ConfigCommand();
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config bit -1"))).success(), false);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config bit 129"))).success(), false);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config bit 0"))).success(), false);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config diagram-style null"))).success(), false);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config header-style null"))).success(), false);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config unknown 12"))).success(), false);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config bit"))).success(), false);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("config bit 1 2"))).success(), false);
    }
}
