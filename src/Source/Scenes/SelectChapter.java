package Source.Scenes;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Source.Logic.Audio;

public class SelectChapter extends JFrame {
    private Audio audio;
    private BufferedImage output;
    private Font helvetiHandFont;
    private ImageIcon backgroundImage;
    private JButton button, chapterSelectButton;
    private JLabel backgroundLabel, chapterImageLabel, titleLabel;
    private JTextArea chapterDescriptionTextArea;
    private JPanel dynamicPanel, panel;
    private List<Component> previousComponents = new ArrayList<>();
    private SpringLayout layout;
    private Timer timer;

    // Constructor
    public SelectChapter() {
        // Audio
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
        titleLabel = new JLabel("Select Chapter");
        titleLabel.setFont(helvetiHandFont.deriveFont(60f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, titleLabel, 40, SpringLayout.NORTH, panel);
        panel.add(titleLabel);

        // Dynamic Panel for Chapter Content
        dynamicPanel = new JPanel();
        dynamicPanel.setLayout(new SpringLayout());
        dynamicPanel.setOpaque(false);
        layout.putConstraint(SpringLayout.WEST, dynamicPanel, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, dynamicPanel, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, dynamicPanel, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, dynamicPanel, 0, SpringLayout.SOUTH, panel);
        panel.add(dynamicPanel);

        // Chapter Selection
        for (int i = 0; i < 11; i++) {
            if (i == 10) {
                button = new JButton("Back");
            } else {
                button = new JButton(getChapterName(i));
            }
            button.setFont(helvetiHandFont);
            button.setForeground(Color.WHITE);
            button.setContentAreaFilled(false);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panel.add(button);

            if (i == 0 || i == 4 || i == 7 || i == 10) {
                layout.putConstraint(SpringLayout.WEST, button, 250, SpringLayout.WEST, panel);
            } else {
                layout.putConstraint(SpringLayout.WEST, button, 300, SpringLayout.WEST, panel);
            }

            if (i == 10) {
                layout.putConstraint(SpringLayout.SOUTH, button, -100, SpringLayout.SOUTH, panel);
            } else {
                layout.putConstraint(SpringLayout.NORTH, button, 150 + i * 50, SpringLayout.NORTH, panel);
            }

            int chapter = i; // Final variable for lambda
            addButtonMouseListeners(button, e -> {
                audio.playSelectSFX();
                if (chapter == 10) {
                    audio.stopMusic();
                    new MainMenu();
                    dispose();
                } else {
                    displayChapterContent(chapter);
                }
            });
        }

        // Add background image
        backgroundImage = new ImageIcon(getClass().getResource("/Assets/Images/Backgrounds/bg_select.jpg"));
        backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundImage);
        setVisible(true);
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST)));
        layout.putConstraint(SpringLayout.WEST, backgroundLabel, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, backgroundLabel, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, backgroundLabel, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backgroundLabel, 0, SpringLayout.SOUTH, panel);
        panel.add(backgroundLabel);

        add(panel);
        setVisible(true);
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
        chapterImageLabel.setIcon(new ImageIcon(makeRoundedCorner(new ImageIcon(getClass().getResource("/Assets/Images/Chapters/c" + chapter + ".jpg")).getImage(), 400, 225, 50)));
        layout.putConstraint(SpringLayout.NORTH, chapterImageLabel, 150, SpringLayout.NORTH, dynamicPanel);
        layout.putConstraint(SpringLayout.EAST, chapterImageLabel, -250, SpringLayout.EAST, dynamicPanel);
        dynamicPanel.add(chapterImageLabel);

        // Chapter Description
        chapterDescriptionTextArea = new JTextArea(getChapterDescription(chapter));
        chapterDescriptionTextArea.setFont(helvetiHandFont.deriveFont(20f));
        chapterDescriptionTextArea.setForeground(Color.WHITE);
        chapterDescriptionTextArea.setLineWrap(true);
        chapterDescriptionTextArea.setWrapStyleWord(true);
        chapterDescriptionTextArea.setEditable(false);
        chapterDescriptionTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        chapterDescriptionTextArea.setOpaque(false);
        layout.putConstraint(SpringLayout.NORTH, chapterDescriptionTextArea, 50, SpringLayout.SOUTH, chapterImageLabel);
        layout.putConstraint(SpringLayout.WEST, chapterDescriptionTextArea, 0, SpringLayout.WEST, chapterImageLabel);
        layout.putConstraint(SpringLayout.EAST, chapterDescriptionTextArea, 0, SpringLayout.EAST, chapterImageLabel);
        dynamicPanel.add(chapterDescriptionTextArea);

        // Chapter Select
        chapterSelectButton = new JButton("Select Chapter");
        chapterSelectButton.setFont(helvetiHandFont);
        chapterSelectButton.setForeground(Color.WHITE);
        chapterSelectButton.setContentAreaFilled(false);
        chapterSelectButton.setHorizontalAlignment(SwingConstants.RIGHT);
        chapterSelectButton.setFocusPainted(false);
        chapterSelectButton.setBorderPainted(false);
        chapterSelectButton.setFont(helvetiHandFont);
        chapterSelectButton.setForeground(Color.WHITE);
        chapterSelectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButtonMouseListeners(chapterSelectButton, e -> {
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
        });
        layout.putConstraint(SpringLayout.EAST, chapterSelectButton, -250, SpringLayout.EAST, dynamicPanel);
        layout.putConstraint(SpringLayout.SOUTH, chapterSelectButton, -100, SpringLayout.SOUTH, dynamicPanel);
        panel.add(chapterSelectButton);

        // Set Z-Order
        panel.setComponentZOrder(chapterImageLabel, 1);
        panel.setComponentZOrder(chapterDescriptionTextArea, 1);
        panel.setComponentZOrder(chapterSelectButton, 1);
    
        // Add previous components to list
        previousComponents.add(chapterImageLabel);
        previousComponents.add(chapterDescriptionTextArea);
        previousComponents.add(chapterSelectButton);

        // Refresh panel
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
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
        output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
