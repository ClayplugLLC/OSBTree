package com.clayplug.utilities.btree.composites;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

import java.util.ArrayList;
import java.util.Arrays;

import static com.clayplug.utilities.btree.Task.STATUS.FAILED;
import static com.clayplug.utilities.btree.Task.STATUS.SUCCEEDED;

public class Sequence extends Composite {

    protected ArrayList<Task> children;

    public Sequence(MethodProvider api, ArrayList<Task> children) {
        super(api, children);
    }

    public Sequence(MethodProvider api, Task... children) {
        super(api);
        this.children = new ArrayList<>(Arrays.asList(children));
    }


    @Override
    public STATUS run() {
        for (Task task : children) {
            if (task.run() == FAILED) {
                return FAILED;
            }
        }
        return SUCCEEDED;
    }
}

