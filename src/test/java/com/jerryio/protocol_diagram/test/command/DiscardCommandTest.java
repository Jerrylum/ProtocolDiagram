package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.FileSystem;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.DiscardCommand;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class DiscardCommandTest {
    @Before
    public void setUp() throws Exception {
        FileSystem.mountedFile = null;
        Main.diagram = new Diagram();
        new File("test.txt").delete();
        new File("test.json").delete();
        Main.diagram.addField(new Field("test1", 1));
        Main.diagram.addField(new Field("test2", 2));
        Main.diagram.addField(new Field("test3", 3));
    }

    @After
    public void tearDown() {
        new File("test.txt").delete();
        new File("test.json").delete();
    }

    @Test
    public void testHandle() {
        List<Parameter> params = new ArrayList<Parameter>();
        DiscardCommand cmd = new DiscardCommand();
        params.add(Parameter.parse(new CodePointBuffer("test")));
        assertEquals(false, cmd.handle(params).success()); // have args
        params.clear();
        assertEquals(true, cmd.handle(params).success()); // no args
    }
}
