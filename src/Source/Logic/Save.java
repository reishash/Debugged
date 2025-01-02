package Source.Logic;

import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;

import Source.Scenes.Scene;

public class Save {
    // Constructor
    public Save() {
    }

    // Load game
    public void loadGame() {
        try (FileReader reader = new FileReader("src/Assets/SaveData/savegame.txt")) {
            int sceneID = reader.read();
            new Scene(sceneID);
            JOptionPane.showMessageDialog(null, "Game loaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save game
    public void saveGame(int sceneID) {
        try (FileWriter writer = new FileWriter("src/Assets/SaveData/savegame.txt")) {
            writer.write(sceneID);
            JOptionPane.showMessageDialog(null, "Game saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Check if game is saved
    public boolean isGameSaved(int sceneID) {
        try (FileReader reader = new FileReader("src/Assets/SaveData/savegame.txt")) {
            int sceneSave = reader.read();
            return sceneSave == sceneID;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}