package com.clayplug.utilities.btree.decorators;

import com.clayplug.utilities.btree.Semaphore;

import java.util.HashMap;

public class SemaphoreHashmap {

    private final HashMap<String, Semaphore> semaphores = new HashMap<>();
    public Semaphore getSemaphore(String name, int maxPermits) {
        if (semaphores.containsKey(name)) {
            return (Semaphore) semaphores.get(name);
        } else {
            Semaphore semaphore = new Semaphore(maxPermits);
            semaphores.put(name, semaphore);
            return semaphore;
        }
    }
}