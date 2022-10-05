package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.command.InsertCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;


public class InsertCommandTest {
    @Before
    public void setUp() {
        Main.diagram = new Diagram();
    }

    @Test
    public void testInsertCommandGet() {
        InsertCommand ic = new InsertCommand();
        assertEquals(ic.getName(), "insert");
        assertEquals(ic.getUsage(), "<index> <length> <name>");
        assertEquals(ic.getDescription(), "Insert a field at the given index");
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
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("insert 1 4 test4"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 2);
        assertEquals(Main.diagram.getField(0), f1);
        assertEquals(Main.diagram.getField(1), f3);
        Main.diagram.addField(f2);
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("delete 2"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 2);
        assertEquals(Main.diagram.getField(0), f1);
        assertEquals(Main.diagram.getField(1), f3);
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("delete 0"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 1);
        assertEquals(Main.diagram.getField(0), f3);
    }

    @Test
    public void testInsertCommandHandleFail() {
        InsertCommand ic = new InsertCommand();
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("delete"))), HandleResult.TOO_FEW_ARGUMENTS);
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("delete 1 2"))), HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("delete -1"))), HandleResult.fail("Index start from zero."));
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("delete 1"))), HandleResult.fail("Index out of range."));
    }
}
