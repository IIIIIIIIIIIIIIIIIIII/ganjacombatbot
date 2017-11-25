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

package com.toliga.ganjacombatbot.rules;

import com.toliga.ganjabots.core.ValidationRule;

public class FeedbackTextValidator implements ValidationRule {

    @Override
    public String validate(String validateData) {

        if (validateData.isEmpty()
                || validateData.startsWith(" ")
                || validateData.endsWith(" ")) {
            throw new IllegalArgumentException("Please check the feedback form again.");
        }

        return validateData;
    }
}
