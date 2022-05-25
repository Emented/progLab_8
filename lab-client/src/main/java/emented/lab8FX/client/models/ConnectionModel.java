package emented.lab8FX.client.models;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.common.util.requests.ConnectionRequest;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.UnknownHostException;

public class ConnectionModel extends AbstractModel {
    public ConnectionModel(ClientSocketWorker clientSocketWorker, Stage currentStage) {
        super(clientSocketWorker, currentStage);
    }

    public void connect(String host, String port) throws ExceptionWithAlert {
        try {
            if (!"".equals(port)) {
                int portNum = Integer.parseInt(port);
                if (portNum > 0 && portNum <= 65535) {
                    getClientSocketWorker().setPort(portNum);
                } else {
                    throw new IllegalArgumentException();
                }
            }
            if (!"".equals(host)) {
                getClientSocketWorker().setAddress(host);
            }
            getClientSocketWorker().sendRequest(new ConnectionRequest("localhost"));
            if (getClientSocketWorker().receiveResponse().isSuccess()) {
                AuthModel authModel = new AuthModel(getClientSocketWorker(), getCurrentStage());
                switchScene(PathToViews.REGISTRATION_VIEW, authModel);
            }
        } catch (IllegalArgumentException e) {
            throw new ExceptionWithAlert("Wrong port! It should be a number between 1 and 65535");
        } catch (UnknownHostException e) {
            throw new ExceptionWithAlert("Wrong address!");
        }
        catch (IOException | ClassNotFoundException e) {
            throw new ExceptionWithAlert("Some troubles with connection, try again later!");
        }
    }
}
