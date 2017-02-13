package com.toliga.ganjacombatbot;

import com.toliga.ganjabots.path.ActionElement;
import com.toliga.ganjabots.path.PathElement;
import com.toliga.ganjabots.path.PathProfile;
import org.dreambot.api.script.AbstractScript;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PathFinderGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static int visibleIndex = 1;
    private JPanel contentPane;
    private JTextField idTextField;
    private JTextField actionTextField;
    private JButton btnAddAction;
    private JButton btnSaveCurrentTile;
    private JList actionList;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private AbstractScript context;
    private PathProfile pathProfile;
    private JButton btnSaveProfile;

    /**
     * Create the frame.
     */
    public PathFinderGUI(AbstractScript context, PathProfile pathProfile) {
        this.context = context;
        this.pathProfile = pathProfile;
        setTitle("GanjaSmuggler - Path Creator");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.NORTH);
        panel_1.setLayout(new GridLayout(2, 2, 0, 0));

        JLabel lblNewLabel = new JLabel("Path Creator");
        panel_1.add(lblNewLabel);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));

        JPanel panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        panel_1.add(panel);

        JLabel lblId = new JLabel("ID:");
        panel.add(lblId);

        idTextField = new JTextField();
        panel.add(idTextField);
        idTextField.setColumns(5);

        JLabel lblAction = new JLabel("Action:");
        panel.add(lblAction);

        actionTextField = new JTextField();
        panel.add(actionTextField);
        actionTextField.setColumns(7);

        btnAddAction = new JButton("Add");
        panel.add(btnAddAction);

        btnSaveCurrentTile = new JButton("Add Current Tile");
        panel.add(btnSaveCurrentTile);

        JScrollPane scrollPane = new JScrollPane();
        actionList = new JList(listModel);
        scrollPane.setViewportView(actionList);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel panel_2 = new JPanel();
        contentPane.add(panel_2, BorderLayout.SOUTH);

        btnSaveProfile = new JButton("Save Profile");
        panel_2.add(btnSaveProfile);

        registerEvents();
    }

    private void registerEvents() {
        btnSaveCurrentTile.addActionListener(event -> {
            PathElement pathElement = new PathElement();
            pathElement.x = context.getLocalPlayer().getTile().getX();
            pathElement.y = context.getLocalPlayer().getTile().getY();

            pathProfile.addElement(pathElement);

            listModel.addElement(visibleIndex++ + ". " + pathElement.toString());
        });

        btnAddAction.addActionListener(event -> {
            ActionElement actionElement = new ActionElement();
            actionElement.objectID = Integer.parseInt(idTextField.getText());
            actionElement.actionName = actionTextField.getText();

            pathProfile.addElement(actionElement);

            listModel.addElement(visibleIndex++ + ". " + actionElement.toString());
        });

        btnSaveProfile.addActionListener(event -> {
            //pathProfile.saveProfile();
            setVisible(false);
            dispose();
        });
    }

    public ListModel<String> getListModel() {
        return listModel;
    }

}
