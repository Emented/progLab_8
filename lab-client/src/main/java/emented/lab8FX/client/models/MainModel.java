package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.InfoController;
import emented.lab8FX.client.controllers.MainController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.LanguagesEnum;
import emented.lab8FX.client.util.PathToViews;
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

    private final Set<MusicBand> bandSet = new HashSet<>();

    private final List<Long> usersIDs = new ArrayList<>();

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

    public Set<MusicBand> getBandSet() {
        return bandSet;
    }

    public List<Long> getUsersIDs() {
        return usersIDs;
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
                Set<MusicBand> newCollection = ((CollectionResponse) response).getCollection();
                List<Long> newIDs = ((CollectionResponse) response).getUsersIds();
                currentController.getCurrentDataController().updateElements(getElementsToAdd(newCollection),
                        getElementsToRemove(newCollection),
                        getElementsToUpdate(newCollection),
                        newIDs);
                bandSet.clear();
                bandSet.addAll(newCollection);
                usersIDs.clear();
                usersIDs.addAll(newIDs);
            }
        } catch (IOException e) {
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.connection"));
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.response"));
        }
    }

    public void initializeCollection() throws ExceptionWithAlert {
        try {
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new CollectionRequest(session.getUsername(), session.getPassword(), getClientInfo()));
            if (response.getType().equals(CollectionResponse.class) && response.isSuccess()) {
                Set<MusicBand> newCollection = ((CollectionResponse) response).getCollection();
                List<Long> newIDs = ((CollectionResponse) response).getUsersIds();
                bandSet.clear();
                bandSet.addAll(newCollection);
                usersIDs.clear();
                usersIDs.addAll(newIDs);
            }
        } catch (IOException e) {
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.connection"));
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.response"));
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
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.connection"));
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.response"));
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
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.connection"));
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.response"));
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
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.connection"));
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.response"));
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
        canvas.setOnMouseEntered(event -> {
            canvas.setScaleX(1.07);
            canvas.setScaleY(1.07);
        });
        canvas.setOnMouseExited(event -> {
            canvas.setScaleX(1);
            canvas.setScaleY(1);
        });
        canvas.setOnMouseClicked(event -> showInfoElement(musicBand));
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
            gc.fillOval(5 + i * offset, 6, 12, 12);
            gc.strokeLine(11 + i * offset, 12, 11 + i * offset, 36);
            gc.strokeLine(11 + i * offset, 17, 20 + i * offset, 29);
            gc.strokeLine(11 + i * offset, 17, 2 + i * offset, 29);
            gc.strokeLine(11 + i * offset, 36, 20 + i * offset, 48);
            gc.strokeLine(11 + i * offset, 36, 2 + i * offset, 48);
        }
        return canvas;
    }

    public List<MusicBand> getElementsToUpdate(Set<MusicBand> newCollection) {
        List<Long> newIDs = newCollection.stream().map(MusicBand::getId).toList();
        List<MusicBand> elementsToUpdate = new ArrayList<>();
        for (Long id : newIDs) {
            MusicBand m = newCollection.stream().filter(musicBand -> musicBand.getId().equals(id)).toList().get(0);
            MusicBand n = bandSet.stream().filter(musicBand -> musicBand.getId().equals(id)).toList().get(0);
            if (!m.equals(n)) {
                elementsToUpdate.add(m);
            }
        }
        return elementsToUpdate;
    }

    public List<MusicBand> getElementsToAdd(Set<MusicBand> newCollection) {
        List<Long> currentIDs = bandSet.stream().map(MusicBand::getId).toList();
        List<Long> newIDs = newCollection.stream().map(MusicBand::getId).toList();
        List<MusicBand> elementsToAdd = new ArrayList<>();
        for (Long id : newIDs) {
            if (!currentIDs.contains(id)) {
                MusicBand m = newCollection.stream().filter(musicBand -> musicBand.getId().equals(id)).toList().get(0);
                elementsToAdd.add(m);
            }
        }
        return elementsToAdd;
    }

    public List<MusicBand> getElementsToRemove(Set<MusicBand> newCollection) {
        List<Long> currentIDs = bandSet.stream().map(MusicBand::getId).toList();
        List<Long> newIDs = newCollection.stream().map(MusicBand::getId).toList();
        List<MusicBand> elementsToRemove = new ArrayList<>();
        for (Long id : currentIDs) {
            if (!newIDs.contains(id)) {
                MusicBand m = bandSet.stream().filter(musicBand -> musicBand.getId().equals(id)).toList().get(0);
                elementsToRemove.add(m);
            }
        }
        return elementsToRemove;
    }

    public void showInfoElement(MusicBand musicBand) {
        try {
            currentController.showPopUpStage(PathToViews.INFO_VIEW,
                    param -> new InfoController(musicBand, this),
                    currentController.getResourceBundle().getString("visual_info.title"),
                    currentController.getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }

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
}
