package com.clayplug.activities.skilling.survival.fishing.tasks.actions;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

import static org.osbot.rs07.script.MethodProvider.*;

public class BankFish extends Task {
    public BankFish(MethodProvider api) {
        super(api);
    }

    @Override
    public STATUS run() {
                // Deposit
            if (api.getNpcs().closest("Banker") != null) {
                try {
                    api.getBank().open();
                } catch (InterruptedException e) {
                    api.log(e);
                    return STATUS.FAILED;
                }
                try {
                    sleep(random(373,789));
                } catch (InterruptedException e) {
                    api.log(e);
                    return STATUS.FAILED;
                }
                api.getBank().depositAllExcept(Fish.getFishTool(), Fish.getFishConsumable());
                api.getBank().close();
            } else {
                api.getDepositBox().open();
                try {
                    sleep(random(373, 789));
                } catch (InterruptedException e) {
                    api.log(e);
                    return STATUS.FAILED;
                }
                api.getDepositBox().depositAllExcept(Fish.getFishTool(), "Coins", Fish.getFishConsumable());
                api.getDepositBox().close();
            }
        return STATUS.SUCCEEDED;
    }


    @Override
    public void terminate() {

    }
}
