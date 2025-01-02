package com.clayplug.utilities.btree.decorators;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class Inverter extends Decorator {

    public Inverter(MethodProvider api, Task child) {
        super(api, child);
    }

    @Override
    public STATUS run() {
        STATUS status = child.run();
        if (status == STATUS.SUCCEEDED) {
            return STATUS.FAILED;
        } else if (status == STATUS.FAILED) {
            return STATUS.SUCCEEDED;
        } else {
            return status;
        }
    }
}
