package com.clayplug.activities.procuring.actions;

import com.clayplug.activities.skilling.survival.fishing.tasks.actions.WalkToSpot;
import com.clayplug.activities.skilling.survival.fishing.tasks.conditions.IsAtSpot;
import com.clayplug.utilities.btree.Task;
import com.clayplug.utilities.btree.composites.Selector;
import com.clayplug.utilities.btree.composites.Sequence;
import com.clayplug.utilities.btree.decorators.Inverter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.script.MethodProvider;

public class BuyToolFromGerrants extends Task {
    private final String tool;
    private final Area gerrantsShop = new Area(
            new int[][]{
                    { 3011, 3229 },
                    { 3017, 3229 },
                    { 3017, 3223 },
                    { 3014, 3220 },
                    { 3011, 3220 }
            }
    );

    public BuyToolFromGerrants(MethodProvider api, String tool) {
        super(api);
        this.tool = tool;
    }

    @Override
    public STATUS run() {

        try {
        Task.STATUS status =
                new Selector(api,
                        new Sequence(api,
                                //If we are not at Gerrant's
                                new Inverter(api, new IsAtSpot(api, gerrantsShop)),
                                //Walk to Gerrant's
                                new WalkToSpot(api, gerrantsShop)
                            ),
                        new Sequence(api,
                                //If we are at Gerrant's
                                new IsAtSpot(api, gerrantsShop),
                                //Buy the tool
                                new BuyTool(api, tool)
                        )
                ).run();
            return status;
        } catch (Exception e) {
            return STATUS.FAILED;
        }

    }

    @Override
    public void terminate() {

    }
}
