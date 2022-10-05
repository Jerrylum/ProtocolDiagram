package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jerryio.protocol_diagram.command.ListCommand;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;


public class ListCommandTest {
    @Test
    public void testListCommandGet() {
        ListCommand lc = new ListCommand();
        assertEquals(lc.getName(), "list");
        assertEquals(lc.getUsage(), "");
        assertEquals(lc.getDescription(), "List all fields in the diagram");
    }

    @Test
    public void testListCommandHandleSuccess() {
        ListCommand lc = new ListCommand();
        assertEquals(lc.handle(CommandLine.parse(new CodePointBuffer("list"))).message(), "There is no field in the diagram.");
        Main.diagram.addField(new Field("test", 1));
        Main.diagram.addField(new Field("test2", 2));
        assertEquals(lc.handle(CommandLine.parse(new CodePointBuffer("list"))).success(), true);
        assertEquals(lc.handle(CommandLine.parse(new CodePointBuffer("list"))).message().startsWith("There are 2 fields in the diagram"), true);
    }

    @Test
    public void testListCommandHandleFail() {
        ListCommand lc = new ListCommand();
        assertEquals(lc.handle(CommandLine.parse(new CodePointBuffer("list test"))), HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(lc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
    }
}