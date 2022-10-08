package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.ClearCommand;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.command.HandleResult;

public class ClearCommandTest {
    @Before
    public void setUp() {
        Main.diagram = new Diagram();
    }

    @Test
    public void testClearCommandHandleSuccess() {
        Main.diagram.addField(new Field("test", 1));
        Main.diagram.addField(new Field("test2", 2));
        assertEquals(2, Main.diagram.size());
        ClearCommand cc = new ClearCommand();
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("clear"))).message(), "Removed all fields.");
        assertEquals(Main.diagram.size(), 0);
    }

    @Test
    public void testClearCommandHandleFail() {
        ClearCommand cc = new ClearCommand();
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("clear test"))), HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
    }
}
