package com.toliga.ganjacombatbot;

import com.toliga.ganjabots.core.AntibanFeature;
import com.toliga.ganjabots.core.AntibanManager;
import com.toliga.ganjacombatbot.antibanfeatures.*;
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
        } else if (name.equalsIgnoreCase("random_world_hop")) {
            antibanFeature = new RandomWorldHop(0.5f);
        } else if (name.equalsIgnoreCase("random_tab_checking")) {
            antibanFeature = new RandomTabChecking(0.1f);
        } else if (name.equalsIgnoreCase("interaction_response")) {
            antibanFeature = new InteractionResponse(1f);
        }

        if (antibanFeature != null && GlobalSettings.USE_ANTIBAN) {
            antibanFeature.setEnabled(true);
        }

        return antibanFeature;
    }
}
