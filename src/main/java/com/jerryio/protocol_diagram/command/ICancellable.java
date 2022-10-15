package com.jerryio.protocol_diagram.command;

public interface ICancellable extends IDiagramModifier {
    public void execute();

    // public void undo(); // TODO
}
