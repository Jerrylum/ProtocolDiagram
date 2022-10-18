package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.command.ResizeCommand;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;

public class ResizeCommandTest {
    @Before
    public void setUp() {
        Main.handler.newDiagram();
    }

    @Test
    public void testResizeCommandHandleSuccess() {
        Main.diagram.addField(new Field("test1", 1));
        Main.diagram.addField(new Field("test2", 2));
        Main.diagram.addField(new Field("test3", 3));
        ResizeCommand rc = new ResizeCommand();
        assertTrue(rc.handle(CommandLine.parse(new CodePointBuffer("resize 0 8"))).success());
        assertEquals(8, Main.diagram.getField(0).getLength());
        assertTrue(rc.handle(CommandLine.parse(new CodePointBuffer("resize 2 32"))).success());
        assertEquals(32, Main.diagram.getField(2).getLength());
        assertTrue(rc.handle(CommandLine.parse(new CodePointBuffer("resize 1 16"))).success());
        assertEquals(16, Main.diagram.getField(1).getLength());
    }

    @Test
    public void testResizeCommandHandleFail() {
        Main.diagram.addField(new Field("test1", 1));
        Main.diagram.addField(new Field("test2", 2));
        Main.diagram.addField(new Field("test3", 3));
        ResizeCommand rc = new ResizeCommand();
        assertEquals(HandleResult.TOO_FEW_ARGUMENTS, rc.handle(CommandLine.parse(new CodePointBuffer("resize 0"))));
        assertEquals(HandleResult.NOT_HANDLED, rc.handle(CommandLine.parse(new CodePointBuffer("test"))));
        assertEquals(HandleResult.TOO_MANY_ARGUMENTS, rc.handle(CommandLine.parse(new CodePointBuffer("resize 0 1 2"))));
        assertEquals(HandleResult.fail("Index start from zero."), rc.handle(CommandLine.parse(new CodePointBuffer("resize a 16"))));
        assertEquals(HandleResult.fail("New size must be a positive integer."), rc.handle(CommandLine.parse(new CodePointBuffer("resize 0 a"))));
        assertEquals(HandleResult.fail("Index start from zero."), rc.handle(CommandLine.parse(new CodePointBuffer("resize -1 16"))));
        assertEquals(HandleResult.fail("New size must be a positive integer."), rc.handle(CommandLine.parse(new CodePointBuffer("resize 0 -1"))));
        assertEquals(HandleResult.fail("Index out of range."), rc.handle(CommandLine.parse(new CodePointBuffer("resize 3 16"))));
    }
}
