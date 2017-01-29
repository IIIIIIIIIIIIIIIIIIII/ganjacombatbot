package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.State;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.wrappers.items.GroundItem;

public class TakeLootState implements State {

    private int index = 0;

    @Override
    public boolean execute(AbstractScript context) {
        AbstractScript.log("TAKE_LOOT");
        GroundItem currentLoot = context.getGroundItems().closest(item -> {
            // TODO: Implement edilecek
        });

        currentLoot.interact("Take");

        if (index >= GlobalSettings.LOOT_NAMES.length) {
            return true;
        }
        return false;
    }

    @Override
    public State next() {
        return new CheckInventoryState();
    }
}
