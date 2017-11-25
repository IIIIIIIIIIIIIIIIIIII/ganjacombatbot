/*
Ganja Combat Bot is a fully or semi autonomous bot for the game RuneScape.
Copyright (C) 2016  Tolga Üstünkök

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

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
