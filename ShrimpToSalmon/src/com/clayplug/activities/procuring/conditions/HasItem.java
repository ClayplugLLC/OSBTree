package com.clayplug.activities.procuring.conditions;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class HasItem extends Task {


    String item;

    public HasItem(MethodProvider api, String item) {
        super(api);
        this.item = item;

    }


    @Override
    public STATUS run() {
        if (item != null &&
                !api.getInventory().contains(item)) {
            return STATUS.FAILED;
        } else {
            return STATUS.SUCCEEDED;
        }

    }

    @Override
    public void terminate() {

    }
}
