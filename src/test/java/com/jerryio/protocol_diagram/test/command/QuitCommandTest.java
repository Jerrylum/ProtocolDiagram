package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.FileSystem;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.command.QuitCommand;

public class QuitCommandTest {

    @Test
    public void testQuitCommandHandleSuccess() {
        QuitCommand qc = new QuitCommand();

        assertThrows(RuntimeException.class, () -> qc.handle(CommandLine.parse(new CodePointBuffer("quit"))));
        assertThrows(RuntimeException.class, () -> qc.handle(CommandLine.parse(new CodePointBuffer("quit any"))));
    
        FileSystem.isModified = true;

        assertEquals(qc.handle(CommandLine.parse(new CodePointBuffer("quit"))).success(), false);
        assertEquals(qc.handle(CommandLine.parse(new CodePointBuffer("quit any"))).success(), false);
        assertThrows(RuntimeException.class, () -> qc.handle(CommandLine.parse(new CodePointBuffer("quit force"))));
    }

    @Test
    public void testQuitCommandHandleFail() {
        QuitCommand qc = new QuitCommand();
        assertEquals(qc.handle(CommandLine.parse(new CodePointBuffer("quit test test"))), HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(qc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
    }
}
