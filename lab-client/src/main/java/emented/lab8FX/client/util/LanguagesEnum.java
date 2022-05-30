package emented.lab8FX.client.util;

public enum LanguagesEnum {
    ENGLISH("English"),
    SLOVAK("Slovenský"),
    LITHUANIAN("Lietuviškas"),
    SPANISH("Español");

    private final String name;

    LanguagesEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
