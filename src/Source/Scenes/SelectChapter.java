package Source.Scenes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

import Source.Logic.Audio;
import Source.Logic.GameEngine;

public class SelectChapter extends JFrame {
    private Font helvetiHandFont;
    private JPanel panel, dynamicPanel;
    private SpringLayout layout;
    private JLabel chapterImageLabel, chapterSelectLabel;
    private JTextArea chapterDescriptionTextArea;
    private java.util.List<Component> previousComponents = new java.util.ArrayList<>();
    private Audio audio;

    public SelectChapter() {
        audio = new Audio();
        Audio audio = new Audio();
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
        setTitle("Select Chapter");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        panel = new JPanel();
        layout = new SpringLayout();
        panel.setLayout(layout);

        // Title Image
        JLabel titleLabel = new JLabel("Select Chapter");
        titleLabel.setFont(helvetiHandFont.deriveFont(60f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, titleLabel, 25, SpringLayout.NORTH, panel);

        // Dynamic Panel for Chapter Content
        dynamicPanel = new JPanel();
        dynamicPanel.setLayout(new SpringLayout());
        dynamicPanel.setOpaque(false);
        panel.add(dynamicPanel);
        layout.putConstraint(SpringLayout.WEST, dynamicPanel, 900, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, dynamicPanel, 150, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, dynamicPanel, -100, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, dynamicPanel, -100, SpringLayout.SOUTH, panel);

        // Chapter Selection
        for (int i = 0; i < 11; i++) {
            JLabel chapterLabel = new JLabel(getChapterName(i));
            chapterLabel.setFont(helvetiHandFont);
            chapterLabel.setForeground(Color.WHITE);
            chapterLabel.setHorizontalAlignment(SwingConstants.LEFT);
            panel.add(chapterLabel);
            layout.putConstraint(SpringLayout.WEST, chapterLabel, 100, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, chapterLabel, 150 + i * 50, SpringLayout.NORTH, panel);

            int chapter = i; // Final variable for lambda
            chapterLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    chapterLabel.setForeground(Color.YELLOW);
                    audio.playSFX("src/Assets/Sounds/menu_hover.wav");
                    Point originalLocation = chapterLabel.getLocation();
                    Timer timer = new Timer(50, new ActionListener() {
                        int count = 0;
                        boolean moveRight = true;
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (moveRight) {
                                chapterLabel.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                            } else {
                                chapterLabel.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                            }
                            moveRight = !moveRight;
                            count++;
                            if (count >= 2) {
                                ((Timer) e.getSource()).stop();
                                chapterLabel.setLocation(originalLocation);
                            }
                        }
                    });
                    timer.start();
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    chapterLabel.setForeground(Color.WHITE);
                }
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    audio.playSFX("src/Assets/Sounds/menu_select.wav");
                    displayChapterContent(chapter);
                }
            });
        }

        // Back Button
        JLabel backButtonLabel = new JLabel("Back");
        backButtonLabel.setFont(helvetiHandFont);
        backButtonLabel.setForeground(Color.WHITE);
        backButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backButtonLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        layout.putConstraint(SpringLayout.WEST, backButtonLabel, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backButtonLabel, -100, SpringLayout.SOUTH, panel);
        backButtonLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
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
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButtonLabel.setForeground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                audio.stopMusic();
                GameEngine gameEngine = new GameEngine();
                gameEngine.initializeMainMenu();
                dispose();
            }
        });
        panel.add(backButtonLabel);

        // Add background image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/Assets/Images/bg_setting.jpg"));
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundImage);
        setVisible(true);
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_FAST)));
        panel.add(backgroundLabel);
        layout.putConstraint(SpringLayout.WEST, backgroundLabel, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, backgroundLabel, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, backgroundLabel, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backgroundLabel, 0, SpringLayout.SOUTH, panel);

        add(panel);
        setVisible(true);
    }

    private String getChapterName(int chapter) {

        String[] chapters = {
            "System Boot", "Memory Leak", "Code Red", "Segmentation Fault",
            "Stack Overflow", "Null Pointer", "Infinite Loop", "Reflection Bug",
            "Exit Code", "Successful Commit", "System Crash"
        };
        return chapters[chapter];
    }

    private void displayChapterContent(int chapter) {
        if (dynamicPanel != null) {
            for (Component component : previousComponents) {
                panel.remove(component);
            }
        }

        // Chapter Image
        chapterImageLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/Assets/Images/c" + chapter + ".jpg")).getImage().getScaledInstance(480, 270, Image.SCALE_FAST)));
        dynamicPanel.add(chapterImageLabel);
        layout.putConstraint(SpringLayout.NORTH, chapterImageLabel, 0, SpringLayout.NORTH, dynamicPanel);
        layout.putConstraint(SpringLayout.WEST, chapterImageLabel, 0, SpringLayout.WEST, dynamicPanel);

        // Chapter Description
        chapterDescriptionTextArea = new JTextArea(getChapterDescription(chapter));
        chapterDescriptionTextArea.setFont(helvetiHandFont);
        chapterDescriptionTextArea.setForeground(Color.WHITE);
        chapterDescriptionTextArea.setBackground(Color.BLACK);
        chapterDescriptionTextArea.setLineWrap(true);
        chapterDescriptionTextArea.setWrapStyleWord(true);
        chapterDescriptionTextArea.setEditable(false);
        chapterDescriptionTextArea.setOpaque(false);
        dynamicPanel.add(chapterDescriptionTextArea);
        layout.putConstraint(SpringLayout.NORTH, chapterDescriptionTextArea, 50, SpringLayout.SOUTH, chapterImageLabel);
        layout.putConstraint(SpringLayout.WEST, chapterDescriptionTextArea, 0, SpringLayout.WEST, dynamicPanel);
        layout.putConstraint(SpringLayout.EAST, chapterDescriptionTextArea, 0, SpringLayout.EAST, dynamicPanel);

        // Chapter Select
        chapterSelectLabel = new JLabel("Select Chapter");
        chapterSelectLabel.setFont(helvetiHandFont);
        chapterSelectLabel.setForeground(Color.WHITE);
        chapterSelectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(chapterSelectLabel);
        chapterSelectLabel.setFont(helvetiHandFont);
        chapterSelectLabel.setForeground(Color.WHITE);
        chapterSelectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chapterSelectLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chapterSelectLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            chapterSelectLabel.setForeground(Color.YELLOW);
            audio.playSFX("src/Assets/Sounds/menu_hover.wav");
            Point originalLocation = chapterSelectLabel.getLocation();
            Timer timer = new Timer(50, new ActionListener() {
                int count = 0;
                boolean moveRight = true;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (moveRight) {
                        chapterSelectLabel.setLocation(originalLocation.x + 1, originalLocation.y + 1);
                    } else {
                        chapterSelectLabel.setLocation(originalLocation.x - 1, originalLocation.y - 1);
                    }
                    moveRight = !moveRight;
                    count++;
                    if (count >= 2) {
                        ((Timer) e.getSource()).stop();
                        chapterSelectLabel.setLocation(originalLocation);
                    }
                }
            });
            timer.start();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                chapterSelectLabel.setForeground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                audio.playSFX("src/Assets/Sounds/menu_select.wav");
                switch (chapter) {
                    case 0:
                        Scene scene = new Scene(1);
                        dispose();
                        break;
                    case 1:
                        Scene scene1 = new Scene(39);
                        dispose();
                        break;
                    case 2:
                        Scene scene2 = new Scene(86);
                        dispose();
                        break;
                    case 3:
                        Scene scene3 = new Scene(129);
                        dispose();
                        break;
                    case 4:
                        Scene scene4 = new Scene(176);
                        dispose();
                        break;
                    case 5:
                        Scene scene5 = new Scene(228);
                        dispose();
                        break;
                    case 6:
                        Scene scen6 = new Scene(276);
                        dispose();
                        break;
                    case 7:
                        Scene scene7 = new Scene(353);
                        dispose();
                        break;
                    case 8:
                        Scene scene8 = new Scene(999);
                        dispose();
                        break;
                    case 9:
                        Scene scene9 = new Scene(999);
                        dispose();
                        break;
                    case 10:
                        Scene scene10 = new Scene(999);
                        dispose();
                        break;
                }
            }
        });
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, chapterSelectLabel, 0, SpringLayout.HORIZONTAL_CENTER, dynamicPanel);
        layout.putConstraint(SpringLayout.SOUTH, chapterSelectLabel, 0, SpringLayout.SOUTH, dynamicPanel);
    
        panel.setComponentZOrder(chapterImageLabel, 1);
        panel.setComponentZOrder(chapterDescriptionTextArea, 1);
        panel.setComponentZOrder(chapterSelectLabel, 1);
    
        previousComponents.add(chapterImageLabel);
        previousComponents.add(chapterDescriptionTextArea);
        previousComponents.add(chapterSelectLabel);

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private String getChapterDescription(int chapter) {
        String[] descriptions = {
            "The first steps are always the hardest. A world that’s both familiar and alien, waiting to be explored.",
            "The past isn’t always as clear as we remember. But sometimes, it’s the only thing we can hold onto.",
            "Sometimes, the people we care about the most are the hardest to understand. But it’s the effort that matters.",
            "Dreams are fragile. But it’s only when we lose them that we realize how much they meant.",
            "We can’t rewrite the past, no matter how much we wish we could. But we can learn to live with the changes.",
            "We search for answers in the spaces between memories. But some gaps are meant to stay empty.",
            "We can’t outrun our past, but we can choose how it shapes us. Sometimes, we have to break free to move forward.",
            "The hardest person to face is the one in the mirror. But it’s only by understanding them that we can truly move on.",
            "Leaving isn’t always about escape. Sometimes, it’s about understanding what we’re leaving behind; and why it matters.",
            "Sometimes, the best outcomes come from letting go. And finding what we need in the places we least expect.",
            "Some choices bind us to the past. And in the silence of the void, we must face what we’ve left behind."
        };
        return descriptions[chapter];
    }
}
