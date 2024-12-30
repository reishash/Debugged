package Source.Scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Source.Logic.Audio;
import Source.Logic.GameEngine;
import Assets.Scripts.Script;

public class Scene extends JFrame {
    GameEngine gameEngine = new GameEngine();
    private Font helvetiHandFont;
    private int sceneID;
    private boolean isFirstScene;
    private JLabel backgroundLabel, characterLabel;
    private Audio audio;
    private SpringLayout layout;

    public Scene(int SceneID) {
        sceneID = SceneID;
        isFirstScene = true;
        
        // Load Font
        try {
            helvetiHandFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Assets/Fonts/HelvetiHand.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(helvetiHandFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Music
        Audio audio = new Audio();
        audio.stopMusic();

        // Frame
        setTitle("Debugged");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        // Panel
        JPanel panel = new JPanel();
        layout = new SpringLayout();
        panel.setLayout(layout);

        // Background Label
        backgroundLabel = new JLabel();

        // Character Label
        characterLabel = new JLabel();

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(helvetiHandFont);
        backButton.setForeground(Color.WHITE);
        backButton.setContentAreaFilled(false);
        backButton.setHorizontalAlignment(SwingConstants.LEFT);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(backButton);
        layout.putConstraint(SpringLayout.WEST, backButton, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, backButton, 50, SpringLayout.NORTH, panel);
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                JDialog dialog = new JDialog();
                dialog.setUndecorated(true);
                dialog.setModal(true);
                JPanel panel = new JPanel();
                panel.setBackground(Color.BLACK);
                panel.setLayout(new BorderLayout());
                JLabel messageLabel = new JLabel("Are you sure you want to go back to the main menu?", JLabel.CENTER);
                messageLabel.setForeground(Color.WHITE);
                messageLabel.setFont(helvetiHandFont);
                panel.add(messageLabel, BorderLayout.CENTER);
                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                buttonPanel.setLayout(new FlowLayout());
                JButton yesButton = new JButton("<html><h2>Yes</h2></html>");
                yesButton.setFont(helvetiHandFont);
                yesButton.setForeground(Color.WHITE);
                yesButton.setContentAreaFilled(false);
                yesButton.setBorderPainted(false);
                yesButton.addActionListener(e -> {
                    gameEngine.initializeMainMenu();
                    dialog.dispose();
                    dispose();
                });
                buttonPanel.add(yesButton);
                JButton noButton = new JButton("<html><h2>No</h2></html>");
                noButton.setFont(helvetiHandFont);
                noButton.setForeground(Color.WHITE);
                noButton.setContentAreaFilled(false);
                noButton.setBorderPainted(false);
                noButton.addActionListener(e -> {
                    dialog.dispose();
                });
                buttonPanel.add(noButton);
                panel.add(buttonPanel, BorderLayout.SOUTH);
                dialog.getContentPane().add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                Point originalLocation = backButton.getLocation();
                Timer timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            backButton.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        } else {
                            backButton.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            backButton.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setForeground(Color.WHITE);
            }
        });

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setFont(helvetiHandFont);
        saveButton.setForeground(Color.WHITE);
        saveButton.setContentAreaFilled(false);
        saveButton.setHorizontalAlignment(SwingConstants.LEFT);
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(saveButton);
        layout.putConstraint(SpringLayout.WEST, saveButton, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, saveButton, 50, SpringLayout.NORTH, panel);
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                JDialog dialog = new JDialog();
                dialog.setUndecorated(true);
                dialog.setModal(true);
                JPanel panel = new JPanel();
                panel.setBackground(Color.BLACK);
                panel.setLayout(new BorderLayout());
                JLabel messageLabel = new JLabel("Are you sure you want to go back to the main menu?", JLabel.CENTER);
                messageLabel.setForeground(Color.WHITE);
                messageLabel.setFont(helvetiHandFont);
                panel.add(messageLabel, BorderLayout.CENTER);
                JPanel buttonPanel = new JPanel();
                buttonPanel.setBackground(Color.BLACK);
                buttonPanel.setLayout(new FlowLayout());
                JButton yesButton = new JButton("Yes");
                yesButton.addActionListener(e -> {
                    gameEngine.initializeMainMenu();
                    dialog.dispose();
                    dispose();
                });
                buttonPanel.add(yesButton);

