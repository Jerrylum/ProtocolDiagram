package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jerryio.protocol_diagram.command.ConfigCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;

public class ConfigCommandTest {
    @Test
    public void testConfigCommandHandleSuccess() {
        ConfigCommand cc = new ConfigCommand();

        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("config Bit 1"))).success());
        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("config bIt 128"))).success());
        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("config biT 32"))).success());
        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("CONFIG BIT 64"))).success());
        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("config diagram-style utf8-HEADER"))).success());
        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("config diagram-style UTF8-corner"))).success());
        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("config diagram-style ASCII"))).success());        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("config diagram-style UTF8"))).success());
        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("config HEADER-style none"))).success());
        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("config header-STYLE FULL"))).success());
        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("config header-style trim"))).success());
        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("config left-space-placeholder true"))).success());
        assertTrue(cc.handle(CommandLine.parse(new CodePointBuffer("config left-space-placeholder false"))).success());
    }

    @Test
    public void testConfigCommandHandleFail() {
        ConfigCommand cc = new ConfigCommand();
        assertFalse(cc.handle(CommandLine.parse(new CodePointBuffer("config bit -1"))).success());
        assertFalse(cc.handle(CommandLine.parse(new CodePointBuffer("config bit 129"))).success());
        assertFalse(cc.handle(CommandLine.parse(new CodePointBuffer("config bit 0"))).success());
        assertFalse(cc.handle(CommandLine.parse(new CodePointBuffer("config diagram-style null"))).success());
        assertFalse(cc.handle(CommandLine.parse(new CodePointBuffer("config header-style null"))).success());
        assertFalse(cc.handle(CommandLine.parse(new CodePointBuffer("config unknown 12"))).success());
        assertFalse(cc.handle(CommandLine.parse(new CodePointBuffer("config bit"))).success());
        assertFalse(cc.handle(CommandLine.parse(new CodePointBuffer("config bit 1 2"))).success());
        assertFalse(cc.handle(CommandLine.parse(new CodePointBuffer("config unknown 1"))).success());
    }
}
