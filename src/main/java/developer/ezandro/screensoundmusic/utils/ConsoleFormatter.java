package developer.ezandro.screensoundmusic.utils;

import developer.ezandro.screensoundmusic.entities.Artist;
import developer.ezandro.screensoundmusic.entities.Song;

import java.util.List;

public class ConsoleFormatter {
    private ConsoleFormatter() {
    }

    public static void printCenteredTitle(String title, int width) {
        String border = "=".repeat(width);
        int paddingSize = (width - 2 - title.length()) / 2;
        String padding = " ".repeat(Math.max(0, paddingSize));
        String line = "|" + padding + title + padding;

        // Ajusta se o comprimento for ímpar
        if (line.length() < width - 1) {
            line += " ";
        }

        line += "|";

        System.out.println(border);
        System.out.println(line);
        System.out.println(border);
    }

    public static void printArtistList(List<Artist> artists) {
        printCenteredTitle("Artists Available", 30);
        System.out.println();

        for (Artist artist : artists) {
            System.out.printf("ID: %-3d | Name: %-15s | Type: %s%n",
                    artist.getId(),
                    artist.getName(),
                    artist.getArtistType());
        }

        System.out.println(); // linha em branco antes da entrada
    }

    public static void printSongList(List<Song> songs) {
        printCenteredTitle("Songs Available", 30);
        System.out.println();

        for (Song song : songs) {
            System.out.printf("ID: %-3d | Song: %-20s | Artist: %s%n",
                    song.getId(),
                    song.getTitle(),
                    song.getArtist().getName());
        }
    }
}
