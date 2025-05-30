package developer.ezandro.screensoundmusic.ui;

import developer.ezandro.screensoundmusic.entities.Artist;
import developer.ezandro.screensoundmusic.entities.ArtistType;
import developer.ezandro.screensoundmusic.entities.Song;
import developer.ezandro.screensoundmusic.exception.ArtistNotFoundException;
import developer.ezandro.screensoundmusic.services.ArtistService;
import developer.ezandro.screensoundmusic.services.SongService;
import developer.ezandro.screensoundmusic.services.integration.ArtistInfoFetchException;
import developer.ezandro.screensoundmusic.services.integration.ChatGptClient;
import developer.ezandro.screensoundmusic.utils.ConsoleFormatter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleMenu {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String FIELD_MUST_NOT_BE_BLANK = "%nERROR: Field '%s' must not be blank.%n";
    private static final String ENTER_ARTIST_NAME = "Enter artist name: ";
    private static final String ARTIST_NAME = "ARTIST NAME";
    private final ArtistService artistService;
    private final SongService songService;

    public ConsoleMenu(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    private void showMenu() {
        System.out.print("""
                
                =============================================
                
                Screen Sound Music
                
                1 - Add artist
                2 - Add music
                3 - List songs
                4 - Search songs by artist
                5 - Search artist information
                
                9 - Exit
                
                =============================================
                
                Choose an option:\s"""
        );
    }

    private void exitProgram() {
        System.out.println("""
                
                === Program terminated ===
                Thank you for using Screen Sound Music.
                Have a great day!
                """);
    }

    public void inputOption() {
        this.showMenu();

        boolean running = true;

        while (running) {
            String input = SCANNER.nextLine().trim();

            if (input.isEmpty()) {
                System.out.printf("%nERROR: Field 'OPTION' must not be blank.%nChoose an option: ");
            } else {
                try {
                    int option = Integer.parseInt(input);

                    if (option == 9) {
                        this.exitProgram();
                        running = false;
                    } else if (option >= 1 && option <= 5) {
                        this.handleOption(option);
                    } else {
                        System.out.printf("%nERROR: Field 'OPTION' must be between 1 and 5.%nChoose an option: ");
                    }
                } catch (NumberFormatException _) {
                    System.out.printf("%nERROR: Field 'OPTION' must be a number (1-5 or 9).%nChoose an option: ");
                }
            }
        }
    }

    private void handleOption(int option) {
        switch (option) {
            case 1 ->
                    this.handleAddUser();
            case 2 ->
                    this.handleAddMusic();
            case 3 ->
                    this.handleListSongs();
            case 4 ->
                    this.handleSearchSongsByArtist();
            case 5 ->
                    this.handleSearchArtistInfo();
            default ->
                    throw new IllegalStateException("Unexpected value: " + option);
        }
    }

    private void handleAddUser() {
        while (true) {
            try {
                String name = this.promptRequiredArtistName(ENTER_ARTIST_NAME, ARTIST_NAME);
                Optional<Artist> optName = this.artistService.findByName(name.toLowerCase());

                if (optName.isPresent()) {
                    System.out.printf("The artist '%s' already exists in the database and cannot be added again.%n", optName.get().getName().toUpperCase());
                    this.showMenu();
                    return;
                }

                ArtistType artistType = this.promptForArtistType();

                Artist artist = this.artistService.createArtist(name, artistType);
                System.out.printf("Artist '%s' was created successfully!%n", artist.getName().toUpperCase());

                if (!askToContinue()) {
                    this.showMenu();
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleAddMusic() {
        List<Artist> artists = this.artistService.getAllArtists();

        if (artists.isEmpty()) {
            System.out.println("No artists found. Please add an artist first.");
            this.showMenu();
            return;
        }

        ConsoleFormatter.printArtistList(artists);

        boolean isValidId = false;
        while (!isValidId) {
            try {
                Integer artistId = this.promptRequiredArtistId();

                Artist artist = this.artistService.findArtistById(artistId);
                String title = this.promptRequiredSongTitle();

                Song song = this.songService.createSong(title, artist);
                System.out.printf("Song '%s' was created successfully!%n", song.getTitle());
                isValidId = true;
                this.showMenu();
            } catch (ArtistNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String promptRequiredArtistName(String prompt, String fieldName) {
        String input = "";
        do {
            try {
                System.out.print(prompt);
                input = SCANNER.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.printf(FIELD_MUST_NOT_BE_BLANK, fieldName);
                }
            } catch (NumberFormatException _) {
                SCANNER.nextLine();
            }
        } while (input.isEmpty());
        return input;
    }

    private ArtistType promptForArtistType() {
        while (true) {
            String input = promptRequiredArtistName("Enter artist type (solo, duo or band): ", "ARTIST TYPE");
            try {
                return ArtistType.fromString(input);
            } catch (IllegalArgumentException _) {
                System.out.println("Invalid artist type. Please enter one of the following: solo, duo, or band.");
            }
        }
    }

    private boolean askToContinue() {
        String input;
        do {
            System.out.print("Would you like to add a new artist? (Y/N): ");
            input = SCANNER.nextLine().trim();

            if (input.equalsIgnoreCase("n")) {
                return false;
            }

            if (input.equalsIgnoreCase("y")) {
                return true;
            }
            System.out.printf("Invalid choice!%nPlease try again (Y/N).");
        } while (true);
    }

    private Integer promptRequiredArtistId() {
        String input = "";
        Integer artistId = 0;
        boolean isValidId = false;

        while (!isValidId) {
            System.out.print("Enter artist ID: ");
            input = SCANNER.nextLine().trim();
            if (input.isEmpty()) {
                System.out.printf(FIELD_MUST_NOT_BE_BLANK, "ARTIST ID");
                continue;
            }

            try {
                artistId = Integer.parseInt(input);
                isValidId = true;
            } catch (NumberFormatException _) {
                System.out.printf("%nERROR: Field '%s' must be a valid number.%n", "ARTIST ID");
            }
        }
        return artistId;
    }

    private String promptRequiredSongTitle() {
        String songTitle;
        do {
            System.out.print("Enter song title: ");
            songTitle = SCANNER.nextLine().trim();
            if (songTitle.isEmpty()) {
                System.out.printf("%nERROR: Song title must not be blank.%n");
            }
        } while (songTitle.isEmpty());
        return songTitle;
    }


    private void handleListSongs() {
        List<Song> songs = this.songService.getAllSongs();

        if (songs.isEmpty()) {
            System.out.println("No songs available in the library.");
        } else {
            ConsoleFormatter.printSongList("Available Songs", songs);
        }
        this.showMenu();
    }

    private void handleSearchSongsByArtist() {
        String artistName = this.promptRequiredArtistName(ENTER_ARTIST_NAME, ARTIST_NAME);
        List<Song> songs = this.songService.searchSongs(artistName);

        if (songs.isEmpty()) {
            System.out.printf("No songs found for artist '%s'.%n", artistName);
        } else {
            String artist = songs.getFirst().getArtist().getName();
            ConsoleFormatter.printSongList("Songs by " + artist, songs);
        }
        this.showMenu();
    }

    private void handleSearchArtistInfo() {
        String artistName = this.promptRequiredArtistName(ENTER_ARTIST_NAME, ARTIST_NAME);

        Optional<Artist> artist = artistService.findByName(artistName);

        if (artist.isPresent()) {
            try {
                System.out.println("Searching for artist information...\n");
                String bio = ChatGptClient.fetchArtistInformation(artistName);
                ConsoleFormatter.printArtistInfo("Artist Information", artist.get().getName(), bio);
            } catch (ArtistInfoFetchException e) {
                System.err.println("Error fetching artist info: " + e.getMessage());
            }
        } else {
            System.out.printf("No artist found with the name '%s'.%n", artistName);
        }

        this.showMenu();
    }
}