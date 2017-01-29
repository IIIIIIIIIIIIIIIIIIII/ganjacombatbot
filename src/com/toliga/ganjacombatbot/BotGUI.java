package com.toliga.ganjacombatbot;

import com.toliga.ganjabots.core.Utilities;
import org.dreambot.api.script.AbstractScript;

import javax.swing.*;

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
    private JTextField textField3;
    private JSlider slider1;
    private JTextField textField4;
    private JCheckBox checkBox4;
    private JCheckBox checkBox5;
    private JCheckBox checkBox6;
    private JCheckBox checkBox7;
    private JCheckBox checkBox8;
    private JCheckBox checkBox9;
    private JCheckBox checkBox10;
    private JTextField textField5;
    private JLabel infoLabel;
    private JPanel lootingPanel;
    private JPanel foodPanel;
    private JPanel potionPanel;
    private GanjaCombatBotMain context;

    public BotGUI(AbstractScript context, String title) {
        this.context = (GanjaCombatBotMain) context;
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
        btnStop.setEnabled(false);
        tabbedPaneMenu.setEnabledAt(1, false);
        tabbedPaneMenu.setEnabledAt(2, false);
        tabbedPaneMenu.setEnabledAt(3, false);
    }

    private void registerEvents() {
        btnStart.addActionListener(event -> {
            context.setStarted(true);
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);

            String mobs[] = mobNameTextField.getText().split(",");

            for (int i = 0; i < mobs.length; i++) {
                mobs[i] = mobs[i].trim();
            }

            GlobalSettings.MOB_NAMES = mobs;

            String loots[] = lootTextField.getText().split(",");

            for (int i = 0; i < loots.length; i++) {
                loots[i] = loots[i].trim();
            }

            GlobalSettings.LOOT_NAMES = loots;
        });

        btnStop.addActionListener(event -> {
            context.setStarted(false);
            btnStop.setEnabled(false);
            btnStart.setEnabled(true);
        });

        powerkillCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.POWERKILL = source.isSelected();
            lootCheckBox.setEnabled(!source.isSelected());
        });

        lootCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.LOOT = source.isSelected();
            powerkillCheckBox.setEnabled(!source.isSelected());
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

        buryBonesCheckBox.addChangeListener(event -> {
            JCheckBox source = (JCheckBox) event.getSource();
            GlobalSettings.BURY_BONES = source.isSelected();
        });
    }
}
