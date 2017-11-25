/*
Ganja Combat Bot is a fully or semi autonomous bot for the game RuneScape.
Copyright (C) 2016  Tolga Üstünkök

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.toliga.ganjacombatbot;

import com.toliga.ganjabots.core.SaveManager;
import com.toliga.ganjabots.core.Utilities;
import com.toliga.ganjabots.core.Validator;
import com.toliga.ganjabots.graphics.InGameGUIBuilder;
import com.toliga.ganjabots.path.ActionElement;
import com.toliga.ganjabots.path.PathElement;
import com.toliga.ganjabots.path.PathProfile;
import com.toliga.ganjacombatbot.drawables.*;
import com.toliga.ganjacombatbot.rules.SeperableTextValidator;
import jdk.nashorn.internal.objects.Global;
import org.dreambot.api.script.AbstractScript;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class BotGUI extends JFrame {
    private JButton btnStart;
    private JButton btnStop;
    private JTabbedPane tabbedPaneMenu;
    private JPanel rootPane;
    private JTextField mobNameTextField;
    private JCheckBox powerkillCheckBox;
    private JCheckBox lootCheckBox;
    private JCheckBox eatFoodCheckBox;
    private JCheckBox usePotionCheckBox;
    private JCheckBox useSpecialAttackCheckBox;
    private JButton button1;
    private JTextArea textArea1;
    private JTextField lootTextField;
    private JCheckBox bankWhenFullCheckBox;
    private JCheckBox logoutWhenFullCheckBox;
    private JCheckBox buryBonesCheckBox;
    private JTextField foodTextField;
    private JSlider healthSlider;
    private JTextField foodAmountTextField;
    private JCheckBox combatCheckBox;
    private JCheckBox strengthCheckBox;
    private JCheckBox defenceCheckBox;
    private JCheckBox attackCheckBox;
    private JCheckBox superStrengthCheckBox;
    private JCheckBox superDefenceCheckBox;
    private JCheckBox superAttackCheckBox;
    private JTextField textField5;
    private JLabel infoLabel;
    private JPanel lootingPanel;
    private JPanel foodPanel;
    private JPanel potionPanel;
    private JCheckBox superCombatCheckBox;
    private JCheckBox useAntibanCheckBox;
    private JSlider mouseSlider;
    private JSlider cameraSlider;
    private JSlider tabSlider;
    private JCheckBox interactionResponseCheckBox;
    private JTextField worldHopTextField;
    private JButton btnToggleInGameGUI;
    private JButton btnOpenAttack;
    private JButton btnOpenStrength;
    private JButton btnOpenDefence;
    private JButton btnOpenHitpoint;
    private JButton btnOpenRange;
    private JButton btnOpenMagic;
    private JTextField pathProfileTextField;
    private JButton btnCreateProfile;
    private JList profileList;
    private JButton btnLoadProfile;
    private JCheckBox usePathCreatorCheckBox;
    private JTextField runTimeTextField;
    private JTextField breakTimeTextField;
    private GanjaCombatBotMain context;
    private ImageIcon inGameGUIOpened;
    private ImageIcon inGameGUIClosed;
    private SaveManager saveManager;
    private Validator validator;
    private boolean inGameGUIOpenState = false;
    private InGameGUIBuilder inGameGUI;
    private DefaultListModel<String> listModel;
    private List<PathProfile> profiles = new ArrayList<>();

    public BotGUI(AbstractScript context, String title) {
        this.context = (GanjaCombatBotMain) context;
        listModel = new DefaultListModel<>();
        profileList.setModel(listModel);
        saveManager = new SaveManager(System.getenv().get("HOME") + "DreamBot\\Scripts\\GanjaCombatBot\\save.dat");
        validator = new Validator();
        inGameGUI = new InGameGUIBuilder(context, GanjaCombatBotMain.VERSION, new MainDrawable(context));
        validator.addValidation(new SeperableTextValidator());
        setTitle(title);
        setContentPane(rootPane);
        pack();
        //setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

        initializeComponents();
        registerEvents();
    }

    private void initializeComponents() {
        btnStart.setIcon(new ImageIcon(Utilities.LoadImage("http://cdn3.iconfinder.com/data/icons/buttons/512/Icon_3-128.png", 35, 35)));
        btnStop.setIcon(new ImageIcon(Utilities.LoadImage("http://cdn3.iconfinder.com/data/icons/buttons/512/Icon_5-128.png", 35, 35)));
        infoLabel.setIcon(new ImageIcon(Utilities.LoadImage("http://cdn3.iconfinder.com/data/icons/buttons/512/Icon_17-128.png", 20, 20)));
        inGameGUIClosed = new ImageIcon(Utilities.LoadImage("http://cdn3.iconfinder.com/data/icons/wpzoom-developer-icon-set/500/102-128.png", 15, 15));
        inGameGUIOpened = new ImageIcon(Utilities.LoadImage("http://cdn3.iconfinder.com/data/icons/wpzoom-developer-icon-set/500/103-128.png", 15, 15));
        btnOpenAttack.setIcon(new ImageIcon(Utilities.LoadImage("http://www.account4rs.com/images/skill_powerleveling/attack.png", 15, 15)));
        btnOpenStrength.setIcon(new ImageIcon(Utilities.LoadImage("http://www.account4rs.com/images/skill_powerleveling/strength.png", 15, 15)));
        btnOpenDefence.setIcon(new ImageIcon(Utilities.LoadImage("http://www.account4rs.com/images/skill_powerleveling/defence.png", 15, 15)));
        btnOpenHitpoint.setIcon(new ImageIcon(Utilities.LoadImage("http://runetrack.com/images/skill_icons/constitution.gif", 15, 15)));
        btnOpenRange.setIcon(new ImageIcon(Utilities.LoadImage("http://www.account4rs.com/images/skill_powerleveling/ranged.png", 15, 15)));
        btnOpenMagic.setIcon(new ImageIcon(Utilities.LoadImage("http://www.account4rs.com/images/skill_powerleveling/magic.png", 15, 15)));
        btnToggleInGameGUI.setIcon(inGameGUIOpened);

        btnStop.setEnabled(false);
        tabbedPaneMenu.setEnabledAt(1, false);
        tabbedPaneMenu.setEnabledAt(2, false);
        tabbedPaneMenu.setEnabledAt(3, false);
        tabbedPaneMenu.setEnabledAt(4, false);

        // Load from file
        AbstractScript.log("Starting loading...");
        HashMap<String, String> savedOptions = saveManager.loadAll();

        if (!savedOptions.isEmpty()) {
            GlobalSettings.MOB_NAMES = savedOptions.get("mobnames").split(",");
            GlobalSettings.LOOT_NAMES = savedOptions.containsKey("lootnames") ? savedOptions.get("lootnames").split(",") : null;
            GlobalSettings.FOOD_NAMES = savedOptions.containsKey("foodnames") ? savedOptions.get("foodnames").split(",") : null;
            healthSlider.setValue(GlobalSettings.HEALTH_PERCENT = Integer.valueOf(savedOptions.get("healthpercent")));
            powerkillCheckBox.setSelected(GlobalSettings.POWERKILL = Boolean.valueOf(savedOptions.get("powerkill")));
            lootCheckBox.setSelected(GlobalSettings.LOOT = Boolean.valueOf(savedOptions.get("loot")));
            eatFoodCheckBox.setSelected(GlobalSettings.EAT_FOOD = Boolean.valueOf(savedOptions.get("eatfood")));
            bankWhenFullCheckBox.setSelected(GlobalSettings.BANK_WHEN_FULL = Boolean.valueOf(savedOptions.get("bankwhenfull")));
            logoutWhenFullCheckBox.setSelected(GlobalSettings.LOGOUT_WHEN_FULL = Boolean.valueOf(savedOptions.get("logoutwhenfull")));
            useAntibanCheckBox.setSelected(GlobalSettings.USE_ANTIBAN = Boolean.valueOf("useantiban"));
            buryBonesCheckBox.setSelected(GlobalSettings.BURY_BONES = Boolean.valueOf("burybones"));
            usePotionCheckBox.setSelected(GlobalSettings.USE_POTION = Boolean.valueOf("usepotion"));
        }

        if (GlobalSettings.MOB_NAMES != null) {
            for (String mob : GlobalSettings.MOB_NAMES) {
                mobNameTextField.setText(mobNameTextField.getText() + mob + ",");
            }
        }

        if (GlobalSettings.LOOT_NAMES != null) {
            for (String loot : GlobalSettings.LOOT_NAMES) {
                lootTextField.setText(lootTextField.getText() + loot + ",");
            }
        }

        if (GlobalSettings.FOOD_NAMES != null) {
            for (String food : GlobalSettings.FOOD_NAMES) {
                foodTextField.setText(foodTextField.getText() + food + ",");
            }
        }
    }

    private void registerEvents() {
        btnStart.addActionListener(event -> {
            if (mobNameTextField.getText().equalsIgnoreCase("/debug")) {
                GlobalSettings.DEBUG = true;
                return;
            }

            HashMap<String, String> pairs = new HashMap<>();
            String mobNames = null, lootNames = null, foodNames = null;

            /********************MOB NAMES*******************/
            mobNames = validator.validate(mobNameTextField.getText());
            String mobs[] = mobNames.split(",");

            for (int i = 0; i < mobs.length; i++) {
                mobs[i] = mobs[i].trim();
            }

            GlobalSettings.MOB_NAMES = mobs;

            /******************LOOT NAMES*******************/
            if (GlobalSettings.LOOT) {
                lootNames = validator.validate(lootTextField.getText());
                String loots[] = lootNames.split(",");

                for (int i = 0; i < loots.length; i++) {
                    loots[i] = loots[i].trim();
                }

                GlobalSettings.LOOT_NAMES = loots;
            }

            /*******************FOOD NAMES*******************/
            if (GlobalSettings.EAT_FOOD) {
                foodNames = validator.validate(foodTextField.getText());
                String foods[] = foodNames.split(",");

                for (int i = 0; i < foods.length; i++) {
                    foods[i] = foods[i].trim();
                }

                GlobalSettings.FOOD_NAMES = foods;
                GlobalSettings.FOOD_AMOUNT = foodAmountTextField.getText().isEmpty() ? 0 : Integer.parseInt(foodAmountTextField.getText());
                GlobalSettings.HEALTH_PERCENT = healthSlider.getValue();
            }

            if (worldHopTextField.getText().isEmpty()) {
                GlobalSettings.WORLD_HOP = false;
            } else {
                GlobalSettings.WORLD_HOP = true;
                GlobalSettings.WORLD_HOP_TIME = Integer.parseInt(worldHopTextField.getText());
            }

            pairs.put("mobnames", mobNames);
            pairs.put("lootnames", lootNames == null ? "" : lootNames);
            pairs.put("foodnames", foodNames == null ? "" : foodNames);
            pairs.put("healthpercent", String.valueOf(GlobalSettings.HEALTH_PERCENT));
            pairs.put("powerkill", String.valueOf(GlobalSettings.POWERKILL));
            pairs.put("loot", String.valueOf(GlobalSettings.LOOT));
            pairs.put("eatfood", String.valueOf(GlobalSettings.EAT_FOOD));
            pairs.put("bankwhenfull", String.valueOf(GlobalSettings.BANK_WHEN_FULL));
            pairs.put("logoutwhenfull", String.valueOf(GlobalSettings.LOGOUT_WHEN_FULL));
            pairs.put("useantiban", String.valueOf(GlobalSettings.USE_ANTIBAN));
            pairs.put("burybones", String.valueOf(GlobalSettings.BURY_BONES));
            pairs.put("usepotion", String.valueOf(GlobalSettings.USE_POTION));

            saveManager.saveOptions(pairs);

            if (GlobalSettings.USE_PATH_CREATOR) {
                int[] selectedIndices = profileList.getSelectedIndices();

                //GlobalSettings.CHOSEN_BANK_GO_PROFILE = profiles.get(profileList.getSelectedIndex());
                GlobalSettings.CHOSEN_BANK_GO_PROFILE = profiles.get(selectedIndices[0]);
                GlobalSettings.CHOSEN_BANK_RETURN_PROFILE = profiles.get(selectedIndices[1]);
            }

            context.setStarted(true);
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            inGameGUI.setCanDraw(true);
        });

        btnStop.addActionListener(event -> {
            context.setStarted(false);
            btnStop.setEnabled(false);
            btnStart.setEnabled(true);
            inGameGUI.setCanDraw(false);
        });

        btnOpenAttack.addActionListener(event -> {
            if (!(inGameGUI.getDrawable() instanceof AttackDrawable)) {
                inGameGUI.setDrawable(new AttackDrawable(context));
            } else {
                inGameGUI.setDrawable(new MainDrawable(context));
            }
        });

        btnOpenStrength.addActionListener(event -> {
            if (!(inGameGUI.getDrawable() instanceof StrengthDrawable)) {
                inGameGUI.setDrawable(new StrengthDrawable(context));
            } else {
                inGameGUI.setDrawable(new MainDrawable(context));
            }
        });

        btnOpenDefence.addActionListener(event -> {
            if (!(inGameGUI.getDrawable() instanceof DefenceDrawable)) {
                inGameGUI.setDrawable(new DefenceDrawable(context));
            } else {
                inGameGUI.setDrawable(new MainDrawable(context));
            }
        });

        btnOpenHitpoint.addActionListener(event -> {
            if (!(inGameGUI.getDrawable() instanceof HitpointDrawable)) {
                inGameGUI.setDrawable(new HitpointDrawable(context));
            } else {
                inGameGUI.setDrawable(new MainDrawable(context));
            }
        });

        btnOpenRange.addActionListener(event -> {
            if (!(inGameGUI.getDrawable() instanceof RangeDrawable)) {
                inGameGUI.setDrawable(new RangeDrawable(context));
            } else {
                inGameGUI.setDrawable(new MainDrawable(context));
            }
        });

        btnOpenMagic.addActionListener(event -> {
            if (!(inGameGUI.getDrawable() instanceof MagicDrawable)) {
                inGameGUI.setDrawable(new MagicDrawable(context));
            } else {
                inGameGUI.setDrawable(new MainDrawable(context));
            }
        });

        btnToggleInGameGUI.addActionListener(event -> {
            if (inGameGUIOpenState) {
                btnToggleInGameGUI.setIcon(inGameGUIOpened);
            } else {
                btnToggleInGameGUI.setIcon(inGameGUIClosed);
            }

            inGameGUIOpenState = !inGameGUIOpenState;
            inGameGUI.setCanDraw(!inGameGUI.getCanDraw());
        });

        healthSlider.addChangeListener(event -> {
            GlobalSettings.HEALTH_PERCENT = ((JSlider) event.getSource()).getValue();
        });

        powerkillCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.POWERKILL = source.isSelected();
            if (source.isSelected()) {
                lootCheckBox.setSelected(!source.isSelected());
            }
        });

        lootCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.LOOT = source.isSelected();
            if (source.isSelected()) {
                powerkillCheckBox.setSelected(!source.isSelected());
            }
            tabbedPaneMenu.setEnabledAt(1, source.isSelected());
        });

        eatFoodCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.EAT_FOOD = source.isSelected();
            tabbedPaneMenu.setEnabledAt(2, source.isSelected());
        });

        bankWhenFullCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.BANK_WHEN_FULL = source.isSelected();
        });

        logoutWhenFullCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.LOGOUT_WHEN_FULL = source.isSelected();
        });

        useAntibanCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            tabbedPaneMenu.setEnabledAt(4, source.isSelected());
            GlobalSettings.USE_ANTIBAN = source.isSelected();
            if (source.isSelected()) {
                context.getAntibanManager().activateAllFeatures();
            } else {
                context.getAntibanManager().disableAllFeatures();
            }
        });

        buryBonesCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.BURY_BONES = source.isSelected();
        });

        usePotionCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.USE_POTION = source.isSelected();
            tabbedPaneMenu.setEnabledAt(3, source.isSelected());
        });

        interactionResponseCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.INTERACTION_RESPONSE = source.isSelected();
        });

        combatCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.COMBAT_POTION = source.isSelected();
        });

        strengthCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.STRENGTH_POTION = source.isSelected();
        });

        defenceCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.DEFENCE_POTION = source.isSelected();
        });

        usePathCreatorCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.USE_PATH_CREATOR = source.isSelected();
        });

        attackCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.ATTACK_POTION = source.isSelected();
        });

        superCombatCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.SUPER_COMBAT_POTION = source.isSelected();
        });

        superStrengthCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.SUPER_STRENGTH_POTION = source.isSelected();
        });

        superDefenceCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.SUPER_DEFENCE_POTION = source.isSelected();
        });

        superAttackCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.SUPER_ATTACK_POTION = source.isSelected();
        });

        mouseSlider.addChangeListener(event -> {
            context.getAntibanManager().getFeature("RANDOM_MOUSE_MOVEMENT").setProbability((float)(mouseSlider.getValue() / 1000.0));
        });

        cameraSlider.addChangeListener(event -> {
            context.getAntibanManager().getFeature("RANDOM_CAMERA_ROTATION").setProbability((float)(cameraSlider.getValue() / 1000.0));
        });

        tabSlider.addChangeListener(event -> {
            context.getAntibanManager().getFeature("RANDOM_TAB_CHECKING").setProbability((float)(tabSlider.getValue() / 1000.0));
        });

        interactionResponseCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            context.getAntibanManager().getFeature("INTERACTION_RESPONSE").setEnabled(source.isSelected());
        });

        btnCreateProfile.addActionListener(event -> {
            PathProfile toProfile = new PathProfile(pathProfileTextField.getText() + "_TO");
            PathProfile fromProfile = new PathProfile(pathProfileTextField.getText() + "_FROM");

            PathFinderGUI pathFinderGUI = new PathFinderGUI(context, toProfile, fromProfile);
            pathFinderGUI.setVisible(true);

            profiles.add(toProfile);
            profiles.add(fromProfile);
            listModel.addElement(toProfile.toString());
            listModel.addElement(fromProfile.toString());
        });

        btnLoadProfile.addActionListener(event -> {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getenv().get("HOME") + "DreamBot\\Scripts\\GanjaCombatBot"));
            int returnValue = fileChooser.showOpenDialog(this);
            SaveManager profileSaveManager;

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                profileSaveManager = new SaveManager(fileChooser.getSelectedFile().getAbsolutePath());
                LinkedHashMap<String, String> savedProfile = profileSaveManager.loadAll();
                PathProfile tempPathProfile = new PathProfile(savedProfile.get("name"));
                savedProfile.remove("name");

                for (Map.Entry<String, String> entry : savedProfile.entrySet()) {
                    if (entry.getKey().contains("tile")) {
                        tempPathProfile.addElement(new PathElement(Integer.parseInt(entry.getValue().split(",")[0]), Integer.parseInt(entry.getValue().split(",")[1])));
                    } else if (entry.getKey().contains("action")) {
                        tempPathProfile.addElement(new ActionElement(Integer.parseInt(entry.getValue().split(",")[0]), entry.getValue().split(",")[1]));
                    }
                    AbstractScript.log(entry.getKey() + " - " + entry.getValue());
                }

                profiles.add(tempPathProfile);

                listModel.addElement(tempPathProfile.toString());
            }
        });
    }

    public void DrawInGameGUI(Graphics2D graphics) {
        inGameGUI.draw(graphics);
    }
}
