package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jerryio.protocol_diagram.command.AddCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;

public class AddCommandTest {

    @Test
    public void testAddCommandHandleSuccess() {
        AddCommand ac = new AddCommand();
        assertEquals(ac.handle(CommandLine.parse(new CodePointBuffer("add 1 test1"))).success(), true);
        assertEquals(Main.diagram.getField(0).getLength(), 1);
        assertEquals(Main.diagram.getField(0).getName(), "test1");
        assertEquals(ac.handle(CommandLine.parse(new CodePointBuffer("add 2 test2"))).success(), true);
        assertEquals(Main.diagram.getField(1).getLength(), 2);
        assertEquals(Main.diagram.getField(1).getName(), "test2");
        assertEquals(ac.handle(CommandLine.parse(new CodePointBuffer("add 3 test3"))).success(), true);
        assertEquals(Main.diagram.getField(2).getLength(), 3);
        assertEquals(Main.diagram.getField(2).getName(), "test3");
    }

    @Test
    public void testAddCommandHandleFail() {
        AddCommand ac = new AddCommand();
        assertEquals(ac.handle(CommandLine.parse(new CodePointBuffer("add 1"))), HandleResult.TOO_FEW_ARGUMENTS);
        assertEquals(ac.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
        assertEquals(ac.handle(CommandLine.parse(new CodePointBuffer("add 1 test test"))),
                HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(ac.handle(CommandLine.parse(new CodePointBuffer("add -1 test"))),
                HandleResult.fail("Length must be a positive integer."));
        assertEquals(ac.handle(CommandLine.parse(new CodePointBuffer("add 0 test"))),
                HandleResult.fail("Length must be a positive integer."));
        assertEquals(ac.handle(CommandLine.parse(new CodePointBuffer("add 1 -1"))),
                HandleResult.fail("Name must be a string."));
    }
}
