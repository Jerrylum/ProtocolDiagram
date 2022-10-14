package com.jerryio.protocol_diagram.test.terminal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.FileSystem;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.diagram.Diagram;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class MainTest {

    private ByteArrayOutputStream out;
    private InputStream defaultIn;
    private PrintStream defaultOut;

    @Before
    public void setup() {
        Main.diagram = new Diagram();
        FileSystem.mountedFile = null;
        FileSystem.isModified = false;

        defaultIn = System.in;

        defaultOut = System.out;
        System.setOut(new java.io.PrintStream(out = new ByteArrayOutputStream()));
    }

    @After
    public void tearDown() {
        System.setIn(defaultIn);
        System.setOut(defaultOut);
    }

    public void setInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    public void testHandleCommand() {
        String invalidFormat = "Usage: <command> [arguments]\nPlease type \"help\" for more information.";
        String unknownCommand = "Unknown command";

        assertEquals(invalidFormat, Main.doHandleCommand(""));
        assertEquals(invalidFormat, Main.doHandleCommand("add '"));
        assertEquals(invalidFormat, Main.doHandleCommand("add 3 '"));

        assertTrue(Main.doHandleCommand("unknown").startsWith(unknownCommand));
        assertTrue(Main.doHandleCommand("unknown 'something'").startsWith(unknownCommand));

        assertTrue(Main.doHandleCommand("add 5 c") != null);

        assertEquals(1, Main.diagram.getFields().size());
    }

    @Test
    public void testHandleSingleLine() {
        assertTrue(Main.doHandleSingleLine("").success());
        assertTrue(!Main.doHandleSingleLine("a").success());
        assertTrue(!Main.doHandleSingleLine("a:").success());
        assertTrue(Main.doHandleSingleLine("a:3").success());
        assertTrue(Main.doHandleSingleLine("a:3,b:4").success());

        assertEquals(3, Main.diagram.getFields().size());
    }

    @Test
    public void testTerminalFailedToContinue() {
        Main.main(new String[] { "unknown" });
        assertTrue(out.toString().startsWith("Failed to load diagram from"));

        out.reset();

        Main.main(new String[] { "unknown", "word" });
        assertTrue(out.toString().startsWith("Only one main parameter allowed but found several"));

        out.reset();

        Main.main(new String[] { "unknown word" });
        assertTrue(out.toString().startsWith("Failed to load diagram from"));

        out.reset();

        Main.main(new String[] { "--unknown" });
        assertTrue(out.toString().startsWith("Failed to load diagram from"));

        out.reset();

        Main.main(new String[] { "--s" });
        assertTrue(out.toString().startsWith("Failed to load diagram from"));
    }

    @Test
    public void testTerminalHelpArgument() {
        Main.main(new String[] { "--help" });
        assertTrue(out.toString().startsWith("Usage: java -jar protocol_diagram.jar [options]"));

        out.reset();

        Main.main(new String[] { "-h" });
        assertTrue(out.toString().startsWith("Usage: java -jar protocol_diagram.jar [options]"));
    }

    @Test
    public void testTerminalSignalLineInputFlag() {
        Main.main(new String[] { "--single-line" });
        assertTrue(out.toString().startsWith("Expected a value after parameter --single-line"));

        out.reset();

        Main.main(new String[] { "-s" });
        assertTrue(out.toString().startsWith("Expected a value after parameter -s"));

        out.reset();

        Main.main(new String[] { "-s", "a" });
        assertTrue(out.toString().startsWith("Invalid single line input"));

        out.reset();

        Main.main(new String[] { "-s", "a:3", "-p" });
    }

    @Test
    public void testTerminalPrintFlag() {
        Main.main(new String[] { "-p" });
        assertTrue(!out.toString().equals(""));
    }

    @Test
    public void testEditorMode() {
        new Main(); // Just for coverage

        setInput("quit");

        Main.main(new String[] {});
        assertTrue(out.toString().startsWith("\n> \nSee you."));

        out.reset();

        setInput("add 3 anything\nquit");

        Main.main(new String[] {});
        assertTrue(out.toString().startsWith("\n> Added field \"anything\".\n\n"));
    }
}
