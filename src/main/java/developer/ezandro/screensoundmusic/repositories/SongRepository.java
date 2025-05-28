package developer.ezandro.screensoundmusic.repositories;

import developer.ezandro.screensoundmusic.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Integer> {
}