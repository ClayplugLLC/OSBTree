package com.clayplug.utilities.btree.decorators;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class UntilFail extends Decorator {

    public UntilFail(MethodProvider api, Task child) {
        super(api, child);
    }

    @Override
    public STATUS run() {
        STATUS status = child.run();
        while (status == STATUS.SUCCEEDED) {
            status = child.run();
        }
        return status;
    }
}

