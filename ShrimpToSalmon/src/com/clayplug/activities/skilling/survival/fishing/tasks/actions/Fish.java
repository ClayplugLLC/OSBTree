package com.clayplug.activities.skilling.survival.fishing.tasks.actions;

import com.clayplug.activities.skilling.survival.fishing.setters.Species;
import com.clayplug.utilities.Sleep;
import com.clayplug.utilities.btree.Task;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.osbot.rs07.script.MethodProvider.random;

public class Fish extends Task {
    private static Area fishingSpot;
    private static Area closestBank;
    private static String fishName;
    private static String locationName;
    private static String fishMethod;
    private static String fishTool;
    private static String fishConsumable;
    private static String spotType;


    public Fish(MethodProvider api) {
        super(api);
    }

    public static void setLocationName(String locationName) {
        Fish.locationName = locationName;
    }
    public static String getLocationName() {
        return locationName;
    }

    public static Area getFishingSpot() {
        return fishingSpot;
    }

    public static void setFishingSpot(Area fishingSpot) {
        Fish.fishingSpot = fishingSpot;
    }

    public static Area getClosestBank() {
        return closestBank;
    }

    public static void setFishConsumable(String fishConsumable) {
        Fish.fishConsumable = fishConsumable;
    }

    public static void setSpotType(String spotType) {
        Fish.spotType = spotType;
    }

    public static String getFishName() {
        return fishName;
    }

    public static void setFishName(String fishName) {
        Fish.fishName = fishName;
    }

    public static String getFishMethod() {
        return fishMethod;
    }

    public static void setFishMethod(String fishMethod) {
        Fish.fishMethod = fishMethod;
    }

    public static String getFishTool() {
        return fishTool;
    }

    public static void setFishTool(String fishTool) {
        Fish.fishTool = fishTool;
    }

    public static String getFishConsumable() {
        return fishConsumable;
    }

    public static String getSpotType() {
        return spotType;
    }
    public static void setClosestBank(Area closestBank) { Fish.closestBank = closestBank;}

    @Override
    public STATUS run() {

        api.log("fish running");
        try {
            List<NPC> nearbySpots = api.npcs.getAll()
                        .stream()
                        .filter(npc -> npc.getName().equals(getSpotType()))
                        .collect(Collectors.toList());
            api.log("nearbySpots: " + nearbySpots);

                for (NPC spot : nearbySpots) {
                    if (spot != null) {
                        api.log(spot);
                        spot.interact(getFishMethod());
                        Sleep.sleepUntil(() -> api.myPlayer().isInteracting(spot), 5000);
                        if (api.myPlayer().isAnimating() || api.myPlayer().isMoving()) {
                            return STATUS.RUNNING;
                        }
                    }
                }
                if (!api.myPlayer().isAnimating() || !api.myPlayer().isMoving()) {
                    api.getWalking().myPosition().translate(random(-2, 2), random(-3, 3));
                }
            } catch (Exception e) {
                api.log("No fishing spot found");
                api.log("Fish method: " + getFishMethod());
                api.log("Spot type: " + getSpotType());
                api.getWalking().myPosition().translate(random(-2, 2), random(-3, 3));
                return STATUS.FAILED;
            }
        return STATUS.SUCCEEDED;
    }

    @Override
    public void terminate() {

    }

