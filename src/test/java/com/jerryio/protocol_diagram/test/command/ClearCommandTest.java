package com.jerryio.protocol_diagram.test.command;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.ClearCommand;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.token.Parameter;
import com.jerryio.protocol_diagram.command.HandleResult;

import java.util.List;
import java.util.ArrayList;

public class ClearCommandTest {
    @Test
    public void testClearHandleSuccess(){
        Main main = new Main();
        Main.diagram.addField(new Field("test", 1));
        Main.diagram.addField(new Field("test2", 2));
        assertEquals(2, Main.diagram.size());
        ClearCommand cc = new ClearCommand();
        cc.handle(CommandLine.parse(new CodePointBuffer("clear")));
        assertEquals(Main.diagram.size(), 0);
    }
    @Test
    public void testClearHandleFailEnum(){
        ClearCommand cc = new ClearCommand();
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("clear test"))), HandleResult.TOO_MANY_ARGUMENTS);
        assertEquals(cc.handle(CommandLine.parse(new CodePointBuffer("test"))), HandleResult.NOT_HANDLED);
    }
}
