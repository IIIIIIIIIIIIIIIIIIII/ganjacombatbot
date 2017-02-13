package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.AntibanManager;
import com.toliga.ganjabots.core.State;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.script.AbstractScript;

public class StartState implements State {

    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
        if (GlobalSettings.DEBUG) AbstractScript.log("START");

        context.getSkillTracker().resetAll();
        context.getSkillTracker().start();

        return true;
    }

    @Override
    public State next() {
        return new CheckInventoryState();
    }
}
