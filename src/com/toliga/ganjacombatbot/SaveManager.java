package com.toliga.ganjacombatbot;

public class SaveManager {
    private FileManager fileManager;

    public SaveManager() {
        fileManager = new FileManager("save.dat");
    }

    public void save() {
        saveMobSettings();
        lootSettings();
        foodSettings();
    }

    private void saveMobSettings() {
        String mobNamesSettings = "MobNames ";

        for (String mob : GlobalSettings.MOB_NAMES) {
            mobNamesSettings += (mob + " ");
        }

        fileManager.writeLineToFile(mobNamesSettings);
    }

    private void lootSettings() {
        String lootNames = "LootNames ";

        for (String lootItem : GlobalSettings.LOOT_NAMES) {
            lootNames += (lootItem + " ");
        }

        fileManager.writeLineToFile(lootNames);
    }

    private void foodSettings() {
        String foodNames = "FoodNames ";

        for (String food : GlobalSettings.FOOD_NAMES) {
            foodNames += (food + " ");
        }

        fileManager.writeLineToFile(foodNames);
    }
}
