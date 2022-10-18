package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.command.QuitCommand;

public class QuitCommandTest {
    @Before
    public void setUp() {
        Main.handler.newDiagram();
    }

    @Test
    public void testQuitCommandHandleSuccess() {
        QuitCommand qc = new QuitCommand();

        Main.handler.setModified(false);

        assertThrows(RuntimeException.class, () -> qc.handle(CommandLine.parse(new CodePointBuffer("quit"))));
        assertThrows(RuntimeException.class, () -> qc.handle(CommandLine.parse(new CodePointBuffer("quit any"))));
    
        Main.handler.setModified(true);

        assertFalse(qc.handle(CommandLine.parse(new CodePointBuffer("quit"))).success());
        assertFalse(qc.handle(CommandLine.parse(new CodePointBuffer("quit any"))).success());
        assertFalse(qc.handle(CommandLine.parse(new CodePointBuffer("quit 123"))).success());
        assertThrows(RuntimeException.class, () -> qc.handle(CommandLine.parse(new CodePointBuffer("quit force"))));
        
        Main.handler.setModified(false);
    }

    @Test
    public void testQuitCommandHandleFail() {
        QuitCommand qc = new QuitCommand();
        assertEquals(HandleResult.TOO_MANY_ARGUMENTS, qc.handle(CommandLine.parse(new CodePointBuffer("quit test test"))));
        assertEquals(HandleResult.NOT_HANDLED, qc.handle(CommandLine.parse(new CodePointBuffer("test"))));
    }
}
