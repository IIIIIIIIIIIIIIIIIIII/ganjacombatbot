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
import org.dreambot.api.wrappers.interactive.NPC;

public class KillMobState implements State {

    private static int standingStill;
    private State nextState;
    private boolean interacting = false;
    private NPC npc = null;

    /**
     * Choose a mob that is closest and attack it.
     *
     * @param context Running environment.
     * @return Continue to next state or not.
     */
    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
        if (GlobalSettings.DEBUG) AbstractScript.log("KILL_MOB");

        if (!interacting) {
            if (context.getLocalPlayer().isInteractedWith()) {
                npc = context.getNpcs().closest((npc) -> npc.isInteracting(context.getLocalPlayer()));
            } else {
                npc = context.getNpcs().closest((npc) -> npc.getName().equalsIgnoreCase(GlobalSettings.MOB_NAMES[Calculations.random(GlobalSettings.MOB_NAMES.length)])
                        && npc.canAttack()
                        && context.getMap().canReach(npc)
                        && !npc.isInCombat()
                        && !npc.isInteractedWith()
                        && npc.exists());
            }
            if (npc != null) {
                AbstractScript.log("Chosen: " + npc.getName());
            }
        } else if (npc != null && npc.getHealthPercent() == 0) {
            if (GlobalSettings.LOOT && GlobalSettings.EAT_FOOD_TAKE_LOOT) {
                nextState = new TakeLootState();
            } else {
                nextState = new CheckInventoryState();
            }
            return true;
        }

        if (context.getWalking().getRunEnergy() >= 80) {
            if (!context.getWalking().isRunEnabled())
                context.getWalking().toggleRun();
        }

        antibanManager.runFeatures();

        if (npc != null) {
            if (!interacting) {
                if (npc.interact("Attack")) {
                    AbstractScript.log("Attacking...");
                    standingStill = 0;
                    interacting = true;
                }
            }

            if (interacting) {
                if (isStandingStill(context)) {
                    interacting = false;
                    AbstractScript.log("Standing still.");
                }

                if (!context.getMap().canReach(npc)) {
                    interacting = false;
                    AbstractScript.log("Can't reach.");
                }

                if (!npc.isInteracting(context.getLocalPlayer()) && npc.isInCombat()) {
                    interacting = false;
                    npc = null;
                }
            }
        }

        if (context.getLocalPlayer().getHealthPercent() < GlobalSettings.HEALTH_PERCENT) {
            int previousHealth = context.getLocalPlayer().getHealthPercent();
            context.getInventory().interact(GlobalSettings.FOOD_NAMES[Calculations.random(GlobalSettings.FOOD_NAMES.length)], "Eat");
            AbstractScript.sleepUntil(() -> context.getLocalPlayer().getHealthPercent() > previousHealth, 2000);
            npc.interact("Attack");
        }

        return false;
    }

    @Override
    public State next() {
        return nextState;
    }

    private boolean isStandingStill(AbstractScript context) {

        if (!context.getLocalPlayer().isAnimating()) {
            standingStill++;
        } else {
            standingStill = 0;
        }

        return standingStill > 35;
    }
}
