package com.jerryio.protocol_diagram.command;

/**
 * this record that holds the information of the command handling result,
 * including the state of success and the reason of what driven to this result
 */
public record HandleResult(boolean success, String message) {
    public static final HandleResult NOT_HANDLED = new HandleResult(false, null);
    public static final HandleResult HANDLED = new HandleResult(true, null);
    public static final HandleResult TOO_FEW_ARGUMENTS = new HandleResult(false, "Too few arguments.");
    public static final HandleResult TOO_MANY_ARGUMENTS = new HandleResult(false, "Too many arguments.");

    /**
     * a utility function that assists to instantiate a new failed HandleResult with
     * dynamic message
     * 
     * @param message the message of the failed result
     * @return HandleResult
     */
    public static HandleResult fail(String message) {
        return new HandleResult(false, message);
    }

    /**
     * a utility function that assists to instantiate a new success HandleResult
     * with dynamic message
     * 
     * @param message the message of the success result
     * @return HandleResult
     */
    public static HandleResult success(String message) {
        return new HandleResult(true, message);
    }
}
