package com.jerryio.protocol_diagram.command;

import com.jerryio.protocol_diagram.diagram.ICancellable;

public abstract class CancellableCommand extends Command implements ICancellable {

    public CancellableCommand(String name, String usage, String description) {
        super(name, usage, description);
    }
    
}
