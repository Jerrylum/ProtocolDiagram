package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.command.ListCommand;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;

public class ListCommandTest {
    @Before
    public void setUp() {
        Main.handler.newDiagram();
    }

    @Test
    public void testListCommandHandleSuccess() {
        ListCommand lc = new ListCommand();

        assertEquals("There is no field in the diagram.", lc.handle(CommandLine.parse(new CodePointBuffer("list"))).message());
        Main.diagram.addField(new Field("test", 1));
        Main.diagram.addField(new Field("test2", 2));
        assertTrue(lc.handle(CommandLine.parse(new CodePointBuffer("list"))).success());
        assertTrue(lc.handle(CommandLine.parse(new CodePointBuffer("list"))).message().startsWith("There are 2 fields in the diagram"));
    }

    @Test
    public void testListCommandHandleFail() {
        ListCommand lc = new ListCommand();
        assertEquals(HandleResult.TOO_MANY_ARGUMENTS, lc.handle(CommandLine.parse(new CodePointBuffer("list test"))));
        assertEquals(HandleResult.NOT_HANDLED, lc.handle(CommandLine.parse(new CodePointBuffer("test"))));
    }
}
