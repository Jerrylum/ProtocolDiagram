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
        assertEquals(Main.diagram.getFields().size(), 4);
        assertEquals(Main.diagram.getField(0).getName(), "test1");
        assertEquals(Main.diagram.getField(1).getName(), "test4");
        assertEquals(Main.diagram.getField(2).getName(), "test2");
        assertEquals(Main.diagram.getField(3).getName(), "test3");
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("insert 0 5 test5"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 5);
        assertEquals(Main.diagram.getField(0).getName(), "test5");
        assertEquals(Main.diagram.getField(1).getName(), "test1");
        assertEquals(Main.diagram.getField(2).getName(), "test4");
        assertEquals(Main.diagram.getField(3).getName(), "test2");
        assertEquals(Main.diagram.getField(4).getName(), "test3");
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("insert 4 6 test6"))).success(), true);
        assertEquals(Main.diagram.getFields().size(), 6);
        assertEquals(Main.diagram.getField(0).getName(), "test5");
        assertEquals(Main.diagram.getField(1).getName(), "test1");
        assertEquals(Main.diagram.getField(2).getName(), "test4");
        assertEquals(Main.diagram.getField(3).getName(), "test2");
        assertEquals(Main.diagram.getField(4).getName(), "test6");
        assertEquals(Main.diagram.getField(5).getName(), "test3");
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
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("insert"))), HandleResult.TOO_FEW_ARGUMENTS);
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("insert 1"))), HandleResult.TOO_FEW_ARGUMENTS);
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("insert 1 2"))), HandleResult.TOO_FEW_ARGUMENTS);
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("insert 1 2 test1 test2"))),
                HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("insert -1 2 test1"))),
                HandleResult.fail("Index start from zero."));
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("insert 0 -1 test4"))),
                HandleResult.fail("Length must be a positive integer."));
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("insert 0 0 test4"))),
                HandleResult.fail("Length must be a positive integer."));
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("insert 0 1 1"))),
                HandleResult.fail("Name must be a string."));
        assertEquals(ic.handle(CommandLine.parse(new CodePointBuffer("insert 3 3 test4"))),
                HandleResult.fail("Index out of range."));
    }
}
