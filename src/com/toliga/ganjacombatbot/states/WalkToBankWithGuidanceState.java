package com.toliga.ganjacombatbot.states;

import com.toliga.ganjabots.core.AntibanManager;
import com.toliga.ganjabots.core.State;
import com.toliga.ganjabots.core.Utilities;
import com.toliga.ganjabots.path.ActionElement;
import com.toliga.ganjabots.path.Element;
import com.toliga.ganjabots.path.PathElement;
import com.toliga.ganjabots.path.PathProfile;
import com.toliga.ganjacombatbot.GlobalSettings;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;

public class WalkToBankWithGuidanceState implements State {

    private Element currentElement;
    private PathProfile chosenProfile;

    public WalkToBankWithGuidanceState() {
        chosenProfile = GlobalSettings.CHOSEN_PROFILE;
        currentElement = chosenProfile.nextElement();
    }

    @Override
    public boolean execute(AbstractScript context, AntibanManager antibanManager) {
        if (GlobalSettings.DEBUG) AbstractScript.log("WALK_TO_BANK_WITH_GUIDANCE");

        if (currentElement == null) {
            return true;
        }

        AbstractScript.log("Current element is: " + currentElement.toString());

        if (currentElement instanceof PathElement) {
            Tile destinationTile = new Tile(((PathElement) currentElement).x, ((PathElement) currentElement).y);

            if (context.getLocalPlayer().distance(destinationTile) > 1) {
                Utilities.GoToTile(context, destinationTile);
            } else {
                currentElement = chosenProfile.nextElement();
            }
            return false;
        } else {
            context.getGameObjects().closest(obj -> obj.getID() == ((ActionElement) currentElement).objectID).interact(((ActionElement) currentElement).actionName);
            AbstractScript.sleep(200, 700);
            currentElement = chosenProfile.nextElement();
            return false;
        }
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
