/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

/**
 *
 * @author dkrou
 */
public class AudioEngine {

    private static AudioEngine single_instance = null;

    HashMap<String, Sound> sounds;
    HashMap<URL, String> cachedSounds;
    HashMap<String, Music> music;
    HashMap<URL, String> cachedMusic;

    private AudioEngine() {
        sounds = new HashMap<>();
        cachedSounds = new HashMap<>();
        music = new HashMap<>();
        cachedMusic = new HashMap<>();
    }

    public static AudioEngine getInstance() {
        if (single_instance == null) {
            TinySound.init();
            single_instance = new AudioEngine();
        }

        return single_instance;
    }

    public String playSound(URL fileUrl, float volume) {
        String audioId = cacheSound(fileUrl);
        sounds.get(audioId).play(volume);
        return audioId;
    }

    public String playMusic(URL fileUrl, boolean loop, float volume) {
        String audioId = cacheMusic(fileUrl);
        music.get(audioId).play(loop, volume);
        return audioId;
    }

    public void pauseMusic(String audioId) {
        music.get(audioId).pause();
    }

    public void pauseAllMusic() {
        music.entrySet().forEach((entry) -> {
            entry.getValue().pause();
        });
    }

    public void resumeMusic(String audioId) {
        music.get(audioId).resume();
    }

    public void resumeAllMusic() {
        music.entrySet().forEach((entry) -> {
            entry.getValue().resume();
        });
    }

    public void stopMusic(String audioId) {
        music.get(audioId).stop();
    }

    public void stopAllMusic() {
        music.entrySet().stream().map((entry) -> entry.getValue()).map((m) -> {
            m.stop();
            return m;
        });
    }

    public void stopSound(String audioId) {
        sounds.get(audioId).stop();
    }

    public void stopAllSounds() {
        sounds.entrySet().stream().map((entry) -> entry.getValue()).map((sound) -> {
            sound.stop();
            return sound;
        });
    }

    public String cacheSound(URL fileUrl) {
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

    public void uncacheSound(String audioId) {
        stopSound(audioId);
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

    public void uncacheAllSounds() {
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

    public String cacheMusic(URL fileUrl) {
        String cachedAudioId = cachedMusic.get(fileUrl);
        if (cachedAudioId == null) {
            String audioId = UUID.randomUUID().toString();
            Music m = TinySound.loadMusic(fileUrl);
            music.put(audioId, m);
            cachedMusic.put(fileUrl, audioId);
            return audioId;
        }

        return cachedAudioId;
    }

    public void uncacheMusic(String audioId) {
        stopMusic(audioId);
        music.get(audioId).unload();
        music.remove(audioId);
        // remove from cachedSounds
        Iterator<Map.Entry<URL, String>> cachedMusicIterator = cachedMusic.entrySet().iterator();
        while (cachedMusicIterator.hasNext()) {
            Map.Entry<URL, String> cachedMusicEntry = cachedMusicIterator.next();
            if (cachedMusicEntry.getValue().equals(audioId)) {
                cachedMusicIterator.remove();
                break;
            }
        }
    }

    public void uncacheAllMusic() {
        Iterator<Map.Entry<String, Music>> musicIterator = music.entrySet().iterator();
        while (musicIterator.hasNext()) {
            Map.Entry<String, Music> musicEntry = musicIterator.next();
            musicEntry.getValue().unload();
            musicIterator.remove();
            // remove from cachedSounds
            Iterator<Map.Entry<URL, String>> cachedSoundsIterator = cachedMusic.entrySet().iterator();
            while (cachedSoundsIterator.hasNext()) {
                Map.Entry<URL, String> cachedSoundEntry = cachedSoundsIterator.next();
                if (cachedSoundEntry.getValue().equals(musicEntry.getKey())) {
                    cachedSoundsIterator.remove();
                    break;
                }
            }
        }
    }

    public void uncacheAll() {
        uncacheAllMusic();
        uncacheAllSounds();
    }
}
