package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.common.entities.MusicBand;
import emented.lab8FX.common.entities.enums.MusicGenre;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableController extends AbstractDataController implements Initializable {

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
    public TextField addressFilter;
    @FXML
    private ComboBox<MusicGenre> genreFilter;
    @FXML
    private TableView<MusicBand> tableView;
    @FXML
    private TableColumn<MusicBand, String> creationDateColumn;
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

    private DateTimeFormatter dateTimeFormatter;

    private final ObservableList<MusicBand> musicBandsList = FXCollections.observableArrayList();


    public TableController(MainController mainController) {
        super(mainController);
    }

    @Override
    public void updateElements(List<MusicBand> elementsToAdd, List<MusicBand> elementsToRemove, List<MusicBand> elementsToUpdate, List<Long> usersIDs) {
        musicBandsList.addAll(elementsToAdd);
        musicBandsList.removeAll(elementsToRemove);
        List<Long> idsToUpdate = elementsToUpdate.stream().map(MusicBand::getId).toList();
        for (Long id : idsToUpdate) {
            MusicBand m = elementsToUpdate.stream().filter(musicBand -> musicBand.getId().equals(id)).toList().get(0);
            MusicBand n = musicBandsList.stream().filter(musicBand -> musicBand.getId().equals(id)).toList().get(0);
            musicBandsList.remove(n);
            musicBandsList.add(m);
        }
    }

    @Override
    public void initializeElements(Set<MusicBand> musicBandSet, List<Long> usersIDs) {
        musicBandsList.addAll(musicBandSet);
        tableView.sort();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
        dateTimeFormatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(getResourceBundle().getLocale());
        genreFilter.setItems(FXCollections.observableArrayList(Stream.of(MusicGenre.values()).collect(Collectors.toList())));
        genreFilter.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                if(event.getClickCount() == 2) {
                    genreFilter.getSelectionModel().clearSelection();
                    applyFilters();
                }
            }
        });
        getMainController().addRegex(idFilter, numberFilter, xFilter, yFilter);
        initializeTable();
        applyFilters();
    }

    private void initializeTable() {
        creationDateColumn.setCellValueFactory(musicBand -> new SimpleStringProperty(musicBand.getValue().getCreationDate().format(dateTimeFormatter)));
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
        getMainController().getMainModel().setToolTip(nameColumn);
        getMainController().getMainModel().setToolTip(descriptionColumn);
        getMainController().getMainModel().setToolTip(genreColumn);
        getMainController().getMainModel().setToolTip(studioColumn);
        initializeContextMenu();
        tableView.getSortOrder().add(idColumn);
    }

    private void initializeContextMenu() {
        tableView.setRowFactory(
                tableView -> {
                    final TableRow<MusicBand> row = new TableRow<>();
                    row.setOnMouseClicked(event -> {
                        if(event.getButton().equals(MouseButton.PRIMARY)) {
                            if(event.getClickCount() == 2) {
                                getMainController().getMainModel().showInfoElement(row.getItem());
                            }
                        }
                    });
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem editItem = new MenuItem(getResourceBundle().getString("main_menu.command_button.update"));
                    MenuItem removeItem = new MenuItem(getResourceBundle().getString("main_menu.command_button.remove.remove_id"));
                    MenuItem removeGreaterItem = new MenuItem(getResourceBundle().getString("main_menu.command_button.remove.remove_greater"));
                    MenuItem countItem = new MenuItem(getResourceBundle().getString("main_menu.command_button.count_less"));
                    editItem.setOnAction(event -> {
                        try {
                            UpdateController controller = getMainController().showPopUpStage(PathToViews.UPDATE_VIEW,
                                    param -> new UpdateController(getMainController().getMainModel().getClientSocketWorker(),
                                            getMainController().getMainModel().getSession(),
                                            getMainController().getMainModel()),
                                    getResourceBundle().getString("update_menu.title"),
                                    getResourceBundle());
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
                            RemoveByIdController controller = getMainController().showPopUpStage(PathToViews.REMOVE_BY_ID_VIEW,
                                    param -> new RemoveByIdController(getMainController().getMainModel().getClientSocketWorker(),
                                            getMainController().getMainModel().getSession(),
                                            getMainController().getMainModel()),
                                    getResourceBundle().getString("remove_by_id.title"),
                                    getResourceBundle());
                            controller.setField(row.getItem().getId());
                        } catch (ExceptionWithAlert e) {
                            e.showAlert();
                        }
                    });
                    removeGreaterItem.setOnAction(event -> {
                        try {
                            RemoveGreaterController controller = getMainController().showPopUpStage(PathToViews.REMOVE_GREATER_VIEW,
                                    param -> new RemoveGreaterController(getMainController().getMainModel().getClientSocketWorker(),
                                            getMainController().getMainModel().getSession(),
                                            getMainController().getMainModel()),
                                    getResourceBundle().getString("remove_greater.title"),
                                    getResourceBundle());
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
                            CountController controller = getMainController().showPopUpStage(PathToViews.COUNT_VIEW,
                                    param -> new CountController(getMainController().getMainModel().getClientSocketWorker(),
                                            getMainController().getMainModel().getSession()),
                                    getResourceBundle().getString("count_less.title"),
                                    getResourceBundle());
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
                    getMainController().getCurrentStage().getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/context.css")).toExternalForm());
                    return row;
                });
    }

    public void applyFilters() {
        FilteredList<MusicBand> filtered = new FilteredList<>(musicBandsList, t -> true);
        idFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(musicBand -> Long.toString(musicBand.getId()).startsWith(newValue)));
        nameFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(musicBand -> musicBand.getName().toLowerCase().contains(newValue.toLowerCase())));
        xFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(musicBand -> Double.toString(musicBand.getCoordinates().getX()).startsWith(newValue)));
        yFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(musicBand -> Float.toString(musicBand.getCoordinates().getY()).startsWith(newValue)));
        dateFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(musicBand -> musicBand.getCreationDate().format(dateTimeFormatter).startsWith(newValue)));
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
}

