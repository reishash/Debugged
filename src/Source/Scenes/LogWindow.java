package Source.Scenes;

import javax.swing.*;
import java.awt.*;
import Assets.Scripts.ScriptRaw;

public class LogWindow extends JFrame {
    private JTextArea logTextArea;
    private int sceneID;
    private int checkpointID;
    private Font helvetiHandFont;

    public LogWindow(int sceneID) {
        this.sceneID = sceneID;
        this.checkpointID = getLastCheckpointID(sceneID);

        // Load Font
        try {
            helvetiHandFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Assets/Fonts/HelvetiHand.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(helvetiHandFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Frame
        setTitle("Story Log");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        // Text area
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        logTextArea.setFont(helvetiHandFont.deriveFont(20f));
        logTextArea.setForeground(Color.WHITE);
        logTextArea.setBackground(Color.BLACK);
        logTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setBackground(Color.DARK_GRAY);
        add(scrollPane);

        loadLogText();
        setVisible(true);
    }

    // Get the last checkpoint ID based on the current scene
    private int getLastCheckpointID(int sceneID) {
        if (sceneID >= 39) {
            return 39;
        } else if (sceneID >= 86) {
            return 86;
        } else {
            return 1;
        }
    }

    // Load the story log text based on the current scene
    private void loadLogText() {
        StringBuilder logText = new StringBuilder();
        String[] storyTexts = ScriptRaw.storyTexts;
        for (int i = checkpointID - 1; i < sceneID; i++) {
            if (i >= 0 && i < storyTexts.length) {
                logText.append(storyTexts[i]).append("\n\n");
            }
        }
        logTextArea.setText(logText.toString());
    }
}