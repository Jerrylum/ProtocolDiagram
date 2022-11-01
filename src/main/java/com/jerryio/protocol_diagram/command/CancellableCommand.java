package com.jerryio.protocol_diagram.command;

import com.jerryio.protocol_diagram.diagram.ICancellable;

/**
 * an abstract class that extends Command and ICancellable, which acts as an adapter,
 * every commands extends upon this will be recognized as cancellable command
 */
public abstract class CancellableCommand extends Command implements ICancellable {

    public CancellableCommand(String name, String usage, String description) {
        super(name, usage, description);
    }
    
}
