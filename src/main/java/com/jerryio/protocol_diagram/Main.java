package com.jerryio.protocol_diagram;

import com.jerryio.protocol_diagram.token.CodePointBuffer;

import java.util.Scanner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.command.IDiagramModifier;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.*;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class Main {

    public static Diagram diagram = new Diagram();

    public static String doHandleCommand(String input) {
        CodePointBuffer buffer = new CodePointBuffer(input);
        CommandLine line = CommandLine.parse(buffer);

        if (line == null) {
            return "Usage: <command> [arguments]\nPlease type \"help\" for more information.";
        }

        for (Command cmd : Command.getAvailableCommands()) {
            HandleResult result = cmd.handle(line);
            if (result == NOT_HANDLED)
                continue;
            if (result.success()) {
                if (cmd instanceof IDiagramModifier) {
                    FileSystem.isModified = true;
                }
            }

            return result.message();
        }

        return "Unknown command \"" + line.name() + "\". Please type \"help\" for more information.";
    }

    public static HandleResult doHandleSingleLine(String input) {
        OneLineInput line = OneLineInput.parse(new CodePointBuffer(input));

        if (line == null) {
            return fail("Invalid single line input");
        }

        for (Pair<String, Integer> pair : line.params()) {
            diagram.addField(new Field(pair));
        }

        return HANDLED;
    }

    public static void doStartScanner() {
        Scanner scan = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("\n> ");

                System.out.println(doHandleCommand(scan.nextLine()));
            }
        } catch (RuntimeException e) { // Keyboard interrupt or quit command
            System.out.println("\nSee you.");
        }
        scan.close();
    }

    public static void main(String[] argv) {
        TerminalArguments args = new TerminalArguments();
        JCommander cmd = JCommander.newBuilder().addObject(args).build();
        cmd.setProgramName("java -jar protocol_diagram.jar");

        try {
            cmd.parse(argv);
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (args.help) {
            cmd.usage();
            return;
        }

        if (args.singleLine != null) {
            HandleResult result = doHandleSingleLine(args.singleLine);
            if (!result.success()) {
                System.out.println(result.message());
                return;
            }
        } else if (args.source != null) {
            diagram = FileSystem.load(args.source);
            if (diagram == null) {
                System.out.println("Failed to load diagram from " + args.source);
                return;
            }
            FileSystem.mountedFile = args.source;
        }

        if (args.print) {
            System.out.println(diagram);
        } else {
            doStartScanner();
        }
    }
}
