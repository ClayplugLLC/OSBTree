package com.clayplug.utilities;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(name = "Pacer", author = "Clayplug", version = 0.1, info = "Paces", logo = "")
public class Pacer extends Script {


        @Override
        public void onStart() throws InterruptedException {
            log("com.clayplug.services.Pacer started");
        }

        @Override
        public int onLoop() throws InterruptedException {

            pace();

            return random(1000, 10000);

        }

        public void pace() {

            int randXDelta = random(-10, 10);
            int randYDelta = random(-10, 10);

            Position walkTarget = myPosition().translate(randXDelta, randYDelta);

            getWalking().walk(walkTarget);
        }

}
