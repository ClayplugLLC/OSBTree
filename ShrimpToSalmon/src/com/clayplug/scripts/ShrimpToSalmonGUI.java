package com.clayplug.scripts;

import com.clayplug.activities.skilling.survival.fishing.setters.Species;
import com.clayplug.activities.skilling.survival.fishing.tasks.actions.Fish;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class ShrimpToSalmonGUI extends JInternalFrame {

    private static JLabel nowDoing;
    private static JLabel locationLabel;
    private static JLabel consumablesLeft;
    private static JLabel currentLevel;
    private static JLabel expTilNextLevel;
    private static JRadioButton bankButton;
    private static JRadioButton dropButton;
    private static JCheckBox dropOtherFish;
    private static boolean choseToBank = true;
    private static boolean choseToDrop = false;
    private static boolean choseToDropOther = false;
    public static boolean getChoseToBank() {
        return choseToBank;
    }
    public static void setChoseToBank(boolean choseToBank) {
        ShrimpToSalmonGUI.choseToBank = choseToBank;
    }
    public static boolean getChoseToDrop() {
        return choseToDrop;
    }
    public static void setChoseToDrop(boolean choseToDrop) {
        ShrimpToSalmonGUI.choseToDrop = choseToDrop;
    }
    public static boolean getChoseToDropOther() { return choseToDropOther; }
    public static void setChoseToDropOther(boolean choseToDropOther) { ShrimpToSalmonGUI.choseToDropOther = choseToDropOther; }

    public ShrimpToSalmonGUI(MethodProvider api) {
        super("Clayplug's Shrimp to Salmon Fisher", false, true, false, true);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout());
        contentPane.setSize(new Dimension(400, 1000));
        setContentPane(contentPane);

        // Status
        nowDoing = new JLabel();
        nowDoing.setText("Fishing: " + Fish.getFishName());
        nowDoing.setPreferredSize(new Dimension(200, 20));
        contentPane.add(nowDoing);

        locationLabel = new JLabel();
        locationLabel.setText("At: " + Fish.getLocationName());
        locationLabel.setPreferredSize(new Dimension(200, 20));
        contentPane.add(locationLabel);

        String fishConsumable = Fish.getFishConsumable();
        String textConsLeft = api.getInventory().getAmount(fishConsumable) + " " + fishConsumable;
        if (fishConsumable == null) textConsLeft = "N/A";

        consumablesLeft = new JLabel();
        consumablesLeft.setText("Consumables left: " + textConsLeft);
        consumablesLeft.setPreferredSize(new Dimension(400, 20));
        contentPane.add(consumablesLeft, BorderLayout.CENTER);

        // EXP
        currentLevel = new JLabel();
        currentLevel.setText("Current Level: " + (api.getSkills()).getDynamic(Skill.FISHING));
        currentLevel.setPreferredSize(new Dimension(200, 20));
        contentPane.add(currentLevel, BorderLayout.AFTER_LAST_LINE);

        expTilNextLevel = new JLabel();
        expTilNextLevel.setText("EXP 'til Next Level: " + (api.getSkills()).experienceToLevel(Skill.FISHING));
        expTilNextLevel.setPreferredSize(new Dimension(200, 20));
        contentPane.add(expTilNextLevel);

        // Bank or Drop option
        bankButton = new JRadioButton("Bank");
        bankButton.setSelected(true);
        bankButton.setBounds(100, 50, 150, 30);
        bankButton.addActionListener(this::selectBankOrDrop);
        dropButton = new JRadioButton("Drop");
        dropButton.setBounds(100, 100, 150, 30);
        dropButton.addActionListener(this::selectBankOrDrop);
        ButtonGroup bankOrDropGroup = new ButtonGroup();
        bankOrDropGroup.add(bankButton);
        bankOrDropGroup.add(dropButton);
        contentPane.add(bankButton);
        contentPane.add(dropButton);

        // Select Fish
        String[] fishNames = {"Shrimp", "Anchovies", "Trout", "Salmon"};
        JComboBox<String> fishList = new JComboBox<>(fishNames);
        fishList.setSelectedIndex(getFishIndex());
        fishList.addActionListener(this::selectFish);
        contentPane.add(fishList);

        // Select Location
        String[] locationNames = {"Lumbridge Swamp", "Lumbridge River", "Barbarian Village"};
        JComboBox<String> locationList = new JComboBox<>(locationNames);
        locationList.setSelectedIndex(getLocationIndex());
        locationList.addItemListener(this::selectLocation);
        contentPane.add(locationList);

        // Drop other fish option
        dropOtherFish = new JCheckBox("Drop other fish");
        dropOtherFish.setSelected(false);
        dropOtherFish.setBounds(100, 150, 150, 30);
        dropOtherFish.addActionListener(this::selectDropOtherFish);
        contentPane.add(dropOtherFish);


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        // call onCancel() on ESCAPE
        contentPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "onCancel() calling");
        contentPane.getActionMap()
                .put("onCancel() calling", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        onCancel();
                    }
                });
