package developer.ezandro.screensoundmusic.services;

import developer.ezandro.screensoundmusic.entities.Artist;
import developer.ezandro.screensoundmusic.entities.Song;
import developer.ezandro.screensoundmusic.repositories.SongRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song createSong(String title, Artist artist) {
        return this.songRepository.save(new Song(null, title, artist));
    }

    public List<Song> getAllSongs() {
        return Collections.unmodifiableList(this.songRepository.findAll());
    }
}