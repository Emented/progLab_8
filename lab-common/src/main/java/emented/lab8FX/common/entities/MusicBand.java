package emented.lab8FX.common.entities;

import emented.lab8FX.common.entities.enums.MusicGenre;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class MusicBand implements Serializable, Comparable<MusicBand> {

    private final LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private long numberOfParticipants; //Значение поля должно быть больше 0
    private String description; //Поле может быть null
    private MusicGenre genre; //Поле может быть null
    private Studio studio; //Поле может быть null

    /**
     * Конструктор, автоматически выставляющий ID и дату инициализации
     */
    public MusicBand() {
        creationDate = LocalDate.now();
    }

    public MusicBand(Long id) {
        this.id = id;
        creationDate = LocalDate.now();
    }

    public MusicBand(LocalDate creationDate, Long id, String name, Coordinates coordinates, long numberOfParticipants, String description, MusicGenre genre, Studio studio) {
        this.creationDate = creationDate;
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.description = description;
        this.genre = genre;
        this.studio = studio;
    }

    /**
     * Метод, возвращающий ID
     *
     * @return ID объекта
     */
    public Long getId() {
        return id;
    }

    /**
     * Метод, устанавливающий ID по данному
     *
     * @param id Новый ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getName() {
        return name;
    }

    /**
     * Метод, устанавливающий имя объекта
     *
     * @param name Новое имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод, возвращающий соординаты объекта
     *
     * @return Координаты объекта
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Метод, устанавливающий координаты объекта
     *
     * @param coordinates Новые координаты
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Метод, возвращающий число участников
     *
     * @return Число участников
     */
    public long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    /**
     * Метод, устанавливающий число участников
     *
     * @param numberOfParticipants Новое число участников
     */
    public void setNumberOfParticipants(Long numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Метод, устанавливающий описание
     *
     * @param description Новое описание
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    /**
     * Метод, устанавливающий жанр
     *
     * @param genre Новый жанр
     */
    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    /**
     * Метод, возвращающий студию
     *
     * @return Студия
     */
    public Studio getStudio() {
        return studio;
    }

    /**
     * Метод, устанавливающий студию
     *
     * @param studio Новая студия
     */
    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    /**
     * Метод сравнения
     *
     * @param anotherBand Группа для сравнения
     * @return Целочисленное значение
     */
    @Override
    public int compareTo(MusicBand anotherBand) {
        if (this.getStudio() == null) {
            return -1;
        } else if (anotherBand.getStudio() == null) {
            return 1;
        }
        return this.getStudio().compareTo(anotherBand.getStudio());
    }

    /**
     * Переопределение метода, возвращающего строковое представление класса
     *
     * @return Строковое представление класса
     */
    @Override
    public String toString() {
        return "ID: " + id + "\n"
                + "Name: " + name + "\n"
                + "Coordinates: " + coordinates + "\n"
                + "Creation date: " + creationDate + "\n"
                + "Number of participants: " + numberOfParticipants + "\n"
                + "Description: " + ((description == null) ? "missing" : description) + "\n"
                + "Genre: " + ((genre == null) ? "not defined" : genre) + "\n"
                + ((studio == null) ? "The studio is missing" : studio);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MusicBand musicBand)) return false;
        return numberOfParticipants == musicBand.numberOfParticipants && creationDate.equals(musicBand.creationDate) && id.equals(musicBand.id) && name.equals(musicBand.name) && coordinates.equals(musicBand.coordinates) && Objects.equals(description, musicBand.description) && genre == musicBand.genre && Objects.equals(studio, musicBand.studio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationDate, id, name, coordinates, numberOfParticipants, description, genre, studio);
    }
}
