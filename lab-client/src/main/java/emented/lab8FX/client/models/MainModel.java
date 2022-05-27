package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.MainController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.entities.Coordinates;
import emented.lab8FX.common.entities.MusicBand;
import emented.lab8FX.common.entities.Studio;
import emented.lab8FX.common.entities.enums.MusicGenre;
import emented.lab8FX.common.util.requests.CollectionRequest;
import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CollectionResponse;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
public class MainModel extends AbstractModel {

    private final static int UPDATE_TIME = 5000;
    private final Session session;

    private final MainController currentController;

    public MainModel(ClientSocketWorker clientSocketWorker, Stage currentStage, Session session, MainController mainController) {
        super(clientSocketWorker, currentStage);
        this.currentController = mainController;
        this.session = session;
        Timer timer = new Timer(true);
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
            getClientSocketWorker().sendRequest(new CollectionRequest(session.getUsername(), session.getPassword(), getClientInfo()));
            AbstractResponse response = getClientSocketWorker().receiveResponse();
            if (response.getType().equals(CollectionResponse.class) && response.isSuccess()) {
                Set<MusicBand> b = new HashSet<>();
                Collections.addAll(b, new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "dfsdfgdsfgsdfgdsfgdsfgsdfgdsfgdsfhdfghdfsggadsfasdf", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 1L, "test", new Coordinates(12, 12F), 34L, null, null, new Studio("test")),
                        new MusicBand(LocalDate.now(), 3L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.PROGRESSIVE_ROCK, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 225L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
                        new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")));
                currentController.updateTable(b);
            }
        } catch (IOException e) {
            throw new ExceptionWithAlert("Some troubles with connection!");
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert("Response came damaged!");
        }
    }

    public String processInfoAction() throws ExceptionWithAlert {
        try {
            getClientSocketWorker().sendRequest(new CommandRequest("info", session.getUsername(), session.getPassword(), getClientInfo()));
            AbstractResponse response = getClientSocketWorker().receiveResponse();
            return response.toString();
        } catch (IOException e) {
            throw new ExceptionWithAlert("Some troubles with connection!");
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert("Response came damaged!");
        }
    }

    public String processHistoryAction() throws ExceptionWithAlert {
        try {
            getClientSocketWorker().sendRequest(new CommandRequest("history",
                    session.getUsername(),
                    session.getPassword(),
                    getClientInfo()));
            AbstractResponse response = getClientSocketWorker().receiveResponse();
            return response.toString();
        } catch (IOException e) {
            throw new ExceptionWithAlert("Some troubles with connection!");
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert("Response came damaged!");
        }
    }

    public String processClearAction() throws ExceptionWithAlert {
        try {
            getClientSocketWorker().sendRequest(new CommandRequest("clear",
                    session.getUsername(),
                    session.getPassword(),
                    getClientInfo()));
            AbstractResponse response = getClientSocketWorker().receiveResponse();
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
