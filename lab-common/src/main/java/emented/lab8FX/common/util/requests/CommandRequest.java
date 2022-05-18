package emented.lab8FX.common.util.requests;

import emented.lab8FX.common.abstractions.AbstractRequest;
import emented.lab8FX.common.entities.MusicBand;

public class CommandRequest extends AbstractRequest {

    private final String commandName;

    private final Long numericArgument;

    private final MusicBand bandArgument;

    private final String username;

    private final String password;

    public CommandRequest(String commandName, String username, String password, String clientInfo) {
        super(clientInfo);
        this.commandName = commandName;
        this.username = username;
        this.password = password;
        this.numericArgument = null;
        this.bandArgument = null;
    }

    public CommandRequest(String commandName, Long numericArgument, String username, String password, String clientInfo) {
        super(clientInfo);
        this.commandName = commandName;
        this.username = username;
        this.password = password;
        this.numericArgument = numericArgument;
        this.bandArgument = null;
    }

    public CommandRequest(String commandName, MusicBand bandArgument, String username, String password, String clientInfo) {
        super(clientInfo);
        this.commandName = commandName;
        this.username = username;
        this.password = password;
        this.numericArgument = null;
        this.bandArgument = bandArgument;
    }

    public CommandRequest(String commandName, Long numericArgument, MusicBand bandArgument, String username, String password, String clientInfo) {
        super(clientInfo);
        this.commandName = commandName;
        this.username = username;
        this.password = password;
        this.numericArgument = numericArgument;
        this.bandArgument = bandArgument;
    }

    public String getCommandName() {
        return commandName;
    }

    public Long getNumericArgument() {
        return numericArgument;
    }

    public MusicBand getBandArgument() {
        return bandArgument;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Name of command to send: " + commandName
                + (bandArgument == null ? "" : "\nInfo about band to send: " + bandArgument)
                + (numericArgument == null ? "" : "\nNumeric argument to send: " + numericArgument);
    }
}
