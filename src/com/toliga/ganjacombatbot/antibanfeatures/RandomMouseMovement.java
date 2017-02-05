package com.toliga.ganjacombatbot.antibanfeatures;

import com.toliga.ganjabots.core.AntibanFeature;
import org.dreambot.api.script.AbstractScript;

public class RandomMouseMovement extends AntibanFeature {

    public RandomMouseMovement(float probability) {
        super("RANDOM_MOUSE_MOVEMENT", probability);
    }

    @Override
    public void execute(AbstractScript context) {
        context.getMouse().move();
    }
}
