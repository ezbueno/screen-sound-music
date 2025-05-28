package developer.ezandro.screensoundmusic.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ArtistType artistType;
    @OneToMany(mappedBy = "artist")
    private List<Song> songs;

    public Artist() {}

    public Artist(Integer id, String name, ArtistType artistType) {
        this.id = id;
        this.name = name;
        this.artistType = artistType;
        this.songs = new ArrayList<>();
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ArtistType getArtistType() {
        return this.artistType;
    }

    public List<Song> getSongs() {
        return this.songs;
    }
}