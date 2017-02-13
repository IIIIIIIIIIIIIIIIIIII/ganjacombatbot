package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.AntibanManager;
import com.toliga.ganjabots.core.State;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;

public class BuryBonesState implements State {

    private int boneCount;

    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
        if (GlobalSettings.DEBUG) AbstractScript.log("BURY_BONES");
        boneCount = context.getInventory().count("Bones");

        if (context.getInventory().contains("Bones")) {
            context.getInventory().interact("Bones", "Bury");
            AbstractScript.sleepUntil(() -> context.getInventory().count("Bones") < boneCount, 2000);
            AbstractScript.sleep(Calculations.random(600, 680));
            return false;
        }
        return true;
    }

    @Override
    public State next() {
        return new CheckInventoryState();
    }
}
