package com.clayplug.activities.skilling.survival.fishing.tasks.actions;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class DropOtherFish extends Task {

    public DropOtherFish(MethodProvider api) {
        super(api);
    }

    public STATUS run() {
        api.getInventory().dropAllExcept(Fish.getFishTool(), Fish.getFishConsumable(), Fish.getFishName(), "Coins");
        return STATUS.SUCCEEDED;
    }

    @Override
    public void terminate() {

    }
}

