package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.State;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.wrappers.interactive.NPC;

public class KillMobState implements State {

    private State nextState;
    private boolean interacting = false;
    private NPC npc = null;

    /**
     * Choose a mob that is closest.
     *
     * @param context Running environment.
     * @return Continue to next state or not.
     */
    @Override
    public boolean execute(AbstractScript context) {
        AbstractScript.log("KILL_MOB");

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
        } else if (npc != null && (GlobalSettings.LOOT ? !npc.exists() : npc.getHealthPercent() == 0)) {
            if (GlobalSettings.LOOT) {
                nextState = new TakeLootState();
            } else {
                nextState = new CheckInventoryState();
            }
            return true;
        }

        if (npc != null) {
            if (!interacting) {
                if (npc.interact("Attack")) {
                    AbstractScript.log("Attacking...");
                    interacting = true;
                }
            }

            if (interacting) {
                if (!npc.isInteracting(context.getLocalPlayer()) && npc.isInCombat()) {
                    interacting = false;
                    npc = null;
                }

                if (!context.getMap().canReach(npc)) {
                    interacting = false;
                    AbstractScript.log("Can't reach.");
                }
            }
        }

        if (context.getLocalPlayer().getHealthPercent() < GlobalSettings.HEALTH_PERCENT) {
            int previousHealth = context.getLocalPlayer().getHealthPercent();
            context.getInventory().interact(GlobalSettings.FOOD_NAMES[Calculations.random(GlobalSettings.FOOD_NAMES.length)], "Eat");
            AbstractScript.sleepUntil(() -> context.getLocalPlayer().getHealthPercent() != previousHealth, 2000);
            interacting = false;
        }

        return false;
    }

    @Override
    public State next() {
        return nextState;
    }
}
