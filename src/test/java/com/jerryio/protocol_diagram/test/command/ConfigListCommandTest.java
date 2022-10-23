package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.command.commands.ConfigListCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;

public class ConfigListCommandTest {

    @Test
    public void testConfigListCommandHandleSuccess() {
        ConfigListCommand clc = new ConfigListCommand();

        assertTrue(clc.handle(CommandLine.parse(new CodePointBuffer("config"))).success());
    }

    @Test
    public void testConfigListCommandHandleFail() {
        ConfigListCommand clc = new ConfigListCommand();
        assertEquals(HandleResult.NOT_HANDLED, clc.handle(CommandLine.parse(new CodePointBuffer("config test"))));
        assertEquals(HandleResult.NOT_HANDLED, clc.handle(CommandLine.parse(new CodePointBuffer("test"))));
    }
}
