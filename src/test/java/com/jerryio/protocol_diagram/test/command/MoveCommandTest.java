package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.command.commands.MoveCommand;
import com.jerryio.protocol_diagram.diagram.Field;

public class MoveCommandTest {
    @Before
    public void setUp() {
        Main.handler.newDiagram();
    }

    @Test
    public void testMoveCommandHandleSuccess() {
        Field f1 = new Field("test1", 1);
        Field f2 = new Field("test2", 2);
        Field f3 = new Field("test3", 3);
        Main.diagram.addField(f1);
        Main.diagram.addField(f2);
        Main.diagram.addField(f3);
        assertEquals(3, Main.diagram.getFields().size());
        MoveCommand mc = new MoveCommand();
        assertTrue(mc.handle(CommandLine.parse(new CodePointBuffer("move 0 2"))).success());
        assertEquals(3, Main.diagram.getFields().size());
        assertEquals("test2", Main.diagram.getField(0).getName());
        assertEquals("test3", Main.diagram.getField(1).getName());
        assertEquals("test1", Main.diagram.getField(2).getName());

        assertTrue(mc.handle(CommandLine.parse(new CodePointBuffer("move 2 1"))).success());
        assertEquals(3, Main.diagram.getFields().size());
        assertEquals("test2", Main.diagram.getField(0).getName());
        assertEquals("test1", Main.diagram.getField(1).getName());
        assertEquals("test3", Main.diagram.getField(2).getName());

        assertTrue(mc.handle(CommandLine.parse(new CodePointBuffer("move 1 0"))).success());
        assertEquals(3, Main.diagram.getFields().size());
        assertEquals("test1", Main.diagram.getField(0).getName());
        assertEquals("test2", Main.diagram.getField(1).getName());
        assertEquals("test3", Main.diagram.getField(2).getName());

        assertTrue(mc.handle(CommandLine.parse(new CodePointBuffer("move 2 0"))).success());
        assertEquals(3, Main.diagram.getFields().size());
        assertEquals("test3", Main.diagram.getField(0).getName());
        assertEquals("test1", Main.diagram.getField(1).getName());
        assertEquals("test2", Main.diagram.getField(2).getName());

        assertTrue(mc.handle(CommandLine.parse(new CodePointBuffer("move 0 1"))).success());
        assertEquals(3, Main.diagram.getFields().size());
        assertEquals("test1", Main.diagram.getField(0).getName());
        assertEquals("test3", Main.diagram.getField(1).getName());
        assertEquals("test2", Main.diagram.getField(2).getName());

        assertTrue(mc.handle(CommandLine.parse(new CodePointBuffer("move 1 2"))).success());
        assertEquals(3, Main.diagram.getFields().size());
        assertEquals("test1", Main.diagram.getField(0).getName());
        assertEquals("test2", Main.diagram.getField(1).getName());
        assertEquals("test3", Main.diagram.getField(2).getName());
    }

    @Test
    public void testMoveCommandHandleFail() {
        Field f1 = new Field("test1", 1);
        Field f2 = new Field("test2", 2);
        Field f3 = new Field("test3", 3);
        Main.diagram.addField(f1);
        Main.diagram.addField(f2);
        Main.diagram.addField(f3);
        MoveCommand mc = new MoveCommand();
        assertEquals(HandleResult.TOO_FEW_ARGUMENTS, mc.handle(CommandLine.parse(new CodePointBuffer("move"))));
        assertEquals(HandleResult.TOO_FEW_ARGUMENTS, mc.handle(CommandLine.parse(new CodePointBuffer("move 0"))));
        assertEquals(HandleResult.NOT_HANDLED, mc.handle(CommandLine.parse(new CodePointBuffer("test"))));
        assertEquals(HandleResult.TOO_MANY_ARGUMENTS, mc.handle(CommandLine.parse(new CodePointBuffer("move 0 2 1"))));
        assertEquals(HandleResult.fail("Index start from zero."), mc.handle(CommandLine.parse(new CodePointBuffer("move a 2"))));
        assertEquals(HandleResult.fail("Index start from zero."), mc.handle(CommandLine.parse(new CodePointBuffer("move -1 2"))));
        assertEquals(HandleResult.fail("Target index start from zero."), mc.handle(CommandLine.parse(new CodePointBuffer("move 0 a"))));
        assertEquals(HandleResult.fail("Target index start from zero."), mc.handle(CommandLine.parse(new CodePointBuffer("move 0 -1"))));
        assertEquals(HandleResult.fail("Index out of range."), mc.handle(CommandLine.parse(new CodePointBuffer("move 3 0"))));
        assertEquals(HandleResult.fail("Target index out of range."), mc.handle(CommandLine.parse(new CodePointBuffer("move 0 3"))));
        assertEquals(HandleResult.fail("Index and target index cannot be the same."), mc.handle(CommandLine.parse(new CodePointBuffer("move 0 0"))));
    }
}
