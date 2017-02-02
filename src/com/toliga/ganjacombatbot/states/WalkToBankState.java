package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.State;
import com.toliga.ganjabots.core.Utilities;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;

public class WalkToBankState implements State {

    @Override
    public boolean execute(AbstractScript context) {
        AbstractScript.log("WALK_TO_BANK");
        String bankName = context.getBank().getClosestBankLocation().name();

        Area destinationArea = context.getBank().getClosestBankLocation().getArea(1);

        if (!destinationArea.contains(context.getLocalPlayer())) {
            Utilities.GoToArea(context, destinationArea);

            if (bankName.equals("LUMBRIDGE")) {
                AbstractScript.sleep(2000);
            }
            return false;
        }

        return true;
    }

    @Override
    public State next() {
        BankState bankState = new BankState();
        if (GlobalSettings.EAT_FOOD) {
            bankState.setGetFood(true);
        }
        return bankState;
    }
}
