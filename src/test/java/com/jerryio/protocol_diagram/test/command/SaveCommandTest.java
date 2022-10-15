package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.FileSystem;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.SaveCommand;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class SaveCommandTest {
    @Before
    public void setUp() throws Exception {
        FileSystem.mountedFile = null;
        Main.diagram = new Diagram();
        File ExpObj = new File("test.txt");
        File JsonObj = new File("test.json");
        if (ExpObj.exists()) {
            ExpObj.delete();
        }
        if (JsonObj.exists()) {
            JsonObj.delete();
        }
        Main.diagram.addField(new Field("test1", 1));
        Main.diagram.addField(new Field("test2", 2));
        Main.diagram.addField(new Field("test3", 3));
    }

    @Test
    public void testHandle() {
        List<Parameter> params = new ArrayList<Parameter>();
        SaveCommand cmd = new SaveCommand();
        assertEquals(false, cmd.handle(params).success()); // no file name
        params.add(Parameter.parse(new CodePointBuffer("test.json")));
        assertEquals(true, cmd.handle(params).success()); // save to json
        params.clear();
        assertEquals(true, cmd.handle(params).success()); //no arg save after save
        params.add(Parameter.parse(new CodePointBuffer("test.json")));
        params.add(Parameter.parse(new CodePointBuffer("test.json")));
        assertEquals(false, cmd.handle(params).success()); //too many args
        params.clear();
        params.add(Parameter.parse(new CodePointBuffer("test")));//no .json
        assertEquals(true, cmd.handle(params).success());
        params.clear();
        params.add(Parameter.parse(new CodePointBuffer("123")));//not string
        assertEquals(false, cmd.handle(params).success());
        // params.add(Parameter.parse(new CodePointBuffer("./")));// dir
        // assertEquals(false, cmd.handle(params).success());
    }
}
