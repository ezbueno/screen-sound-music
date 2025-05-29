package developer.ezandro.screensoundmusic.services;

import developer.ezandro.screensoundmusic.entities.Artist;
import developer.ezandro.screensoundmusic.entities.ArtistType;
import developer.ezandro.screensoundmusic.exception.ArtistNotFoundException;
import developer.ezandro.screensoundmusic.repositories.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist createArtist(String name, ArtistType type) {
        return this.artistRepository.save(new Artist(null, name, type));
    }

    public List<Artist> getAllArtists() {
        return Collections.unmodifiableList(this.artistRepository.findAll());
    }

    public Artist findArtistById(Integer artistId) {
        return this.artistRepository
                .findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException(String.format("%nArtist with ID: %d not found.", artistId)));
    }

    public Optional<Artist> findByName(String name) {
        return this.artistRepository.findByName(name);
    }
}