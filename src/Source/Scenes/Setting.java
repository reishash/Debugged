package Source.Scenes;

import java.awt.BorderLayout;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Source.Logic.Audio;

public class Setting extends JFrame {
    private Audio audio;
    private Font helvetiHandFont;
    private GraphicsEnvironment graphicsEnvironment;
    private ImageIcon backgroundImage;
    private int keyBinding = KeyEvent.VK_SPACE;
    private JPanel panel;
    private JLabel titleLabel, backgroundLabel, messageLabel;
    private JButton button;
    private JDialog dialog;
    private JLabel label;
    private JSlider slider;
    private SpringLayout layout;
    private Timer timer;

    // Constructor
    public Setting(Object parent) {
        // Music
        audio = new Audio();
        audio.stopMusic();
        audio.playMusic("src/Assets/Audio/Music/setting_music.wav");

        // Load Font
        try {
            helvetiHandFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Assets/Fonts/HelvetiHand.ttf")).deriveFont(30f);
            graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(helvetiHandFont);
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
        panel = new JPanel();
        layout = new SpringLayout();
        panel.setLayout(layout);

        // Title Image
        titleLabel = new JLabel("Settings");
        titleLabel.setFont(helvetiHandFont.deriveFont(60f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, titleLabel, 40, SpringLayout.NORTH, panel);
        panel.add(titleLabel);

        // Labels
        String[] labelNames = {"Music Volume:", "SFX Volume:", "Voice Volume:", "Key Binding:"};
        int[] labelOffsets = {200, 300, 400, 500};
        for (int i = 0; i < labelNames.length; i++) {
            label = new JLabel(labelNames[i]);
            label.setFont(helvetiHandFont);
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(SwingConstants.LEFT);
            layout.putConstraint(SpringLayout.WEST, label, 250, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, label, labelOffsets[i], SpringLayout.NORTH, panel);
            panel.add(label);
        }

        // Sliders
        String[] sliderNames = {"Music Volume", "SFX Volume", "Voice Volume"};
        int[] sliderOffsets = {200, 300, 400};
        int[] sliderValues = {(int) Audio.getMusicVolume(), (int) Audio.getSFXVolume(), (int) Audio.getVoiceVolume()};
        for (int i = 0; i < sliderNames.length; i++) {
            slider = new JSlider(0, 100, sliderValues[i]);
            slider.setFont(helvetiHandFont);
            slider.setOpaque(false);
            slider.setForeground(Color.WHITE);
            layout.putConstraint(SpringLayout.EAST, slider, -250, SpringLayout.EAST, panel);
            layout.putConstraint(SpringLayout.NORTH, slider, sliderOffsets[i], SpringLayout.NORTH, panel);
            int index = i;
            slider.addChangeListener(e -> {
                int volume = slider.getValue();
                switch (index) {
                    case 0 -> audio.setMusicVolume(volume);
                    case 1 -> {
                        audio.setSFXVolume(volume);
                        if (!slider.getValueIsAdjusting()) {
                            audio.playHoverSFX();
                        }
                    }
                    case 2 -> {
                        audio.setVoiceVolume(volume);
                        audio.playSFX("src/Assets/Audio/SFX/voice.wav");
                    }
                }
            });
            panel.add(slider);
        }

        // Buttons
        String[] buttonNames = {"Key Binding", "Back"};
        ActionListener[] buttonActions = {
            e -> {
                audio.playSelectSFX();
                keyBinding();
            },
            e -> {
                audio.playSelectSFX();
                audio.stopMusic();
                if (parent instanceof MainMenu) {
                    new MainMenu();
                }
                dispose();
            }
        };
        for (int i = 0; i < buttonNames.length; i++) {
            button = new JButton(buttonNames[i].equals("Key Binding") ? KeyEvent.getKeyText(keyBinding) : buttonNames[i]);
            button.setFont(helvetiHandFont);
            button.setForeground(Color.WHITE);
            button.setContentAreaFilled(false);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            if (i == 0) {
                layout.putConstraint(SpringLayout.EAST, button, -250, SpringLayout.EAST, panel);
                layout.putConstraint(SpringLayout.NORTH, button, 500, SpringLayout.NORTH, panel);
            } else {
                layout.putConstraint(SpringLayout.WEST, button, 250, SpringLayout.WEST, panel);
                layout.putConstraint(SpringLayout.SOUTH, button, -100, SpringLayout.SOUTH, panel);
            }
            addButtonMouseListeners(button, buttonActions[i]);
            panel.add(button);
        }

        // Background Image
        backgroundImage = new ImageIcon("src/Assets/Images/Backgrounds/bg_setting.jpg");
        backgroundLabel = new JLabel(backgroundImage);
        setVisible(true);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH)));
        layout.putConstraint(SpringLayout.WEST, backgroundLabel, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, backgroundLabel, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, backgroundLabel, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backgroundLabel, 0, SpringLayout.SOUTH, panel);
        panel.add(backgroundLabel);

        add(panel);
        setVisible(true);
    }

    // Key Binding
    private void keyBinding() {
        dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setModal(true);
        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        messageLabel = new JLabel("Press any key to set as new key binding", JLabel.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(helvetiHandFont);
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                setKeyBinding(evt.getKeyCode());
                dialog.dispose();
            }
        });
        panel.add(messageLabel, BorderLayout.CENTER);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
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

    // Button Mouse Listeners
    private void addButtonMouseListeners(JButton button, ActionListener clickAction) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setForeground(Color.YELLOW);
                audio.playHoverSFX();
                Point originalLocation = button.getLocation();
                timer = new Timer(50, new ActionListener() {
                    int count = 0;
                    boolean moveRight = true;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (moveRight) {
                            button.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                        } else {
                            button.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                        }
                        moveRight = !moveRight;
                        count++;
                        if (count >= 2) {
                            ((Timer) e.getSource()).stop();
                            button.setLocation(originalLocation);
                        }
                    }
                });
                timer.start();
            }
            public void mouseExited(MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
            public void mouseClicked(MouseEvent evt) {
                clickAction.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
            }
        });
    }
}