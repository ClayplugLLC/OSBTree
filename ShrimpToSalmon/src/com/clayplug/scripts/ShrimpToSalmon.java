package com.clayplug.scripts;

import com.clayplug.activities.procuring.actions.BuyToolFromGerrants;
import com.clayplug.activities.procuring.actions.ProcureFeathers;
import com.clayplug.activities.procuring.actions.SellToGerrants;
import com.clayplug.activities.procuring.actions.TakeObject;
import com.clayplug.activities.procuring.conditions.*;
import com.clayplug.activities.skilling.survival.fishing.setters.ChoseToBank;
import com.clayplug.activities.skilling.survival.fishing.setters.ChoseToDrop;
import com.clayplug.activities.skilling.survival.fishing.setters.ChoseToDropOther;
import com.clayplug.activities.skilling.survival.fishing.setters.Species;
import com.clayplug.activities.skilling.survival.fishing.tasks.actions.*;
import com.clayplug.activities.skilling.survival.fishing.tasks.conditions.IsAtSpot;
import com.clayplug.activities.skilling.survival.fishing.tasks.conditions.IsMoving;
import com.clayplug.utilities.btree.Task;
import com.clayplug.utilities.btree.composites.Selector;
import com.clayplug.utilities.btree.composites.Sequence;
import com.clayplug.utilities.btree.decorators.Inverter;
import com.clayplug.utilities.mouse.MouseCursor;
import com.clayplug.utilities.mouse.MouseTrail;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static com.clayplug.activities.skilling.survival.fishing.tasks.actions.Fish.atLocation;
import static com.clayplug.activities.skilling.survival.fishing.tasks.actions.Fish.goFish;

@ScriptManifest(name = "Shrimp To Salmon F2P", author = "Clayplug", version = 0.2, info = "Progressively trains F2P fishing to salmon. Banks/drops as needed.", logo = "https://i.imgur.com/Dzhga1w.jpg")
public class ShrimpToSalmon extends Script {

    private final MouseTrail trail = new MouseTrail(213, 163, 114, 2001);
    private final MouseCursor cursor = new MouseCursor(69, 5, this);

    @Override
    public void onStart() throws InterruptedException {
        log("Fisher started");

        int fishingLevel = skills.getStatic(Skill.FISHING);
        determineFishingStrategy(fishingLevel);

        initializeGUI();
    }

    private void determineFishingStrategy(int fishingLevel) {
        if (fishingLevel < 15) {
            goFish(Species.SHRIMP);
            atLocation("LUMBRIDGE_SWAMP");
        } else if (fishingLevel < 20) {
            goFish(Species.ANCHOVIES);
            atLocation("LUMBRIDGE_SWAMP");
        } else if (fishingLevel < 30) {
            goFish(Species.TROUT);
            atLocation("LUMBRIDGE_RIVER");
        } else {
            goFish(Species.SALMON);
            atLocation("BARBARIAN_VILLAGE");
        }
    }

    private void initializeGUI() {
        JInternalFrame gui = new ShrimpToSalmonGUI(this);
        Arrays.stream(Frame.getFrames())
                .filter(frame -> frame.getTitle().startsWith("OSBot"))
                .findFirst()
                .ifPresent(frame -> {
                    frame.add(gui);
                    gui.setVisible(true);
                    gui.pack();
                });
    }

    @Override
    public void onPaint(Graphics2D g) {
        // Set Mouse
        trail.paint(this, g);
        cursor.paint(g);
        ShrimpToSalmonGUI.update(this);
    }

