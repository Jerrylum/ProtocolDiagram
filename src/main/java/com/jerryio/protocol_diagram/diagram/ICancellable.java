package com.jerryio.protocol_diagram.diagram;

/**
 * this interface is used to distinguish whether the descendant command is allowed to be undo/redo 
 */
public interface ICancellable extends IDiagramModifier {
    /** 
     * the method for invoking cancellable command
     */
    public void execute();
}
