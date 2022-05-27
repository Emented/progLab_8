package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.MainModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.common.entities.MusicBand;
import emented.lab8FX.common.entities.enums.MusicGenre;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainController extends AbstractController {

    @FXML
    private TableView<MusicBand> tableView;
    // Table columns
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

    @FXML
    private ComboBox<String> genreFilter;

    private final MainModel mainModel;

    private final ObservableList<MusicBand> musicBandsList = FXCollections.observableArrayList();

    public MainController(ClientSocketWorker clientSocketWorker, Session session) {
        mainModel = new MainModel(clientSocketWorker, getCurrentStage(), session, this);
    }

    public void initialize() {
        userInfoButton.setText(mainModel.getSession().getUsername());
        connectionLabel.setText("Connected to " + mainModel.getClientSocketWorker().getAddress() + ":" + mainModel.getClientSocketWorker().getPort());
        genreFilter.setItems(FXCollections.observableArrayList(Stream.of(MusicGenre.values()).map(Enum::toString).collect(Collectors.toList())));
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
        tableView.setItems(musicBandsList);


//        musicBandsList.addAll(new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "dfsdfgdsfgsdfgdsfgdsfgsdfgdsfgdsfhdfghdfsggadsfasdf", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 1L, "test", new Coordinates(12, 12F), 34L, null, null, new Studio("test")),
//                new MusicBand(LocalDate.now(), 3L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.PROGRESSIVE_ROCK, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 225L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")),
//                new MusicBand(LocalDate.now(), 2343L, "test", new Coordinates(12, 12F), 34L, "asd", MusicGenre.BLUES, new Studio("test")));
//
//        tableView.setItems(musicBandsList);

    }

    public void updateTable(Set<MusicBand> collection) {
        musicBandsList.clear();
        musicBandsList.addAll(collection);
        tableView.sort();
    }

    @FXML
    public void switchView(ActionEvent actionEvent) {
    }

    @FXML
    public void userInfoAction(ActionEvent actionEvent) {
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
    public void addAction(ActionEvent actionEvent) {
    }

    @FXML
    public void clearAction() {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, mainModel.processClearAction());
            alert.setHeaderText(null);
            alert.showAndWait();
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
    public void executeScriptAction(ActionEvent actionEvent) {
    }

    @FXML
    public void countAction(ActionEvent actionEvent) {
    }

    @FXML
    public void exitAction() {
        mainModel.prepareForExit();
        Platform.exit();
    }

    @FXML
    public void updateAction(ActionEvent actionEvent) {
    }

    @FXML
    public void removeByIdAction(ActionEvent actionEvent) {
    }

    @FXML
    public void removeGreaterAction(ActionEvent actionEvent) {
    }

    @FXML
    public void removeByNumberOfParticipantsAction(ActionEvent actionEvent) {
    }
}
