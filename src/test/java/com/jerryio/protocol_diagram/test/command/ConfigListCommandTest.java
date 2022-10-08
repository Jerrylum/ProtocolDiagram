package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jerryio.protocol_diagram.command.ConfigListCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.command.HandleResult;

public class ConfigListCommandTest {

    @Test
    public void testConfigListCommandHandleSuccess() {
        ConfigListCommand clc = new ConfigListCommand();

        clc.execute(); // dummy

        assertEquals(clc.handle(CommandLine.parse(new CodePointBuffer("config"))).success(), true);
    }

    @Test
    public void testConfigListCommandHandleFail() {
        ConfigListCommand clc = new ConfigListCommand();
        assertEquals(clc.handle(CommandLine.parse(new CodePointBuffer("config test"))), HandleResult.NOT_HANDLED);
        assertEquals(clc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
    }
}