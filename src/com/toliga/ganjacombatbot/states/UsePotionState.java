package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.AntibanManager;
import com.toliga.ganjabots.core.State;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;

public class UsePotionState implements State {
    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
        if (GlobalSettings.DEBUG) AbstractScript.log("USE_POTION");

        if (GlobalSettings.STRENGTH_POTION && context.getSkills().getBoostedLevels(Skill.STRENGTH) == context.getSkills().getRealLevel(Skill.STRENGTH)) {
            AbstractScript.log("Try to drink strength potion.");
            drinkPotion(context, "Strength potion");
        }

        if (GlobalSettings.ATTACK_POTION && context.getSkills().getBoostedLevels(Skill.ATTACK) == context.getSkills().getRealLevel(Skill.ATTACK)) {
            AbstractScript.log("Try to drink attack potion.");
            drinkPotion(context, "Attack potion");
        }

        if (GlobalSettings.DEFENCE_POTION && context.getSkills().getBoostedLevels(Skill.DEFENCE) == context.getSkills().getRealLevel(Skill.DEFENCE)) {
            AbstractScript.log("Try to drink defence potion.");
            drinkPotion(context, "Defence potion");
        }

        if (GlobalSettings.COMBAT_POTION && (context.getSkills().getBoostedLevels(Skill.STRENGTH) == context.getSkills().getRealLevel(Skill.STRENGTH)
                || context.getSkills().getBoostedLevels(Skill.ATTACK) == context.getSkills().getRealLevel(Skill.ATTACK))) {
            AbstractScript.log("Try to drink combat potion.");
            drinkPotion(context, "Combat potion");
        }

        if (GlobalSettings.SUPER_STRENGTH_POTION && context.getSkills().getBoostedLevels(Skill.STRENGTH) == context.getSkills().getRealLevel(Skill.STRENGTH)) {
            AbstractScript.log("Try to drink super strength potion.");
            drinkPotion(context, "Super strength");
        }

        if (GlobalSettings.SUPER_ATTACK_POTION && context.getSkills().getBoostedLevels(Skill.ATTACK) == context.getSkills().getRealLevel(Skill.ATTACK)) {
            AbstractScript.log("Try to drink super attack potion.");
            drinkPotion(context, "Super attack");
        }

        if (GlobalSettings.SUPER_DEFENCE_POTION && context.getSkills().getBoostedLevels(Skill.DEFENCE) == context.getSkills().getRealLevel(Skill.DEFENCE)) {
            AbstractScript.log("Try to drink super defence potion.");
            drinkPotion(context, "Super defence");
        }

        if (GlobalSettings.SUPER_COMBAT_POTION && (context.getSkills().getBoostedLevels(Skill.STRENGTH) == context.getSkills().getRealLevel(Skill.STRENGTH)
                || context.getSkills().getBoostedLevels(Skill.ATTACK) == context.getSkills().getRealLevel(Skill.ATTACK))) {
            AbstractScript.log("Try to drink super combat potion.");
            drinkPotion(context, "Super combat potion");
        }

        return true;
    }

    private void drinkPotion(AbstractScript context, String potionName) {
        if (!context.getInventory().interact(potionName + "(1)", "Drink")) {
            if (!context.getInventory().interact(potionName + "(2)", "Drink")) {
                if (!context.getInventory().interact(potionName + "(3)", "Drink")) {
                    context.getInventory().interact(potionName + "(4)", "Drink");
                }
            }
        }
    }

    @Override
    public State next() {
        return new KillMobState();
    }
}
