package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.AntibanManager;
import com.toliga.ganjabots.core.State;
import com.toliga.ganjacombatbot.FileManager;
import org.dreambot.api.script.AbstractScript;

public class StartState implements State {

    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
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
