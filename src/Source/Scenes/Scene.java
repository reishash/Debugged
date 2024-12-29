package Source.Scenes;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
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
        Audio music = new Audio();
        music.playSFX("src/Assets/Sounds/start_click.wav");

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

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setFont(helvetiHandFont);
        saveButton.setForeground(Color.WHITE);
        saveButton.setContentAreaFilled(false);
        saveButton.setHorizontalAlignment(SwingConstants.LEFT);
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.setBounds(50, 25, 200, 50);
        saveButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(saveButton);

        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                music.playSFX("src/Assets/Sounds/start_click.wav");
                gameEngine.saveGame(sceneID);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveButton.setForeground(Color.YELLOW);
                music.playSFX("src/Assets/Sounds/menu_hover.wav");
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
        settingButton.setBounds(250, 25, 200, 50);
        settingButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(settingButton);

        settingButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                music.playSFX("src/Assets/Sounds/start_click.wav");
                Setting setting = new Setting(music);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                settingButton.setForeground(Color.YELLOW);
                music.playSFX("src/Assets/Sounds/menu_hover.wav");
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
        hideUIButton.setBounds(500, 25, 200, 50);
        hideUIButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(hideUIButton);

        hideUIButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hideUIButton.setForeground(Color.YELLOW);
                music.playSFX("src/Assets/Sounds/menu_hover.wav");
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
                music.playSFX("src/Assets/Sounds/start_click.wav");
                for (Component component : panel.getComponents()) {
                    if (component != hideUIButton) {
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
        logButton.setBounds(700, 25, 200, 50);
        logButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.add(logButton);

        logButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logButton.setForeground(Color.YELLOW);
                music.playSFX("src/Assets/Sounds/menu_hover.wav");
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
                music.playSFX("src/Assets/Sounds/start_click.wav");
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
                music.playSFX("src/Assets/Sounds/start_click.wav");
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
            if (sceneID == 1) {
                audio.stopMusic();
            }
        }
    
        if (storyText != null) {
            storyText.setFont(helvetiHandFont);
            storyText.setForeground(Color.WHITE);
            storyText.setBounds(50, 450, 1400, 800);
            storyText.setVerticalAlignment(SwingConstants.TOP);
            panel.add(storyText, Integer.valueOf(0)); // Add on top of background
            previousComponent = storyText;
        }
    
        panel.setBackground(Color.BLACK);
        panel.revalidate();
        panel.repaint();
    }
}