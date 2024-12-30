package Source.Scenes;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import Source.Logic.Audio;
import Source.Logic.GameEngine;

public class Setting extends JFrame {
    private GameEngine gameEngine = new GameEngine();
    private Audio audio;
    private Font helvetiHandFont;

    public Setting(Audio audio, Object parent) {
        this.audio = audio;
        
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
        setTitle("Setting");
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
        layout.putConstraint(SpringLayout.NORTH, titleLabel, 25, SpringLayout.NORTH, panel);

        // Music Volume Label
        JLabel musicVolumeLabel = new JLabel("Music Volume:");
        musicVolumeLabel.setFont(helvetiHandFont);
        musicVolumeLabel.setForeground(Color.WHITE);
        panel.add(musicVolumeLabel);
        layout.putConstraint(SpringLayout.WEST, musicVolumeLabel, 100, SpringLayout.WEST, panel);
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
        layout.putConstraint(SpringLayout.EAST, musicVolumeSlider, -100, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, musicVolumeSlider, 200, SpringLayout.NORTH, panel);

        // SFX Volume Label
        JLabel sfxVolumeLabel = new JLabel("SFX Volume:");
        sfxVolumeLabel.setFont(helvetiHandFont);
        sfxVolumeLabel.setForeground(Color.WHITE);
        panel.add(sfxVolumeLabel);
        layout.putConstraint(SpringLayout.WEST, sfxVolumeLabel, 100, SpringLayout.WEST, panel);
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
        layout.putConstraint(SpringLayout.EAST, sfxVolumeSlider, -100, SpringLayout.EAST, panel);
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
        layout.putConstraint(SpringLayout.WEST, voiceVolumeLabel, 100, SpringLayout.WEST, panel);
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
        layout.putConstraint(SpringLayout.EAST, voiceVolumeSlider, -100, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, voiceVolumeSlider, 400, SpringLayout.NORTH, panel);
        voiceVolumeSlider.addChangeListener(e -> {
            int volume = voiceVolumeSlider.getValue();
            audio.setVoiceVolume(volume);
            audio.playSFX("src/Assets/Sounds/voice.wav");
        });

        // Back Button
        JLabel backButtonLabel = new JLabel("Back");
        backButtonLabel.setFont(helvetiHandFont.deriveFont(30f));
        backButtonLabel.setForeground(Color.WHITE);
        backButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backButtonLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        layout.putConstraint(SpringLayout.WEST, backButtonLabel, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backButtonLabel, -100, SpringLayout.SOUTH, panel);
        backButtonLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButtonLabel.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButtonLabel.setForeground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
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
}