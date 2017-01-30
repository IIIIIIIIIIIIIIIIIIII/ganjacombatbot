package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.State;
import com.toliga.ganjabots.core.Utilities;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.script.AbstractScript;

public class WalkFromBankState implements State {

    @Override
    public boolean execute(AbstractScript context) {
        AbstractScript.log("WALK_FROM_BANK");
        if (!(GlobalSettings.SOURCE_TILE.distance(context.getLocalPlayer().getTile()) < 2)) {
            Utilities.GoToTile(context, GlobalSettings.SOURCE_TILE);
            return false;
        }
        return true;
    }

    @Override
    public State next() {
        return new CheckInventoryState();
    }
}
