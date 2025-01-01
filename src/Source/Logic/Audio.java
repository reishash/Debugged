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

    public Audio() {
    }

    public void playMusic(String Path) {
        try {
            File musicPath = new File(Path);
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

    public void stopMusic() {
        if (music != null && music.isRunning()) {
            music.stop();
        }
    }

    public void setMusicVolume(float volume) {
        musicVolume = volume;
        if (music != null) {
            FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    public static float getMusicVolume() {
        return musicVolume;
    }

    public void playSFX(String Path) {
        try {
            File sfxPath = new File(Path);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(sfxPath);
            sfx = AudioSystem.getClip();
            sfx.open(audioInput);
            setSFXVolume(sfxVolume);
            sfx.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSFXVolume(float volume) {
        sfxVolume = volume;
        if (sfx != null) {
            FloatControl gainControl = (FloatControl) sfx.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    public static float getSFXVolume() {
        return sfxVolume;
    }

    public void stopSFX() {
        if (sfx != null && sfx.isRunning()) {
            sfx.stop();
        }
    }

    public void playVoice(String Path) {
        try {
            File voicePath = new File(Path);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(voicePath);
            voice = AudioSystem.getClip();
            voice.open(audioInput);
            setVoiceVolume(voiceVolume);
            voice.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setVoiceVolume(float volume) {
        voiceVolume = volume;
        if (voice != null) {
            FloatControl gainControl = (FloatControl) voice.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    public static float getVoiceVolume() {
        return voiceVolume;
    }
}