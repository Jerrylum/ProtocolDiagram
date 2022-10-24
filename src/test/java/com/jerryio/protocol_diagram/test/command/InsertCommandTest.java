package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.command.commands.InsertCommand;
import com.jerryio.protocol_diagram.diagram.Field;

public class InsertCommandTest {
    @Before
    public void setUp() {
        Main.handler.newDiagram();
    }

    @Test
    public void testInsertCommandHandleSuccess() {
        Field f1 = new Field("test1", 1);
        Field f2 = new Field("test2", 2);
        Field f3 = new Field("test3", 3);
        Main.diagram.addField(f1);
        Main.diagram.addField(f2);
        Main.diagram.addField(f3);
        assertEquals(Main.diagram.getFields().size(), 3);
        InsertCommand ic = new InsertCommand();
        assertTrue(ic.handle(CommandLine.parse(new CodePointBuffer("insert 1 4 test4"))).success());
        assertEquals(4, Main.diagram.getFields().size());
        assertEquals("test1", Main.diagram.getField(0).getName());
        assertEquals("test4", Main.diagram.getField(1).getName());
        assertEquals("test2", Main.diagram.getField(2).getName());
        assertEquals("test3", Main.diagram.getField(3).getName());
        assertTrue(ic.handle(CommandLine.parse(new CodePointBuffer("insert 0 5 test5"))).success());
        assertEquals(5, Main.diagram.getFields().size());
        assertEquals("test5", Main.diagram.getField(0).getName());
        assertEquals("test1", Main.diagram.getField(1).getName());
        assertEquals("test4", Main.diagram.getField(2).getName());
        assertEquals("test2", Main.diagram.getField(3).getName());
        assertEquals("test3", Main.diagram.getField(4).getName());
        assertTrue(ic.handle(CommandLine.parse(new CodePointBuffer("insert 4 6 test6"))).success());
        assertEquals(6, Main.diagram.getFields().size());
        assertEquals("test5", Main.diagram.getField(0).getName());
        assertEquals("test1", Main.diagram.getField(1).getName());
        assertEquals("test4", Main.diagram.getField(2).getName());
        assertEquals("test2", Main.diagram.getField(3).getName());
        assertEquals("test6", Main.diagram.getField(4).getName());
        assertEquals("test3", Main.diagram.getField(5).getName());
    }

    @Test
    public void testInsertCommandHandleFail() {
        Field f1 = new Field("test1", 1);
        Field f2 = new Field("test2", 2);
        Field f3 = new Field("test3", 3);
        Main.diagram.addField(f1);
        Main.diagram.addField(f2);
        Main.diagram.addField(f3);
        InsertCommand ic = new InsertCommand();
        assertEquals(HandleResult.TOO_FEW_ARGUMENTS, ic.handle(CommandLine.parse(new CodePointBuffer("insert"))));
        assertEquals(HandleResult.TOO_FEW_ARGUMENTS, ic.handle(CommandLine.parse(new CodePointBuffer("insert 1"))));
        assertEquals(HandleResult.TOO_FEW_ARGUMENTS, ic.handle(CommandLine.parse(new CodePointBuffer("insert 1 2"))));
        assertEquals(HandleResult.TOO_MANY_ARGUMENTS, ic.handle(CommandLine.parse(new CodePointBuffer("insert 1 2 test3 test4"))));
        assertEquals(HandleResult.NOT_HANDLED, ic.handle(CommandLine.parse(new CodePointBuffer("test"))));
        assertEquals(HandleResult.fail("Index start from zero."), ic.handle(CommandLine.parse(new CodePointBuffer("insert a 2 test1"))));
        assertEquals(HandleResult.fail("Length must be a positive integer."), ic.handle(CommandLine.parse(new CodePointBuffer("insert 0 a test4"))));
        assertEquals(HandleResult.fail("Index start from zero."), ic.handle(CommandLine.parse(new CodePointBuffer("insert -1 2 test1"))));
        assertEquals(HandleResult.fail("Length must be a positive integer."), ic.handle(CommandLine.parse(new CodePointBuffer("insert 0 -1 test4"))));
        assertEquals(HandleResult.fail("Length must be a positive integer."), ic.handle(CommandLine.parse(new CodePointBuffer("insert 0 0 test4"))));
        assertEquals(HandleResult.fail("Name must be a string."), ic.handle(CommandLine.parse(new CodePointBuffer("insert 0 1 1"))));
        assertEquals(HandleResult.fail("Index out of range."), ic.handle(CommandLine.parse(new CodePointBuffer("insert 3 3 test4"))));
    }
}
