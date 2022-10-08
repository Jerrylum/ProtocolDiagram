package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.ViewCommand;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.command.HandleResult;

public class ViewCommandTest {
    @Before
    public void setUp() {
        Main.diagram = new Diagram();
    }

    @Test
    public void testViewCommandHandleSuccess() {
        ViewCommand vc = new ViewCommand();

        vc.execute(); // dummy

        Main.diagram.addField(new Field("test", 1));
        Main.diagram.addField(new Field("test2", 2));
        assertEquals(2, Main.diagram.size());

        assertEquals(vc.handle(CommandLine.parse(new CodePointBuffer("view"))).success(), true);
    }

    @Test
    public void testViewCommandHandleFail() {
        ViewCommand vc = new ViewCommand();
        assertEquals(vc.handle(CommandLine.parse(new CodePointBuffer("view test"))), HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(vc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
    }
}
