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
