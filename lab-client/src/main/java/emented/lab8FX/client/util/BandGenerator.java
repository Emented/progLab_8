package emented.lab8FX.client.util;

import emented.lab8FX.client.util.validators.BandValidator;
import emented.lab8FX.common.entities.Coordinates;
import emented.lab8FX.common.entities.MusicBand;
import emented.lab8FX.common.entities.Studio;
import emented.lab8FX.common.entities.enums.MusicGenre;

public class BandGenerator {

    private final MusicBand musicBand;

    public BandGenerator(String name, String x, String y, String number, MusicGenre genre, String description, String address) {
        this.musicBand = new MusicBand();
        musicBand.setName(getName(name));
        musicBand.setCoordinates(new Coordinates(getX(x), getY(y)));
        musicBand.setNumberOfParticipants(getNumber(number));
        musicBand.setGenre(genre);
        musicBand.setDescription(getDescription(description));
        musicBand.setStudio(getAddress(address));
    }

    private String getName(String name) {
        return BandValidator.validateName(name);
    }

    private double getX(String x) {
        return BandValidator.validateX(x);
    }

    private Float getY(String y) {
        return BandValidator.validateY(y);
    }

    private Long getNumber(String number) {
        return BandValidator.validateNumber(number);
    }

    private String getDescription(String description) {
        if ("".equals(description)) {
            return null;
        }
        return description;
    }

    private Studio getAddress(String address) {
        if ("".equals(address) || address == null) {
            return null;
        }
        return new Studio(address);
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }
}
