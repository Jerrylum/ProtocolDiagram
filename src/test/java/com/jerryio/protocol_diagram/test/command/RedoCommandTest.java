package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.commands.AddCommand;
import com.jerryio.protocol_diagram.command.commands.RedoCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class RedoCommandTest {
    @Test
    public void testRedoCommandHandleSuccess() {
        RedoCommand rc = new RedoCommand();
        List<Parameter> params = new ArrayList<Parameter>();
        AddCommand tic = new AddCommand();
        Main.handler.operate(tic);
        Main.handler.undo();
        assertTrue(rc.handle(params).success());
    }

    @Test
    public void testRedoCommandHandleFail() {
        RedoCommand rc = new RedoCommand();
        List<Parameter> params = new ArrayList<Parameter>();
        assertFalse(rc.handle(params).success());

        params.add(Parameter.parse(new CodePointBuffer("test")));
        assertFalse(rc.handle(params).success());
    }
}
