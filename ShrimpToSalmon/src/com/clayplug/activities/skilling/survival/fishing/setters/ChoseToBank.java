package com.clayplug.activities.skilling.survival.fishing.setters;

import com.clayplug.scripts.ShrimpToSalmonGUI;
import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class ChoseToBank extends Task {

    public ChoseToBank(MethodProvider api) {
        super(api);
    }
    @Override
    public STATUS run() {
        if (ShrimpToSalmonGUI.getChoseToBank()) {
            return STATUS.SUCCEEDED;
        } else {
            return STATUS.FAILED;
        }
    }

    @Override
    public void terminate() {

    }
}
