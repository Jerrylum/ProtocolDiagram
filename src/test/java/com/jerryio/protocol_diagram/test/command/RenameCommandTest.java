package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.command.RenameCommand;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;

public class RenameCommandTest {
    @Before
    public void setUp() {
        Main.handler.newDiagram();
    }

    @Test
    public void testRenameCommandHandleSuccess() {
        Main.diagram.addField(new Field("test1", 1));
        Main.diagram.addField(new Field("test2", 2));
        Main.diagram.addField(new Field("test3", 3));
        RenameCommand rc = new RenameCommand();
        assertTrue(rc.handle(CommandLine.parse(new CodePointBuffer("rename 0 test4"))).success());
        assertEquals("test4", Main.diagram.getField(0).getName());
        assertTrue(rc.handle(CommandLine.parse(new CodePointBuffer("rename 2 test5"))).success());
        assertEquals("test5", Main.diagram.getField(2).getName());
        assertTrue(rc.handle(CommandLine.parse(new CodePointBuffer("rename 1 test6"))).success());
        assertEquals("test6", Main.diagram.getField(1).getName());
    }

    @Test
    public void testRenameCommandHandleFail() {
        RenameCommand rc = new RenameCommand();
        assertEquals(HandleResult.TOO_FEW_ARGUMENTS, rc.handle(CommandLine.parse(new CodePointBuffer("rename test"))));
        assertEquals(HandleResult.NOT_HANDLED, rc.handle(CommandLine.parse(new CodePointBuffer("test"))));
        assertEquals(HandleResult.TOO_MANY_ARGUMENTS, rc.handle(CommandLine.parse(new CodePointBuffer("rename 0 test1 test2"))));
        assertEquals(HandleResult.fail("Index start from zero."), rc.handle(CommandLine.parse(new CodePointBuffer("rename a test1"))));
        assertEquals(HandleResult.fail("Index start from zero."), rc.handle(CommandLine.parse(new CodePointBuffer("rename -1 test1"))));
        assertEquals(HandleResult.fail("New name must be a string."), rc.handle(CommandLine.parse(new CodePointBuffer("rename 0 1"))));
        assertEquals(HandleResult.fail("Index out of range."), rc.handle(CommandLine.parse(new CodePointBuffer("rename 1 test1"))));
    }
}
