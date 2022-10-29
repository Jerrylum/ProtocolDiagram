package com.jerryio.protocol_diagram;

import com.jerryio.protocol_diagram.token.CodePointBuffer;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.Scanner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.jerryio.protocol_diagram.command.CancellableCommand;
import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.IDiagramModifier;
import com.jerryio.protocol_diagram.token.*;

public class Main {

    public static Diagram diagram = new Diagram();
    public static final MainDiagramHandler handler = new MainDiagramHandler();

    /**
     * a static function that takes one string input, the input would be the form of our defined command,
     * the function return a string object, if the command parsed from the input is valid, then
     * the returned string value will be the processed outcome of the command,
     * else, if no command matches the input, hence no command is executed, the returned value will be our defined string
     * that tells the user no command was found, please type help for more information.
     * @param input
     * @return String
     */
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
                if (cmd instanceof CancellableCommand cb) {
                    // ICancellable modifies the diagram and can be cancelled, it should be added to
                    // timeline.
                    handler.operate(cb);
                } else if (cmd instanceof IDiagramModifier) { // IDiagramModifier but not ICancellable
                    // IDiagramModifier modifies the diagram but cannot be undone, for example,
                    // config changes. It should not be added to timeline. However, it counts as a
                    // modification, so the diagram should be marked as modified.
                    handler.setModified(true);
                }
            }

            return result.message();
        }

        return "Unknown command \"" + line.name() + "\". Please type \"help\" for more information.";
    }

    /**
     * a static function that requires one string input, from the arguments received basically,
     * parse the string that yields multiple fields, foreach field, append them into our single diagram instance
     * @param input
     * @return
     */
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

    /**
     * a static function that instantiates a scanner that receive standard input,
     * interpret every lines issued by the user, and pass the retrieved string to the doHandleCommand function
     */
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

    /**
     * the program entry point, allow three modes of input, arguments, JSON file and in-program scanner.
     * When executing the command from cli, if there the flag of `singleLine` is being specified, then
     * it will allow the input from the cli arguments; else, if the flag of `source` is being specified, then
     * the the program will goto the specified path and read the file, reconstruct the diagram based on the specification,
     * lastly, if the arguments does not specify anything/just leave empty, then the program will handle input
     * from the scanner, and blocking the current cli until the user leave the TUI.
     */
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

        if (args.template != null) {
            args.singleLine = ExistingProtocol.getProtocol(args.template);
            if (args.singleLine == null) {
                System.out.println("Unknown template \"" + args.template + "\"");
                return;
            }
        }

        if (args.singleLine != null) {
            HandleResult result = doHandleSingleLine(args.singleLine);
            if (!result.success()) {
                System.out.println(result.message());
                return;
            }
        } else if (args.source != null) {
            HandleResult result = handler.load(args.source);
            if (!result.success()) {
                System.out.println(result.message());
                return;
            }
        }

        if (args.print) {
            System.out.println(diagram);
        } else {
            doStartScanner();
        }
    }
}