//        discordLink.setText("Visit our Discord!");
//        discordLink.setForeground(Color.BLUE.darker());
//        discordLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//
//        discordLink.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent mouseEvent) {
//                try {
//                    if (Desktop.isDesktopSupported()) {
//                        Desktop.getDesktop().browse(new URI("https://www.discord.com"));
//                    } else {
//                        api.log("This desktop is not supported by java.awt.Desktop");
//                    }
//                } catch (IOException | URISyntaxException e1) {
//                    api.log("error on mouse click : discordLink");
//                    e1.printStackTrace();
//                }
//            }
//        });
    }

    private void selectDropOtherFish(ActionEvent e) {
        setChoseToDropOther(dropOtherFish.isSelected());
    }


    //
    private void onCancel() {
        dispose();
    }

    public static void update(MethodProvider api) {
        nowDoing.setText("Fishing: " + Fish.getFishName());
        locationLabel.setText("At: " + Fish.getLocationName());
        String fishConsumable = Fish.getFishConsumable();
        String textConsLeft = api.getInventory().getAmount(fishConsumable) + " " + fishConsumable;
        if (fishConsumable == null) textConsLeft = "N/A";
        consumablesLeft.setText("Consumables Left: " + textConsLeft);
        currentLevel.setText("Current Level: " + (api.getSkills()).getDynamic(Skill.FISHING));
        expTilNextLevel.setText("EXP 'til Next Level: " + (api.getSkills()).experienceToLevel(Skill.FISHING));
    }

    public void selectBankOrDrop(ActionEvent e) {
        if (bankButton.isSelected()) {
            setChoseToBank(true);
            setChoseToDrop(false);
        } else if (dropButton.isSelected()) {
            setChoseToBank(false);
            setChoseToDrop(true);
        }
    }

    private int getFishIndex() {
        switch (Fish.getFishName()) {
            case "Raw anchovies":
                return 1;
            case "Raw trout":
                return 2;
            case "Raw salmon":
                return 3;
            default:
                return 0;
        }
    }

    private void selectFish(ActionEvent e) {
        JComboBox<String> cb = (JComboBox<String>) e.getSource();
        String fishName = (String) cb.getSelectedItem();
        switch (Objects.requireNonNull(fishName)) {
            case "Shrimp":
                Fish.goFish(Species.SHRIMP);
                Fish.atLocation("LUMBRIDGE_SWAMP");
                break;
            case "Anchovies":
                Fish.goFish(Species.ANCHOVIES);
                Fish.atLocation("LUMBRIDGE_SWAMP");
                break;
            case "Trout":
                Fish.goFish(Species.TROUT);
                Fish.atLocation("BARBARIAN_VILLAGE");
                break;
            case "Salmon":
                Fish.goFish(Species.SALMON);
                Fish.atLocation("BARBARIAN_VILLAGE");
                break;
        }
    }

    private int getLocationIndex() {
        switch (Fish.getLocationName()) {
            case "LUMBRIDGE_RIVER":
                return 1;
            case "BARBARIAN_VILLAGE":
                return 2;
            default:
                return 0;
        }
    }

    private void selectLocation(ItemEvent e) {
        JComboBox<String> cb = (JComboBox<String>) e.getSource();
        String locationName = (String) cb.getSelectedItem();
        switch (Objects.requireNonNull(locationName)) {
            case "Lumbridge Swamp":
                Fish.atLocation("LUMBRIDGE_SWAMP");
                break;
            case "Lumbridge River":
                Fish.atLocation("LUMBRIDGE_RIVER");
                break;
            case "Barbarian Village":
                Fish.atLocation("BARBARIAN_VILLAGE");
                break;
        }
    }
}



