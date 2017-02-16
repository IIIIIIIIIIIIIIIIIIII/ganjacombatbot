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
