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
