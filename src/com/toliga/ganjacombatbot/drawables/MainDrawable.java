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

import com.toliga.ganjabots.graphics.Drawable;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;

import java.awt.*;

public class MainDrawable implements Drawable {

    private AbstractScript context;

    public MainDrawable(AbstractScript context) {
        this.context = context;
    }

    @Override
    public void draw(Graphics2D graphics) {
        int xhAtk = context.getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK),
                xhStr = context.getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH),
                xhDef = context.getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE),
                xhHit = context.getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS),
                xhRan = context.getSkillTracker().getGainedExperiencePerHour(Skill.RANGED),
                xhMag = context.getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC);

        long xgAtk = context.getSkillTracker().getGainedExperience(Skill.ATTACK),
                xgStr = context.getSkillTracker().getGainedExperience(Skill.STRENGTH),
                xgDef = context.getSkillTracker().getGainedExperience(Skill.DEFENCE),
                xgHit = context.getSkillTracker().getGainedExperience(Skill.HITPOINTS),
                xgRan = context.getSkillTracker().getGainedExperience(Skill.RANGED),
                xgMag = context.getSkillTracker().getGainedExperience(Skill.MAGIC);

        graphics.drawString("           XP/hr:", 280, 382);
        graphics.drawString("       XP gained:", 280, 399);

        if (GlobalSettings.BURY_BONES) {
            graphics.drawString("Prayer XP gained:", 280, 416);
        }

        graphics.drawString((xhAtk + xhStr + xhDef + xhHit + xhRan + xhMag) + " XP", 420, 382); // XP / hr
        graphics.drawString((xgAtk + xgStr + xgDef + xgHit + xgRan + xgMag) + " XP", 420, 399); // XP gained
        if (GlobalSettings.BURY_BONES) {
            graphics.drawString(context.getSkillTracker().getGainedExperience(Skill.PRAYER) + " XP", 420, 416); // Prayer XP gained
        }
    }
}
