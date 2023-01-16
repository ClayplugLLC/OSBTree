package com.clayplug.activities.procuring.actions;

import com.clayplug.utilities.Sleep;
import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class TakeGroundItem extends Task {
    private final String itemName;

    public TakeGroundItem(MethodProvider api, String itemName) {
        super(api);
        this.itemName = itemName;
    }

    @Override
    public STATUS run() {
        api.getGroundItems().closest(itemName).interact("Take");
        Sleep.sleepUntil(() -> api.getInventory().contains(itemName), 5000);
        return STATUS.SUCCEEDED;
    }

    @Override
    public void terminate() {

    }

}
