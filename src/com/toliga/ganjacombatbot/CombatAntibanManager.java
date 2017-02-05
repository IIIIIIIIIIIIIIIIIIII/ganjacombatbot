package com.toliga.ganjacombatbot;

import com.toliga.ganjabots.core.AntibanFeature;
import com.toliga.ganjabots.core.AntibanManager;
import com.toliga.ganjacombatbot.antibanfeatures.RandomCameraRotation;
import com.toliga.ganjacombatbot.antibanfeatures.RandomMouseMovement;
import org.dreambot.api.script.AbstractScript;

public class CombatAntibanManager extends AntibanManager {

    public CombatAntibanManager(AbstractScript context) {
        super(context);
    }

    @Override
    protected AntibanFeature createFeature(String name) {
        AntibanFeature antibanFeature = null;

        if (name.equalsIgnoreCase("random_camera_rotation")) {
            antibanFeature = new RandomCameraRotation(0.05f);
        } else if (name.equalsIgnoreCase("random_mouse_movement")) {
            antibanFeature = new RandomMouseMovement(0.02f);
        }

        if (antibanFeature != null) {
            antibanFeature.setEnabled(true);
        }

        return antibanFeature;
    }
}
