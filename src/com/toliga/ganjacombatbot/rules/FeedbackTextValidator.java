package com.toliga.ganjacombatbot.rules;

import com.toliga.ganjabots.core.ValidationRule;

public class FeedbackTextValidator implements ValidationRule {

    @Override
    public String validate(String validateData) {

        if (validateData.isEmpty()
                || validateData.startsWith(" ")
                || validateData.endsWith(" ")) {
            throw new IllegalArgumentException("Please check the feedback form again.");
        }

        return validateData;
    }
}
