package com.clayplug.activities.skilling.combat.tasks.actions;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class AttackNpc extends Task {
    private String npcName;

    public AttackNpc(MethodProvider api, String npcName) {
        super(api);
        this.npcName = npcName;
    }

    @Override
    public STATUS run() {
        try {
            api.getNpcs().closest(npcName).interact("Attack");
        } catch (NullPointerException e) {
            return STATUS.FAILED;
        }
        return STATUS.SUCCEEDED;
    }

    @Override
    public void terminate() {

    }
}
