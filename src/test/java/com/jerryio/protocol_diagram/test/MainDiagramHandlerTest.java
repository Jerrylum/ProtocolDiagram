package com.jerryio.protocol_diagram.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.MainDiagramHandler;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;

public class MainDiagramHandlerTest {
    public boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf("win") >= 0);
    }

    @Before
    public void setUp() {
        new File("test.txt").delete();
        new File("test.json").delete();
    }

    @After
    public void tearDown() {
        new File("test.txt").delete();
        new File("test.json").delete();
    }

    @Test
    public void testMainDiagramHandler() {
        MainDiagramHandler test = new MainDiagramHandler();
        assertEquals(Main.diagram, test.getDiagram());
        Diagram d = new Diagram();
        test.setDiagram(d);
        assertEquals(d, Main.diagram);
        assertFalse(test.isModified());
        test.setModified(true);
        assertTrue(test.isModified());

        test = new MainDiagramHandler();
        String root = "/test.json";
        if (isWindows())
            root = System.getenv("SystemDrive") + root;
        assertFalse(test.save(root).success());
        assertFalse(test.save("./").success());
        assertFalse(test.load("./").success());

        assertTrue(test.save("./test.json").success());
        assertTrue(test.load("./test.json").success());
        Main.diagram.addField(new Field("test", 4));
        test.operate(null);
        assertTrue(test.isModified());

        test.resetHistory();
        assertFalse(test.isModified());

        test.setModified(true);
        assertTrue(test.isModified());

        Main.diagram.addField(new Field("test", 4));
        test.operate(null);
        assertTrue(test.isModified());
    }
    
}
