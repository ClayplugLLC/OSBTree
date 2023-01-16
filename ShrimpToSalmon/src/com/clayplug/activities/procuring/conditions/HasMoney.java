package com.clayplug.activities.procuring.conditions;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class HasMoney extends Task {

    private final int amount;
    public HasMoney(MethodProvider api, Integer amount) {
        super(api);
        this.amount = amount;
    }

    @Override
    public STATUS run() {

        if (api.getInventory().getAmount("Coins") >= amount) {
            return STATUS.SUCCEEDED;
        } else {
            return STATUS.FAILED;
        }


    }

    @Override
    public void terminate() {

    }
}
