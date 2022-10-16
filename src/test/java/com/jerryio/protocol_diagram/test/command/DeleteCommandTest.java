package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.command.DeleteCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;

public class DeleteCommandTest {
    @Before
    public void setUp() {
        Main.diagram = new Diagram();
    }

    @Test
    public void testDeleteCommandHandleSuccess() {
        Field f1 = new Field("test1", 1);
        Field f2 = new Field("test2", 2);
        Field f3 = new Field("test3", 3);
        Main.diagram.addField(f1);
        Main.diagram.addField(f2);
        Main.diagram.addField(f3);
        assertEquals(3, Main.diagram.getFields().size());
        DeleteCommand dc = new DeleteCommand();
        assertTrue(dc.handle(CommandLine.parse(new CodePointBuffer("delete 1"))).success());
        assertEquals(2, Main.diagram.getFields().size());
        assertEquals(f1, Main.diagram.getField(0));
        assertEquals(f3, Main.diagram.getField(1));
        Main.diagram.addField(f2);
        assertTrue(dc.handle(CommandLine.parse(new CodePointBuffer("delete 2"))).success());
        assertEquals(2, Main.diagram.getFields().size());
        assertEquals(f1, Main.diagram.getField(0));
        assertEquals(f3, Main.diagram.getField(1));
        assertTrue(dc.handle(CommandLine.parse(new CodePointBuffer("delete 0"))).success());
        assertEquals(1, Main.diagram.getFields().size());
        assertEquals(f3, Main.diagram.getField(0));
    }

    @Test
    public void testDeleteCommandHandleFail() {
        DeleteCommand dc = new DeleteCommand();
        assertEquals(HandleResult.TOO_FEW_ARGUMENTS, dc.handle(CommandLine.parse(new CodePointBuffer("delete"))));
        assertEquals(HandleResult.NOT_HANDLED, dc.handle(CommandLine.parse(new CodePointBuffer("test"))));
        assertEquals(HandleResult.TOO_MANY_ARGUMENTS, dc.handle(CommandLine.parse(new CodePointBuffer("delete 1 2"))));
        assertEquals(HandleResult.fail("Index start from zero."), dc.handle(CommandLine.parse(new CodePointBuffer("delete a"))));
        assertEquals(HandleResult.fail("Index start from zero."), dc.handle(CommandLine.parse(new CodePointBuffer("delete -1"))));
        assertEquals(HandleResult.fail("Index out of range."), dc.handle(CommandLine.parse(new CodePointBuffer("delete 1"))));
    }
}
