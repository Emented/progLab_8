package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.MainController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.entities.MusicBand;
import emented.lab8FX.common.util.requests.CollectionRequest;
import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CollectionResponse;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainModel extends AbstractModel {

    private final static int UPDATE_TIME = 15000;
    private final Session session;

    private final MainController currentController;

    public MainModel(ClientSocketWorker clientSocketWorker, Stage currentStage, Session session, MainController mainController) {
        super(clientSocketWorker, currentStage);
        this.currentController = mainController;
        this.session = session;
    }

    public void runUpdateLoop() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    getNewCollection();
                } catch (ExceptionWithAlert e) {
                    e.showAlert();
                }
            }
        }, 0, UPDATE_TIME);
    }

    public Session getSession() {
        return session;
    }

    public void setToolTip(TableColumn<MusicBand, String> tableColumn) {
        tableColumn.setCellFactory
                (column -> new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                        if (item != null && item.length() >= 10) {
                            setTooltip(new Tooltip(item));
                        }
                    }
                });
    }

    public void getNewCollection() throws ExceptionWithAlert {
        try {
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new CollectionRequest(session.getUsername(), session.getPassword(), getClientInfo()));
            if (response.getType().equals(CollectionResponse.class) && response.isSuccess()) {
                currentController.updateTable(((CollectionResponse) response).getCollection());
            }
        } catch (IOException e) {
            throw new ExceptionWithAlert("Some troubles with connection!");
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert("Response came damaged!");
        }
    }

    public String processInfoAction() throws ExceptionWithAlert {
        try {
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new CommandRequest("info",
                    session.getUsername(),
                    session.getPassword(),
                    getClientInfo()));
            return response.toString();
        } catch (IOException e) {
            throw new ExceptionWithAlert("Some troubles with connection!");
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert("Response came damaged!");
        }
    }

    public String processHistoryAction() throws ExceptionWithAlert {
        try {
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new CommandRequest("history",
                    session.getUsername(),
                    session.getPassword(),
                    getClientInfo()));
            return response.toString();
        } catch (IOException e) {
            throw new ExceptionWithAlert("Some troubles with connection!");
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert("Response came damaged!");
        }
    }

    public String processClearAction() throws ExceptionWithAlert {
        try {
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new CommandRequest("clear",
                    session.getUsername(),
                    session.getPassword(),
                    getClientInfo()));
            return response.toString();
        } catch (IOException e) {
            throw new ExceptionWithAlert("Some troubles with connection!");
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert("Response came damaged!");
        }
    }

    public void prepareForExit() {
        getClientSocketWorker().closeSocket();
    }

}
