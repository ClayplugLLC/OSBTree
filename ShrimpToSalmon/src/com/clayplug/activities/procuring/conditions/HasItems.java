package com.clayplug.activities.procuring.conditions;

import org.osbot.rs07.script.MethodProvider;


public class HasItems extends HasItem {

    private final int amount;
    public HasItems(MethodProvider api, String item, int amount) {
        super(api, item);
        this.item = item;
        this.amount = amount;
    }

    @Override
    public STATUS run() {
        if (item != null &&
                !(api.getInventory().getAmount(item) >= amount)) {
            return STATUS.FAILED;
        } else {
            return STATUS.SUCCEEDED;
        }
    }
}
