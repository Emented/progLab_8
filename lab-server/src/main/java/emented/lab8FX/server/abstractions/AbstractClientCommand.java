package emented.lab8FX.server.abstractions;

import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CommandResponse;

public abstract class AbstractClientCommand {

    private final String name;
    private final int amountOfArgs;
    private final String description;
    private final String descriptionOfArgs;

    public AbstractClientCommand(String name, int amountOfArgs, String description, String descriptionOfArgs) {
        this.name = name;
        this.amountOfArgs = amountOfArgs;
        this.description = description;
        this.descriptionOfArgs = descriptionOfArgs;
    }

    public AbstractClientCommand(String name, int amountOfArgs, String description) {
        this.name = name;
        this.amountOfArgs = amountOfArgs;
        this.description = description;
        this.descriptionOfArgs = "";
    }

    public abstract CommandResponse executeClientCommand(CommandRequest request);

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name of command: " + name + ", " + "args: "
                + ((amountOfArgs == 0) ? "this command doesn't need args" : descriptionOfArgs)
                + ", description: " + description;
    }
}
