package Source.Scenes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Source.Logic.Audio;

public class Credits extends JFrame {
    private Audio audio;
    private Font helvetiHandFont;
    private ImageIcon profileImage, backgroundImage;
    private JButton backButton;
    private JPanel panel;
    private JLabel titleLabel, profileLabel, profileNameLabel, creditLabel, backgroundLabel;
    private SpringLayout layout;
    private Timer timer;

    public Credits() {
        // Music
        audio = new Audio();
        audio.playMusic("src/Assets/Audio/Music/setting_music.wav");

        // Load Font
        try {
            helvetiHandFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Assets/Fonts/HelvetiHand.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(helvetiHandFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // Frame
        setTitle("Debugged: Credits");
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
        titleLabel = new JLabel("Credits");
        titleLabel.setFont(helvetiHandFont.deriveFont(60f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, titleLabel, 40, SpringLayout.NORTH, panel);
        panel.add(titleLabel);

        // Profile Image
        profileImage = new ImageIcon("src/Assets/Images/profile.jpg");
        profileLabel = new JLabel(profileImage);
        profileLabel.setHorizontalAlignment(SwingConstants.LEFT);
        profileLabel.setIcon(new ImageIcon(profileImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)) {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                int width = getIconWidth();
                int height = getIconHeight();
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setClip(new Ellipse2D.Float(x, y, width, height));
                super.paintIcon(c, g2d, x, y);
                g2d.dispose();
            }
        });
        layout.putConstraint(SpringLayout.WEST, profileLabel, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, profileLabel, 150, SpringLayout.NORTH, panel);
        panel.add(profileLabel);

        // Profile Name
        profileNameLabel = new JLabel("Reishash");
        profileNameLabel.setFont(helvetiHandFont);
        profileNameLabel.setForeground(Color.WHITE);
        profileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.WEST, profileNameLabel, 0, SpringLayout.WEST, profileLabel);
        layout.putConstraint(SpringLayout.NORTH, profileNameLabel, 50, SpringLayout.SOUTH, profileLabel);
        layout.putConstraint(SpringLayout.EAST, profileNameLabel, 0, SpringLayout.EAST, profileLabel);
        profileNameLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profileNameLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                audio.playSelectSFX();
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/reishash"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        panel.add(profileNameLabel);

        // Credits Information
        String[] credits = {
            "Debugged: In a world of glitches, you are the fix.",
            "",
            "Art and Design:",
            "Character Art: Makowka Character Maker II",
            "Background Image: Freepik",
            "Font: HelvetiHand by Billy Snyder",
            "",
            "Sound and Music:",
            "Sound Effects: freesound.org",
            "Music: Life is Strange Soundtrack",
            "Song: Obstacles by Syd Matters",
            "Song: uoY fo llA oT by srettaM dyS",
        };


        // Add credits to the panel
        for (int i = 0; i < credits.length; i++) {
            creditLabel = new JLabel(credits[i]);
            creditLabel.setFont(helvetiHandFont);
            creditLabel.setForeground(Color.WHITE);
            creditLabel.setHorizontalAlignment(SwingConstants.LEFT);
            panel.add(creditLabel);
            if (i == 0 || i == 2 || i == 7) {
                layout.putConstraint(SpringLayout.WEST, creditLabel, 150, SpringLayout.EAST, profileLabel);
            } else {
                layout.putConstraint(SpringLayout.WEST, creditLabel, 200, SpringLayout.EAST, profileLabel);
            }
            layout.putConstraint(SpringLayout.NORTH, creditLabel, 150 + i * 50, SpringLayout.NORTH, panel);
            if (i >= 3 && i <= 5 || i == 8 || i == 10) {
                final int index = i;
                creditLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                creditLabel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        audio.playSelectSFX();
                        String url = "";
                        try {
                            Desktop.getDesktop().browse(new URI(url));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        switch (index) {
                        case 3:
                            url = "https://picrew.me/en/image_maker/644129";
                            break;
                        case 4:
                            url = "https://www.freepik.com";
                            break;
                        case 5:
                            url = "https://www.dafont.com/helvetihand.font";
                            break;
                        case 8:
                            url = "https://freesound.org/";
                            break;    
                        case 10:
                            url = "https://open.spotify.com/track/2ynCjjrmED5CfiVn2ZLkUk?si=34bae70a24af49a3";
                            break;
                        default:
                            break;
                    }
                    }
                });
            }
        }

        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(helvetiHandFont);
        backButton.setForeground(Color.WHITE);
        backButton.setContentAreaFilled(false);
        backButton.setHorizontalAlignment(SwingConstants.LEFT);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        layout.putConstraint(SpringLayout.WEST, backButton, 250, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backButton, -100, SpringLayout.SOUTH, panel);
        ActionListener backAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                audio.playSelectSFX();
                audio.stopMusic();
                new MainMenu();
                dispose();
            }
        };
        addButtonMouseListeners(backButton, backAction);
        panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escapeAction");
        panel.getActionMap().put("escapeAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backAction.actionPerformed(new ActionEvent(backButton, ActionEvent.ACTION_PERFORMED, null));
            }
        });
        panel.add(backButton);

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
