package com.toliga.ganjacombatbot;

import com.toliga.ganjabots.core.StateScheduler;
import com.toliga.ganjacombatbot.states.StartState;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;

import java.awt.*;

@ScriptManifest(author = "GanjaSmuggler", category = Category.COMBAT, name = "Ganja Combat Bot", description = "", version = 1.0)
public class GanjaCombatBotMain extends AbstractScript {
    public static final String VERSION = "0.4.0";

    private StateScheduler stateScheduler;
    private BotGUI botGUI;
    private boolean isStarted = false;
    private Timer timer;

    @Override
    public void onStart() {
        super.onStart();
        timer = new Timer();
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            SaveManager saveManager = new SaveManager();
            saveManager.load();
        }
        botGUI = new BotGUI(this, "Ganja Combat Bot");
        stateScheduler = new StateScheduler(this, new StartState());
    }

    @Override
    public int onLoop() {
        if (isStarted) {
            stateScheduler.executeState();
        }
        return Calculations.random(50, 100);
    }

    @Override
    public void onExit() {
        botGUI.setVisible(false);
        botGUI.dispose();
        super.onExit();
    }

    @Override
    public void onPaint(Graphics2D graphics2D) {
        if (isStarted) {
            botGUI.DrawInGameGUI(graphics2D);
        }
        super.onPaint(graphics2D);
    }

    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public Timer getTimer() { return timer; }
}
