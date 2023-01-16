package com.clayplug.activities.skilling.combat.tasks.conditions;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class IsUnderAttack extends Task {

    public IsUnderAttack(MethodProvider api) {
        super(api);
    }

    @Override
    public Task.STATUS run() {
        if (api.myPlayer().isUnderAttack()) {
            return Task.STATUS.SUCCEEDED;
        } else {
            return Task.STATUS.FAILED;
        }
    }

    @Override
    public void terminate() {

    }
}
