package Source.Scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import Source.Logic.Audio;
// import Source.Logic.Save;
import Source.Logic.UI;

public class MainMenu extends JFrame {
    private Audio audio;
    private Font helvetiHandFont;
    private JDialog dialog;
    private JPanel panel, buttonPanel;
    private JLabel titleLabel, backgroundLabel, messageLabel;
    private JButton button, yesButton, noButton;
    private ImageIcon titleImage, backgroundImage;
    // private Save save;
    private SpringLayout layout;

    // Constructor
    public MainMenu() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Font
        try {
            helvetiHandFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Assets/Fonts/HelvetiHand.ttf")).deriveFont(screenHeight/30f);
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
        audio.playMusic("src/Assets/Audio/Music/main_menu_music.wav");

        // Panel
        panel = new JPanel();
        layout = new SpringLayout();
        panel.setLayout(layout);

        // Title Image
        titleImage = new ImageIcon("src/Assets/Images/title_image.png");
        titleLabel = new JLabel(titleImage);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setIcon(new ImageIcon(titleImage.getImage().getScaledInstance((int)(screenWidth * 0.25), (int)(screenHeight * 0.2), Image.SCALE_FAST)));
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.NORTH, titleLabel, (int)(screenHeight * 0.025), SpringLayout.NORTH, panel);
        panel.add(titleLabel);
        
        // Button properties
        String[] buttonNames = {"Credits", "Exit Game", "Settings", "Select Chapter", /* "Load Game",*/ "Start Game"};
        ActionListener[] buttonActions = {
            evt -> {
                audio.playSelectSFX();
                audio.stopMusic();
                new Credits();
                dispose();
            },
                evt -> {
                audio.playSelectSFX();
                confirmExit();
            },
            evt -> {
                audio.playSelectSFX();
                audio.stopMusic();
                new Setting(MainMenu.this);
                dispose();
            },
            evt -> {
                audio.playSelectSFX();
                audio.stopMusic();
                new SelectChapter();
                dispose();
            },
            evt -> {
                audio.playSelectSFX();
                audio.stopMusic();
                new Scene(1);
                dispose();
            }
        };

        // Create and add buttons
        JButton[] buttons = new JButton[buttonNames.length];
        for (int i = 0; i < buttonNames.length; i++) {
            button = UI.createButton(buttonNames[i], helvetiHandFont);
            buttons[i] = button;
            if (buttonNames[i].equals("Credits")) {
                layout.putConstraint(SpringLayout.EAST, button, -(int)(screenWidth * 0.15), SpringLayout.EAST, panel);
                layout.putConstraint(SpringLayout.SOUTH, button, -(int)(screenHeight * 0.10), SpringLayout.SOUTH, panel);
            } else {
                layout.putConstraint(SpringLayout.WEST, button, (int)(screenWidth * 0.15), SpringLayout.WEST, panel);
                layout.putConstraint(SpringLayout.SOUTH, button, -(int)(screenHeight * 0.10) - (i-1) * (int)(screenHeight * 0.10), SpringLayout.SOUTH, panel);
            }
            UI.addButtonMouseListeners(button, buttonActions[i]);
            if (buttonNames[i].equals("Exit Game")) {
                addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent evt) {
                        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            buttonActions[1].actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
                        }
                    }
                });
            }
            int[] currentIndex = {0};
            InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = panel.getActionMap();
            inputMap.put(KeyStroke.getKeyStroke("UP"), "navigateUp");
            actionMap.put("navigateUp", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttons[currentIndex[0]].setForeground(Color.WHITE);
                    currentIndex[0] = (currentIndex[0] + 1) % buttons.length;
                    buttons[currentIndex[0]].setForeground(Color.YELLOW);
                    buttons[currentIndex[0]].requestFocus();
                }
            });
            inputMap.put(KeyStroke.getKeyStroke("DOWN"), "navigateDown");
            actionMap.put("navigateDown", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttons[currentIndex[0]].setForeground(Color.WHITE);
                    currentIndex[0] = (currentIndex[0] - 1 + buttons.length) % buttons.length;
                    buttons[currentIndex[0]].setForeground(Color.YELLOW);
                    buttons[currentIndex[0]].requestFocus();
                }
            });
            inputMap.put(KeyStroke.getKeyStroke("ENTER"), "pressButton");
            actionMap.put("pressButton", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonActions[currentIndex[0]].actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
                }
            });
            panel.add(button);
        }

        // Background Image
        backgroundImage = new ImageIcon("src/Assets/Images/Backgrounds/bg_mainmenu.jpg");
        backgroundLabel = new JLabel(backgroundImage);
        panel.add(backgroundLabel);
        setVisible(true);
        layout.putConstraint(SpringLayout.WEST, backgroundLabel, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, backgroundLabel, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, backgroundLabel, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backgroundLabel, 0, SpringLayout.SOUTH, panel);
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST)));

        add(panel);
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
    }

    // Confirm Exit Dialog
    private void confirmExit() {
        dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setModal(true);
        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        messageLabel = new JLabel("Are you sure you want to exit?", JLabel.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(helvetiHandFont);
        panel.add(messageLabel, BorderLayout.CENTER);
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout());
        yesButton = UI.createButton("Yes", helvetiHandFont);
        UI.addButtonMouseListeners(yesButton, e -> {
            audio.stopMusic();
            audio.playSelectSFX();
            System.exit(0);
        });
        yesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                yesButton.setForeground(Color.RED);
            }
        });
        buttonPanel.add(yesButton);
        noButton = UI.createButton("No", helvetiHandFont);
        UI.addButtonMouseListeners(noButton, e -> {
            audio.playSelectSFX();
            dialog.dispose();
        });
        buttonPanel.add(noButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}