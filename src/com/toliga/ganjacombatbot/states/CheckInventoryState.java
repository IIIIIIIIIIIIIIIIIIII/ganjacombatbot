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
import jdk.nashorn.internal.objects.Global;
import org.dreambot.api.script.AbstractScript;

public class CheckInventoryState implements State {

    private State nextState;

    private int countFood(AbstractScript context) {
        int total = 0;

        for (String food : GlobalSettings.FOOD_NAMES) {
            total += context.getInventory().count(food);
        }

        return total;
    }

    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
        if (GlobalSettings.DEBUG) AbstractScript.log("CHECK_INVENTORY");

        if (context.getInventory().isFull()) {
            if (GlobalSettings.BURY_BONES && context.getInventory().contains("Bones")) {
                nextState = new BuryBonesState();
            } else if (GlobalSettings.BANK_WHEN_FULL) {
                if (GlobalSettings.EAT_FOOD && countFood(context) > 0) {
                    GlobalSettings.EAT_FOOD_TAKE_LOOT = false;
                    nextState = new KillMobState();
                } else {
                    GlobalSettings.SOURCE_TILE = context.getLocalPlayer().getTile();
                    if (GlobalSettings.USE_PATH_CREATOR) {
                        nextState = new WalkToBankWithGuidanceState();
                    } else {
                        nextState = new WalkToBankState();
                    }
                }
            } else if (GlobalSettings.LOGOUT_WHEN_FULL) {
                context.getTabs().logout();
                context.stop();
            } else {
                GlobalSettings.LOOT = false;
                GlobalSettings.POWERKILL = true;
                nextState = new KillMobState();
            }
        } else {

            if (GlobalSettings.EAT_FOOD && GlobalSettings.FOOD_AMOUNT > 0) {
                int noFoodCounter = 0;
                for (String food : GlobalSettings.FOOD_NAMES) {
                    if (context.getInventory().count(food) == 0) {
                        noFoodCounter++;
                    }
                }

                if (noFoodCounter == GlobalSettings.FOOD_NAMES.length) {
                    GlobalSettings.SOURCE_TILE = context.getLocalPlayer().getTile();
                    if (GlobalSettings.USE_PATH_CREATOR) {
                        nextState = new WalkToBankWithGuidanceState();
                    } else {
                        nextState = new WalkToBankState();
                    }
                } else {
                    nextState = new KillMobState();
                    if (GlobalSettings.USE_POTION && context.getInventory().contains(item -> item.getName().contains("potion"))) {
                        nextState = new UsePotionState();
                    }
                }
            } else {
                nextState = new KillMobState();
                if (GlobalSettings.USE_POTION && context.getInventory().contains(item -> item.getName().contains("potion"))) {
                    nextState = new UsePotionState();
                }
            }


        }

        return true;
    }

    @Override
    public State next() {
        return nextState;
    }
}
