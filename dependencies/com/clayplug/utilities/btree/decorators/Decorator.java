package com.clayplug.utilities.btree.decorators;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class Decorator extends Task {

    protected Task child;

    public Decorator(MethodProvider api, Task child) {
        super(api);
        this.child = child;
    }

    @Override
    public STATUS run() {
        return child.run();
    }

    @Override
    public void terminate() {

    }
}

