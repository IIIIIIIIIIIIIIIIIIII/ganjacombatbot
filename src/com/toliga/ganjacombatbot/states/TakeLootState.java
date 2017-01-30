package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.State;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.wrappers.items.GroundItem;

public class TakeLootState implements State {

    private int index = 0;
    private int previousLootCount;

    @Override
    public boolean execute(AbstractScript context) {
        AbstractScript.log("TAKE_LOOT");
        AbstractScript.log("Item name: " + GlobalSettings.LOOT_NAMES[index]);
        GroundItem currentLoot = context.getGroundItems().closest(item -> item.distance() < 3
                && item.getName().equalsIgnoreCase(GlobalSettings.LOOT_NAMES[index]));
        previousLootCount = context.getInventory().count(GlobalSettings.LOOT_NAMES[index]);

        if (currentLoot != null) {
            currentLoot.interact("Take");
            AbstractScript.sleepUntil(() -> previousLootCount < context.getInventory().count(GlobalSettings.LOOT_NAMES[index]), 3000);
        }
        index++;

        return index >= GlobalSettings.LOOT_NAMES.length;
    }

    @Override
    public State next() {
        return new CheckInventoryState();
    }
}
