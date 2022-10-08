package com.jerryio.protocol_diagram;

import com.jerryio.protocol_diagram.token.CodePointBuffer;

import java.util.Scanner;

import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.diagram.Diagram;
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
            if (result == NOT_HANDLED) continue;

            return result.message();
        }

        return "Unknown command \"" + line.name() + "\". Please type \"help\" for more information.";
    }

    public static void doStartScanner() {
        try (Scanner scan = new Scanner(System.in)) {
            while (true) {
                System.out.print("\n> ");

                String feedback = doHandleCommand(scan.nextLine());
                if (feedback != null) {
                    System.out.println(feedback);
                }
            }
        } catch (RuntimeException e) { // Keyboard interrupt or quit command
            System.out.println("\nSee you.");
        }
    }

    public static void main(String[] args) {
        doStartScanner();
    }
}
