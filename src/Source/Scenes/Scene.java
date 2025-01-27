package Source.Scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import Source.Logic.Audio;
// import Source.Logic.Save;
import Assets.Scripts.Script;
import Source.Logic.UI;

public class Scene extends JFrame {
    private Audio audio;
    private boolean isFirstScene;
    private Component previousComponent;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Font helvetiHandFont;
    private ImageIcon backgroundImage, characterImage;
    private int sceneID, screenWidth = screenSize.width, screenHeight = screenSize.height;
    private JLabel backgroundLabel, characterLabel;
    private JButton button, yesButton, noButton, triangleButton;
    private JDialog dialog;
    private JLabel messageLabel/*, savedLabel, errorLabel*/;
    private JPanel buttonPanel, panel, sceneUpdater;
    // private Save save;
    private SpringLayout layout;

    // Constructor
    public Scene(int SceneID) {
        sceneID = SceneID;
        isFirstScene = true;

        // Font
        try {
            helvetiHandFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Assets/Fonts/HelvetiHand.ttf")).deriveFont(screenHeight/30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(helvetiHandFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        // Music
        audio = new Audio();
        audio.stopMusic();

        // Frame
        setTitle("Debugged");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        // Panel
        panel = new JPanel();
        layout = new SpringLayout();
        panel.setLayout(layout);

        backgroundLabel = new JLabel();
        characterLabel = new JLabel();

        // Button properties
        String[] buttonNames = {"Back", /* "Save", */ "Settings", "Hide UI", "Log"};
        ActionListener[] buttonActions = {
            evt -> {
                audio.playSelectSFX();
                confirmExit();
            },
            // evt -> {
            //     audio.playSelectSFX();
            //     confirmSave();
            // },
            evt -> {
                audio.playSelectSFX();
                new Setting(Scene.this);
            },
            evt -> {
                audio.playSelectSFX();
                for (Component component : panel.getComponents()) {
                    if (component != backgroundLabel && component != characterLabel && !component.getName().equals("Hide UI")) {
                        component.setVisible(!component.isVisible());
                    }
                }
            },
            evt -> {
                audio.playSelectSFX();
                new LogWindow(sceneID);
            }
        };

        // Create and add buttons
        for (int i = 0; i < buttonNames.length; i++) {
            button = UI.createButton(buttonNames[i], helvetiHandFont);
            layout.putConstraint(SpringLayout.WEST, button, (int)(screenWidth * 0.15) + i * (int)(screenWidth * 0.1), SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, button, (int)(screenHeight * 0.05), SpringLayout.NORTH, panel);
            UI.addButtonMouseListeners(button, buttonActions[i]);
            if (buttonNames[i].equals("Back")) {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    buttonActions[0].actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
                }
                }
            });
            }
            panel.add(button);
        }

        // Scene updater
        sceneUpdater = new JPanel();
        sceneUpdater.setOpaque(false);
        sceneUpdater.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                sceneUpdater.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseClicked(MouseEvent evt) {
                setFocusable(true);
                requestFocusInWindow();
                audio.playSelectSFX();
                sceneID++;
                updateScene(panel);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == Setting.getKeyBinding()) {
                    audio.playSelectSFX();
                    sceneID++;
                    updateScene(panel);
                }
            }
        });
        layout.putConstraint(SpringLayout.WEST, sceneUpdater, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, sceneUpdater, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, sceneUpdater, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, sceneUpdater, 0, SpringLayout.SOUTH, panel);
        panel.add(sceneUpdater);

        setFocusable(true);
        requestFocusInWindow();
        updateScene(panel);
        add(panel);
        setVisible(true);
    }

    // Update scene
    private void updateScene(JPanel panel) {
        if (!isFirstScene) {
            for (Component component : panel.getComponents()) {
                if (component == previousComponent) {
                    panel.remove(component);
                }
            }
        } else {
            isFirstScene = false;
        }
        String[] storyTexts = Script.storyTexts;
        JLabel storyText = null;
        audio = new Audio();
        if (sceneID > 0 && sceneID <= storyTexts.length) {
            storyText = new JLabel(storyTexts[sceneID - 1]);

            // Music
            switch (sceneID) {
                case 1:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Audio/Music/0.wav");
                    break;
                case 39:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Audio/Music/1.wav");
                    break;
                case 86:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Audio/Music/2.wav");
                    break;
                case 129:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Audio/Music/3.wav");
                    break;
                case 176:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Audio/Music/4.wav");
                    break;
                case 228:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Audio/Music/5.wav");
                    break;
                case 271:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Audio/Music/6.wav");
                    break;
                case 999:
                    audio.stopMusic();
                    audio.playMusic("src/Assets/Audio/Music/7.wav");
                    break;
            }

            // Sound Effects
            switch (sceneID) {
                case 1:
                    audio.playSFX("src/Assets/Audio/SFX/sfx_boot.wav");
                    break;
                case 3:
                    audio.stopSFX();
                    audio.playSFX("src/Assets/Audio/SFX/sfx_typing.wav");
                    break;
                case 4, 15:
                    audio.stopSFX();
                    audio.playSelectSFX();
                    break;
                case 6:
                    audio.stopSFX();
                    audio.playSFX("src/Assets/Audio/SFX/sfx_error.wav");
                    break;
                case 16, 19, 32:
                    audio.stopSFX();
                    audio.playSFX("src/Assets/Audio/SFX/sfx_glitch.wav");
                    break;
                case 18:
                    audio.stopSFX();
                    audio.playSFX("src/Assets/Audio/SFX/sfx_shutdown.wav");
                    break;
                case 22:
                    audio.stopSFX();
                    audio.playSFX("src/Assets/Audio/SFX/sfx_whoosh.wav");
                    break;
            }
        }

        // Set story text
        if (storyText != null) {
            storyText.setFont(helvetiHandFont);
            storyText.setForeground(new Color(255, 255, 197));
            if (sceneID == 1 || sceneID == 39 || sceneID == 86 || sceneID == 129 || sceneID == 176 || sceneID == 228 || sceneID == 271) {
                storyText.setVerticalAlignment(SwingConstants.CENTER);
                storyText.setHorizontalAlignment(SwingConstants.CENTER);
                layout.putConstraint(SpringLayout.VERTICAL_CENTER, storyText, 0, SpringLayout.VERTICAL_CENTER, panel);
            } else {
                storyText.setVerticalAlignment(SwingConstants.TOP);
                storyText.setHorizontalAlignment(SwingConstants.LEFT);
                layout.putConstraint(SpringLayout.SOUTH, storyText, -(int)(screenHeight * 0.10), SpringLayout.SOUTH, panel);
            }
            layout.putConstraint(SpringLayout.WEST, storyText, (int)(screenWidth * 0.15), SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.EAST, storyText, -(int)(screenWidth * 0.15), SpringLayout.EAST, panel);
            panel.add(storyText, Integer.valueOf(0));
            previousComponent = storyText;
        }

        // Set character image
        String charPath;
        switch (sceneID) {
            case 63, 64, 66, 68, 69, 70, 71, 72, 73, 82, 83:
                charPath = "src/Assets/Images/Characters/raina.png";
                break;
            case 103, 106, 113, 141, 142, 143, 144, 145, 146, 245, 256, 257:
                charPath = "src/Assets/Images/Characters/elara_young.png";
                break;
            case 97, 98, 99, 101, 102, 105, 107, 109, 110, 111, 112, 114:
                charPath = "src/Assets/Images/Characters/grandma.png";
                break;
            case 161, 162:
                charPath = "src/Assets/Images/Characters/elara_old.png";
                break;
            case 22, 23, 24, 25, 26, 27, 28, 29, 30, 33, 34, 35, 36, 37, 47, 48, 
            49, 50, 51, 52, 55, 56, 74, 75, 78, 79, 80, 81, 123, 124, 125, 170, 
            171, 172, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 259, 
            260, 261, 262, 263, 264, 265, 266:
                charPath = "src/Assets/Images/Characters/byte.png";
                break;
            case 186, 187, 193, 194, 196, 197, 208:
                charPath = "src/Assets/Images/Characters/ex.png";
                break;
            case 252, 253, 254, 255:
                charPath = "src/Assets/Images/Characters/friend.png";
                break;
            case 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 
            292, 293, 294, 295, 296, 297, 298, 299, 300, 302, 303, 304, 305, 
            306, 307, 308, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 
            321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 
            334:
                charPath = "src/Assets/Images/Characters/elara.png";
                break;
            default:
                charPath = "";
                break;
        }

        // Set background image
        String bgPath;
        switch (sceneID) {
            case 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 195, 196, 197, 198,
            199:
                bgPath = "src/Assets/Images/Backgrounds/bg_roomelara.jpg";
                break;
            case 16, 17, 61, 62, 63, 64, 65, 68, 69, 70, 71, 72, 73, 74, 
            75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 92, 107, 108, 109, 
            114, 115, 116, 117, 122, 170, 179, 180, 191, 231, 232, 246, 
            251, 274, 275:
                bgPath = "src/Assets/Images/Backgrounds/bg_glitch.jpg";
                break;
            case 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 
            35, 36, 37, 45, 46, 47, 48, 49, 50, 51, 52, 123, 124, 125, 171, 
            172:
                bgPath = "src/Assets/Images/Backgrounds/bg_nullspace.jpg";
                break;
            case 53, 54, 55, 56, 57, 58, 59, 60:
                bgPath = "src/Assets/Images/Backgrounds/bg_nullspace2.jpg";
                break;
            case 66, 67, 93, 94, 95, 96, 134, 140, 147, 181, 200, 201, 
            202, 233, 250, 276:
                bgPath = "src/Assets/Images/Backgrounds/bg_fracture.jpg";
                break;
            case 97, 98, 99:
                bgPath = "src/Assets/Images/Backgrounds/bg_roomgrandma.jpg";
                break;
            case 100, 101, 102:
                bgPath = "src/Assets/Images/Backgrounds/bg_roomdining.jpg";
                break;
            case 103, 104, 105, 106:
                bgPath = "src/Assets/Images/Backgrounds/bg_roomwinter.jpg";
                break;
            case 110, 111, 112, 113:
                bgPath = "src/Assets/Images/Backgrounds/bg_roomhospital.jpg";
                break;
            case 132, 133, 168, 169:
                bgPath = "src/Assets/Images/Backgrounds/bg_map.jpg";
                break;
            case 135, 136, 137, 138, 139:
                bgPath = "src/Assets/Images/Backgrounds/bg_roomflag.jpg";
                break;
            case 141, 142, 143, 144, 145, 146:
                bgPath = "src/Assets/Images/Backgrounds/bg_roommap.jpg";
                break;
            case 148, 149, 150, 151, 152, 153, 154:
                bgPath = "src/Assets/Images/Backgrounds/bg_roomclass.jpg";
                break;
            case 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 
            166, 167:
                bgPath = "src/Assets/Images/Backgrounds/bg_roomflag2.jpg";
                break;
            case 182, 183, 184, 185, 186, 187:
                bgPath = "src/Assets/Images/Backgrounds/bg_room.jpg";
                break;
            case 188, 189, 190, 252, 253, 254, 255, 256, 257:
                bgPath = "src/Assets/Images/Backgrounds/bg_cafe.jpg";
                break;
            case 192, 193, 194, 205, 206, 207, 208, 209, 210, 213, 214, 
            215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226:
                bgPath = "src/Assets/Images/Backgrounds/bg_room2.jpg";
                break;
            case 234, 235, 236, 237, 238, 239, 240:
                bgPath = "src/Assets/Images/Backgrounds/bg_arcade.jpg";
                break;
            case 241, 242, 243, 244, 245:
                bgPath = "src/Assets/Images/Backgrounds/bg_park.jpg";
                break;
            case 258, 259, 260, 261, 262, 263, 264, 265, 266:
                bgPath = "src/Assets/Images/Backgrounds/bg_arcade2.jpg";
                break;
            case 277, 278, 324, 325, 326, 327, 328, 329, 336, 337, 338,
            339, 340, 341, 342, 343:
                bgPath = "src/Assets/Images/Backgrounds/bg_room3.jpg";
                break;
            case 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289,
            290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301,
            302, 303, 304, 305, 306, 307, 308, 330, 331, 332, 333, 334:
                bgPath = "src/Assets/Images/Backgrounds/bg_mirror.jpg";
                break;
            case 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321,
            322, 323:
                bgPath = "src/Assets/Images/Backgrounds/bg_room4.jpg";
                break;
            case 335:
                bgPath = "src/Assets/Images/Backgrounds/bg_mirror2.jpg";
                break;
            default:
                bgPath = "";
                break;
        }

        // Character image
        characterImage = new ImageIcon(charPath);
        characterLabel.setIcon(characterImage);
        characterLabel.setIcon(new ImageIcon(characterImage.getImage().getScaledInstance((int)(screenHeight * 0.85), (int)(screenHeight * 0.85), Image.SCALE_FAST)));
        panel.add(characterLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, characterLabel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.SOUTH, characterLabel, 0, SpringLayout.SOUTH, panel);

        // Background image
        backgroundImage = new ImageIcon(bgPath);
        backgroundLabel.setIcon(backgroundImage);
        panel.add(backgroundLabel);
        setVisible(true);
        backgroundLabel.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST)));
        layout.putConstraint(SpringLayout.WEST, backgroundLabel, 0, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, backgroundLabel, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, backgroundLabel, 0, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, backgroundLabel, 0, SpringLayout.SOUTH, panel);

        // Triangle Button
        triangleButton = UI.createButton("<html><h2>▼</h2></html>", helvetiHandFont);
        if (sceneID != 1 && sceneID != 39 && sceneID != 86 && sceneID != 129 && sceneID != 176 && sceneID != 228 && sceneID != 271) {
            panel.add(triangleButton);
        }
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, triangleButton, 0, SpringLayout.HORIZONTAL_CENTER, panel);
        layout.putConstraint(SpringLayout.SOUTH, triangleButton, (int)(screenHeight * 0.01), SpringLayout.SOUTH, panel);

        panel.setBackground(Color.BLACK);
        panel.revalidate();
        panel.repaint();

        // Go back to main menu
        if (storyText == null) {
            new MainMenu();
            dispose();
        }
    }

    // Confirm exit
    private void confirmExit() {
        dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setModal(true);
        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        messageLabel = new JLabel("Are you sure you want to go back to the main menu?", JLabel.CENTER);
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
            new MainMenu();
            dialog.dispose();
            dispose();
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

    // Confirm save
    // private void confirmSave() {
    //     dialog = new JDialog();
    //     dialog.setUndecorated(true);
    //     dialog.setModal(true);
    //     panel = new JPanel();
    //     panel.setBackground(Color.BLACK);
    //     panel.setLayout(new BorderLayout());
    //     messageLabel = new JLabel("Do you want to save your progress?", JLabel.CENTER);
    //     messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    //     messageLabel.setForeground(Color.WHITE);
    //     messageLabel.setFont(helvetiHandFont);
    //     panel.add(messageLabel, BorderLayout.CENTER);
    //     buttonPanel = new JPanel();
    //     buttonPanel.setOpaque(false);
    //     buttonPanel.setLayout(new FlowLayout());
    //     yesButton = new JButton("Yes");
    //     yesButton.setFont(helvetiHandFont);
    //     yesButton.setForeground(Color.WHITE);
    //     yesButton.setContentAreaFilled(false);
    //     yesButton.setBorderPainted(false);
    //     UI.addButtonMouseListeners(yesButton, e -> {
    //         save = new Save();
    //         save.saveGame(sceneID);
    //         dialog.dispose();
    //         if (save.isGameSaved(sceneID)) {
    //             savedLabel = new JLabel("Game Saved Successfully!", JLabel.CENTER);
    //             savedLabel.setForeground(Color.GREEN);
    //             savedLabel.setFont(helvetiHandFont);
    //             panel.add(savedLabel, BorderLayout.CENTER);
    //             timer = new Timer(2000, new ActionListener() {
    //                 @Override
    //                 public void actionPerformed(ActionEvent e) {
    //                     panel.remove(savedLabel);
    //                     panel.revalidate();
    //                     panel.repaint();
    //                 }
    //             });
    //             timer.setRepeats(false);
    //             timer.start();
    //         } else {
    //             errorLabel = new JLabel("Failed to Save Game!", JLabel.CENTER);
    //             errorLabel.setForeground(Color.RED);
    //             errorLabel.setFont(helvetiHandFont);
    //             panel.add(errorLabel, BorderLayout.CENTER);
    //             timer = new Timer(2000, new ActionListener() {
    //                 @Override
    //                 public void actionPerformed(ActionEvent e) {
    //                     panel.remove(errorLabel);
    //                     panel.revalidate();
    //                     panel.repaint();
    //                 }
    //             });
    //             timer.setRepeats(false);
    //             timer.start();
    //         }
    //     });
    //     buttonPanel.add(yesButton);
    //     noButton = new JButton("No");
    //     noButton.setFont(helvetiHandFont);
    //     noButton.setForeground(Color.WHITE);
    //     noButton.setContentAreaFilled(false);
    //     noButton.setBorderPainted(false);
    //     UI.addButtonMouseListeners(noButton, e -> {
    //         audio.playSelectSFX();
    //         dialog.dispose();
    //     });
    //     buttonPanel.add(noButton);
    //     panel.add(buttonPanel, BorderLayout.SOUTH);
    //     dialog.getContentPane().add(panel);
    //     dialog.pack();
    //     dialog.setLocationRelativeTo(null);
    //     dialog.setVisible(true);
    // }
}