package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.commands.SaveCommand;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class SaveCommandTest {
    @Before
    public void setUp() throws Exception {
        Main.handler.newDiagram();

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
        SaveCommand cmd = new SaveCommand();
        String root = "";
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            root = System.getenv("SystemDrive") + "/test.json";
        else
            root = "/test.json";

        params.add(Parameter.parse(new CodePointBuffer(root)));
        assertFalse(cmd.handle(params).success());

        params.clear();
        assertFalse(cmd.handle(params).success()); // no file name

        params.add(Parameter.parse(new CodePointBuffer("test.json")));
        assertTrue(cmd.handle(params).success()); // save to json

        params.clear();
        assertTrue(cmd.handle(params).success()); // no arg save after save

        params.add(Parameter.parse(new CodePointBuffer("test.json")));
        params.add(Parameter.parse(new CodePointBuffer("test.json")));
        assertFalse(cmd.handle(params).success()); // too many args

        params.clear();
        params.add(Parameter.parse(new CodePointBuffer("test"))); // no .json
        assertTrue(cmd.handle(params).success());

        params.clear();
        params.add(Parameter.parse(new CodePointBuffer("123"))); // not string
        assertFalse(cmd.handle(params).success());
    }
}
