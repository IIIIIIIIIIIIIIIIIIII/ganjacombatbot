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

package com.toliga.ganjacombatbot.antibanfeatures;

import com.toliga.ganjabots.core.AntibanFeature;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.utilities.Timer;

public class RandomWorldHop extends AntibanFeature {

    private static Timer timer = new Timer();
    private boolean isMember = false;
    private long hopElapsed;

    public RandomWorldHop(float probability) {
        super("RANDOM_WORLD_HOP", probability);
    }

    @Override
    public void execute(AbstractScript context) {
        if (GlobalSettings.WORLD_HOP) {
            if (timer.elapsed() - hopElapsed > GlobalSettings.WORLD_HOP_TIME * 60 * 1000) {
                World chosenWorld = context.getWorlds().getRandomWorld(world -> {
                    int playerTotalLevel = 0;
                    int[] levels = context.getClient().getLevels();

                    for (int level : levels) {
                        playerTotalLevel += level;
                    }
                    return (isMember ? world.isMembers() : world.isF2P())
                            && (world.getMinimumLevel() < playerTotalLevel)
                            && !world.isPVP()
                            && !world.isDeadmanMode()
                            && !world.isHighRisk()
                            && !world.isLastManStanding();
                });

                context.getWorldHopper().hopWorld(chosenWorld);
                hopElapsed = timer.elapsed();
            }
        }
    }
}
