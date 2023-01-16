package com.clayplug.utilities.btree.composites;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

import java.util.ArrayList;

import static com.clayplug.utilities.btree.Task.STATUS.*;

public class Parallel extends Task {

        protected ArrayList<Task> children;


        public Parallel(MethodProvider api, ArrayList<Task> children) {
            super(api);
            this.children = children;
        }

        @Override
        public STATUS run() {

            // Come back once you understand threads
            for (Task child: children) {
                Thread thread = new Thread();
                thread.start();
                runChild(child);
            }

            return null;
        }

    STATUS runChild(Task child) {

        ArrayList<Task> runningChildren = new ArrayList<>();
            runningChildren.add(child);
            STATUS returned = child.run();
            runningChildren.remove(child);

        STATUS result = FRESH;
        if (returned == FAILED) {
                result = FAILED;
                terminate();
            } else if (returned == RUNNING) {
                result = RUNNING;
            }

            if (runningChildren.size() == 0) {
                result = SUCCEEDED;
            }

        return result;
    }

    @Override
    public void terminate() {
        for (Task child : children) {
            child.terminate();
        }
    }
}



