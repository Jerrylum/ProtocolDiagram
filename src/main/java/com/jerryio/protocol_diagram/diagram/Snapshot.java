package com.jerryio.protocol_diagram.diagram;

import com.jerryio.protocol_diagram.command.ICancellable;

public record Snapshot(Diagram.Memento origin, ICancellable command) {
    
}
