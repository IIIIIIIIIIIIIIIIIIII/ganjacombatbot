package com.toliga.ganjacombatbot;

import com.toliga.ganjabots.core.AntibanManager;
import com.toliga.ganjabots.core.StateScheduler;
import com.toliga.ganjacombatbot.antibanfeatures.InteractionResponse;
import com.toliga.ganjacombatbot.states.StartState;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.message.Message;

import java.awt.*;

@ScriptManifest(author = "GanjaSmuggler", category = Category.COMBAT, name = "Ganja Combat Bot", description = "", version = 1.0)
public class GanjaCombatBotMain extends AbstractScript implements MessageListener {
    public static final String VERSION = "0.5.0";

    private StateScheduler stateScheduler;
    private BotGUI botGUI;
    private boolean isStarted = false;
    private Timer timer;
    private AntibanManager antibanManager;

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
        antibanManager = new CombatAntibanManager(this);

        antibanManager.addFeature("RANDOM_CAMERA_ROTATION");
        antibanManager.addFeature("RANDOM_MOUSE_MOVEMENT");
        antibanManager.addFeature("RANDOM_TAB_CHECKING");
        antibanManager.addFeature("INTERACTION_RESPONSE");
    }

    @Override
    public int onLoop() {
        if (isStarted) {
            stateScheduler.executeState(antibanManager);
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

    public AntibanManager getAntibanManager() {
        return antibanManager;
    }

    public BotGUI getBotGUI() {
        return botGUI;
    }

    @Override
    public void onGameMessage(Message message) {

    }

    @Override
    public void onPlayerMessage(Message message) {

    }

    @Override
    public void onTradeMessage(Message message) {
        ((InteractionResponse)antibanManager.getFeature("INTERACTION_RESPONSE")).tradeMessageReceived();
    }

    @Override
    public void onPrivateInMessage(Message message) {

    }

    @Override
    public void onPrivateOutMessage(Message message) {

    }
}
