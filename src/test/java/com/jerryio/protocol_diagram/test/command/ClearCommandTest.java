package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.ClearCommand;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.command.HandleResult;

public class ClearCommandTest {
    @Before
    public void setUp() {
        Main.handler.newDiagram();
    }

    @Test
    public void testClearCommandHandleSuccess() {
        Main.diagram.addField(new Field("test", 1));
        Main.diagram.addField(new Field("test2", 2));
        assertEquals(2, Main.diagram.size());
        ClearCommand cc = new ClearCommand();
        assertEquals("Removed all fields.", cc.handle(CommandLine.parse(new CodePointBuffer("clear"))).message());
        assertEquals(0, Main.diagram.size());
    }

    @Test
    public void testClearCommandHandleFail() {
        ClearCommand cc = new ClearCommand();
        assertEquals(HandleResult.TOO_MANY_ARGUMENTS, cc.handle(CommandLine.parse(new CodePointBuffer("clear test"))));
        assertEquals(HandleResult.NOT_HANDLED, cc.handle(CommandLine.parse(new CodePointBuffer("test"))));
    }
}
