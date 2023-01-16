package com.clayplug.activities.procuring.conditions;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class HasInventorySpace extends Task {

    public HasInventorySpace(MethodProvider api) {
        super(api);
    }

    @Override
        public STATUS run() {
            if (api.getInventory().isFull()) {
                return STATUS.FAILED;
            }
            return STATUS.SUCCEEDED;
        }

        @Override
        public void terminate() {

        }
}