//    /**
//     * Method generated by IntelliJ IDEA GUI Designer
//     * >>> IMPORTANT!! <<<
//     * DO NOT edit this method OR call it in your code!
//     *
//     * @noinspection ALL
//     */
//    private void $$$setupUI$$$() {
//
//        final JPanel panel1 = new JPanel();
//        panel1.setLayout(new CardLayout(0, 0));
//        contentPane.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//        tabbedPane1 = new JTabbedPane();
//        tabbedPane1.setAlignmentX(1.0f);
//        tabbedPane1.setTabLayoutPolicy(0);
//        tabbedPane1.setTabPlacement(3);
//        panel1.add(tabbedPane1, "Card1");
//        Status = new JPanel();
//        Status.setLayout(new CardLayout(0, 0));
//        tabbedPane1.addTab("Status", Status);
//        final JPanel panel2 = new JPanel();
//        panel2.setLayout(new GridBagLayout());
//        Status.add(panel2, "Card1");
//
//        nowDoing.setText("Now: ");
//        GridBagConstraints gbc;
//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.anchor = GridBagConstraints.WEST;
//        panel2.add(nowDoing, gbc);
//        locationLabel = new JLabel();
//        locationLabel.setText("At Location: ");
//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.anchor = GridBagConstraints.WEST;
//        panel2.add(locationLabel, gbc);
//        consumablesLeft = new JLabel();
//        consumablesLeft.setText("Consumables left: ");
//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.anchor = GridBagConstraints.WEST;
//        panel2.add(consumablesLeft, gbc);
//        EXP = new JPanel();
//        EXP.setLayout(new CardLayout(0, 0));
//        tabbedPane1.addTab("EXP", EXP);
//        final JPanel panel3 = new JPanel();
//        panel3.setLayout(new GridBagLayout());
//        EXP.add(panel3, "Card1");
//        currentExp = new JLabel();
//        currentExp.setText("Current EXP: ");
//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.anchor = GridBagConstraints.WEST;
//        panel3.add(currentExp, gbc);
//        currentLevel = new JLabel();
//        currentLevel.setText("Current Level: ");
//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.anchor = GridBagConstraints.WEST;
//        panel3.add(currentLevel, gbc);
//        expTilNextLevel = new JLabel();
//        expTilNextLevel.setText("EXP 'til Next Level: ");
//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.anchor = GridBagConstraints.WEST;
//        panel3.add(expTilNextLevel, gbc);
//        final JPanel panel4 = new JPanel();
//        panel4.setLayout(new CardLayout(0, 0));
//        tabbedPane1.addTab("Options", panel4);
//        final JPanel panel5 = new JPanel();
//        panel5.setLayout(new GridBagLayout());
//        tabbedPane1.addTab("About", panel5);
//        clayplugLogo = new JLabel();
//        clayplugLogo.setText("Label");
//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.anchor = GridBagConstraints.WEST;
//        panel5.add(clayplugLogo, gbc);
//        discordLink = new JLabel();
//        discordLink.setText("Label");
//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.anchor = GridBagConstraints.WEST;
//        panel5.add(discordLink, gbc);
//    }

