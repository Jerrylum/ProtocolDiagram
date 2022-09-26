package com.jerryio.protocol_diagram.command;

public record HandleResult(String message) {
    public static final HandleResult NOT_HANDLED = new HandleResult(null);
    public static final HandleResult TOO_FEW_ARGUMENTS = new HandleResult("Too few arguments.");
    public static final HandleResult TOO_MANY_ARGUMENTS = new HandleResult("Too many arguments.");

    public static HandleResult result(String message) {
        return new HandleResult(message);
    }    
}
