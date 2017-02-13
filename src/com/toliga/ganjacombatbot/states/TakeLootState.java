package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.AntibanManager;
import com.toliga.ganjabots.core.State;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.wrappers.items.GroundItem;

import java.util.ArrayList;
import java.util.List;

public class TakeLootState implements State {

    private int currentIndex = 0;
    private List<Integer> previousIndexes = new ArrayList<>(GlobalSettings.LOOT_NAMES.length);
    private int index = 0;
    private int previousLootCount;

    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
        if (GlobalSettings.DEBUG) AbstractScript.log("TAKE_LOOT");
        do {
            currentIndex = Calculations.random(GlobalSettings.LOOT_NAMES.length);
        } while (previousIndexes.contains(currentIndex));
        previousIndexes.add(currentIndex);

        AbstractScript.log("Item name: " + GlobalSettings.LOOT_NAMES[currentIndex]);
        GroundItem currentLoot = context.getGroundItems().closest(item -> item.distance() < 3
                && item.getName().equalsIgnoreCase(GlobalSettings.LOOT_NAMES[currentIndex]));
        previousLootCount = context.getInventory().count(GlobalSettings.LOOT_NAMES[currentIndex]);

        if (currentLoot != null) {
            currentLoot.interact("Take");
            AbstractScript.sleepUntil(() -> previousLootCount < context.getInventory().count(GlobalSettings.LOOT_NAMES[currentIndex]), 3000);
        }
        index++;

        return index >= GlobalSettings.LOOT_NAMES.length;
    }

    @Override
    public State next() {
        return new CheckInventoryState();
    }
}
