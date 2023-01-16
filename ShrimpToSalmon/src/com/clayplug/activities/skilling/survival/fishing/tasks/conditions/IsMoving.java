package com.clayplug.activities.skilling.survival.fishing.tasks.conditions;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class IsMoving extends Task {

    public IsMoving(MethodProvider api) {
        super(api);
    }

    @Override
    public STATUS run() {
        if (api.myPlayer().isMoving() || api.myPlayer().isAnimating()) {
            return STATUS.SUCCEEDED;
        }
        return STATUS.FAILED;
    }

    @Override
    public void terminate() {
    }
}
