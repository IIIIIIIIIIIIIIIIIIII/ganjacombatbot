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

        Area destinationArea = context.getBank().getClosestBankLocation().getArea(1);

        if (!destinationArea.contains(context.getLocalPlayer())) {
            Utilities.GoToArea(context, destinationArea);
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
