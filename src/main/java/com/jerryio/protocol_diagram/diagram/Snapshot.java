package com.jerryio.protocol_diagram.diagram;

public record Snapshot<T extends ICancellable>(Diagram.Memento origin, T modifier) {
    
}
