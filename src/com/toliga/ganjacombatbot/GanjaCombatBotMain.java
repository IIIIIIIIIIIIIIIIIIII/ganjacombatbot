package com.toliga.ganjacombatbot;

import com.toliga.ganjabots.core.StateScheduler;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

@ScriptManifest(author = "GanjaSmuggler", category = Category.COMBAT, name = "Ganja Combat Bot", description = "", version = 1.0)
public class GanjaCombatBotMain extends AbstractScript {
    public static final String VERSION = "0.1.0";

    private StateScheduler stateScheduler;
    private BotGUI botGUI;

    @Override
    public void onStart() {
        super.onStart();
        // GUI initialization
        botGUI = new BotGUI(this, "Ganja Combat Bot");
        botGUI.Display();
        stateScheduler = new StateScheduler(this, new StartState());
    }

    @Override
    public int onLoop() {
        stateScheduler.executeState();
        return Calculations.random(50, 100);
    }

    @Override
    public void onExit() {
        botGUI.Close();
        botGUI.dispose();
        super.onExit();
    }
}
