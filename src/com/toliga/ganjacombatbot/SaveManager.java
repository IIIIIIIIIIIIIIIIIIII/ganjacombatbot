package com.toliga.ganjacombatbot;

public class SaveManager {
    private FileManager fileManager;

    public SaveManager() {
        fileManager = new FileManager("save.dat");
    }

    public void save() {
        fileManager.openWriter();
        fileManager.writeLineToFile("# Do not edit the file.");
        fileManager.writeLineToFile("# It may be unstable if you edit it wrong.");

        if (GlobalSettings.MOB_NAMES != null) {
            saveMobSettings();
        }

        if (GlobalSettings.LOOT_NAMES != null) {
            lootSettings();
        }

        if (GlobalSettings.FOOD_NAMES != null) {
            foodSettings();
        }
        fileManager.closeWriter();
    }

    public void load() {
        if (fileManager.isFileExists()) {
            loadMobSettings();
            loadLootSettings();
            loadFoodSettings();
        }
    }

    private void loadMobSettings() {
        fileManager.openReader();
        String line;
        while ((line = fileManager.readLineFromFile()) != null && !line.startsWith("MobNames"));

        if (line != null) {
            String names[] = line.split("-");

            GlobalSettings.MOB_NAMES = new String[names.length - 1];

            for (int i = 1, j = 0; i < names.length; i++, j++) {
                GlobalSettings.MOB_NAMES[j] = names[i];
            }
        }
        fileManager.closeReader();
    }

    private void loadLootSettings() {
        fileManager.openReader();
        String line;
        while ((line = fileManager.readLineFromFile()) != null && !line.startsWith("LootNames"));

        if (line != null) {
            String names[] = line.split("-");

            GlobalSettings.LOOT_NAMES = new String[names.length - 1];

            for (int i = 1, j = 0; i < names.length; i++, j++) {
                GlobalSettings.LOOT_NAMES[j] = names[i];
            }
        }
        fileManager.closeReader();
    }

    private void loadFoodSettings() {
        fileManager.openReader();
        String line;
        while ((line = fileManager.readLineFromFile()) != null && !line.startsWith("FoodNames"));

        if (line != null) {
            String names[] = line.split("-");

            GlobalSettings.FOOD_NAMES = new String[names.length - 1];

            for (int i = 1, j = 0; i < names.length; i++, j++) {
                GlobalSettings.FOOD_NAMES[j] = names[i];
            }
        }
        fileManager.closeReader();
    }

    private void saveMobSettings() {
        String mobNamesSettings = "MobNames-";

        for (String mob : GlobalSettings.MOB_NAMES) {
            mobNamesSettings += (mob + "-");
        }

        fileManager.writeLineToFile(mobNamesSettings);
    }

    private void lootSettings() {
        String lootNames = "LootNames-";

        for (String lootItem : GlobalSettings.LOOT_NAMES) {
            lootNames += (lootItem + "-");
        }

        fileManager.writeLineToFile(lootNames);
    }

    private void foodSettings() {
        String foodNames = "FoodNames-";

        for (String food : GlobalSettings.FOOD_NAMES) {
            foodNames += (food + "-");
        }

        fileManager.writeLineToFile(foodNames);
    }
}
