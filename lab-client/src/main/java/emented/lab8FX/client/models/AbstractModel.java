package emented.lab8FX.client.models;

import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.LanguagesEnum;
import emented.lab8FX.common.abstractions.AbstractResponse;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public abstract class AbstractModel {

    private final String clientInfo;
    private final ClientSocketWorker clientSocketWorker;
    private final Stage currentStage;

    public AbstractModel(ClientSocketWorker clientSocketWorker, Stage currentStage) {
        this.clientSocketWorker = clientSocketWorker;
        this.currentStage = currentStage;
        this.clientInfo = clientSocketWorker.getAddress() + ":" + clientSocketWorker.getPort();
    }
    public LanguagesEnum getLanguage(String s) {
        if ("".equals(s)) {
            return LanguagesEnum.ENGLISH;
        } else if ("sk".equals(s)) {
            return LanguagesEnum.SLOVAK;
        } else if ("lt".equals(s)) {
            return LanguagesEnum.LITHUANIAN;
        } else {
            return LanguagesEnum.SPANISH;
        }
    }

    public ClientSocketWorker getClientSocketWorker() {
        return this.clientSocketWorker;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public String getClientInfo() {
        return clientInfo;
    }
}
