package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.State;
import org.dreambot.api.script.AbstractScript;

public class BuryBonesState implements State {

    private int boneCount;

    @Override
    public boolean execute(AbstractScript context) {
        AbstractScript.log("BURY_BONES");
        boneCount = context.getInventory().count("Bones");

        if (context.getInventory().contains("Bones")) {
            context.getInventory().interact("Bones", "Bury");
            AbstractScript.sleepUntil(() -> context.getInventory().count("Bones") < boneCount, 2000);
            return false;
        }
        return true;
    }

    @Override
    public State next() {
        return new CheckInventoryState();
    }
}
