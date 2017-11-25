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

package com.toliga.ganjacombatbot.drawables;

import com.toliga.ganjabots.core.Utilities;
import com.toliga.ganjabots.graphics.Drawable;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class RangeDrawable implements Drawable {

    private AbstractScript context;
    private Image image;

    public RangeDrawable(AbstractScript context) {
        this.context = context;
        image = Utilities.LoadImage("http://www.account4rs.com/images/skill_powerleveling/ranged.png", 15, 15);
    }

    @Override
    public void draw(Graphics2D graphics) {

        graphics.drawImage(image, 320, 350, null);

        graphics.drawString("           XP/hr:", 280, 382);
        graphics.drawString("       XP gained:", 280, 399);
        graphics.drawString("Time to level up:", 280, 416);
        graphics.drawString("   Current Level:", 280, 433);

        if (GlobalSettings.BURY_BONES) {
            graphics.drawString("Prayer XP gained:", 280, 450);
        }

        graphics.drawString(context.getSkillTracker().getGainedExperiencePerHour(Skill.RANGED) + " XP", 420, 382); // XP / hr
        graphics.drawString(context.getSkillTracker().getGainedExperience(Skill.RANGED) + " XP", 420, 399); // XP gained

        long timeToLevelUp = context.getSkillTracker().getTimeToLevel(Skill.RANGED);

        graphics.drawString(String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeToLevelUp),
                TimeUnit.MILLISECONDS.toMinutes(timeToLevelUp) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeToLevelUp)),
                TimeUnit.MILLISECONDS.toSeconds(timeToLevelUp) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeToLevelUp))), 420, 416);

        int realLevel = context.getSkills().getRealLevel(Skill.RANGED);
        int boostedLevel = context.getSkills().getBoostedLevels(Skill.RANGED);
        graphics.drawString(realLevel + " + " + (boostedLevel - realLevel), 420, 433);
    }
}
