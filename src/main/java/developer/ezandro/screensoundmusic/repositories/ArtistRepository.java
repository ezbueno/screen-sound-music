package developer.ezandro.screensoundmusic.repositories;

import developer.ezandro.screensoundmusic.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    Optional<Artist> findArtistByName(String name);
}