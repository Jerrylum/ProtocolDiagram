package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.command.commands.HelpCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;

public class HelpCommandTest {
    @Test
    public void testHelpCommandHandleSuccess() {
        HelpCommand hc = new HelpCommand();
        assertTrue(hc.handle(CommandLine.parse(new CodePointBuffer("help"))).success());
    }

    @Test
    public void testHelpCommandHandleFail() {
        HelpCommand hc = new HelpCommand();
        assertEquals(HandleResult.NOT_HANDLED, hc.handle(CommandLine.parse(new CodePointBuffer("test"))));
    }
}
