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
