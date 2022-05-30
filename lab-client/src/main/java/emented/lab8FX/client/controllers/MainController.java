package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.MainModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.common.entities.MusicBand;
import emented.lab8FX.common.entities.enums.MusicGenre;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainController extends AbstractController {

    private final MainModel mainModel;
    private final ObservableList<MusicBand> musicBandsList = FXCollections.observableArrayList();
    @FXML
    public TextField dateFilter;
    @FXML
    public TextField idFilter;
    @FXML
    public TextField nameFilter;
    @FXML
    public TextField xFilter;
    @FXML
    public TextField yFilter;
    @FXML
    public TextField numberFilter;
    @FXML
    public TextField descriptionFilter;
    @FXML
    public Pane tablePain;
    @FXML
    public Pane visualPane;
    @FXML
    public Button switchButton;
    @FXML
    public Pane bandsPane;
    @FXML
    public TextField addressFilter;
    @FXML
    public Button clearFilterButton;
    @FXML
    private ComboBox<MusicGenre> genreFilter;
    @FXML
    private TableView<MusicBand> tableView;
    @FXML
    private TableColumn<MusicBand, LocalDate> creationDateColumn;
    @FXML
    private TableColumn<MusicBand, Long> idColumn;
    @FXML
    private TableColumn<MusicBand, String> nameColumn;
    @FXML
    private TableColumn<MusicBand, Double> xColumn;
    @FXML
    private TableColumn<MusicBand, Float> yColumn;
    @FXML
    private TableColumn<MusicBand, Long> numberOfParticipantsColumn;
    @FXML
    private TableColumn<MusicBand, String> descriptionColumn;
    @FXML
    private TableColumn<MusicBand, String> genreColumn;
    @FXML
    private TableColumn<MusicBand, String> studioColumn;
    @FXML
    private Button userInfoButton;
    @FXML
    private Label connectionLabel;

    public MainController(ClientSocketWorker clientSocketWorker, Session session) {
        mainModel = new MainModel(clientSocketWorker, getCurrentStage(), session, this);
    }

    public void initialize() {
        userInfoButton.setText(mainModel.getSession().getUsername());
        connectionLabel.setText("Connected to " + mainModel.getClientSocketWorker().getAddress() + ":" + mainModel.getClientSocketWorker().getPort());
        genreFilter.setItems(FXCollections.observableArrayList(Stream.of(MusicGenre.values()).collect(Collectors.toList())));
        genreFilter.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                    genreFilter.getSelectionModel().clearSelection();
                }
            }
        });
        addRegex(idFilter, numberFilter, xFilter, yFilter);
        initializeTable();
        tablePain.setVisible(true);
        visualPane.setVisible(false);
        applyFilters();
        mainModel.runUpdateLoop();
    }

    public ObservableList<MusicBand> getMusicBandsList() {
        return musicBandsList;
    }

    // working with buttons
    @FXML
    public void switchView() {
        if (tablePain.isVisible()) {
            tablePain.setVisible(false);
            visualPane.setVisible(true);
            clearFilterButton.setVisible(false);

            try {
                mainModel.getNewCollection();
                reloadVisual();
            } catch (ExceptionWithAlert e) {
                e.showAlert();
            }
            switchButton.setText("Switch to table");
        } else {
            tablePain.setVisible(true);
            clearFilterButton.setVisible(true);
            visualPane.setVisible(false);
            try {
                mainModel.getNewCollection();
            } catch (ExceptionWithAlert e) {
                e.showAlert();
            }
            switchButton.setText("Switch to visual");
        }
    }

    @FXML
    public void logoutAction() {
        try {
            switchScene(PathToViews.LOGIN_VIEW, param -> new LoginController(mainModel.getClientSocketWorker()));
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void addAction() {
        try {
            showPopUpStage(PathToViews.ADD_VIEW, param -> new AddController(mainModel.getClientSocketWorker(), mainModel.getSession(), mainModel), "Add menu");
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void clearAction() {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, mainModel.processClearAction());
            alert.setHeaderText(null);
            alert.showAndWait();
            mainModel.getNewCollection();
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void infoAction() {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, mainModel.processInfoAction());
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void historyAction() {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, mainModel.processHistoryAction());
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void executeScriptAction() {
    }

    @FXML
    public void countAction() {
        try {
            showPopUpStage(PathToViews.COUNT_VIEW, param -> new CountController(mainModel.getClientSocketWorker(), mainModel.getSession()), "Count menu");
            mainModel.getNewCollection();
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void exitAction() {
        mainModel.prepareForExit();
        Platform.exit();
    }

    @FXML
    public void updateAction() {
        try {
            showPopUpStage(PathToViews.UPDATE_VIEW, param -> new UpdateController(mainModel.getClientSocketWorker(), mainModel.getSession(), mainModel), "Update menu");
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void removeByIdAction() {
        try {
            showPopUpStage(PathToViews.REMOVE_BY_ID_VIEW, param -> new RemoveByIdController(mainModel.getClientSocketWorker(), mainModel.getSession(), mainModel), "Remove by id menu");
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void removeGreaterAction() {
        try {
            showPopUpStage(PathToViews.REMOVE_GREATER_VIEW, param -> new RemoveGreaterController(mainModel.getClientSocketWorker(), mainModel.getSession(), mainModel), "Remove greater menu");
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void removeByNumberOfParticipantsAction() {
        try {
            showPopUpStage(PathToViews.REMOVE_ANY_VIEW, param -> new RemoveAnyController(mainModel.getClientSocketWorker(), mainModel.getSession(), mainModel), "Remove any menu");
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    // working with table
    private void initializeContextMenu() {
        tableView.setRowFactory(
                tableView -> {
                    final TableRow<MusicBand> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem editItem = new MenuItem("Update");
                    MenuItem removeItem = new MenuItem("Remove by id");
                    MenuItem removeGreaterItem = new MenuItem("Remove greater");
                    MenuItem countItem = new MenuItem("Count less than number of participants");
                    editItem.setOnAction(event -> {
                        try {
                            UpdateController controller = showPopUpStage(PathToViews.UPDATE_VIEW,
                                    param -> new UpdateController(mainModel.getClientSocketWorker(), mainModel.getSession(), mainModel),
                                    "Update menu");
                            controller.setFields(row.getItem().getId(),
                                    row.getItem().getName(),
                                    row.getItem().getCoordinates().getX(),
                                    row.getItem().getCoordinates().getY(),
                                    row.getItem().getNumberOfParticipants(),
                                    row.getItem().getGenre(),
                                    row.getItem().getDescription(),
                                    (row.getItem().getStudio() == null) ? null : row.getItem().getStudio().getAddress());
                        } catch (ExceptionWithAlert e) {
                            e.showAlert();
                        }
                    });
                    removeItem.setOnAction(event -> {
                        try {
                            RemoveByIdController controller = showPopUpStage(PathToViews.REMOVE_BY_ID_VIEW,
                                    param -> new RemoveByIdController(mainModel.getClientSocketWorker(), mainModel.getSession(), mainModel),
                                    "Remove by id menu");
                            controller.setField(row.getItem().getId());
                        } catch (ExceptionWithAlert e) {
                            e.showAlert();
                        }
                    });
                    removeGreaterItem.setOnAction(event -> {
                        try {
                            RemoveGreaterController controller = showPopUpStage(PathToViews.REMOVE_GREATER_VIEW,
                                    param -> new RemoveGreaterController(mainModel.getClientSocketWorker(), mainModel.getSession(), mainModel),
                                    "Remove grater menu");
                            controller.setFields(row.getItem().getName(),
                                    row.getItem().getCoordinates().getX(),
                                    row.getItem().getCoordinates().getY(),
                                    row.getItem().getNumberOfParticipants(),
                                    row.getItem().getGenre(),
                                    row.getItem().getDescription(),
                                    (row.getItem().getStudio() == null) ? null : row.getItem().getStudio().getAddress());
                        } catch (ExceptionWithAlert e) {
                            e.showAlert();
                        }
                    });
                    countItem.setOnAction(event -> {
                        try {
                            CountController controller = showPopUpStage(PathToViews.COUNT_VIEW,
                                    param -> new CountController(mainModel.getClientSocketWorker(), mainModel.getSession()),
                                    "Count menu");
                            controller.setField(row.getItem().getNumberOfParticipants());
                        } catch (ExceptionWithAlert e) {
                            e.showAlert();
                        }
                    });
                    rowMenu.getItems().addAll(editItem, removeItem, removeGreaterItem, countItem);
                    row.contextMenuProperty().bind(
                            Bindings.when(row.emptyProperty())
                                    .then((ContextMenu) null)
                                    .otherwise(rowMenu));
                    getCurrentStage().getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/context.css")).toExternalForm());
                    return row;
                });
    }

    private void initializeTable() {
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        xColumn.setCellValueFactory(musicBand -> new SimpleDoubleProperty(musicBand.getValue().getCoordinates().getX()).asObject());
        yColumn.setCellValueFactory(musicBand -> new SimpleFloatProperty(musicBand.getValue().getCoordinates().getY()).asObject());
        numberOfParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfParticipants"));
        descriptionColumn.setCellValueFactory(musicBand -> new SimpleStringProperty(musicBand.getValue().getDescription() != null
                ? musicBand.getValue().getDescription()
                : "-"));
        genreColumn.setCellValueFactory(musicBand -> new SimpleStringProperty(musicBand.getValue().getGenre() != null
                ? musicBand.getValue().getGenre().toString()
                : "-"));
        studioColumn.setCellValueFactory(musicBand -> new SimpleStringProperty(musicBand.getValue().getStudio() != null
                ? musicBand.getValue().getStudio().getAddress()
                : "-"));
        mainModel.setToolTip(nameColumn);
        mainModel.setToolTip(descriptionColumn);
        mainModel.setToolTip(genreColumn);
        mainModel.setToolTip(studioColumn);
        initializeContextMenu();
        tableView.getSortOrder().add(idColumn);
    }

    public void updateTable(Set<MusicBand> collection, List<Long> usersIDs) {
        List<Long> currentIDs = musicBandsList.stream().map(MusicBand::getId).toList();
        List<Long> newIDs = collection.stream().map(MusicBand::getId).toList();
        mainModel.addNewElements(collection, usersIDs, currentIDs, newIDs);
        mainModel.removeElements(currentIDs, newIDs);
        mainModel.updateElements(collection, usersIDs);
        tableView.sort();
    }

    // working with filters
    public void applyFilters() {
        FilteredList<MusicBand> filtered = new FilteredList<>(musicBandsList, t -> true);
        idFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(musicBand -> Long.toString(musicBand.getId()).startsWith(newValue)));
        nameFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(musicBand -> musicBand.getName().toLowerCase().contains(newValue.toLowerCase())));
        xFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(musicBand -> Double.toString(musicBand.getCoordinates().getX()).startsWith(newValue)));
        yFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(musicBand -> Float.toString(musicBand.getCoordinates().getY()).startsWith(newValue)));
        dateFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(musicBand -> musicBand.getCreationDate().toString().startsWith(newValue)));
        numberFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.
                setPredicate(musicBand -> Long.toString(musicBand.getNumberOfParticipants()).startsWith(newValue)));
        genreFilter.setOnAction(event -> filtered.setPredicate(musicBand -> musicBand.getGenre() == genreFilter.getValue()));
        descriptionFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered
                .setPredicate(musicBand -> ((musicBand.getDescription() == null) ? "-" : musicBand.getDescription().toLowerCase()).contains(newValue.toLowerCase())));
        addressFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(musicBand -> ((musicBand.getStudio() == null) ? "-" : musicBand.getStudio().getAddress()).toLowerCase().contains(newValue.toLowerCase())));
        SortedList<MusicBand> sorted = new SortedList<>(filtered);
        sorted.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sorted);
    }

    @FXML
    public void clearFilterAction() {
        dateFilter.clear();
        idFilter.clear();
        nameFilter.clear();
        xFilter.clear();
        yFilter.clear();
        numberFilter.clear();
        descriptionFilter.clear();
        genreFilter.getSelectionModel().clearSelection();
        addressFilter.clear();
        applyFilters();
    }

    // working with visual part
    public void removeFromVisual(MusicBand musicBand) {
        Canvas canvas = mainModel.getVisualBands().get(musicBand);
        bandsPane.getChildren().remove(canvas);
    }


    public void addToVisual(MusicBand musicBand, boolean alien) {
        Color color;
        if (alien) {
            color = Color.web("4143C4FF");
        } else {
            color = Color.web("5CD20EFF");
        }
        Canvas canvas = mainModel.generateBandCanvas(color, musicBand);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1500));
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setNode(canvas);
        fade.play();
        bandsPane.getChildren().add(canvas);
    }

    public void reloadVisual() {
        ObservableList<Node> nodes = FXCollections.observableArrayList(bandsPane.getChildren());
        bandsPane.getChildren().clear();
        for (Node node : nodes) {
            FadeTransition fade = new FadeTransition();
            fade.setDuration(Duration.millis(1500));
            fade.setFromValue(0);
            fade.setToValue(10);
            fade.setNode(node);
            fade.play();
            bandsPane.getChildren().add(node);
        }
    }
}
