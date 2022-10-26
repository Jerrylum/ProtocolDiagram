package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.command.commands.AddCommand;

public class AddCommandTest {
    @Before
    public void setUp() {
        Main.handler.newDiagram();
    }

    @Test
    public void testAddCommandHandleSuccess() {
        AddCommand ac = new AddCommand();
        assertTrue(ac.handle(CommandLine.parse(new CodePointBuffer("add 1 test1"))).success());
        assertEquals(1, Main.diagram.getField(0).getLength());
        assertEquals("test1", Main.diagram.getField(0).getName());
        assertTrue(ac.handle(CommandLine.parse(new CodePointBuffer("add 2 test2"))).success());
        assertEquals(2, Main.diagram.getField(1).getLength());
        assertEquals("test2", Main.diagram.getField(1).getName());
        assertTrue(ac.handle(CommandLine.parse(new CodePointBuffer("add 3 test3"))).success());
        assertEquals(3, Main.diagram.getField(2).getLength());
        assertEquals("test3", Main.diagram.getField(2).getName());
    }

    @Test
    public void testAddCommandHandleFail() {
        AddCommand ac = new AddCommand();
        assertEquals(HandleResult.TOO_FEW_ARGUMENTS, ac.handle(CommandLine.parse(new CodePointBuffer("add 1"))));
        assertEquals(HandleResult.NOT_HANDLED, ac.handle(CommandLine.parse(new CodePointBuffer("test"))));
        assertEquals(HandleResult.TOO_MANY_ARGUMENTS, ac.handle(CommandLine.parse(new CodePointBuffer("add 1 test test"))));
        assertEquals(HandleResult.fail("Length must be a positive integer."), ac.handle(CommandLine.parse(new CodePointBuffer("add a test"))));
        assertEquals(HandleResult.fail("Length must be a positive integer."), ac.handle(CommandLine.parse(new CodePointBuffer("add -1 test"))));
        assertEquals(HandleResult.fail("Length must be a positive integer."), ac.handle(CommandLine.parse(new CodePointBuffer("add 0 test"))));
        assertEquals(HandleResult.fail("Name must be a string."), ac.handle(CommandLine.parse(new CodePointBuffer("add 1 -1"))));
    }
}
