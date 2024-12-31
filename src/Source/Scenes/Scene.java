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
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        backButton.setHorizontalAlignment(SwingConstants.CENTER);
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
                    audio.stopMusic();
                    audio.playSFX("src/Assets/Sounds/menu_select.wav");
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
                    audio.playSFX("src/Assets/Sounds/menu_select.wav");
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
        saveButton.setHorizontalAlignment(SwingConstants.CENTER);
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
                JLabel messageLabel = new JLabel("Do you want to save your progress?", JLabel.CENTER);
                messageLabel.setForeground(Color.WHITE);
                messageLabel.setFont(helvetiHandFont);
                panel.add(messageLabel, BorderLayout.CENTER);
                JPanel buttonPanel = new JPanel();
                buttonPanel.setBackground(Color.BLACK);
                buttonPanel.setLayout(new FlowLayout());
                JButton yesButton = new JButton("Yes");
                yesButton.addActionListener(e -> {
                    gameEngine.saveGame(sceneID);
                    dialog.dispose();
                    if (gameEngine.isGameSaved(sceneID)) {
                        JLabel savedLabel = new JLabel("Game Saved Successfully!", JLabel.CENTER);
                        savedLabel.setForeground(Color.GREEN);
                        savedLabel.setFont(helvetiHandFont);
                        panel.add(savedLabel, BorderLayout.CENTER);
                        Timer timer = new Timer(2000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                panel.remove(savedLabel);
                                panel.revalidate();
                                panel.repaint();
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                    } else {
                        JLabel errorLabel = new JLabel("Failed to Save Game!", JLabel.CENTER);
                        errorLabel.setForeground(Color.RED);
                        errorLabel.setFont(helvetiHandFont);
                        panel.add(errorLabel, BorderLayout.CENTER);
                        Timer timer = new Timer(2000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                panel.remove(errorLabel);
                                panel.revalidate();
                                panel.repaint();
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                    }
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
        settingButton.setHorizontalAlignment(SwingConstants.CENTER);
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
                new Setting(audio, Scene.this);
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
        hideUIButton.setHorizontalAlignment(SwingConstants.CENTER);
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
        logButton.setHorizontalAlignment(SwingConstants.CENTER);
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
                new LogWindow(sceneID);
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
                // if (sceneID == 9999) {
                //     // Choice
                //     JDialog choiceDialog = new JDialog();
                //     choiceDialog.setUndecorated(true);
                //     choiceDialog.setModal(true);
                //     JPanel choicePanel = new JPanel();
                //     choicePanel.setBackground(Color.BLACK);
                //     choicePanel.setLayout(new BorderLayout());
                //     JLabel choiceLabel = new JLabel("[Choice:]", JLabel.CENTER);
                //     choiceLabel.setForeground(Color.WHITE);
                //     choiceLabel.setFont(helvetiHandFont);
                //     choicePanel.add(choiceLabel, BorderLayout.CENTER);
                //     JPanel choiceButtonPanel = new JPanel();
                //     choiceButtonPanel.setBackground(Color.BLACK);
                //     choiceButtonPanel.setLayout(new FlowLayout());
                //     JButton choiceAButton = new JButton("<html><h2>Sacrifice Byte to leave Nullspace.</h2></html>");
                //     choiceAButton.setFont(helvetiHandFont);
                //     choiceAButton.setForeground(Color.WHITE);
                //     choiceAButton.setContentAreaFilled(false);
                //     choiceAButton.setBorderPainted(false);
                //     choiceAButton.addActionListener(e -> {
                //         sceneID = 10000; 
                //         choiceAButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                //         choiceDialog.dispose();
                //         updateScene(panel);
                //     });
                //     choiceButtonPanel.add(choiceAButton);
                //     JButton choiceBButton = new JButton("<html><h2>Refuse to leave, staying with Byte.</h2></html>");
                //     choiceBButton.setFont(helvetiHandFont);
                //     choiceBButton.setForeground(Color.WHITE);
                //     choiceBButton.setContentAreaFilled(false);
                //     choiceBButton.setBorderPainted(false);
                //     choiceBButton.addActionListener(e -> {
                //         sceneID = 11000; 
                //         choiceBButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                //         choiceDialog.dispose();
                //         updateScene(panel);
                //     });
                //     choiceButtonPanel.add(choiceBButton);
                //     choicePanel.add(choiceButtonPanel, BorderLayout.SOUTH);
                //     choiceDialog.getContentPane().add(choicePanel);
                //     choiceDialog.pack();
                //     choiceDialog.setLocationRelativeTo(null);
                //     choiceDialog.setVisible(true);
                // } else {
                    sceneID++;
                // }
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
                case 129:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Sounds/4.wav");
                    break;
                case 176:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Sounds/5.wav");
                    break;
                case 228:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Sounds/6.wav");
                    break;
                case 276:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Sounds/7.wav");
                    break;
                case 324:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Sounds/8.wav");
                    break;
            }
        }
    
        // Set story text
        if (storyText != null) {
            storyText.setFont(helvetiHandFont);
            storyText.setForeground(new Color(255, 255, 197));
            if (sceneID == 1 || sceneID == 39 || sceneID == 86 || sceneID == 129 || sceneID == 176 || sceneID == 228 || sceneID == 276 || sceneID == 324) {
                storyText.setVerticalAlignment(SwingConstants.CENTER);
                storyText.setHorizontalAlignment(SwingConstants.CENTER);
                layout.putConstraint(SpringLayout.VERTICAL_CENTER, storyText, 0, SpringLayout.VERTICAL_CENTER, panel);
            } else {
                storyText.setVerticalAlignment(SwingConstants.TOP);
                storyText.setHorizontalAlignment(SwingConstants.LEFT);
                layout.putConstraint(SpringLayout.SOUTH, storyText, -100, SpringLayout.SOUTH, panel);
            }
            panel.add(storyText, Integer.valueOf(0));
            previousComponent = storyText;
            layout.putConstraint(SpringLayout.WEST, storyText, 200, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.EAST, storyText, -200, SpringLayout.EAST, panel);
        }

        // Set character image
        String charPath;
        switch (sceneID) {
            case 63, 64, 66, 68, 69, 70, 71, 72, 73, 82, 83:
                charPath = "src/Assets/Images/raina.png";
                break;
            case 103, 106, 113, 141, 142, 143, 144, 145, 146:
                charPath = "src/Assets/Images/elara_young.png";
                break;
            case 97, 98, 99, 101, 102, 105, 107, 109, 110, 111, 112, 114:
                charPath = "src/Assets/Images/grandma.png";
                break;
            case 161, 162:
                charPath = "src/Assets/Images/elara_old.png";
                break;
            case 22, 23, 24, 25, 26, 27, 28, 29, 30, 33, 34, 35, 36, 37, 47, 48, 49, 50, 51, 52, 55, 56, 74, 75, 78, 79, 80, 81, 123, 124, 125, 170, 171, 172:
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
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, characterLabel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.SOUTH, characterLabel, 0, SpringLayout.SOUTH, panel);


        // Set background image
        String bgPath;
        switch (sceneID) {
            case 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15:
                bgPath = "src/Assets/Images/bg_roomelara.jpg";
                break;
            case 16, 17, 61, 62, 63, 64, 65, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 92, 107, 108, 109, 114, 115, 116, 117, 122, 170:
                bgPath = "src/Assets/Images/bg_glitch.jpg";
                break;
            case 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 45, 46, 47, 48, 49, 50, 51, 52, 123, 124, 125, 171, 172:
                bgPath = "src/Assets/Images/bg_nullspace.jpg";
                break;
            case 53, 54, 55, 56, 57, 58, 59, 60:
                bgPath = "src/Assets/Images/bg_nullspace2.jpg";
                break;
            case 66, 67, 93, 94, 95, 96, 134, 140, 147:
                bgPath = "src/Assets/Images/bg_fracture.jpg";
                break;
            case 97, 98, 99:
                bgPath = "src/Assets/Images/bg_roomgrandma.jpg";
                break;
            case 100, 101, 102:
                bgPath = "src/Assets/Images/bg_roomdining.jpg";
                break;
            case 103, 104, 105, 106:
                bgPath = "src/Assets/Images/bg_roomwinter.jpg";
                break;
            case 110, 111, 112, 113:
                bgPath = "src/Assets/Images/bg_roomhospital.jpg";
                break;
            case 132, 133, 168, 169:
                bgPath = "src/Assets/Images/bg_map.jpg";
                break;
            case 135, 136, 137, 138, 139:
                bgPath = "src/Assets/Images/bg_roomflag.jpg";
                break;
            case 141, 142, 143, 144, 145, 146:
                bgPath = "src/Assets/Images/bg_roommap.jpg";
                break;
            case 148, 149, 150, 151, 152, 153, 154:
                bgPath = "src/Assets/Images/bg_roomclass.jpg";
                break;
            case 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167:
                bgPath = "src/Assets/Images/bg_roomflag2.jpg";
                break;
            default:
                bgPath = "";
                break;
        }
        ImageIcon backgroundImage = new ImageIcon(bgPath);
        backgroundLabel.setIcon(backgroundImage);
        setVisible(true);
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_FAST)));
        panel.add(backgroundLabel);
        layout.putConstraint(SpringLayout.WEST, backgroundLabel, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, backgroundLabel, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, backgroundLabel, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backgroundLabel, 0, SpringLayout.SOUTH, panel);

        // Triangle Button
        JButton triangleButton = new JButton("<html><h2>▼</h2></html>");
        triangleButton.setForeground(Color.WHITE);
        triangleButton.setContentAreaFilled(false);
        triangleButton.setBorderPainted(false);
        if (sceneID != 1 && sceneID != 39 && sceneID != 86 && sceneID != 129 && sceneID != 176 && sceneID != 228 && sceneID != 276 && sceneID != 324) {
            panel.add(triangleButton);
        }
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, triangleButton, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.SOUTH, triangleButton, -10, SpringLayout.SOUTH, panel);

        panel.setBackground(Color.BLACK);
        panel.revalidate();
        panel.repaint();

        if (storyText == null) {
            gameEngine.initializeMainMenu();
            dispose();
        }
    }
}