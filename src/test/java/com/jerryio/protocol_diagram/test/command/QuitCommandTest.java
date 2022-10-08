package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import com.jerryio.protocol_diagram.command.QuitCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.command.HandleResult;

public class QuitCommandTest {

    @Test
    public void testQuitCommandHandleSuccess() {
        QuitCommand qc = new QuitCommand();

        qc.execute(); // dummy

        assertThrows(RuntimeException.class, () -> qc.handle(CommandLine.parse(new CodePointBuffer("quit"))));
    }

    @Test
    public void testQuitCommandHandleFail() {
        QuitCommand qc = new QuitCommand();
        assertEquals(qc.handle(CommandLine.parse(new CodePointBuffer("quit test"))), HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(qc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
    }
}
