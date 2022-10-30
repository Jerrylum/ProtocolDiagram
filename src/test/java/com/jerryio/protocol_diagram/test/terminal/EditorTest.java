package com.jerryio.protocol_diagram.test.terminal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.util.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EditorTest {
    private ByteArrayOutputStream out;
    private InputStream defaultIn;
    private PrintStream defaultOut;

    @Before
    public void setup() {
        Main.handler.newDiagram();

        defaultIn = System.in;

        defaultOut = System.out;
        System.setOut(new java.io.PrintStream(out = new ByteArrayOutputStream()));
    }

    @After
    public void tearDown() {
        System.setIn(defaultIn);
        System.setOut(defaultOut);

        new File("test.json").delete();
        new File("test.txt").delete();
    }

    public void setInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    public void test(String name) {
        File file = new File("./src/test/resources/" + name + ".txt");

        try {
            Scanner sc = new Scanner(file, StandardCharsets.UTF_8);

            String in = "";
            String expected_output = "";
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.startsWith("> ")) {
                    in += line.substring(2) + "\n";
                    expected_output += "> "; // No trailing newline
                } else {
                    expected_output += line + "\n";
                }
            }
            sc.close();

            setInput(in);

            Main.main(new String[] {});

            String output = "";
            for (String line : out.toString().replace("\r", "").split("\n")) {
                // More than two space at the end of the line will be trimmed
                output += line.endsWith("  ") ? line.replaceFirst("\\s++$", "") : line;
                output += "\n";
            }

            assertEquals(expected_output, output);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testEditor1() {
        // test help
        test("1");
    }

    @Test
    public void testEditor2() {
        // test add, insert, move
        test("2");
    }

    @Test
    public void testEditor3() {
        // test clear, delete, rename, resize
        test("3");
    }

    @Test
    public void testEditor4() {
        // test list, redo, undo
        test("4");
    }

    @Test
    public void testEditor5_1() {
        // test discard, export, load, save, quit
        test("5_1");
    }

    @Test
    public void testEditor5_2() {
        test("5_2");
    }

    @Test
    public void testEditor5_3() {
        test("5_3");
    }

    @Test
    public void testEditor6_1() {
        // test UTF8 style
        test("6_1");
    }

    @Test
    public void testEditor6_2() {
        // test UTF8 header style
        test("6_2");
    }

    @Test
    public void testEditor6_3() {
        // test UTF8 corner style
        test("6_3");
    }

    @Test
    public void testEditor6_4() {
        // test ascii style
        test("6_4");
    }

    @Test
    public void testEditor6_5() {
        // test ascii-verbose style
        test("6_5");
    }
}
