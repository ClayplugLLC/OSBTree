package com.clayplug.activities.procuring.conditions;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class IsItemVisible extends Task {
    private final String itemName;

    public IsItemVisible(MethodProvider api, String itemName) {
        super(api);
        this.itemName = itemName;
    }

    @Override
    public STATUS run() {
        try {
            if(api.getGroundItems().closest(itemName).isVisible()) {
                return STATUS.SUCCEEDED;
            }
        } catch (NullPointerException e) {
            return STATUS.FAILED;
        }
        return STATUS.FAILED;
    }

    @Override
    public void terminate() {

    }

}
