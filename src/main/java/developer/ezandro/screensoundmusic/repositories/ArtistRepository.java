package developer.ezandro.screensoundmusic.repositories;

import developer.ezandro.screensoundmusic.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    @Query("SELECT a FROM Artist a WHERE LOWER(a.name) = LOWER(:name)")
    Optional<Artist> findByName(@Param(value = "name") String name);
}