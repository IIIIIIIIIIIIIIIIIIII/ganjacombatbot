package com.toliga.ganjacombatbot.antibanfeatures;

import com.toliga.ganjabots.core.AntibanFeature;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;

public class InteractionResponse extends AntibanFeature {

    private boolean newTradeMessageReceived = false;
    private String tradeMessages[];

    public InteractionResponse(float probability) {
        super("INTERACTION_RESPONSE", probability);
        tradeMessages = new String[] {
                "go away",
                "im busy",
                "busy right now",
                "not interested",
                "not now"
        };
    }

    @Override
    public void execute(AbstractScript context) {
        if (GlobalSettings.INTERACTION_RESPONSE) {
            AbstractScript.log("In response");
            if (newTradeMessageReceived) {
                context.getKeyboard().type(tradeMessages[Calculations.random(tradeMessages.length)]);
                newTradeMessageReceived = false;
            }
        }
    }

    public void tradeMessageReceived() {
        newTradeMessageReceived = true;
    }
}
