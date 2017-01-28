package com.toliga.ganjacombatbot;

import com.toliga.ganjabots.core.State;
import org.dreambot.api.script.AbstractScript;

public class StartState implements State {

    @Override
    public boolean execute(AbstractScript context) {
        return false;
    }

    @Override
    public State next() {
        return null;
    }
}
