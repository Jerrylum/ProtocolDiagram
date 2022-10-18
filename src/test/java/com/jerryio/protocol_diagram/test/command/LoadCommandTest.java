package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.LoadCommand;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;
import com.jerryio.protocol_diagram.util.FileUtils;

public class LoadCommandTest {
    @Before
    public void setUp() throws Exception {
        Main.handler.newDiagram();

        new File("test.txt").delete();
        new File("test.json").delete();

        Diagram d = new Diagram();
        d.addField(new Field("test1", 1));
        d.addField(new Field("test2", 2));
        d.addField(new Field("test3", 3));
        FileUtils.save("test.json", d);
    }

    @After
    public void tearDown() {
        new File("test.txt").delete();
        new File("test.json").delete();
    }

    @Test
    public void testHandle() {
        List<Parameter> params = new ArrayList<Parameter>();
        LoadCommand cmd = new LoadCommand();
        assertFalse(cmd.handle(params).success()); // no file name

        params.add(Parameter.parse(new CodePointBuffer("test.json")));
        params.add(Parameter.parse(new CodePointBuffer("test.json")));
        assertFalse(cmd.handle(params).success()); // too many args

        params.clear();
        params.add(Parameter.parse(new CodePointBuffer("123"))); // not string
        assertFalse(cmd.handle(params).success());

        params.clear();
        params.add(Parameter.parse(new CodePointBuffer("test.json")));
        assertTrue(cmd.handle(params).success());

        Main.handler.setModified(true);

        assertFalse(cmd.handle(params).success()); // file un-save changes

        Main.handler.setModified(false);

        params.clear();
        params.add(Parameter.parse(new CodePointBuffer("test2.json")));
        assertFalse(cmd.handle(params).success()); // no diagram
    }

}
