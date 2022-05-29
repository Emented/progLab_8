package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.MainModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.common.entities.MusicBand;
import emented.lab8FX.common.entities.enums.MusicGenre;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
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
    private ComboBox<MusicGenre> genreFilter;
    @FXML
    public TextField addressFilter;
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
        initializeTable();
        mainModel.runUpdateLoop();
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
        tableView.setItems(musicBandsList);
        tableView.getSortOrder().add(idColumn);
    }

    public void updateTable(Set<MusicBand> collection) {
        musicBandsList.clear();
        musicBandsList.addAll(collection);
        tableView.sort();
    }

    @FXML
    public void switchView() {
    }

    @FXML
    public void userInfoAction() {
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
    }
}
