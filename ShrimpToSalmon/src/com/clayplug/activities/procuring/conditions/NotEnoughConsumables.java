package com.clayplug.activities.procuring.conditions;

import com.clayplug.activities.skilling.survival.fishing.tasks.actions.Fish;
import org.osbot.rs07.script.MethodProvider;


public class NotEnoughConsumables extends HasItem {

    private static boolean timeToRestock = false;
    public NotEnoughConsumables(MethodProvider api, String item, int amount) {
        super(api, item);
        this.item = item;
        if (Fish.getFishConsumable() != null && api.getInventory().getAmount(item) == 0) {
            timeToRestock = true;
        } else if (Fish.getFishConsumable() == null) {
            timeToRestock = false;
        } else if (api.getInventory().getAmount(item) > amount) {
            timeToRestock = false;
        }
    }

    @Override
    public STATUS run() {
        if (timeToRestock) {
            return STATUS.SUCCEEDED;
        } else {
            return STATUS.FAILED;
        }
    }
}
