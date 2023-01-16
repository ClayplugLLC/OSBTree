package com.clayplug.utilities.btree.decorators;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

import static com.clayplug.utilities.btree.Task.STATUS.FRESH;
import static com.clayplug.utilities.btree.Task.STATUS.RUNNING;

public class Interrupter extends Decorator {
    private boolean isRunning = false;

    private STATUS result = FRESH;

    public Interrupter(MethodProvider api, Task child) {
        super(api, child);
    }

    @Override
    public STATUS run() {
        Thread thread = new Thread(() -> runChild());

        while (result == FRESH || result == RUNNING) {
            //insert thread.sleep here
        }

        return result;
    }

    public STATUS runChild() {
        isRunning = true;
        result = child.run();
        isRunning = false;
        return result;
    }

    public void terminate() {
        if (isRunning) {
            child.terminate();
        }
    }

    public STATUS setResult(STATUS desiredResult) {
        result = desiredResult;
        return result;
    }
}
