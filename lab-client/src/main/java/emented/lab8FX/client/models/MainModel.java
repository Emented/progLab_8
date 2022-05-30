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
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class MainModel extends AbstractModel {

    private final static int UPDATE_TIME = 5000;
    private final Session session;
    private final MainController currentController;
    private final HashMap<MusicBand, Canvas> visualBands = new HashMap<>();

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
                    Platform.runLater(e::showAlert);
                }
            }
        }, 0, UPDATE_TIME);
    }

    public Session getSession() {
        return session;
    }

    public HashMap<MusicBand, Canvas> getVisualBands() {
        return visualBands;
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
                currentController.updateTable(((CollectionResponse) response).getCollection(), ((CollectionResponse) response).getUsersIds());
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

    public Canvas generateBandCanvas(Color color, MusicBand musicBand) {
        Canvas canvas;
        if (musicBand.getNumberOfParticipants() == 1) {
            canvas = drawPerson(color, 1L);
        } else if (musicBand.getNumberOfParticipants() == 2) {
            canvas = drawPerson(color, 2L);
        } else {
            canvas = drawPerson(color, 3L);
        }
        canvas.setLayoutX(15.0 + (750.0 / 947.0) * musicBand.getCoordinates().getX());
        canvas.setLayoutY(593.0 - (593.0 / 104.0) * musicBand.getCoordinates().getY());
        visualBands.put(musicBand, canvas);
        return canvas;
    }

    private Canvas drawPerson(Color color, Long amount) {
        final int offset = 20;
        Canvas canvas = new Canvas(22 + (amount - 1) * offset, 50);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.setStroke(color);
        gc.setLineWidth(3);
        for (int i = 0; i < amount; i++) {
            gc.fillOval(5 + i * offset,6, 12, 12);
            gc.strokeLine(11 + i * offset, 12, 11 + i * offset, 36);
            gc.strokeLine(11 + i * offset, 17, 20 + i * offset, 29);
            gc.strokeLine(11 + i * offset, 17, 2 + i * offset, 29);
            gc.strokeLine(11 + i * offset, 36, 20 + i * offset, 48);
            gc.strokeLine(11 + i * offset, 36, 2 + i * offset, 48);
        }
        return canvas;
    }

    public void updateElements(Set<MusicBand> collection, List<Long> ids) {
        List<Long> currentIDs;
        currentIDs = currentController.getMusicBandsList().stream().map(MusicBand::getId).toList();
        for (Long id : currentIDs) {
            MusicBand m = collection.stream().filter(musicBand -> musicBand.getId().equals(id)).toList().get(0);
            MusicBand n = currentController.getMusicBandsList().stream().filter(musicBand -> musicBand.getId().equals(id)).toList().get(0);
            if (!m.equals(n)) {
                currentController.getMusicBandsList().remove(n);
                currentController.getMusicBandsList().add(m);
                Platform.runLater(() -> {
                    currentController.removeFromVisual(n);
                    currentController.addToVisual(m, !ids.contains(id));
                });
            }
        }
    }

    public void removeElements(List<Long> currentIDs, List<Long> newIDs) {
        for (Long id : currentIDs) {
            if (!newIDs.contains(id)) {
                MusicBand m = currentController.getMusicBandsList().stream().filter(musicBand -> musicBand.getId().equals(id)).toList().get(0);
                currentController.getMusicBandsList().remove(m);
                Platform.runLater(() -> currentController.removeFromVisual(m));
            }
        }
    }

    public void addNewElements(Set<MusicBand> collection, List<Long> ids, List<Long> currentIDs, List<Long> newIDs) {
        for (Long id : newIDs) {
            if (!currentIDs.contains(id)) {
                MusicBand m = collection.stream().filter(musicBand -> musicBand.getId().equals(id)).toList().get(0);
                currentController.getMusicBandsList().add(m);
                if (ids.contains(id)) {
                    Platform.runLater(() -> currentController.addToVisual(m, false));
                } else {
                    Platform.runLater(() -> currentController.addToVisual(m, true));
                }
            }
        }
    }

}
