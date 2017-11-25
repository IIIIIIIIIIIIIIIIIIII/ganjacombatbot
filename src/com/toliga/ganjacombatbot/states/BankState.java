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
import com.toliga.ganjabots.core.Utilities;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;

public class BankState implements State {

    private int itemIndex = 0;
    private int foodIndex = 0;
    private boolean bankIsOpen = false;
    private boolean getFood = false;
    private boolean finishedFood = false;
    private boolean finishedItem = false;

    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
        if (GlobalSettings.DEBUG) AbstractScript.log("BANK");
        Utilities.OpenTab(context, Tab.INVENTORY);

        if (!getFood) {
            finishedFood = true;
        }

        if (!bankIsOpen && context.getBank().open()) {
            AbstractScript.sleepUntil(() -> context.getBank().isOpen(), 9000);
            bankIsOpen = true;
        }

        if (context.getBank().isOpen()) {
            if (GlobalSettings.BANK_WHEN_FULL && itemIndex < GlobalSettings.LOOT_NAMES.length) {
                String itemName = GlobalSettings.LOOT_NAMES[itemIndex];
                AbstractScript.log("Item name: " + itemName);
                context.getBank().depositAll(itemName);
                AbstractScript.sleepUntil(() -> !context.getInventory().contains(itemName), 3000);
                itemIndex++;
            } else {
                finishedItem = true;
            }

            if (finishedItem && getFood) {
                if (foodIndex < GlobalSettings.FOOD_NAMES.length) {
                    String foodName = GlobalSettings.FOOD_NAMES[foodIndex];
                    AbstractScript.log("Food name: " + foodName);
                    context.getBank().withdraw(foodName, GlobalSettings.FOOD_AMOUNT);
                    AbstractScript.sleepUntil(() -> context.getInventory().contains(foodName), 3000);
                    foodIndex++;
                } else {
                    finishedFood = true;
                }
            }
        }

        if (finishedFood && finishedItem) {
            if (context.getBank().close()) {
                AbstractScript.sleepUntil(() -> !context.getBank().isOpen(), 8000);
                return true;
            }
        }

        return false;
    }

    @Override
    public State next() {
        if (GlobalSettings.USE_PATH_CREATOR) {
            return new WalkFromBankWithGuidanceState();
        }
        return new WalkFromBankState();
    }

    public void setGetFood(boolean getFood) {
        this.getFood = getFood;
    }
}