    @Override
    public int onLoop() throws InterruptedException {
        try {
            Task.STATUS test =
                    new Selector(this,
                            new IsMoving(this),
                            new Selector(this,
                                // If we have inventory space but not tool
                                new Sequence(this,
                                        new HasInventorySpace(this),
                                        new Inverter(this, new HasItem(this, Fish.getFishTool())),
                                        new Selector(this,
                                                // If we have money
                                                new Sequence(this,
                                                    new HasMoney(this, 5),
                                                    //Then buy tool
                                                    new BuyToolFromGerrants(this, Fish.getFishTool())),
                                                // If we don't have money
                                                new Sequence(this,
                                                    new Inverter(this, new HasMoney(this, 5)),
                                                    // Then go to Lumbridge Swamp
                                                    new Selector(this,
                                                        new Sequence(this,
                                                                new Inverter(this, new HasItems(this, "Small fishing net", 5)),
                                                            new WalkToSpot(this, new Area(3241, 3160, 3245, 3154)),
                                                            // Pick up the net
                                                            new TakeObject(this, "Small fishing net"),
                                                                new TakeObject(this, "Small fishing net"),
                                                                new TakeObject(this, "Small fishing net"),
                                                                new TakeObject(this, "Small fishing net"),
                                                                new TakeObject(this, "Small fishing net")
                                                        ),
                                                    // Sell fish
                                                        new Sequence(this,
                                                            new HasItems(this, "Small fishing net", 5),
                                                            new SellToGerrants(this, "Small fishing net", 5)
                                                        )
                                                    )
                                                )
                                        )
                                ),
                                    // If we have inventory space and are at the spot
                                    new Sequence(this,
                                            new Sequence(this,
                                                    new IsAtSpot(this, Fish.getFishingSpot()),
                                                    new HasItem(this, Fish.getFishTool()),
                                                    new HasItem(this, Fish.getFishConsumable()),
                                                    new HasInventorySpace(this)
                                            ),
                                            // Then fish
                                            new Fish(this)
                                    ),
                                // If we have inventory space but not consumables
                                new Sequence(this,
                                        new HasInventorySpace(this),
                                        new HasItem(this, Fish.getFishTool()),
                                        new NotEnoughConsumables(this, Fish.getFishConsumable(),1000),
                                        // Then get consumable
                                        new ProcureFeathers(this)
                                ),


                                // If we have inventory space and tools but are not at the spot
                                new Sequence(this,
                                    new Sequence(this,
                                            new HasInventorySpace(this),
                                            new HasItem(this, Fish.getFishTool()),
                                            new Inverter(this, new NotEnoughConsumables(this, Fish.getFishConsumable(),1000)),
                                            new Inverter(this, new IsAtSpot(this, Fish.getFishingSpot()))
                                    ),
                                    // Then walk to the spot
                                    new WalkToSpot(this, Fish.getFishingSpot())
                                ),
                                // If we do not have inventory space and chose to drop other fish
                                    new Sequence(this,
                                            new Sequence(this,
                                                    new Inverter(this, new HasInventorySpace(this)),
                                                    new Inverter(this, new HasItem(this, Fish.getFishName())),
                                                    new ChoseToDropOther(this)
                                            ),
                                            // Then drop other fish
                                            new DropOtherFish(this)
                                    ),
                                // If we do not have inventory space and are not at the bank
                                new Sequence(this,
                                    new Sequence(this,
                                            new Inverter(this, new HasInventorySpace(this)),
                                            new Inverter(this, new IsAtSpot(this, Fish.getClosestBank())),
                                            new ChoseToBank(this)
                                    ),
                                    // Then walk to the bank
                                    new WalkToSpot(this, Fish.getClosestBank())
                                ),
                                // If we do not have inventory space and are at the bank
                                new Sequence(this,
                                    new Sequence(this,
                                            new IsAtSpot(this, Fish.getClosestBank()),
                                            new Inverter(this, new HasInventorySpace(this)),
                                            new ChoseToBank(this)
                                    ),
                                    // Then deposit inventory
                                    new BankFish(this)
                                ),
                                // If we do not have inventory space and are at the spot and chose to drop
                                    new Sequence(this,
                                            new Sequence(this,
                                                    new Inverter(this, new HasInventorySpace(this)),
                                                    new ChoseToDrop(this)
                                            ),
                                            // Then drop inventory
                                            new DropFish(this)
                                    )

                            )
                    ).run();

        } catch (NullPointerException e) {
            log(Arrays.toString(e.getStackTrace()));
        }

        return random(1000, 9001);
    }


}



