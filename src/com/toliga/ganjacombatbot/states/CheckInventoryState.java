package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.AntibanManager;
import com.toliga.ganjabots.core.State;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.script.AbstractScript;

public class CheckInventoryState implements State {

    private State nextState;

    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
        if (GlobalSettings.DEBUG) AbstractScript.log("CHECK_INVENTORY");

        if (context.getInventory().isFull()) {
            if (GlobalSettings.BURY_BONES && context.getInventory().contains("Bones")) {
                nextState = new BuryBonesState();
            } else if (GlobalSettings.BANK_WHEN_FULL) {
                GlobalSettings.SOURCE_TILE = context.getLocalPlayer().getTile();
                nextState = new WalkToBankState();
            } else if (GlobalSettings.LOGOUT_WHEN_FULL) {
                // TODO: Implement new LogoutState() state.
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
                    nextState = new WalkToBankState();
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
