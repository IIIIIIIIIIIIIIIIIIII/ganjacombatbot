package com.toliga.ganjacombatbot.antibanfeatures;

import com.toliga.ganjabots.core.AntibanFeature;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;

public class RandomCameraRotation extends AntibanFeature {

    public RandomCameraRotation(float probability) {
        super("RANDOM_CAMERA_ROTATION", probability);
    }

    @Override
    public void execute(AbstractScript context) {
        context.getCamera().rotateTo(Calculations.random(100, 700), Calculations.random(100, 700));
    }
}
