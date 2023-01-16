package com.clayplug.activities.skilling.survival.fishing.setters;

import com.clayplug.scripts.ShrimpToSalmonGUI;
import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class ChoseToDrop extends Task {

    public ChoseToDrop(MethodProvider api) {
        super(api);
    }
    @Override
    public STATUS run() {
        if (ShrimpToSalmonGUI.getChoseToDrop()) {
            return STATUS.SUCCEEDED;
        } else {
            return STATUS.FAILED;
        }
    }

    @Override
    public void terminate() {

    }
}
