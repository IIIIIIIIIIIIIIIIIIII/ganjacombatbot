package com.toliga.ganjacombatbot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import com.toliga.ganjabots.core.Utilities;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.utilities.Timer;

public class BotGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    public final int WIDTH = 300;
    public final int HEIGHT = 340;

    private Image backgroundImage = null;
    private Image ganjaIcon;
    private JButton btnStart;
    private JButton btnStop;
    private JTextArea taFeedback;
    private AbstractScript context;
    private Timer timer;

    public BotGUI(AbstractScript context, String title) {
        super(title);
        this.context = context;
        timer = new Timer();
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        InitializeComponents();

        try {
            backgroundImage = Utilities.LoadImage("http://i63.tinypic.com/2j48v94.png", 190, 111);
            ganjaIcon = Utilities.LoadImage("http://ai-i1.infcdn.net/icons_siandroid/png/200/1138/1138962.png", 20, 20);

        } catch (Exception e) {
            AbstractScript.log(e.getMessage());
        }
    }

    class BtnStartClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class BtnStopClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class BtnSubmitClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = taFeedback.getText();
            taFeedback.setText("");
            //context.getFeedback().SendString(message, context.getClient().getUsername());
        }
    }

    private void InitializeComponents() {
        JPanel genericPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        genericPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT - 40));

        btnStart = new JButton();
        btnStart.setIcon(new ImageIcon(Utilities.LoadImage("http://cdn3.iconfinder.com/data/icons/buttons/512/Icon_3-128.png", 35, 35)));
        btnStart.setPreferredSize(new Dimension(40, 40));
        btnStart.addActionListener(new BtnStartClicked());

        btnStop = new JButton();
        btnStop.setIcon(new ImageIcon(Utilities.LoadImage("http://cdn3.iconfinder.com/data/icons/buttons/512/Icon_5-128.png", 35, 35)));
        btnStop.setPreferredSize(new Dimension(40, 40));
        btnStop.setEnabled(false);
        btnStop.addActionListener(new BtnStopClicked());

        genericPanel.add(btnStart);
        genericPanel.add(btnStop);

        JTabbedPane tabPanel = new JTabbedPane();
        tabPanel.setPreferredSize(new Dimension(WIDTH - 15, HEIGHT - 90));
        tabPanel.setBackground(new Color(0xBF, 0xBF, 0xBF));

        // *************** SETTINGS TAB ***************//
        tabPanel.addTab("Settings", InitializeSettingsComponents());

        // *************** FEEDBACK TAB ***************//
        tabPanel.addTab("Feedback", InitializeFeedbackComponents());

        genericPanel.add(tabPanel);

        add(genericPanel);
    }

    private JPanel InitializeSettingsComponents() {
        JPanel pSettings = new JPanel(new FlowLayout(FlowLayout.LEFT));

        return pSettings;
    }



    private JPanel InitializeFeedbackComponents() {
        JPanel pFeedback = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel lblFeedback = new JLabel("<html>Please inform us about any suggestions<br>or bug issues.</html>");
        pFeedback.add(lblFeedback);

        taFeedback = new JTextArea(9, 20);
        taFeedback.setLineWrap(true);
        taFeedback.setAutoscrolls(true);
        taFeedback.setWrapStyleWord(true);

        JScrollPane spFeedback = new JScrollPane(taFeedback);
        pFeedback.add(spFeedback);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new BtnSubmitClicked());
        pFeedback.add(btnSubmit);

        return pFeedback;
    }

    public void Display() {
        setVisible(true);
    }

    public void Close() {
        setVisible(false);
    }

    public void DrawInGameGUI(Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawImage(backgroundImage, 305, 347, null);

        graphics.setFont(new Font("Magneto", Font.BOLD, 15));
        graphics.setColor(new Color(0x00, 0x66, 0x00));
        graphics.drawString("Ganja Woodcutter", 264, 365);

        graphics.setFont(new Font("Consolas", Font.PLAIN, 15));
        graphics.setColor(Color.BLACK);
        graphics.drawString("        Run Time:", 280, 382);
        graphics.drawString("Time to level up:", 280, 399);
        graphics.drawString("       XP gained:", 280, 416);
        graphics.drawString("           XP/hr:", 280, 433);

        graphics.drawString("v" + GanjaCombatBotMain.VERSION, 420, 365);
        graphics.drawImage(ganjaIcon, 470, 347, null);
        graphics.drawString(timer.formatTime(), 420, 382);
        graphics.drawString(String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(context.getSkillTracker().getTimeToLevel(Skill.WOODCUTTING)),
                TimeUnit.MILLISECONDS.toMinutes(context.getSkillTracker().getTimeToLevel(Skill.WOODCUTTING))
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(context.getSkillTracker().getTimeToLevel(Skill.WOODCUTTING))),
                TimeUnit.MILLISECONDS.toSeconds(context.getSkillTracker().getTimeToLevel(Skill.WOODCUTTING))
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(context.getSkillTracker().getTimeToLevel(Skill.WOODCUTTING)))),
                420, 399);
        graphics.drawString(context.getSkillTracker().getGainedExperience(Skill.WOODCUTTING) + " XP", 420, 416);
        graphics.drawString(context.getSkillTracker().getGainedExperiencePerHour(Skill.WOODCUTTING) + " XP", 420, 433);
    }
}
