package developer.ezandro.screensoundmusic.repositories;

import developer.ezandro.screensoundmusic.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer> {
    @Query("SELECT s FROM Song s WHERE s.artist.name ILIKE %:artistName%")
    List<Song> findByArtistName(@Param("artistName") String artistName);
}