package com.jerryio.protocol_diagram.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.FileSystem;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

public class FileSystemTest {
    @Before
    public void setUp() {
        new FileSystem(); // dummy

        new File("test.txt").delete();
        new File("test.json").delete();
    }

    @After
    public void tearDown() {
        new File("test.txt").delete();
        new File("test.json").delete();
    }

    public boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf("win") >= 0);
    }

    public String getFileContent(String path) throws IOException {
        File file = new File(path);
        Scanner sc = new Scanner(file);
        String str = "";
        while (sc.hasNextLine()) {
            str += sc.nextLine();
        }
        sc.close();
        return str;
    }

    @Test
    public void testSaveLoad() throws IOException {
        String root = "/test.json";
        if (isWindows())
            root = System.getenv("SystemDrive") + root;

        assertEquals(false, FileSystem.save(root, new Diagram()).success());
        assertEquals(false, FileSystem.save("./", new Diagram()).success());
        assertEquals(null, FileSystem.load("./"));

        Diagram d1, d2;

        d1 = new Diagram();
        assertEquals(true, FileSystem.save("./test.json", d1).success());
        d2 = FileSystem.load("./test.json");
        assertEquals(d1.toJson(), d2.toJson());

        d1 = new Diagram();
        d1.addField(new Field("test4", 4));
        assertEquals(true, FileSystem.save("./test.json", d1).success());
        d2 = FileSystem.load("./test.json");
        assertEquals(d2.toJson(), d2.toJson());

        d1 = new Diagram();
        d1.addField(new Field("test5", 5));
        assertEquals(true, FileSystem.save("./test.json", d1).success());
        d2 = FileSystem.load("./test.json");
        assertEquals(d1.toJson(), d2.toJson());

        d1 = new Diagram();
        d1.getConfig().setValue("bit", Parameter.parse(new CodePointBuffer("8")));
        assertEquals(true, FileSystem.save("./test.json", d1).success());
        d2 = FileSystem.load("./test.json");
        assertEquals(d1.toJson(), d2.toJson());

        d1 = new Diagram();
        d1.getConfig().setValue("diagram-style", Parameter.parse(new CodePointBuffer("ascii-verbose")));
        assertEquals(true, FileSystem.save("./test.json", d1).success());
        d2 = FileSystem.load("./test.json");
        assertEquals(d1.toJson(), d2.toJson());

        d1 = new Diagram();
        d1.getConfig().setValue("header-style", Parameter.parse(new CodePointBuffer("full")));
        assertEquals(true, FileSystem.save("./test.json", d1).success());
        d2 = FileSystem.load("./test.json");
        assertEquals(d1.toJson(), d2.toJson());

        d1 = new Diagram();
        d1.getConfig().setValue("left-space-placeholder", Parameter.parse(new CodePointBuffer("true")));
        assertEquals(true, FileSystem.save("./test.json", d1).success());
        d2 = FileSystem.load("./test.json");
        assertEquals(d1.toJson(), d2.toJson());

        d1 = new Diagram();
        d1.getConfig().setValue("left-space-placeholder", Parameter.parse(new CodePointBuffer("true")));
        d1.getConfig().setValue("header-style", Parameter.parse(new CodePointBuffer("trim")));
        d1.getConfig().setValue("diagram-style", Parameter.parse(new CodePointBuffer("utf8-corner")));
        d1.getConfig().setValue("bit", Parameter.parse(new CodePointBuffer("16")));
        d1.addField(new Field("test6", 6));
        assertEquals(true, FileSystem.save("./test.json", d1).success());
        d2 = FileSystem.load("./test.json");
        assertEquals(d1.toJson(), d2.toJson());
    }

    @Test
    public void testExport() throws IOException {
        String root = "/test.txt";
        if (isWindows())
            root = System.getenv("SystemDrive") + root;

        assertEquals(false, FileSystem.export(root, new Diagram()).success());
        assertEquals(false, FileSystem.export("./", Main.diagram).success());

        Diagram d1;

        d1 = new Diagram();
        String path = "./test.txt";
        String data;
        assertEquals(true, FileSystem.export("./test.txt", d1).success());
        data = getFileContent(path);
        assertEquals(d1.toString(), data);

        d1 = new Diagram();
        d1.addField(new Field("test4", 4));
        assertEquals(true, FileSystem.export("./test.txt", d1).success());
        data = getFileContent(path);
        assertEquals(d1.toString(), data);

        d1 = new Diagram();
        d1.addField(new Field("test4", 4));
        d1.addField(new Field("test5", 5));
        d1.removeField(0);
        assertEquals(true, FileSystem.export("./test.txt", d1).success());
        data = getFileContent(path);
        assertEquals(d1.toString(), data);

        d1 = new Diagram();
        d1.addField(new Field("test4", 4));
        d1.addField(new Field("test5", 5));
        d1.getConfig().setValue("bit", Parameter.parse(new CodePointBuffer("8")));
        assertEquals(true, FileSystem.export("./test.txt", d1).success());
        data = getFileContent(path);
        assertEquals(d1.toString(), data);

        d1 = new Diagram();
        d1.addField(new Field("test4", 4));
        d1.addField(new Field("test5", 5));
        d1.getConfig().setValue("diagram-style", Parameter.parse(new CodePointBuffer("ascii-verbose")));
        assertEquals(true, FileSystem.export("./test.txt", d1).success());
        data = getFileContent(path);
        assertEquals(d1.toString(), data);

        d1 = new Diagram();
        d1.addField(new Field("test4", 4));
        d1.addField(new Field("test5", 5));
        d1.getConfig().setValue("header-style", Parameter.parse(new CodePointBuffer("full")));
        assertEquals(true, FileSystem.export("./test.txt", d1).success());
        data = getFileContent(path);
        assertEquals(d1.toString(), data);

        d1 = new Diagram();
        d1.addField(new Field("test4", 4));
        d1.addField(new Field("test5", 5));
        d1.getConfig().setValue("left-space-placeholder", Parameter.parse(new CodePointBuffer("true")));
        assertEquals(true, FileSystem.export("./test.txt", d1).success());
        data = getFileContent(path);
        assertEquals(d1.toString(), data);

        d1 = new Diagram();
        d1.addField(new Field("test4", 4));
        d1.addField(new Field("test5", 5));
        d1.getConfig().setValue("left-space-placeholder", Parameter.parse(new CodePointBuffer("true")));
        d1.getConfig().setValue("header-style", Parameter.parse(new CodePointBuffer("trim")));
        d1.getConfig().setValue("diagram-style", Parameter.parse(new CodePointBuffer("utf8-corner")));
        d1.getConfig().setValue("bit", Parameter.parse(new CodePointBuffer("16")));
        assertEquals(true, FileSystem.export("./test.txt", d1).success());
        data = getFileContent(path);
        assertEquals(d1.toString(), data);
    }

    @Test
    public void testResolvePath() {
        assertNull(FileSystem.resolvePath("", "json"));
        assertNull(FileSystem.resolvePath(" ", "json"));
        FileSystem.save("test.json", Main.diagram);
        assertNotNull(FileSystem.resolvePath("test", "json"));
        assertNull(FileSystem.resolvePath("./", "json"));
        if (isWindows())
            assertNull(FileSystem.resolvePath("./ ", "json"));
        else
            assertNull(FileSystem.resolvePath("\u0000", "json")); // only non-visible character is invalid on linux
        assertNull(FileSystem.resolvePath("..", "json"));
        assertNull(FileSystem.resolvePath("../", "json"));
        assertNull(FileSystem.resolvePath("./..", "json"));
        assertNotNull(FileSystem.resolvePath("test", "json"));
    }
}
