package Source.Scenes;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.Timer;

import javax.swing.ImageIcon;

import Source.Logic.Audio;
import Source.Logic.GameEngine;

public class SelectChapter extends JFrame {
    private Audio audio;
    private JLabel chapterImageLabel, chapterSelectLabel;
    private JTextArea chapterDescriptionTextArea;
    private JPanel dynamicPanel, panel;
    private Font helvetiHandFont;
    private SpringLayout layout;
    private List<Component> previousComponents = new ArrayList<>();
    private GameEngine gameEngine = new GameEngine();

    // Constructor
    public SelectChapter() {
        // Audio
        audio = new Audio();
        Audio audio = new Audio();
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
        setTitle("Debugged: Select Chapter");
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
        JLabel titleLabel = new JLabel("Select Chapter");
        titleLabel.setFont(helvetiHandFont.deriveFont(60f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, titleLabel, 40, SpringLayout.NORTH, panel);

        // Dynamic Panel for Chapter Content
        dynamicPanel = new JPanel();
        dynamicPanel.setLayout(new SpringLayout());
        dynamicPanel.setOpaque(false);
        panel.add(dynamicPanel);
        layout.putConstraint(SpringLayout.WEST, dynamicPanel, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, dynamicPanel, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, dynamicPanel, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, dynamicPanel, 0, SpringLayout.SOUTH, panel);

        // Chapter Selection
        for (int i = 0; i < 10; i++) {
            JLabel chapterLabel = new JLabel(getChapterName(i));
            chapterLabel.setFont(helvetiHandFont);
            chapterLabel.setForeground(Color.WHITE);
            chapterLabel.setHorizontalAlignment(SwingConstants.LEFT);
            panel.add(chapterLabel);
            chapterLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            if (i == 0 || i == 4 || i == 7) {
                layout.putConstraint(SpringLayout.WEST, chapterLabel, 250, SpringLayout.WEST, panel);
            } else {
                layout.putConstraint(SpringLayout.WEST, chapterLabel, 300, SpringLayout.WEST, panel);
            }
            layout.putConstraint(SpringLayout.NORTH, chapterLabel, 150 + i * 50, SpringLayout.NORTH, panel);

            int chapter = i; // Final variable for lambda
            chapterLabel.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    chapterLabel.setForeground(Color.YELLOW);
                    audio.playSFX("src/Assets/Audio/SFX/menu_hover.wav");
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
                    displayChapterContent(chapter);
                }
                public void mouseExited(MouseEvent evt) {
                    chapterLabel.setForeground(Color.WHITE);
                }
                public void mouseClicked(MouseEvent evt) {
                    audio.playSelectSFX();
                    displayChapterContent(chapter);
                }
            });
        }

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
                audio.playSFX("src/Assets/Audio/SFX/menu_hover.wav");
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
                audio.playSelectSFX();
                audio.stopMusic();
                gameEngine.initializeMainMenu();
                dispose();
            }
        });
        panel.add(backButtonLabel);

        // Add background image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/Assets/Images/Backgrounds/bg_select.jpg"));
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundImage);
        setVisible(true);
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST)));
        panel.add(backgroundLabel);
        layout.putConstraint(SpringLayout.WEST, backgroundLabel, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, backgroundLabel, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, backgroundLabel, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backgroundLabel, 0, SpringLayout.SOUTH, panel);

        add(panel);
        setVisible(true);
    }

    // Chapter Name
    private String getChapterName(int chapter) {
        String[] chapters = {
            "System Boot",
            "Memory Leak",
            "Code Red",
            "Segmentation Fault",
            "Stack Overflow",
            "Infinite Loop",
            "Reflection Bug",
            "Exit Code",
            "Successful Commit",
            "System Crash"
        };
        return chapters[chapter];
    }

    // Display Chapter Content
    private void displayChapterContent(int chapter) {
        // Remove previous components
        if (dynamicPanel != null) {
            for (Component component : previousComponents) {
                panel.remove(component);
            }
        }

        // Chapter Image
        chapterImageLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/Assets/Images/Chapters/c" + chapter + ".jpg")).getImage().getScaledInstance(400, 225, Image.SCALE_FAST)));
        chapterImageLabel.setIcon(new ImageIcon(makeRoundedCorner(new ImageIcon(getClass().getResource("/Assets/Images/c" + chapter + ".jpg")).getImage(), 400, 225, 50)));
        dynamicPanel.add(chapterImageLabel);
        layout.putConstraint(SpringLayout.NORTH, chapterImageLabel, 150, SpringLayout.NORTH, dynamicPanel);
        layout.putConstraint(SpringLayout.EAST, chapterImageLabel, -250, SpringLayout.EAST, dynamicPanel);

        // Chapter Description
        chapterDescriptionTextArea = new JTextArea(getChapterDescription(chapter));
        chapterDescriptionTextArea.setFont(helvetiHandFont.deriveFont(20f));
        chapterDescriptionTextArea.setForeground(Color.WHITE);
        chapterDescriptionTextArea.setLineWrap(true);
        chapterDescriptionTextArea.setWrapStyleWord(true);
        chapterDescriptionTextArea.setEditable(false);
        chapterDescriptionTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        chapterDescriptionTextArea.setOpaque(false);
        dynamicPanel.add(chapterDescriptionTextArea);
        layout.putConstraint(SpringLayout.NORTH, chapterDescriptionTextArea, 50, SpringLayout.SOUTH, chapterImageLabel);
        layout.putConstraint(SpringLayout.WEST, chapterDescriptionTextArea, 0, SpringLayout.WEST, chapterImageLabel);
        layout.putConstraint(SpringLayout.EAST, chapterDescriptionTextArea, 0, SpringLayout.EAST, chapterImageLabel);

        // Chapter Select
        chapterSelectLabel = new JLabel("Select Chapter");
        chapterSelectLabel.setFont(helvetiHandFont);
        chapterSelectLabel.setForeground(Color.WHITE);
        chapterSelectLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(chapterSelectLabel);
        chapterSelectLabel.setFont(helvetiHandFont);
        chapterSelectLabel.setForeground(Color.WHITE);
        chapterSelectLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chapterSelectLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
            chapterSelectLabel.setForeground(Color.YELLOW);
            audio.playSFX("src/Assets/Audio/SFX/menu_hover.wav");
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
            public void mouseExited(MouseEvent evt) {
                chapterSelectLabel.setForeground(Color.WHITE);
            }
            public void mouseClicked(MouseEvent evt) {
                audio.playSelectSFX();
                switch (chapter) {
                    case 0:
                        new Scene(1);
                        dispose();
                        break;
                    case 1:
                        new Scene(39);
                        dispose();
                        break;
                    case 2:
                        new Scene(86);
                        dispose();
                        break;
                    case 3:
                        new Scene(129);
                        dispose();
                        break;
                    case 4:
                        new Scene(176);
                        dispose();
                        break;
                    case 5:
                        new Scene(228);
                        dispose();
                        break;
                    case 6:
                        new Scene(276);
                        dispose();
                        break;
                    case 7:
                        new Scene(353);
                        dispose();
                        break;
                    case 8:
                        new Scene(999);
                        dispose();
                        break;
                    case 9:
                        new Scene(999);
                        dispose();
                        break;
                }
            }
        });
        layout.putConstraint(SpringLayout.EAST, chapterSelectLabel, -250, SpringLayout.EAST, dynamicPanel);
        layout.putConstraint(SpringLayout.SOUTH, chapterSelectLabel, -100, SpringLayout.SOUTH, dynamicPanel);
    
        // Set Z-Order
        panel.setComponentZOrder(chapterImageLabel, 1);
        panel.setComponentZOrder(chapterDescriptionTextArea, 1);
        panel.setComponentZOrder(chapterSelectLabel, 1);
    
        // Add previous components to list
        previousComponents.add(chapterImageLabel);
        previousComponents.add(chapterDescriptionTextArea);
        previousComponents.add(chapterSelectLabel);

        // Refresh panel
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    // Chapter Description
    private String getChapterDescription(int chapter) {
        String[] descriptions = {
            "The first steps are always the hardest. A world that’s both familiar and alien, waiting to be explored.",
            "The past isn’t always as clear as we remember. But sometimes, it’s the only thing we can hold onto.",
            "Sometimes, the people we care about the most are the hardest to understand. But it’s the effort that matters.",
            "Dreams are fragile. But it’s only when we lose them that we realize how much they meant.",
            "We can’t rewrite the past, no matter how much we wish we could. But we can learn to live with the changes.",
            "We can’t outrun our past, but we can choose how it shapes us. Sometimes, we have to break free to move forward.",
            "The hardest person to face is the one in the mirror. But it’s only by understanding them that we can truly move on.",
            "Leaving isn’t always about escape. Sometimes, it’s about understanding what we’re leaving behind; and why it matters.",
            "Sometimes, the best outcomes come from letting go. And finding what we need in the places we least expect.",
            "Some choices bind us to the past. And in the silence of the void, we must face what we’ve left behind."
        };
        return descriptions[chapter];
    }

    // Rounded Corner Image
    private BufferedImage makeRoundedCorner(Image image, int width, int height, int cornerRadius) {
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
        return output;
    }
}
