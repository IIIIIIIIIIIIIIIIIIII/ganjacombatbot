package com.toliga.ganjacombatbot.rules;

import com.toliga.ganjabots.core.ValidationRule;

public class SeperableTextValidator implements ValidationRule {

    @Override
    public String validate(String validateData) {
        if (validateData.isEmpty()
                || validateData.startsWith(" ")
                || validateData.endsWith(" ")
                || validateData.matches(".*\\d+.*")) {
            throw new IllegalArgumentException("Please check one of your inputs again.");
        }

        return validateData;
    }
}
