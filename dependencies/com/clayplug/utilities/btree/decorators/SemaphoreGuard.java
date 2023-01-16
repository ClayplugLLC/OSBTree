package com.clayplug.utilities.btree.decorators;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class SemaphoreGuard extends Decorator {

    private int semaphore;

    public SemaphoreGuard(MethodProvider api, Task child, int semaphore) {
        super(api, child);
        this.semaphore = semaphore;
    }

    @Override
    public STATUS run() {
        if (semaphore > 0) {
            semaphore--;
            STATUS status = child.run();
            semaphore++;
            return status;
        } else {
            return STATUS.FAILED;
        }
    }
}