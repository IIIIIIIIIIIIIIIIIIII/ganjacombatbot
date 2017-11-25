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
        } else if (name.equalsIgnoreCase("random_world_hop")) {
            antibanFeature = new RandomWorldHop(1f);
        } else if (name.equalsIgnoreCase("random_logout")) {
            antibanFeature = new RandomLogout(1f);
        }

        if (antibanFeature != null && GlobalSettings.USE_ANTIBAN) {
            antibanFeature.setEnabled(true);
        }

        return antibanFeature;
    }
}
