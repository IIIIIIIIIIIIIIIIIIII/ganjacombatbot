package com.toliga.ganjacombatbot.antibanfeatures;

import com.toliga.ganjabots.core.AntibanFeature;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;

public class InteractionResponse extends AntibanFeature {

    private boolean newTradeMessageReceived = false;
    private String messages[];

    public InteractionResponse(float probability) {
        super("INTERACTION_RESPONSE", probability);
        messages = new String[] {
                "go away",
                "im busy",
                "busy right now",
                "not interested",
                "not now"
        };
    }

    @Override
    public void execute(AbstractScript context) {
        AbstractScript.log("In response");
        if (newTradeMessageReceived) {
            context.getKeyboard().type(messages[Calculations.random(messages.length)]);
            newTradeMessageReceived = false;
        }
    }

    public void tradeMessageReceived() {
        newTradeMessageReceived = true;
    }
}
