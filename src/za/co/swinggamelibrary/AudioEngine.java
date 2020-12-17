/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author dkrou
 */
public class AudioEngine {

    private static AudioEngine single_instance = null;

    HashMap<String, Sound> sounds;
    HashMap<URL, String> cachedSounds;

    private AudioEngine() {
        sounds = new HashMap<>();
        cachedSounds = new HashMap<>();
    }

    public static AudioEngine getInstance() {
        if (single_instance == null) {
            TinySound.init();
            single_instance = new AudioEngine();
        }

        return single_instance;
    }

    public String playSound(URL fileUrl, float volume) throws LineUnavailableException, UnsupportedAudioFileException, IOException, URISyntaxException {
        String audioId = cache(fileUrl);
        sounds.get(audioId).play(volume);
        return audioId;
    }

    public String playMusic(URL fileUrl, boolean loop, float volume) throws LineUnavailableException, UnsupportedAudioFileException, IOException, URISyntaxException {
        throw new NotImplementedException();
    }

    /* Only relevant for music
    public void pause(String audioId) {
        sounds.get(audioId).stop();
    }

    public void pauseAll() {
        sounds.entrySet().forEach((entry) -> {
            entry.getValue().stop();
        });
    }
     */
    public void stop(String audioId) {
        sounds.get(audioId).stop();
    }

    public void stopAll() {
        sounds.entrySet().stream().map((entry) -> entry.getValue()).map((clip) -> {
            clip.stop();
            return clip;
        });
    }

    public void resume(String audioId) {
        sounds.get(audioId).stop();
    }

    public void resumeAll() {
        sounds.entrySet().forEach((entry) -> {
            entry.getValue().play();
        });
    }

    public String cache(URL fileUrl) throws LineUnavailableException, UnsupportedAudioFileException, IOException, URISyntaxException {
        String cachedAudioId = cachedSounds.get(fileUrl);
        if (cachedAudioId == null) {
            String audioId = UUID.randomUUID().toString();
            Sound sound = TinySound.loadSound(fileUrl);
            sounds.put(audioId, sound);
            cachedSounds.put(fileUrl, audioId);
            return audioId;
        }

        return cachedAudioId;
    }

    public void uncache(String audioId) {
        stop(audioId);
        sounds.get(audioId).unload();
        sounds.remove(audioId);
        // remove from cachedSounds
        Iterator<Map.Entry<URL, String>> cachedSoundsIterator = cachedSounds.entrySet().iterator();
        while (cachedSoundsIterator.hasNext()) {
            Map.Entry<URL, String> cachedAudioEntry = cachedSoundsIterator.next();
            if (cachedAudioEntry.getValue().equals(audioId)) {
                cachedSoundsIterator.remove();
                break;
            }
        }
    }

    public void uncacheAll() {
        Iterator<Map.Entry<String, Sound>> soundsIterator = sounds.entrySet().iterator();
        while (soundsIterator.hasNext()) {
            Map.Entry<String, Sound> soundEntry = soundsIterator.next();
            soundEntry.getValue().unload();
            soundsIterator.remove();
            // remove from cachedSounds
            Iterator<Map.Entry<URL, String>> cachedSoundsIterator = cachedSounds.entrySet().iterator();
            while (cachedSoundsIterator.hasNext()) {
                Map.Entry<URL, String> cachedSoundEntry = cachedSoundsIterator.next();
                if (cachedSoundEntry.getValue().equals(soundEntry.getKey())) {
                    cachedSoundsIterator.remove();
                    break;
                }
            }
        }
    }
}