                JButton noButton = new JButton("No");
                noButton.addActionListener(e -> {
                    dialog.dispose();
                });
                buttonPanel.add(noButton);
                panel.add(buttonPanel, BorderLayout.SOUTH);
                dialog.getContentPane().add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveButton.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                Point originalLocation = saveButton.getLocation();
                Timer timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            saveButton.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        } else {
                            saveButton.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            saveButton.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveButton.setForeground(Color.WHITE);
            }
        });

        // Setting Button
        JButton settingButton = new JButton("Settings");
        settingButton.setFont(helvetiHandFont);
        settingButton.setForeground(Color.WHITE);
        settingButton.setContentAreaFilled(false);
        settingButton.setHorizontalAlignment(SwingConstants.LEFT);
        settingButton.setFocusPainted(false);
        settingButton.setBorderPainted(false);
        settingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(settingButton);
        layout.putConstraint(SpringLayout.WEST, settingButton, 400, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, settingButton, 50, SpringLayout.NORTH, panel);
        settingButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                Setting setting = new Setting(audio, Scene.this);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                settingButton.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                Point originalLocation = settingButton.getLocation();
                Timer timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            settingButton.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        } else {
                            settingButton.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            settingButton.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                settingButton.setForeground(Color.WHITE);
            }
        });

        // Hide UI Button
        JButton hideUIButton = new JButton("Hide UI");
        hideUIButton.setFont(helvetiHandFont);
        hideUIButton.setForeground(Color.WHITE);
        hideUIButton.setContentAreaFilled(false);
        hideUIButton.setHorizontalAlignment(SwingConstants.LEFT);
        hideUIButton.setFocusPainted(false);
        hideUIButton.setBorderPainted(false);
        hideUIButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hideUIButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(hideUIButton);
        layout.putConstraint(SpringLayout.WEST, hideUIButton, 550, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, hideUIButton, 50, SpringLayout.NORTH, panel);
        hideUIButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hideUIButton.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                Point originalLocation = hideUIButton.getLocation();
                Timer timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            hideUIButton.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        } else {
                            hideUIButton.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            hideUIButton.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hideUIButton.setForeground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                for (Component component : panel.getComponents()) {
                    if (component != hideUIButton && component != backgroundLabel && component != characterLabel) {
                        component.setVisible(!component.isVisible());
                    }
                }
            }
        });

        // Log button
        JButton logButton = new JButton("Log");
        logButton.setFont(helvetiHandFont);
        logButton.setForeground(Color.WHITE);
        logButton.setContentAreaFilled(false);
        logButton.setHorizontalAlignment(SwingConstants.LEFT);
        logButton.setFocusPainted(false);
        logButton.setBorderPainted(false);
        logButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(logButton);
        layout.putConstraint(SpringLayout.WEST, logButton, 700, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, logButton, 50, SpringLayout.NORTH, panel);

        logButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logButton.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                Point originalLocation = logButton.getLocation();
                Timer timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            logButton.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        } else {
                            logButton.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            logButton.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logButton.setForeground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                LogWindow logWindow = new LogWindow(sceneID);
            }
        });

        // Scene updater
        JPanel sceneUpdater = new JPanel();
        sceneUpdater.setOpaque(false);
        sceneUpdater.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sceneUpdater.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                if (sceneID == 1000) {
                    // Choice
                    JDialog choiceDialog = new JDialog();
                    choiceDialog.setUndecorated(true);
                    choiceDialog.setModal(true);
                    JPanel choicePanel = new JPanel();
                    choicePanel.setBackground(Color.BLACK);
                    choicePanel.setLayout(new BorderLayout());
                    JLabel choiceLabel = new JLabel("[Choice:]", JLabel.CENTER);
                    choiceLabel.setForeground(Color.WHITE);
                    choiceLabel.setFont(helvetiHandFont);
                    choicePanel.add(choiceLabel, BorderLayout.CENTER);
                    JPanel choiceButtonPanel = new JPanel();
                    choiceButtonPanel.setBackground(Color.BLACK);
                    choiceButtonPanel.setLayout(new FlowLayout());
                    JButton choiceAButton = new JButton("<html><h2>Sacrifice Byte to leave Nullspace.</h2></html>");
                    choiceAButton.setFont(helvetiHandFont);
                    choiceAButton.setForeground(Color.WHITE);
                    choiceAButton.setContentAreaFilled(false);
                    choiceAButton.setBorderPainted(false);
                    choiceAButton.addActionListener(e -> {
                        sceneID = 2000; 
                        choiceAButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        choiceDialog.dispose();
                        updateScene(panel);
                    });
                    choiceButtonPanel.add(choiceAButton);
                    JButton choiceBButton = new JButton("<html><h2>Refuse to leave, staying with Byte.</h2></html>");
                    choiceBButton.setFont(helvetiHandFont);
                    choiceBButton.setForeground(Color.WHITE);
                    choiceBButton.setContentAreaFilled(false);
                    choiceBButton.setBorderPainted(false);
                    choiceBButton.addActionListener(e -> {
                        sceneID = 3000; 
                        choiceBButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        choiceDialog.dispose();
                        updateScene(panel);
                    });
                    choiceButtonPanel.add(choiceBButton);
                    choicePanel.add(choiceButtonPanel, BorderLayout.SOUTH);
                    choiceDialog.getContentPane().add(choicePanel);
                    choiceDialog.pack();
                    choiceDialog.setLocationRelativeTo(null);
                    choiceDialog.setVisible(true);
                } else {
                    sceneID++;
                }
                updateScene(panel);
            }
        });
        panel.add(sceneUpdater);
        layout.putConstraint(SpringLayout.WEST, sceneUpdater, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, sceneUpdater, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, sceneUpdater, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, sceneUpdater, 0, SpringLayout.SOUTH, panel);

    
        updateScene(panel);
        add(panel);
        setVisible(true);
    }

    private Component previousComponent;

    // Update scene based on scene ID
    private void updateScene(JPanel panel) {
        setVisible(true);
        if (!isFirstScene) {
            for (Component component : panel.getComponents()) {
                if (component == previousComponent) {
                    panel.remove(component);
                }
            }
        } else {
            isFirstScene = false;
        }
        String[] storyTexts = Script.storyTexts;
        JLabel storyText = null;
        Audio audio = new Audio();
        if (sceneID > 0 && sceneID <= storyTexts.length) {
            storyText = new JLabel(storyTexts[sceneID - 1]);
            switch (sceneID) {
                case 1:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Sounds/1.wav");
                    break;
                case 39:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Sounds/2.wav");
                    break;
                case 86:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Sounds/3.wav");
                    break;
                case 128:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Sounds/4.wav");
                    break;
            }
        }
    
        // Set story text
        if (storyText != null) {
            storyText.setFont(helvetiHandFont);
            storyText.setForeground(new Color(255, 255, 197));
            storyText.setVerticalAlignment(SwingConstants.TOP);
            storyText.setHorizontalAlignment(SwingConstants.LEFT);
            panel.add(storyText, Integer.valueOf(0));
            previousComponent = storyText;
            layout.putConstraint(SpringLayout.WEST, storyText, 200, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, storyText, 650, SpringLayout.NORTH, panel);
            layout.putConstraint(SpringLayout.EAST, storyText, -200, SpringLayout.EAST, panel);
            layout.putConstraint(SpringLayout.SOUTH, storyText, 0, SpringLayout.SOUTH, panel);
        }
        else {
            gameEngine.initializeMainMenu();
            dispose();
        }

        // Set character image
        String charPath;
        switch (sceneID) {
            case 63, 64:
                charPath = "src/Assets/Images/raina.png";
                break;
            case 102, 105, 142, 143, 145, 153:
                charPath = "src/Assets/Images/elara_young.png";
                break;
            case 104, 111:
                charPath = "src/Assets/Images/grandma.png";
                break;
            case 160:
                charPath = "src/Assets/Images/elara_old.png";
                break;
            case 23, 25, 28, 30, 34, 35, 36, 47, 49, 50, 52, 55, 56, 74, 75, 78, 79, 81, 122, 124:
                charPath = "src/Assets/Images/byte.png";
                break;
            default:
                charPath = "";
                break;
        }
        ImageIcon characterImage = new ImageIcon(charPath);
        characterLabel.setIcon(characterImage);
        characterLabel.setIcon(new ImageIcon(characterImage.getImage().getScaledInstance(800, 800, java.awt.Image.SCALE_FAST)));
        panel.add(characterLabel);
        layout.putConstraint(SpringLayout.WEST, characterLabel, 400, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, characterLabel, 100, SpringLayout.NORTH, panel);


        // Set background image
        String bgPath;
        switch (sceneID) {
            case 3, 4, 5, 6, 7, 8, 9:
                bgPath = "src/Assets/Images/bg_roomelara.jpg";
                break;
            case 10, 11, 12, 13, 14, 15:
                bgPath = "src/Assets/Images/bg_code.jpg";
                break;
            case 16, 17:
                bgPath = "src/Assets/Images/bg_glitch.jpg";
            case 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37:
                bgPath = "src/Assets/Images/bg_nullspace.jpg";
                break;
            default:
                bgPath = "";
                break;
        }
        ImageIcon backgroundImage = new ImageIcon(bgPath);
        backgroundLabel.setIcon(backgroundImage);
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_FAST)));
        panel.add(backgroundLabel);
        layout.putConstraint(SpringLayout.WEST, backgroundLabel, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, backgroundLabel, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, backgroundLabel, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backgroundLabel, 0, SpringLayout.SOUTH, panel);

        panel.setBackground(Color.BLACK);
        panel.revalidate();
        panel.repaint();
    }
}