package Source.Scenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

import Source.Logic.Audio;
import Source.Logic.GameEngine;

public class MainMenu extends JFrame {
    private Audio audio;
    private GameEngine game;
    private Font helvetiHandFont;

    public MainMenu() {
        // Font
        try {
            helvetiHandFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Assets/Fonts/HelvetiHand.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(helvetiHandFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // Frame
        setTitle("Main Menu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        // Music
        audio = new Audio();
        audio.playMusic("src/Assets/Sounds/main_menu_music.wav");

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title Image
        ImageIcon titleImage = new ImageIcon("src/Assets/Images/title_image.png");
        JLabel titleLabel = new JLabel(titleImage);
        titleLabel.setBounds(525, 25, titleImage.getIconWidth(), titleImage.getIconHeight());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setIcon(new ImageIcon(titleImage.getImage().getScaledInstance(500,200, Image.SCALE_SMOOTH)));
        panel.add(titleLabel);

        // Start Button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(helvetiHandFont);
        startButton.setForeground(Color.WHITE);
        startButton.setContentAreaFilled(false);
        startButton.setHorizontalAlignment(SwingConstants.LEFT);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.setBounds(100, 450, 180, 75);
        startButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            )
        ));

        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                Point originalLocation = startButton.getLocation();
                Timer timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            startButton.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        }
                        else {
                            startButton.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            startButton.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setForeground(Color.WHITE);
            }
            
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                audio.stopMusic();
                game = new GameEngine();
                game.startGame();
                dispose();
            }
        });
        panel.add(startButton);

        // Load Button
        JButton loadButton = new JButton("Load Game");
        loadButton.setFont(helvetiHandFont);
        loadButton.setForeground(Color.WHITE);
        loadButton.setContentAreaFilled(false);
        loadButton.setHorizontalAlignment(SwingConstants.LEFT);
        loadButton.setFocusPainted(false);
        loadButton.setBorderPainted(false);
        loadButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loadButton.setBounds(100, 510, 180, 75);
        loadButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        loadButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loadButton.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                Point originalLocation = loadButton.getLocation();
                Timer timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            loadButton.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        }
                        else {
                            loadButton.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            loadButton.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loadButton.setForeground(Color.WHITE);
            }
            
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                audio.stopMusic();
                game = new GameEngine();
                game.loadGame();
                dispose();
            }
        });
        panel.add(loadButton);

        // Setting Button
        JButton settingButton = new JButton("Settings");
        settingButton.setFont(helvetiHandFont);
        settingButton.setForeground(Color.WHITE);
        settingButton.setContentAreaFilled(false);
        settingButton.setHorizontalAlignment(SwingConstants.LEFT);
        settingButton.setFocusPainted(false);
        settingButton.setBorderPainted(false);
        settingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingButton.setBounds(100, 570, 125, 75);
        settingButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        settingButton.addMouseListener(new java.awt.event.MouseAdapter() {
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
                        }
                        else {
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
            
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                audio.stopMusic();
                Setting setting = new Setting(audio, MainMenu.this);
                dispose();
            }
        });
        panel.add(settingButton);
        
        // Exit Button
        JButton exitButton = new JButton("Exit Game");
        exitButton.setFont(helvetiHandFont);
        exitButton.setForeground(Color.WHITE);
        exitButton.setContentAreaFilled(false);
        exitButton.setHorizontalAlignment(SwingConstants.LEFT);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setBounds(100, 630, 160, 75);
        exitButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setForeground(Color.RED);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                Point originalLocation = exitButton.getLocation();
                Timer timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            exitButton.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        }
                        else {
                            exitButton.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            exitButton.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setForeground(Color.WHITE);
            }
            
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                UIManager.put("OptionPane.background", Color.BLACK);
                UIManager.put("Panel.background", Color.BLACK);
                UIManager.put("OptionPane.messageForeground", Color.WHITE);
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        panel.add(exitButton);

        // Background Image
        ImageIcon backgroundImage = new ImageIcon("src/Assets/Images/main_menu_background.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        panel.add(backgroundLabel);
        setVisible(true);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH)));

        add(panel);
        setVisible(true);
    }
}