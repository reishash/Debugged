package Source.Scenes;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        setTitle("Scene");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Background Label
        backgroundLabel = new JLabel();

        // Character Label
        characterLabel = new JLabel();

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setFont(helvetiHandFont);
        saveButton.setForeground(Color.WHITE);
        saveButton.setContentAreaFilled(false);
        saveButton.setHorizontalAlignment(SwingConstants.LEFT);
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.setBounds(100, 50, 200, 50);
        saveButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(saveButton);

        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                gameEngine.saveGame(sceneID);
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
        settingButton.setBounds(300, 50, 200, 50);
        settingButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(settingButton);

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
        hideUIButton.setBounds(500, 50, 200, 50);
        hideUIButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(hideUIButton);

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
        logButton.setBounds(700, 50, 200, 50);
        logButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(logButton);

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
        sceneUpdater.setBounds(0, 0, 1920, 1080);
        sceneUpdater.setOpaque(false);
        sceneUpdater.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sceneUpdater.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                sceneID++;
                updateScene(panel);
            }
        });
        panel.add(sceneUpdater);
    
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
            storyText.setBounds(0, 600, 1600, 800);
            storyText.setVerticalAlignment(SwingConstants.TOP);
            storyText.setHorizontalAlignment(SwingConstants.LEFT);
            storyText.setBorder(BorderFactory.createEmptyBorder(50, 100, 0, 200));
            panel.add(storyText, Integer.valueOf(0));
            previousComponent = storyText;
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
        characterLabel.setBounds(400, 100, 800, 800);
        characterLabel.setIcon(new ImageIcon(characterImage.getImage().getScaledInstance(800, 800, java.awt.Image.SCALE_FAST)));
        panel.add(characterLabel);

        // Set background image
        String bgPath;
        switch (sceneID) {
            default:
                bgPath = "";
                break;
        }
        ImageIcon backgroundImage = new ImageIcon(bgPath);
        backgroundLabel.setIcon(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_FAST)));
        panel.add(backgroundLabel);

        panel.setBackground(Color.BLACK);
        panel.revalidate();
        panel.repaint();
    }
}