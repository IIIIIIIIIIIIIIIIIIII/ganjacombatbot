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
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.utilities.Timer;

public class RandomLogout extends AntibanFeature {

    private static Timer timer = new Timer();
    private boolean loggedOut = false;
    private long workElapsed;
    private int workTime;
    private int breakTime;

    public RandomLogout(float probability) {
        super("RANDOM_LOGOUT", probability);
    }

    @Override
    public void execute(AbstractScript context) {
        if (GlobalSettings.RANDOM_LOGOUT) {
            if (loggedOut) {
                if (timer.elapsed() - workElapsed > (GlobalSettings.BREAK_TIME == 0 ? 5 : GlobalSettings.BREAK_TIME) * 60 * 1000) {
                    AbstractScript.log("Trying to login.");
                    context.getRandomManager().enableSolver(RandomEvent.LOGIN);
                    loggedOut = false;
                    workElapsed = timer.elapsed();
                }
            } else if (timer.elapsed() - workElapsed > (GlobalSettings.WORK_TIME == 0 ? 5 : GlobalSettings.WORK_TIME) * 60 * 1000) {
                AbstractScript.log("Trying to logout.");
                context.getRandomManager().disableSolver(RandomEvent.LOGIN);
                context.getTabs().logout();
                loggedOut = true;
                workElapsed = timer.elapsed();
            }
        }
    }
}
