package Source.Logic; 

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Audio {
    private static Clip music;
    private static Clip sfx;
    private static Clip voice;
    private static float musicVolume = 100;
    private static float sfxVolume = 100;
    private static float voiceVolume = 100;

    // Constructor
    public Audio() {
    }

    // Play music
    public void playMusic(String path) {
        try {
            File musicPath = new File(path);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            music = AudioSystem.getClip();
            music.open(audioInput);
            setMusicVolume(musicVolume);
            music.loop(Clip.LOOP_CONTINUOUSLY);
            music.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Stop music
    public void stopMusic() {
        if (music != null && music.isRunning()) {
            music.stop();
        }
    }

    // Set music volume
    public void setMusicVolume(float volume) {
        musicVolume = volume;
        if (music != null) {
            FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    // Get music volume
    public static float getMusicVolume() {
        return musicVolume;
    }

    // Play SFX
    public void playSFX(String path) {
        try {
            File sfxPath = new File(path);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(sfxPath);
            sfx = AudioSystem.getClip();
            sfx.open(audioInput);
            setSFXVolume(sfxVolume);
            sfx.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Play Select SFX
    public void playSelectSFX() {
        try {
            File sfxPath = new File("/Audio/SFX/menu_select.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(sfxPath);
            sfx = AudioSystem.getClip();
            sfx.open(audioInput);
            setSFXVolume(sfxVolume);
            sfx.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Play Hover SFX
    public void playHoverSFX() {
        try {
            File sfxPath = new File("/Audio/SFX/menu_hover.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(sfxPath);
            sfx = AudioSystem.getClip();
            sfx.open(audioInput);
            setSFXVolume(sfxVolume);
            sfx.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Stop SFX
    public void stopSFX() {
        if (sfx != null && sfx.isRunning()) {
            sfx.stop();
        }
    }

    // Set SFX volume
    public void setSFXVolume(float volume) {
        sfxVolume = volume;
        if (sfx != null) {
            FloatControl gainControl = (FloatControl) sfx.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    // Get SFX volume
    public static float getSFXVolume() {
        return sfxVolume;
    }

    // Play voice
    public void playVoice(String path) {
        try {
            File voicePath = new File(path);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(voicePath);
            voice = AudioSystem.getClip();
            voice.open(audioInput);
            setVoiceVolume(voiceVolume);
            voice.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Stop voice
    public void setVoiceVolume(float volume) {
        voiceVolume = volume;
        if (voice != null) {
            FloatControl gainControl = (FloatControl) voice.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    // Get voice volume
    public static float getVoiceVolume() {
        return voiceVolume;
    }
}