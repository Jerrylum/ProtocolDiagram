package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jerryio.protocol_diagram.command.RenameCommand;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;


public class RenameCommandTest {
    @Test
    public void testRenameCommandGet() {
        RenameCommand rc = new RenameCommand();
        assertEquals(rc.getName(), "rename");
        assertEquals(rc.getUsage(), "<index> <new-name>");
        assertEquals(rc.getDescription(), "Rename the specified field");
    }

    @Test
    public void testRenameCommandHandleSuccess() {
        Main.diagram.addField(new Field("test1", 1));
        Main.diagram.addField(new Field("test2", 2));
        Main.diagram.addField(new Field("test3", 3));
        RenameCommand rc = new RenameCommand();
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("rename 0 test4"))).success(), true);
        assertEquals(Main.diagram.getField(0).getName(), "test4");
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("rename 2 test5"))).success(), true);
        assertEquals(Main.diagram.getField(2).getName(), "test5");
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("rename 1 test6"))).success(), true);
        assertEquals(Main.diagram.getField(1).getName(), "test6");
    }

    @Test
    public void testRenameCommandHandleFail() {
        RenameCommand rc = new RenameCommand();
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("rename test"))), HandleResult.TOO_FEW_ARGUMENTS);
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("rename 0 test1 test2"))), HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("rename -1 test1"))), HandleResult.fail("Index start from zero."));
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("rename 0 1"))), HandleResult.fail("New name must be a string."));
        assertEquals(rc.handle(CommandLine.parse(new CodePointBuffer("rename 1 test1"))), HandleResult.fail("Index out of range."));
    }
}
