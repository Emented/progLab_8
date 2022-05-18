package emented.lab8FX.common.entities.enums;

/**
 * ENUM, хранящий возможные жанры музыки
 */
public enum MusicGenre {
    PROGRESSIVE_ROCK,
    PSYCHEDELIC_CLOUD_RAP,
    JAZZ,
    BLUES,
    BRIT_POP;

    /**
     * Метод, возвращающий строковое предстваление класса
     *
     * @return Строковое представление класса
     */
    public static String show() {
        StringBuilder sb = new StringBuilder();
        for (MusicGenre j : values()) {
            sb.append(j);
            sb.append("\n");
        }
        return sb.toString();
    }
}