    public static void goFish(Species species) {
        switch (species) {
            case SHRIMP:
                Fish.setFishName("Raw shrimps");
                Fish.setFishMethod("Net");
                Fish.setFishTool("Small fishing net");
                Fish.setFishConsumable(null);
                Fish.setSpotType("Fishing spot");
                break;
            case SARDINE:
                Fish.fishName = "Raw sardine";
                Fish.fishMethod = "Bait";
                Fish.fishTool = "Fishing rod";
                Fish.fishConsumable = "Fishing bait";
                Fish.spotType = "Bait fishing spot";
                break;
            case GUPPY:
                Fish.fishName = "Raw guppy";
                Fish.fishMethod = "Net";
                Fish.fishTool = "Small fishing net";
                Fish.fishConsumable = null;
                Fish.spotType = "Fishing spot";
                break;
            case HERRING:
                Fish.fishName = "Raw herring";
                Fish.fishMethod = "Bait";
                Fish.fishTool = "Fishing rod";
                Fish.fishConsumable = "Fishing bait";
                Fish.spotType = "Bait fishing spot";
                break;
            case ANCHOVIES:
                Fish.setFishName("Raw anchovies");
                Fish.setFishMethod("Net");
                Fish.setFishTool("Small fishing net");
                Fish.setFishConsumable(null);
                Fish.setSpotType("Fishing spot");
                break;
            case CAVEFISH:
                Fish.fishName = "Raw cavefish";
                break;
            case TROUT:
                Fish.fishName = "Raw trout";
                Fish.fishMethod = "Lure";
                Fish.fishTool = "Fly fishing rod";
                Fish.fishConsumable = "Feather";
                Fish.spotType = "Rod Fishing spot";
                break;
            case PIKE:
                Fish.fishName = "Raw pike";
                Fish.fishMethod = "Bait";
                Fish.fishTool = "Fishing rod";
                Fish.fishConsumable = "Fishing bait";
                Fish.spotType = "Bait Fishing spot";
                break;
            case SALMON:
                Fish.fishName = "Raw salmon";
                Fish.fishMethod = "Lure";
                Fish.fishTool = "Fly fishing rod";
                Fish.fishConsumable = "Feather";
                Fish.spotType = "Rod Fishing spot";
                break;
            case TETRA:
                break;
            case TUNA:
                Fish.fishName = "Raw tuna";
                Fish.fishMethod = "Harpoon";
                Fish.fishTool = "Harpoon";
                Fish.fishConsumable = null;
                Fish.setSpotType("Fishing spot");
                break;
            case LOBSTER:
                Fish.setFishName("Raw lobster");
                Fish.setFishMethod("Cage");
                Fish.setFishTool("Lobster pot");
                Fish.setFishConsumable(null);
                Fish.setSpotType("Cage Fishing spot");
                break;
            case SWORDFISH:
                Fish.setFishName("Raw swordfish");
                Fish.setFishMethod("Harpoon");
                Fish.setFishTool("Harpoon");
                Fish.setFishConsumable(null);
                Fish.setSpotType("Fishing spot");
                break;
            default:
                throw new IllegalStateException("Unexpected species: " + species);
        }
    }

    public static void atLocation(String locationName) {

        HashMap<String, Area> locations = new HashMap<>();

        locations.put("LUMBRIDGE_SWAMP", new Area(3238, 3152, 3244, 3144));
        locations.put("LUMBRIDGE_RIVER", new Area(3238, 3253, 3241, 3240));
        locations.put("AL_KHARID", new Area(3264, 3153, 3279, 3138));
        locations.put("DRAYNOR_VILLAGE", new Area(3080, 3236, 3088, 3223));
        locations.put("PORT_SARIM", new Area(new int[][]{{ 2986, 3177 }, { 2990, 3177 }, { 3000, 3161 }, { 2996, 3152 }}));
        locations.put("MUSA_POINT", new Area(2921, 3180, 2926, 3175));
        locations.put("BARBARIAN_VILLAGE", new Area(new int[][]{{ 3101, 3440 }, { 3112, 3434 }, { 3102, 3421 }, { 3100, 3432 }}));


        switch (locationName) {
            case "AL_KHARID":
                setClosestBank(Banks.AL_KHARID);
                Fish.fishingSpot = locations.get("AL_KHARID");
                setLocationName(locationName);
                break;
            case "BARBARIAN_VILLAGE":
                setClosestBank(Banks.EDGEVILLE);
                Fish.fishingSpot = locations.get("BARBARIAN_VILLAGE");
                setLocationName(locationName);
                break;
            case "DRAYNOR_VILLAGE":
                setClosestBank(Banks.DRAYNOR);
                Fish.fishingSpot = locations.get("DRAYNOR_VILLAGE");
                setLocationName(locationName);
                break;
            case "LUMBRIDGE_SWAMP":
                setClosestBank(Banks.LUMBRIDGE_UPPER);
                Fish.fishingSpot = locations.get("LUMBRIDGE_SWAMP");
                setLocationName(locationName);
                break;
            case "LUMBRIDGE_RIVER":
                setClosestBank(Banks.LUMBRIDGE_UPPER);
                Fish.fishingSpot = locations.get("LUMBRIDGE_RIVER");
                setLocationName(locationName);
                break;
            case "MUSA_POINT":
                setClosestBank(new Area(3042, 3237, 3052, 3234));
                Fish.setFishingSpot(locations.get("MUSA_POINT"));
                setLocationName(locationName);
                break;
            case "PORT_SARIM":
                setClosestBank(new Area(3042, 3237, 3052, 3234));
                Fish.fishingSpot = locations.get("PORT_SARIM");
                setLocationName(locationName);
                break;
            default:
                throw new IllegalStateException("Unexpected location: " + locationName);
        }
    }
}
