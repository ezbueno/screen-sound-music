package developer.ezandro.screensoundmusic.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public Song() {}

    public Song(Integer id, String title, Artist artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Artist getArtist() {
        return this.artist;
    }
}