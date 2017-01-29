package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.State;
import org.dreambot.api.script.AbstractScript;

public class StartState implements State {

    @Override
    public boolean execute(AbstractScript context) {
        AbstractScript.log("START");

        context.getSkillTracker().resetAll();
        context.getSkillTracker().start();
        return true;
    }

    @Override
    public State next() {
        return new CheckInventoryState();
    }
}
