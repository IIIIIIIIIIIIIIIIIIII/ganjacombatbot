package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.AntibanManager;
import com.toliga.ganjabots.core.State;
import com.toliga.ganjacombatbot.GlobalSettings;
import jdk.nashorn.internal.objects.Global;
import org.dreambot.api.script.AbstractScript;

public class CheckInventoryState implements State {

    private State nextState;

    private int countFood(AbstractScript context) {
        int total = 0;

        for (String food : GlobalSettings.FOOD_NAMES) {
            total += context.getInventory().count(food);
        }

        return total;
    }

    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
        if (GlobalSettings.DEBUG) AbstractScript.log("CHECK_INVENTORY");

        if (context.getInventory().isFull()) {
            if (GlobalSettings.BURY_BONES && context.getInventory().contains("Bones")) {
                nextState = new BuryBonesState();
            } else if (GlobalSettings.BANK_WHEN_FULL) {
                if (GlobalSettings.EAT_FOOD && countFood(context) > 0) {
                    GlobalSettings.EAT_FOOD_TAKE_LOOT = false;
                    nextState = new KillMobState();
                } else {
                    GlobalSettings.SOURCE_TILE = context.getLocalPlayer().getTile();
                    if (GlobalSettings.USE_PATH_CREATOR) {
                        nextState = new WalkToBankWithGuidanceState();
                    } else {
                        nextState = new WalkToBankState();
                    }
                }
            } else if (GlobalSettings.LOGOUT_WHEN_FULL) {
                context.getTabs().logout();
                context.stop();
            } else {
                GlobalSettings.LOOT = false;
                GlobalSettings.POWERKILL = true;
                nextState = new KillMobState();
            }
        } else {

            if (GlobalSettings.EAT_FOOD && GlobalSettings.FOOD_AMOUNT > 0) {
                int noFoodCounter = 0;
                for (String food : GlobalSettings.FOOD_NAMES) {
                    if (context.getInventory().count(food) == 0) {
                        noFoodCounter++;
                    }
                }

                if (noFoodCounter == GlobalSettings.FOOD_NAMES.length) {
                    GlobalSettings.SOURCE_TILE = context.getLocalPlayer().getTile();
                    if (GlobalSettings.USE_PATH_CREATOR) {
                        nextState = new WalkToBankWithGuidanceState();
                    } else {
                        nextState = new WalkToBankState();
                    }
                } else {
                    nextState = new KillMobState();
                    if (GlobalSettings.USE_POTION && context.getInventory().contains(item -> item.getName().contains("potion"))) {
                        nextState = new UsePotionState();
                    }
                }
            } else {
                nextState = new KillMobState();
                if (GlobalSettings.USE_POTION && context.getInventory().contains(item -> item.getName().contains("potion"))) {
                    nextState = new UsePotionState();
                }
            }


        }

        return true;
    }

    @Override
    public State next() {
        return nextState;
    }
}
