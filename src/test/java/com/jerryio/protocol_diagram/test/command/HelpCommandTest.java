package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HelpCommand;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.token.Parameter;
import com.jerryio.protocol_diagram.command.HandleResult;

import java.util.List;
import java.util.ArrayList;

public class HelpCommandTest {
    @Test
    public void testHelpCommandGet() {
        HelpCommand hc = new HelpCommand();
        assertEquals(hc.getName(), "help");
        assertEquals(hc.getUsage(), "");
        assertEquals(hc.getDescription(), "Show help message");
    }

    @Test
    public void testHelpCommandHandleSuccess() {
        HelpCommand hc = new HelpCommand();
        assertEquals(hc.handle(CommandLine.parse(new CodePointBuffer("help"))).success(), true);
    }

    @Test
    public void testHelpCommandHandleFail() {
        HelpCommand hc = new HelpCommand();
        assertEquals(hc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
    }
}
