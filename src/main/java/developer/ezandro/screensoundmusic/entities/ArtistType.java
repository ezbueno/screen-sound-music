package developer.ezandro.screensoundmusic.entities;

public enum ArtistType {
    SOLO("Solo"),
    DUO("Duo"),
    BAND("Band");

    private final String type;

    ArtistType(String type) {
        this.type = type;
    }

    public static ArtistType fromString(String type) {
        for (ArtistType artistType : ArtistType.values()) {
            if (artistType.type.equalsIgnoreCase(type)) {
                return artistType;
            }
        }
        throw new IllegalArgumentException("ERROR: No artist type found for the given type: " + type);
    }

    public String getType() {
        return this.type;
    }
}