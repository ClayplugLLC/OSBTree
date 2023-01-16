package com.clayplug.utilities.btree.decorators;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class Limit extends Decorator {

    private int limit;
    private int runCount;

    public Limit(MethodProvider api, Task child, int limit) {
        super(api, child);
        this.limit = limit;
    }

    @Override
    public STATUS run() {
        if (runCount < limit) {
            runCount++;
            return child.run();
        } else {
            return STATUS.FAILED;
        }
    }
}

