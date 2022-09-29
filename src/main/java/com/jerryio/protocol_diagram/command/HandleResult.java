package com.jerryio.protocol_diagram.command;

public record HandleResult(boolean success, String message) {
    public static final HandleResult NOT_HANDLED = new HandleResult(false, null);
    public static final HandleResult HANDLED = new HandleResult(true, null);
    public static final HandleResult TOO_FEW_ARGUMENTS = new HandleResult(false, "Too few arguments.");
    public static final HandleResult TOO_MANY_ARGUMENTS = new HandleResult(false, "Too many arguments.");

    public static HandleResult fail(String message) {
        return new HandleResult(false, message);
    }

    public static HandleResult success(String message) {
        return new HandleResult(true, message);
    }
}