package com.clayplug.utilities;

import java.util.function.BooleanSupplier;
import com.clayplug.utilities.Sleep;

public final class PaceOnce extends Task {


    public PaceOnce(MethodProvider api) {
        super(api);
    }

    @Override
    public STATUS run() {
        int randXDelta = random(-10, 10);
        int randYDelta = random(-10, 10);

        Position walkTarget = myPosition().translate(randXDelta, randYDelta);

        getWalking().walk(walkTarget);

        return STATUS.SUCEEDED;
    }

    @Override
    public void terminate() {}