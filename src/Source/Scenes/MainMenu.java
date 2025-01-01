package Source.Scenes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SpringLayout;
import javax.swing.Timer;

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
        setTitle("Debugged");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        // Music
        audio = new Audio();
        audio.stopMusic();
        audio.playMusic("src/Assets/Sounds/main_menu_music.wav");

        // Panel
        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);

        // Title Image
        ImageIcon titleImage = new ImageIcon("src/Assets/Images/title_image.png");
        JLabel titleLabel = new JLabel(titleImage);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setIcon(new ImageIcon(titleImage.getImage().getScaledInstance(500,200, Image.SCALE_FAST)));
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, titleLabel, 25, SpringLayout.NORTH, panel);
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
        startButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            )
        ));
        layout.putConstraint(SpringLayout.WEST, startButton, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.SOUTH, startButton, -400, SpringLayout.SOUTH, panel);
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
        loadButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        layout.putConstraint(SpringLayout.WEST, loadButton, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.SOUTH, loadButton, -325, SpringLayout.SOUTH, panel);
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

        // Select Chapter Label
        JButton chapterButton = new JButton("Select Chapter");
        chapterButton.setFont(helvetiHandFont);
        chapterButton.setForeground(Color.WHITE);
        chapterButton.setContentAreaFilled(false);
        chapterButton.setHorizontalAlignment(SwingConstants.LEFT);
        chapterButton.setFocusPainted(false);
        chapterButton.setBorderPainted(false);
        chapterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chapterButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        layout.putConstraint(SpringLayout.WEST, chapterButton, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.SOUTH, chapterButton, -250, SpringLayout.SOUTH, panel);
        chapterButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                chapterButton.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                Point originalLocation = chapterButton.getLocation();
                Timer timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            chapterButton.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        }
                        else {
                            chapterButton.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            chapterButton.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                chapterButton.setForeground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                audio.stopMusic();
                new SelectChapter();
                dispose();
            }
        });
        panel.add(chapterButton);

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
        layout.putConstraint(SpringLayout.WEST, settingButton, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.SOUTH, settingButton, -175, SpringLayout.SOUTH, panel);
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
                new Setting(audio, MainMenu.this);
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
        exitButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        layout.putConstraint(SpringLayout.WEST, exitButton, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.SOUTH, exitButton, -100, SpringLayout.SOUTH, panel);
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
                JDialog dialog = new JDialog();
                dialog.setUndecorated(true);
                dialog.setModal(true);
                JPanel panel = new JPanel();
                panel.setBackground(Color.BLACK);
                panel.setLayout(new BorderLayout());
                JLabel messageLabel = new JLabel("Are you sure you want to exit?", JLabel.CENTER);
                messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                messageLabel.setForeground(Color.WHITE);
                messageLabel.setFont(helvetiHandFont);
                panel.add(messageLabel, BorderLayout.CENTER);
                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                buttonPanel.setLayout(new FlowLayout());
                JButton yesButton = new JButton("Yes");
                yesButton.setFont(helvetiHandFont);
                yesButton.setForeground(Color.WHITE);
                yesButton.setContentAreaFilled(false);
                yesButton.setBorderPainted(false);
                yesButton.addActionListener(e -> {
                    audio.stopMusic();
                    audio.playSFX("src/Assets/Sounds/menu_select.wav");
                    System.exit(0);
                });
                JButton noButton = new JButton("No");
                noButton.setFont(helvetiHandFont);
                noButton.setForeground(Color.WHITE);
                noButton.setContentAreaFilled(false);
                noButton.setBorderPainted(false);
                noButton.addActionListener(e -> {
                    audio.playSFX("src/Assets/Sounds/menu_select.wav");
                    dialog.dispose();
                });
                buttonPanel.add(yesButton);
                buttonPanel.add(noButton);
                panel.add(buttonPanel, BorderLayout.SOUTH);
                dialog.getContentPane().add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        panel.add(exitButton);

        // Credit Button
        JButton creditButton = new JButton("Credits");
        creditButton.setFont(helvetiHandFont);
        creditButton.setForeground(Color.WHITE);
        creditButton.setContentAreaFilled(false);
        creditButton.setHorizontalAlignment(SwingConstants.LEFT);
        creditButton.setFocusPainted(false);
        creditButton.setBorderPainted(false);
        creditButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        creditButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        layout.putConstraint(SpringLayout.EAST, creditButton, -250, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, creditButton, -100, SpringLayout.SOUTH, panel);
        creditButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                creditButton.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                Point originalLocation = creditButton.getLocation();
                Timer timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            creditButton.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        }
                        else {
                            creditButton.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            creditButton.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                creditButton.setForeground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                audio.stopMusic();
                new Credits();
                dispose();
            }
        });

        // Background Image
        ImageIcon backgroundImage = new ImageIcon("src/Assets/Images/bg_mainmenu.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        panel.add(backgroundLabel);
        setVisible(true);
        layout.putConstraint(SpringLayout.WEST, backgroundLabel, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, backgroundLabel, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, backgroundLabel, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backgroundLabel, 0, SpringLayout.SOUTH, panel);
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST)));

        add(panel);
        setVisible(true);
    }
}