package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.ViewCommand;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.command.HandleResult;


public class ViewCommandTest {
    @Test
    public void testClearCommandGet() {
        ViewCommand cc = new ViewCommand();
        assertEquals(cc.getName(), "view");
        assertEquals(cc.getUsage(), "");
        assertEquals(cc.getDescription(), "Print out the diagram");
    }

    @Test
    public void testViewCommandHandleSuccess() {
        Main.diagram.addField(new Field("test", 1));
        Main.diagram.addField(new Field("test2", 2));
        assertEquals(2, Main.diagram.size());
        ViewCommand vc = new ViewCommand();
        assertEquals(vc.handle(CommandLine.parse(new CodePointBuffer("view"))).success(), true);
    }

    @Test
    public void testViewCommandHandleFail() {
        ViewCommand vc = new ViewCommand();
        assertEquals(vc.handle(CommandLine.parse(new CodePointBuffer("view test"))), HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(vc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
    }
}
