package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.command.ResizeCommand;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;

public class ResizeCommandTest {
    @Before
    public void setUp() {
        Main.diagram = new Diagram();
    }

    @Test
    public void testResizeCommandHandleSuccess() {
        Main.diagram.addField(new Field("test1", 1));
        Main.diagram.addField(new Field("test2", 2));
        Main.diagram.addField(new Field("test3", 3));
        ResizeCommand rc = new ResizeCommand();
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("resize 0 8"))).success(), true);
        assertEquals(Main.diagram.getField(0).getLength(), 8);
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("resize 2 32"))).success(), true);
        assertEquals(Main.diagram.getField(2).getLength(), 32);
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("resize 1 16"))).success(), true);
        assertEquals(Main.diagram.getField(1).getLength(), 16);
    }

    @Test
    public void testResizeCommandHandleFail() {
        Main.diagram.addField(new Field("test1", 1));
        Main.diagram.addField(new Field("test2", 2));
        Main.diagram.addField(new Field("test3", 3));
        ResizeCommand rc = new ResizeCommand();
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("resize 0"))), HandleResult.TOO_FEW_ARGUMENTS);
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("resize 0 1 2"))),
                HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("resize a 16"))),
                HandleResult.fail("Index start from zero."));
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("resize 0 a"))),
                HandleResult.fail("New size must be a positive integer."));
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("resize -1 16"))),
                HandleResult.fail("Index start from zero."));
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("resize 0 -1"))),
                HandleResult.fail("New size must be a positive integer."));
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("resize 3 16"))),
                HandleResult.fail("Index out of range."));
    }
}
