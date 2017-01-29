package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.State;
import com.toliga.ganjabots.core.Utilities;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;

public class BankState implements State {

    private int index = 0;
    private boolean bankIsOpen = false;

    @Override
    public boolean execute(AbstractScript context) {
        AbstractScript.log("BANK");
        Utilities.OpenTab(context, Tab.INVENTORY);

        if (!bankIsOpen && context.getBank().open()) {
            AbstractScript.sleepUntil(() -> context.getBank().isOpen(), 9000);
            bankIsOpen = true;
        }

        if (context.getBank().isOpen()) {
            String itemName = GlobalSettings.LOOT_NAMES[index];
            AbstractScript.log("Item name: " + itemName);
            context.getBank().depositAll(itemName);
            AbstractScript.sleepUntil(() -> !context.getInventory().contains(itemName), 3000);
            index++;
        }

        if (index >= GlobalSettings.LOOT_NAMES.length) {
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
}
