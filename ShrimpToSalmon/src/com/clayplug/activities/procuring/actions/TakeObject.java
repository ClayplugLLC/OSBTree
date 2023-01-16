package com.clayplug.activities.procuring.actions;

import com.clayplug.utilities.Sleep;
import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class TakeObject extends Task {
    private final String objectName;

    public TakeObject(MethodProvider api, String objectName) {
        super(api);
        this.objectName = objectName;
    }

    @Override
    public STATUS run() {
        api.objects.objects.closestThatContains(objectName).interact("Take");
        Sleep.sleepUntil(() -> api.getInventory().contains(objectName), 5000);
        return STATUS.SUCCEEDED;
    }

    @Override
    public void terminate() {

    }

}
