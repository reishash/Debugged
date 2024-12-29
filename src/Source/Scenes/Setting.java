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

public class Setting extends JFrame {
    private Audio audio;
    private Font helvetiHandFont;

    public Setting(Audio audio) {
        this.audio = audio;

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
        panel.setLayout(null);

        // Title Image
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setBounds(525, -50, 500, 200);
        titleLabel.setFont(helvetiHandFont.deriveFont(60f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        // Music Volume Label
        JLabel musicVolumeLabel = new JLabel("Music Volume:");
        musicVolumeLabel.setBounds(100, 150, 400, 30);
        musicVolumeLabel.setFont(helvetiHandFont);
        musicVolumeLabel.setForeground(Color.WHITE);
        panel.add(musicVolumeLabel);

        // Music Volume Slider
        JSlider musicVolumeSlider = new JSlider(0, 100, (int) Audio.getMusicVolume());
        musicVolumeSlider.setBounds(1125, 150, 300, 50);
        musicVolumeSlider.setFont(helvetiHandFont);
        musicVolumeSlider.addChangeListener(e -> {
            int volume = musicVolumeSlider.getValue();
            audio.setMusicVolume(volume);
        });
        musicVolumeSlider.setOpaque(false);
        musicVolumeSlider.setForeground(Color.WHITE);
        panel.add(musicVolumeSlider);

        // SFX Volume Label
        JLabel sfxVolumeLabel = new JLabel("SFX Volume:");
        sfxVolumeLabel.setBounds(100, 300, 400, 30);
        sfxVolumeLabel.setFont(helvetiHandFont);
        sfxVolumeLabel.setForeground(Color.WHITE);
        panel.add(sfxVolumeLabel);

        // SFX Volume Slider
        JSlider sfxVolumeSlider = new JSlider(0, 100, (int) Audio.getSFXVolume());
        sfxVolumeSlider.setBounds(1125, 300, 300, 50);
        sfxVolumeSlider.setFont(helvetiHandFont);
        sfxVolumeSlider.addChangeListener(e -> {
            int volume = sfxVolumeSlider.getValue();
            audio.setSFXVolume(volume);
        });
        sfxVolumeSlider.setOpaque(false);
        sfxVolumeSlider.setForeground(Color.WHITE);
        panel.add(sfxVolumeSlider);
        // Add sound effect to sliders

        sfxVolumeSlider.addChangeListener(e -> {
            int volume = sfxVolumeSlider.getValue();
            audio.setSFXVolume(volume);
            if (!sfxVolumeSlider.getValueIsAdjusting()) {
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
            }
        });

        // Voice Volume Label
        JLabel voiceVolumeLabel = new JLabel("Voice Volume:");
        voiceVolumeLabel.setBounds(100, 450, 400, 30);
        voiceVolumeLabel.setFont(helvetiHandFont);
        voiceVolumeLabel.setForeground(Color.WHITE);
        panel.add(voiceVolumeLabel);

        // Voice Volume Slider
        JSlider voiceVolumeSlider = new JSlider(0, 100, (int) Audio.getVoiceVolume());
        voiceVolumeSlider.setBounds(1125, 450, 300, 50);
        voiceVolumeSlider.setFont(helvetiHandFont);
        voiceVolumeSlider.addChangeListener(e -> {
            int volume = voiceVolumeSlider.getValue();
            audio.setVoiceVolume(volume);
        });
        voiceVolumeSlider.setOpaque(false);
        voiceVolumeSlider.setForeground(Color.WHITE);
        panel.add(voiceVolumeSlider);

        voiceVolumeSlider.addChangeListener(e -> {
            int volume = voiceVolumeSlider.getValue();
            audio.setVoiceVolume(volume);
            audio.playSFX("src/Assets/Sounds/voice.wav");
        });

        // Back Button
        JLabel backButtonLabel = new JLabel("Back");
        backButtonLabel.setFont(helvetiHandFont.deriveFont(30f));
        backButtonLabel.setForeground(Color.WHITE);
        backButtonLabel.setBounds(10, 650, 250, 75);
        backButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backButtonLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButtonLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButtonLabel.setForeground(Color.YELLOW);
                audio.playSFX("src/Assets/Sounds/menu_hover.wav");
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButtonLabel.setForeground(Color.WHITE);
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
            }
        });
        panel.add(backButtonLabel);
        
        // Semi-transparent Box
        JPanel transparentBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50); 
            }
        };
        transparentBox.setBounds(50, 100, 1425, 700);
        transparentBox.setOpaque(false);
        panel.add(transparentBox);

        setVisible(true);

        // Background Image
        ImageIcon backgroundImage = new ImageIcon("src/Assets/Images/main_menu_background.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH)));
        panel.add(backgroundLabel);

        add(panel);
    }
}