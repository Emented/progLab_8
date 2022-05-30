package emented.lab8FX.common.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс, хранящий информацию о студии
 */
public class Studio implements Serializable, Comparable<Studio> {

    /**
     * Поле, хранящее адрес студии (не может быть null)
     */
    private String address; //Поле не может быть null

    public Studio(String adress) {
        this.address = adress;
    }

    /**
     * Метод, возвращающий адрес
     *
     * @return Адрес
     */
    public String getAddress() {
        return address;
    }

    /**
     * Метод, устанавливающий адрес
     *
     * @param address Новый адрес
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Метод, сравнивающий две студии
     *
     * @param anotherStudio Студия для сравнения
     * @return Целое число
     */
    @Override
    public int compareTo(Studio anotherStudio) {
        return this.address.compareTo(anotherStudio.getAddress());
    }

    /**
     * Переопределение метода, возвращающего строковое представление класса
     *
     * @return Строковое представление класса
     */
    @Override
    public String toString() {
        return "адрес студии: " + address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Studio studio)) return false;
        return address.equals(studio.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
