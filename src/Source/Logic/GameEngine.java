package Source.Logic;

import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JOptionPane;

import Source.Scenes.MainMenu;
import Source.Scenes.Scene;

public class GameEngine {
    
    public GameEngine() {
    }

    public void initializeMainMenu() {
        MainMenu mainMenu = new MainMenu();
    }

    public void startGame() {
        Scene scene = new Scene(1);
    }

    public void loadGame() {
        try (FileReader reader = new FileReader("src/Assets/SaveData/savegame.txt")) {
                int sceneID = reader.read();
                Scene scene = new Scene(sceneID);
                JOptionPane.showMessageDialog(null, "Game loaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveGame(int sceneID) {
        try (FileWriter writer = new FileWriter("src/Assets/SaveData/savegame.txt")) {
            writer.write(sceneID);
            JOptionPane.showMessageDialog(null, "Game saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}