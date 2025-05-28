package developer.ezandro.screensoundmusic.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public final class EnvConfig {
    private static Dotenv dotenv;

    private static String openaiApiKey;

    @PostConstruct
    public void loadEnv() {
        dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        openaiApiKey = getRequiredEnvVar("OPENAI_API_KEY");
    }

    private static String getRequiredEnvVar(String key) {
        String value = dotenv.get(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Mandatory variable not found in .env: " + key);
        }
        return value;
    }

    public static String getOpenAiApiKey() {
        if (openaiApiKey == null) {
            throw new IllegalStateException("OPENAI_API_KEY not initialized.");
        }
        return openaiApiKey;
    }
}