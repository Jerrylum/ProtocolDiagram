package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jerryio.protocol_diagram.command.DeleteCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.diagram.Field;


public class DeleteCommandTest {
    @Test
    public void testDeleteCommandGet() {
        DeleteCommand dc = new DeleteCommand();
        assertEquals(dc.getName(), "delete");
        assertEquals(dc.getUsage(), "<index>");
        assertEquals(dc.getDescription(), "Remove the specified field from the diagram");
    }

    @Test
    public void testDeleteCommandHandleSuccess() {
        Field f1 = new Field("test1", 1);
        Field f2 = new Field("test2", 2);
        Field f3 = new Field("test3", 3);
        Main.diagram.addField(f1);
        Main.diagram.addField(f2);
        Main.diagram.addField(f3);
        assertEquals(Main.diagram.getFields().size(), 3);
        DeleteCommand dc = new DeleteCommand();
        assertEquals(dc.handle(CommandLine.parse(new CodePointBuffer("delete 1"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 2);
        assertEquals(Main.diagram.getField(0), f1);
        assertEquals(Main.diagram.getField(1), f3);
        Main.diagram.addField(f2);
        assertEquals(dc.handle(CommandLine.parse(new CodePointBuffer("delete 2"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 2);
        assertEquals(Main.diagram.getField(0), f1);
        assertEquals(Main.diagram.getField(1), f3);
        assertEquals(dc.handle(CommandLine.parse(new CodePointBuffer("delete 0"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 1);
        assertEquals(Main.diagram.getField(0), f3);
    }

    @Test
    public void testDeleteCommandHandleFail() {
        DeleteCommand dc = new DeleteCommand();
        assertEquals(dc.handle(CommandLine.parse(new CodePointBuffer("delete"))), HandleResult.TOO_FEW_ARGUMENTS);
        assertEquals(dc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
        assertEquals(dc.handle(CommandLine.parse(new CodePointBuffer("delete 1 2"))), HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(dc.handle(CommandLine.parse(new CodePointBuffer("delete -1"))), HandleResult.fail("Index start from zero."));
        assertEquals(dc.handle(CommandLine.parse(new CodePointBuffer("delete 1"))), HandleResult.fail("Index out of range."));
    }
}
