package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.State;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.script.AbstractScript;

public class CheckInventoryState implements State {

    private State nextState;

    @Override
    public boolean execute(AbstractScript context) {
        AbstractScript.log("CHECK_INVENTORY");

        if (context.getInventory().isFull()) {
            if (GlobalSettings.BURY_BONES) {
                nextState = new BuryBonesState();
            } else if (GlobalSettings.BANK_WHEN_FULL) {
                GlobalSettings.SOURCE_TILE = context.getLocalPlayer().getTile();
                nextState = new WalkToBankState();
            } else if (GlobalSettings.LOGOUT_WHEN_FULL) {
                // TODO: Implement new LogoutState() state.
            } else {
                GlobalSettings.LOOT = false;
            }
        } else {
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
            }
        }

        return true;
    }

    @Override
    public State next() {
        return nextState;
    }
}
