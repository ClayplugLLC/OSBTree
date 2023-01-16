package com.clayplug.utilities.btree.composites;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

import java.util.ArrayList;
import java.util.Arrays;

public class Composite extends Task {

    protected ArrayList<Task> children;

    public Composite(MethodProvider api) {
        super(api);
    }
    public Composite(MethodProvider api, ArrayList<Task> children) {
        super(api);
        this.children = children;
    }

    public Composite(MethodProvider api, Task[] children) {
        super(api);
        this.children = new ArrayList<>(Arrays.asList(children));
    }

  

    @Override
    public STATUS run() {
        return STATUS.FRESH;
    }

    @Override
    public void terminate() {

    }
}

