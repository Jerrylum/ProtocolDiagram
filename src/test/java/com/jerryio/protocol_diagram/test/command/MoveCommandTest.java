package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.command.MoveCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;

public class MoveCommandTest {
    @Before
    public void setUp() {
        Main.diagram = new Diagram();
    }

    @Test
    public void testMoveCommandHandleSuccess() {
        Field f1 = new Field("test1", 1);
        Field f2 = new Field("test2", 2);
        Field f3 = new Field("test3", 3);
        Main.diagram.addField(f1);
        Main.diagram.addField(f2);
        Main.diagram.addField(f3);
        assertEquals(Main.diagram.getFields().size(), 3);
        MoveCommand mc = new MoveCommand();
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move 0 2"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 3);
        assertEquals(Main.diagram.getField(0).getName(), "test2");
        assertEquals(Main.diagram.getField(1).getName(), "test3");
        assertEquals(Main.diagram.getField(2).getName(), "test1");
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move 2 1"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 3);
        assertEquals(Main.diagram.getField(0).getName(), "test2");
        assertEquals(Main.diagram.getField(1).getName(), "test1");
        assertEquals(Main.diagram.getField(2).getName(), "test3");
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move 1 0"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 3);
        assertEquals(Main.diagram.getField(0).getName(), "test1");
        assertEquals(Main.diagram.getField(1).getName(), "test2");
        assertEquals(Main.diagram.getField(2).getName(), "test3");
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move 2 0"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 3);
        assertEquals(Main.diagram.getField(0).getName(), "test3");
        assertEquals(Main.diagram.getField(1).getName(), "test1");
        assertEquals(Main.diagram.getField(2).getName(), "test2");
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move 0 1"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 3);
        assertEquals(Main.diagram.getField(0).getName(), "test1");
        assertEquals(Main.diagram.getField(1).getName(), "test3");
        assertEquals(Main.diagram.getField(2).getName(), "test2");
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move 1 2"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 3);
        assertEquals(Main.diagram.getField(0).getName(), "test1");
        assertEquals(Main.diagram.getField(1).getName(), "test2");
        assertEquals(Main.diagram.getField(2).getName(), "test3");
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
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move"))), HandleResult.TOO_FEW_ARGUMENTS);
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move 0"))), HandleResult.TOO_FEW_ARGUMENTS);
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move 0 2 1"))), HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move -1 2"))),
                HandleResult.fail("Index start from zero."));
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move 0 -1"))),
                HandleResult.fail("Target index start from zero."));
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move 3 0"))),
                HandleResult.fail("Index out of range."));
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move 0 3"))),
                HandleResult.fail("Target index out of range."));
        assertEquals(mc.handle(CommandLine.parse(new CodePointBuffer("move 0 0"))),
                HandleResult.fail("Index and target index cannot be the same."));
    }
}
