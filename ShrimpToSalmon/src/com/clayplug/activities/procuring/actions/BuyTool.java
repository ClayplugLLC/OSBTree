package com.clayplug.activities.procuring.actions;

import com.clayplug.utilities.Sleep;
import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class BuyTool extends Task {

    private final String tool;
    public BuyTool(MethodProvider api, String tool) {
        super(api);
        this.tool = tool;
    }

    @Override
    public STATUS run() {
        try {
            api.getNpcs().closest("Gerrant").interact("Trade");
        } catch (Exception e) {
            return STATUS.FAILED;
        }
        Sleep.sleepUntil(() -> api.store.isOpen(), 5000);
        try {
            api.store.buy(tool, 1);
        } catch (Exception e) {
            return STATUS.FAILED;
        }
        return STATUS.SUCCEEDED;
    }

    @Override
    public void terminate() {

    }
}
