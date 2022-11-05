package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.commands.AddCommand;
import com.jerryio.protocol_diagram.command.commands.UndoCommand;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class UndoCommandTest {
    
    @Test
    public void testUndoCommandHandleSuccess() {
        UndoCommand uc = new UndoCommand();
        List<Parameter> params = new ArrayList<Parameter>();
        AddCommand tic = new AddCommand();
        Main.handler.operate(tic);
        assertTrue(uc.handle(params).success());
    }

    @Test
    public void testUndoCommandHandleFail() {
        UndoCommand uc = new UndoCommand();
        List<Parameter> params = new ArrayList<Parameter>();
        assertFalse(uc.handle(params).success());

        params.add(Parameter.parse(new CodePointBuffer("test")));
        assertFalse(uc.handle(params).success());
    }
}
