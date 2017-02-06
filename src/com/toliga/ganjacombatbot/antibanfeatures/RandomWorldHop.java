package com.toliga.ganjacombatbot.antibanfeatures;

import com.toliga.ganjabots.core.AntibanFeature;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.utilities.Timer;

public class RandomWorldHop extends AntibanFeature {

    private Timer timer;
    private boolean isMember = false;
    private long hopElapsed;
    private long worldHopTime;
    private int worldHopTimeRandom;

    public RandomWorldHop(float probability) {
        super("RANDOM_WORLD_HOP", probability);
        timer = new Timer();
    }

    @Override
    public void execute(AbstractScript context) {
        worldHopTimeRandom = (int)(worldHopTime * getProbability() * (Calculations.random(2) > 0 ? -1 : 1));
        if (timer.elapsed() - hopElapsed > (worldHopTime + worldHopTimeRandom) * 60 * 1000) {
            World chosenWorld = context.getWorlds().getRandomWorld(world -> {
                int playerTotalLevel = 0;
                int[] levels = context.getClient().getLevels();

                for (int level : levels) {
                    playerTotalLevel += level;
                }
                return (isMember ? world.isMembers() : world.isF2P())
                        && (world.getMinimumLevel() < playerTotalLevel)
                        && !world.isPVP() && !world.isDeadmanMode()
                        && !world.isHighRisk()
                        && !world.isLastManStanding();
            });

            context.getWorldHopper().hopWorld(chosenWorld);
            hopElapsed = timer.elapsed();
        }
    }

    public void setWorldHopTime(long worldHopTime) {
        this.worldHopTime = worldHopTime;
    }
}
