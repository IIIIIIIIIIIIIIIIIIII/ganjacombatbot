package com.toliga.ganjacombatbot.antibanfeatures;

import com.toliga.ganjabots.core.AntibanFeature;
import com.toliga.ganjabots.core.Utilities;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;

public class RandomTabChecking extends AntibanFeature {

    private int questCounter = 0;

    public RandomTabChecking(float probability) {
        super("RANDOM_TAB_CHECKING", probability);
    }

    @Override
    public void execute(AbstractScript context) {
        int dice = Calculations.random(8);

        switch (dice) {
            case 0:
                skillTab(context);
                break;
            case 1:
                questTab(context);
                break;
            case 2:
                equipmentTab(context);
                break;
            case 3:
                prayerTab(context);
                break;
            case 4:
                magicTab(context);
                break;
            case 5:
                friendsTab(context);
                break;
            case 6:
                // Emotes
                break;
            case 7:
                // Music
                break;
        }

        Utilities.OpenTab(context, Tab.INVENTORY);
    }

    private void friendsTab(AbstractScript context) {
        Utilities.OpenTab(context, Tab.FRIENDS);
        AbstractScript.sleep(1000, 2000);
    }

    private void magicTab(AbstractScript context) {
        Utilities.OpenTab(context, Tab.MAGIC);
        context.getMouse().move(context.getWidgets().getWidgetChild(218, Calculations.random(1, 67)).getRectangle());
        AbstractScript.sleep(500, 2500);
    }

    private void prayerTab(AbstractScript context) {
        Utilities.OpenTab(context, Tab.PRAYER);
        context.getMouse().move(context.getWidgets().getWidgetChild(541, Calculations.random(4, 33)).getRectangle());
        AbstractScript.sleep(500, 2500);
    }

    private void equipmentTab(AbstractScript context) {
        Utilities.OpenTab(context, Tab.EQUIPMENT);
        AbstractScript.sleep(2000, 3000);
    }

    private void skillTab(AbstractScript context) {
        Utilities.OpenTab(context, Tab.STATS);
        context.getMouse().move(context.getWidgets().getWidgetChild(320, Calculations.random(1, 24)).getRectangle());
        AbstractScript.sleep(500, 2500);
    }

    private void questTab(AbstractScript context) {
        Utilities.OpenTab(context, Tab.QUEST);
        context.getMouse().move(context.getWidgets().getWidgetChild(399, 6).getRectangle());
        context.getMouse().scrollWhile(questCounter++ > 2, Calculations.random(500, 1000), () -> false);
        AbstractScript.sleep(2000);

        if (questCounter > 4) {
            questCounter = 0;
        }
    }
}
