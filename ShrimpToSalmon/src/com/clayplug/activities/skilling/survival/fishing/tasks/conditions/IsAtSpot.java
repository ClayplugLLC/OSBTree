package com.clayplug.activities.skilling.survival.fishing.tasks.conditions;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.script.MethodProvider;

public class IsAtSpot extends Task {

    private final Area area;

    public IsAtSpot(MethodProvider api, Area area) {
        super(api);
        this.area = area;
    }

    @Override
        public STATUS run() {
        if (area.contains(api.myPosition())) {
                return STATUS.SUCCEEDED;
            } else {
                return STATUS.FAILED;
            }
        }

        @Override
        public void terminate() {

        }
}
