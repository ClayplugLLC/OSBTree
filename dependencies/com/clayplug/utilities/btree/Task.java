package com.clayplug.utilities.btree;

import org.osbot.rs07.script.MethodProvider;

/**
 * Created by Clayplug in 2022
 * This interface and its derived classes were heavily influenced by
 * Ian Millington's "AI for Games", Third Edition
 */
public abstract class Task {

    /**
     * The status of a task.
     */
    public enum STATUS {
        FRESH, SUCCEEDED, FAILED, RUNNING, CANCELLED
    }

    protected MethodProvider api;

    public Task(MethodProvider api) {
        this.api = api;
    }

    public abstract STATUS run();

    public abstract void terminate();
}
