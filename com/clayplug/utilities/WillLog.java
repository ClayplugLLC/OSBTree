package com.clayplug.utilities;

import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.script.MethodProvider;

public class WillLog extends Task {
    private final String logMessage;

    public WillLog(MethodProvider api, String logMessage) {
        super(api);
        this.logMessage = logMessage;
    }

    @Override
    public STATUS run() {
       api.log(logMessage);
        return STATUS.SUCCEEDED;
    }

    @Override
    public void terminate() {

    }
}
