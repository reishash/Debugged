package Source.Scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Assets.Scripts.ScriptRaw;

public class LogWindow extends JFrame {
    private Font helvetiHandFont;
    private int sceneID;
    private int checkpointID;
    private JScrollPane scrollPane;
    private JTextArea logTextArea;
    private StringBuilder logText;

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
        scrollPane = new JScrollPane(logTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setBackground(Color.DARK_GRAY);
        add(scrollPane);

        loadLogText();
        setVisible(true);
    }

    // Get the last checkpoint ID based on the current scene
    private int getLastCheckpointID(int sceneID) {
        if (sceneID >= 39 && sceneID < 86) {
            return 39;
        } else if (sceneID >= 86 && sceneID < 128) {
            return 86;
        } else if (sceneID >= 129 && sceneID < 176) {
            return 129;
        } else if (sceneID >= 176 && sceneID < 228) {
            return 176;
        } else if (sceneID >= 228 && sceneID < 276) {
            return 228;
        } else if (sceneID >= 276 && sceneID < 353) {
            return 276;
        } else {
            return 1;
        }
    }

    // Load the story log text based on the current scene
    private void loadLogText() {
        logText = new StringBuilder();
        String[] storyTexts = ScriptRaw.storyTexts;
        for (int i = checkpointID - 1; i < sceneID; i++) {
            if (i >= 0 && i < storyTexts.length) {
                logText.append(storyTexts[i]).append("\n\n");
            }
        }
        logTextArea.setText(logText.toString());
    }
}