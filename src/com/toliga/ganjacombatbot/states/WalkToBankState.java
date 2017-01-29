package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.State;
import org.dreambot.api.script.AbstractScript;

public class WalkToBankState implements State {

    @Override
    public boolean execute(AbstractScript context) {
        AbstractScript.log("WALK_TO_BANK");
        return true;
    }

    @Override
    public State next() {
        return new StartState();
    }
}
