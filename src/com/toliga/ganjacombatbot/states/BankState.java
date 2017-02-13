package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.AntibanManager;
import com.toliga.ganjabots.core.State;
import com.toliga.ganjabots.core.Utilities;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;

public class BankState implements State {

    private int itemIndex = 0;
    private int foodIntex = 0;
    private boolean bankIsOpen = false;
    private boolean getFood = false;
    private boolean finishedFood = false;
    private boolean finishedItem = false;

    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
        if (GlobalSettings.DEBUG) AbstractScript.log("BANK");
        Utilities.OpenTab(context, Tab.INVENTORY);

        if (!getFood) {
            finishedFood = true;
        }

        if (!bankIsOpen && context.getBank().open()) {
            AbstractScript.sleepUntil(() -> context.getBank().isOpen(), 9000);
            bankIsOpen = true;
        }

        if (context.getBank().isOpen()) {
            if (GlobalSettings.BANK_WHEN_FULL && itemIndex < GlobalSettings.LOOT_NAMES.length) {
                String itemName = GlobalSettings.LOOT_NAMES[itemIndex];
                AbstractScript.log("Item name: " + itemName);
                context.getBank().depositAll(itemName);
                AbstractScript.sleepUntil(() -> !context.getInventory().contains(itemName), 3000);
                itemIndex++;
            } else {
                finishedItem = true;
            }

            if (getFood) {
                if (foodIntex < GlobalSettings.LOOT_NAMES.length) {
                    String foodName = GlobalSettings.FOOD_NAMES[foodIntex];
                    AbstractScript.log("Food name: " + foodName);
                    context.getBank().withdraw(foodName, GlobalSettings.FOOD_AMOUNT);
                    AbstractScript.sleepUntil(() -> context.getInventory().contains(foodName), 3000);
                    foodIntex++;
                } else {
                    finishedFood = true;
                }
            }
        }

        if (finishedFood && finishedItem) {
            if (context.getBank().close()) {
                AbstractScript.sleepUntil(() -> !context.getBank().isOpen(), 8000);
                return true;
            }
        }

        return false;
    }

    @Override
    public State next() {
        return new WalkFromBankState();
    }

    public void setGetFood(boolean getFood) {
        this.getFood = getFood;
    }
}
