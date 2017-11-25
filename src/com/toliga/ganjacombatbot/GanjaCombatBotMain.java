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
import org.dreambot.api.wrappers.widgets.message.Message;

import java.awt.*;

@ScriptManifest(author = "GanjaSmuggler", category = Category.COMBAT, name = "Ganja Combat Bot", description = "", version = 1.0)
public class GanjaCombatBotMain extends AbstractScript implements MessageListener {
    public static final String VERSION = "0.7.0";

    private StateScheduler stateScheduler;
    private BotGUI botGUI;
    private boolean isStarted = false;
    private AntibanManager antibanManager;

    @Override
    public void onStart() {
        super.onStart();
        botGUI = new BotGUI(this, "Ganja Combat Bot");
        stateScheduler = new StateScheduler(this, new StartState());
        antibanManager = new CombatAntibanManager(this);

        antibanManager.addFeature("RANDOM_CAMERA_ROTATION");
        antibanManager.addFeature("RANDOM_MOUSE_MOVEMENT");
        antibanManager.addFeature("RANDOM_TAB_CHECKING");
        antibanManager.addFeature("INTERACTION_RESPONSE");
        antibanManager.addFeature("RANDOM_WORLD_HOP");
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
    public void onPaint(Graphics graphics2D) {
        if (isStarted) {
            botGUI.DrawInGameGUI((Graphics2D) graphics2D);
        }
        super.onPaint(graphics2D);
    }

    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public boolean isStarted() {
        return isStarted;
    }

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
