package developer.ezandro.screensoundmusic;

import developer.ezandro.screensoundmusic.ui.ConsoleMenu;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreensoundmusicApplication implements CommandLineRunner {
    private final ConsoleMenu consoleMenu;

    public ScreensoundmusicApplication(ConsoleMenu consoleMenu) {
        this.consoleMenu = consoleMenu;
    }

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(e ->
                System.setProperty(e.getKey(), e.getValue())
        );

        SpringApplication.run(ScreensoundmusicApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.consoleMenu.inputOption();
    }
}