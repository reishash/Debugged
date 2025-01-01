package Source.Scenes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Source.Logic.Audio;
import Source.Logic.GameEngine;

public class Setting extends JFrame {
    private Font helvetiHandFont;
    private GameEngine gameEngine = new GameEngine();
    private int keyBinding = KeyEvent.VK_SPACE;

    // Constructor
    public Setting(Audio audio, Object parent) {
        // Music
        audio.playMusic("src/Assets/Sounds/setting_music.wav");

        // Load Font
        try {
            helvetiHandFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Assets/Fonts/HelvetiHand.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(helvetiHandFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // Frame
        setTitle("Debugged: Settings");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        // Panel
        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);

        // Title Image
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(helvetiHandFont.deriveFont(60f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, titleLabel, 40, SpringLayout.NORTH, panel);

        // Music Volume Label
        JLabel musicVolumeLabel = new JLabel("Music Volume:");
        musicVolumeLabel.setFont(helvetiHandFont);
        musicVolumeLabel.setForeground(Color.WHITE);
        panel.add(musicVolumeLabel);
        layout.putConstraint(SpringLayout.WEST, musicVolumeLabel, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, musicVolumeLabel, 200, SpringLayout.NORTH, panel);

        // Music Volume Slider
        JSlider musicVolumeSlider = new JSlider(0, 100, (int) Audio.getMusicVolume());
        musicVolumeSlider.setFont(helvetiHandFont);
        musicVolumeSlider.addChangeListener(e -> {
            int volume = musicVolumeSlider.getValue();
            audio.setMusicVolume(volume);
        });
        musicVolumeSlider.setOpaque(false);
        musicVolumeSlider.setForeground(Color.WHITE);
        panel.add(musicVolumeSlider);
        layout.putConstraint(SpringLayout.EAST, musicVolumeSlider, -250, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, musicVolumeSlider, 200, SpringLayout.NORTH, panel);

        // SFX Volume Label
        JLabel sfxVolumeLabel = new JLabel("SFX Volume:");
        sfxVolumeLabel.setFont(helvetiHandFont);
        sfxVolumeLabel.setForeground(Color.WHITE);
        panel.add(sfxVolumeLabel);
        layout.putConstraint(SpringLayout.WEST, sfxVolumeLabel, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, sfxVolumeLabel, 300, SpringLayout.NORTH, panel);

        // SFX Volume Slider
        JSlider sfxVolumeSlider = new JSlider(0, 100, (int) Audio.getSFXVolume());
        sfxVolumeSlider.setFont(helvetiHandFont);
        sfxVolumeSlider.addChangeListener(e -> {
            int volume = sfxVolumeSlider.getValue();
            audio.setSFXVolume(volume);
        });
        sfxVolumeSlider.setOpaque(false);
        sfxVolumeSlider.setForeground(Color.WHITE);
        panel.add(sfxVolumeSlider);
        layout.putConstraint(SpringLayout.EAST, sfxVolumeSlider, -250, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, sfxVolumeSlider, 300, SpringLayout.NORTH, panel);
        sfxVolumeSlider.addChangeListener(e -> {
            int volume = sfxVolumeSlider.getValue();
            audio.setSFXVolume(volume);
            if (!sfxVolumeSlider.getValueIsAdjusting()) {
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
            }
        });

        // Voice Volume Label
        JLabel voiceVolumeLabel = new JLabel("Voice Volume:");
        voiceVolumeLabel.setFont(helvetiHandFont);
        voiceVolumeLabel.setForeground(Color.WHITE);
        panel.add(voiceVolumeLabel);
        layout.putConstraint(SpringLayout.WEST, voiceVolumeLabel, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, voiceVolumeLabel, 400, SpringLayout.NORTH, panel);

        // Voice Volume Slider
        JSlider voiceVolumeSlider = new JSlider(0, 100, (int) Audio.getVoiceVolume());
        voiceVolumeSlider.setFont(helvetiHandFont);
        voiceVolumeSlider.addChangeListener(e -> {
            int volume = voiceVolumeSlider.getValue();
            audio.setVoiceVolume(volume);
        });
        voiceVolumeSlider.setOpaque(false);
        voiceVolumeSlider.setForeground(Color.WHITE);
        panel.add(voiceVolumeSlider);
        layout.putConstraint(SpringLayout.EAST, voiceVolumeSlider, -250, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, voiceVolumeSlider, 400, SpringLayout.NORTH, panel);
        voiceVolumeSlider.addChangeListener(e -> {
            int volume = voiceVolumeSlider.getValue();
            audio.setVoiceVolume(volume);
            audio.playSFX("src/Assets/Sounds/voice.wav");
        });

        // Key Binding Label
        JLabel keyBindingLabel = new JLabel("Key Binding:");
        keyBindingLabel.setFont(helvetiHandFont);
        keyBindingLabel.setForeground(Color.WHITE);
        panel.add(keyBindingLabel);
        layout.putConstraint(SpringLayout.WEST, keyBindingLabel, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, keyBindingLabel, 500, SpringLayout.NORTH, panel);

        // Key Binding Button
        JLabel keyBindingButton = new JLabel("Change Key Binding");
        keyBindingButton.setFont(helvetiHandFont);
        keyBindingButton.setForeground(Color.WHITE);
        keyBindingButton.setHorizontalAlignment(SwingConstants.CENTER);
        keyBindingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        layout.putConstraint(SpringLayout.EAST, keyBindingButton, -250, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, keyBindingButton, 500, SpringLayout.NORTH, panel);
        keyBindingButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                keyBindingButton.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                Point originalLocation = keyBindingButton.getLocation();
                Timer timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            keyBindingButton.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        } else {
                            keyBindingButton.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            keyBindingButton.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }
            public void mouseExited(MouseEvent evt) {
                keyBindingButton.setForeground(Color.WHITE);
            }
            public void mouseClicked(MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                JDialog dialog = new JDialog();
                dialog.setModal(true);
                dialog.setUndecorated(true);
                dialog.setSize(300, 100);
                dialog.setLocationRelativeTo(null);
                JLabel label = new JLabel("Press any key to set as new key binding", JLabel.CENTER);
                dialog.add(label);
                dialog.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent evt) {
                        setKeyBinding(evt.getKeyCode());
                        dialog.dispose();
                    }
                });
                dialog.setVisible(true);
            }
        });
        panel.add(keyBindingButton);

        // Back Button
        JLabel backButtonLabel = new JLabel("Back");
        backButtonLabel.setFont(helvetiHandFont);
        backButtonLabel.setForeground(Color.WHITE);
        backButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backButtonLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        layout.putConstraint(SpringLayout.WEST, backButtonLabel, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backButtonLabel, -100, SpringLayout.SOUTH, panel);
        backButtonLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                backButtonLabel.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                Point originalLocation = backButtonLabel.getLocation();
                Timer timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            backButtonLabel.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        } else {
                            backButtonLabel.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            backButtonLabel.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }
            public void mouseExited(MouseEvent evt) {
                backButtonLabel.setForeground(Color.WHITE);
            }
            public void mouseClicked(MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                audio.stopMusic();
                if (parent instanceof MainMenu) {
                    gameEngine.initializeMainMenu();
                }
                dispose();
            }
        });
        panel.add(backButtonLabel);

        // Background Image
        ImageIcon backgroundImage = new ImageIcon("src/Assets/Images/bg_setting.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        panel.add(backgroundLabel);
        setVisible(true);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH)));
        layout.putConstraint(SpringLayout.WEST, backgroundLabel, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, backgroundLabel, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, backgroundLabel, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backgroundLabel, 0, SpringLayout.SOUTH, panel);

        add(panel);
        setVisible(true);
    }

    // Set key binding
    private void setKeyBinding(int keyBinding) {
        this.keyBinding = keyBinding;
    }

    // Get key binding
    public int getKeyBinding() {
        return keyBinding;
    }

    // Constructor
    public Setting() {
    }
}