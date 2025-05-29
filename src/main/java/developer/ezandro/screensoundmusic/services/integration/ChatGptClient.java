package developer.ezandro.screensoundmusic.services.integration;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import developer.ezandro.screensoundmusic.config.EnvConfig;

import java.util.List;
import java.util.Objects;

public final class ChatGptClient {
    private static final int MIN_NAME_LENGTH = 3;
    private static final int DELAY_MS = 1000;
    private static final int MAX_TOKENS = 500;
    private static final double TEMPERATURE = 0.5;

    private ChatGptClient() {
    }

    public static String fetchArtistInformation(String artistName) throws ArtistInfoFetchException {
        Objects.requireNonNull(artistName, "Artist name cannot be null");
        validateArtistName(artistName);

        try {
            OpenAiService service = new OpenAiService(EnvConfig.getOpenAiApiKey());
            ChatCompletionRequest request = buildChatRequest(artistName);

            Thread.sleep(DELAY_MS);
            return getCompletionContent(service, request);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ArtistInfoFetchException("Operation interrupted while fetching artist info", e);
        }
    }

    private static void validateArtistName(String artistName) {
        if (artistName.isBlank()) {
            throw new IllegalArgumentException("Artist name cannot be blank");
        }
        if (artistName.length() < MIN_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Artist name must be at least %d characters long", MIN_NAME_LENGTH)
            );
        }
    }

    private static ChatCompletionRequest buildChatRequest(String artistName) {
        return ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(createChatMessage(artistName)))
                .maxTokens(MAX_TOKENS)
                .temperature(TEMPERATURE)
                .build();
    }

    private static ChatMessage createChatMessage(String artistName) {
        return new ChatMessage(
                ChatMessageRole.USER.value(),
                String.format(
                        "Provide a concise 3-paragraph biography about %s. " +
                                "Focus on musical career, major achievements, and artistic style. " +
                                "Use formal tone, complete paragraphs, and avoid bullet points.",
                        artistName
                )
        );
    }

    private static String getCompletionContent(OpenAiService service, ChatCompletionRequest request)
            throws ArtistInfoFetchException {
        try {
            return service.createChatCompletion(request)
                    .getChoices()
                    .getFirst()
                    .getMessage()
                    .getContent()
                    .trim();
        } catch (Exception e) {
            throw new ArtistInfoFetchException("Failed to fetch artist information from ChatGPT API", e);
        }
    }
}