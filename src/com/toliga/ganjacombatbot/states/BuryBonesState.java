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

public class BuryBonesState implements State {

    private int boneCount;

    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
        if (GlobalSettings.DEBUG) AbstractScript.log("BURY_BONES");
        boneCount = context.getInventory().count("Bones");

        if (context.getInventory().contains("Bones")) {
            context.getInventory().interact("Bones", "Bury");
            AbstractScript.sleepUntil(() -> context.getInventory().count("Bones") < boneCount, 2000);
            AbstractScript.sleep(Calculations.random(600, 680));
            return false;
        }
        return true;
    }

    @Override
    public State next() {
        return new CheckInventoryState();
    }
}
