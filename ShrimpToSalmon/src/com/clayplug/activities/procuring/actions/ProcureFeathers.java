package com.clayplug.activities.procuring.actions;


import com.clayplug.activities.procuring.conditions.IsItemVisible;
import com.clayplug.activities.skilling.combat.tasks.actions.AttackNpc;
import com.clayplug.activities.skilling.combat.tasks.conditions.IsUnderAttack;
import com.clayplug.activities.skilling.survival.fishing.tasks.actions.WalkToSpot;
import com.clayplug.activities.skilling.survival.fishing.tasks.conditions.IsAtSpot;
import com.clayplug.activities.skilling.survival.fishing.tasks.conditions.IsMoving;
import com.clayplug.utilities.btree.Task;
import com.clayplug.utilities.btree.composites.Selector;
import com.clayplug.utilities.btree.composites.Sequence;
import com.clayplug.utilities.btree.decorators.Inverter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.script.MethodProvider;

public class ProcureFeathers extends Task {

    Area chickenCoop = new Area(3171, 3301, 3183, 3290);

    public ProcureFeathers(MethodProvider api) {
        super(api);
    }


    @Override
    public STATUS run() {

        STATUS test =
                new Selector(api,
                    // Continue moving if moving
                    new IsMoving(api),
                    // Continue attacking if attacking
                    new IsUnderAttack(api),
                    // If not at spot, move to spot
                    new Sequence(api,
                        new Inverter(api, new IsAtSpot(api, chickenCoop)),
                        new WalkToSpot(api, chickenCoop)
                    ),
                    // If at spot and not under attack, kill chickens
                    new Sequence(api,
                        new IsAtSpot(api, chickenCoop),
                        new Inverter(api, new IsUnderAttack(api)),
                        new Inverter(api, new IsItemVisible(api, "Feather")),
                        new AttackNpc(api, "Chicken")
                    ),
                    // If at spot and feathers visible, take feathers
                    new Sequence(api,
                        new IsAtSpot(api, chickenCoop),
                        new Inverter(api, new IsUnderAttack(api)),
                        new IsItemVisible(api, "Feather"),
                        new TakeGroundItem(api, "Feather")
                    )
                        ).run();

        if (test == STATUS.SUCCEEDED) {
            return STATUS.SUCCEEDED;
        } else {
            return STATUS.FAILED;
        }
    }

    @Override
    public void terminate() {

    }
}
