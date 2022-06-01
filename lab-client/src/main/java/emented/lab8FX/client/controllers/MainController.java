package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.MainModel;
import emented.lab8FX.client.util.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainController extends AbstractController implements Initializable {

    private final MainModel mainModel;
    @FXML
    public Button switchButton;
    @FXML
    public ChoiceBox<LanguagesEnum> languageBox;
    @FXML
    public Pane visualizationPane;
    @FXML
    private Button userInfoButton;
    @FXML
    private Label connectionLabel;
    private PathToVisuals currentVisual;
    private AbstractDataController currentDataController;

    public MainController(ClientSocketWorker clientSocketWorker, Session session, PathToVisuals currentVisual) {
        mainModel = new MainModel(clientSocketWorker, getCurrentStage(), session, this);
        this.currentVisual = currentVisual;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
        userInfoButton.setText(mainModel.getSession().getUsername());
        connectionLabel.setText(getResourceBundle().getString("main_menu.text.connection") + " " + mainModel.getClientSocketWorker().getAddress() + ":" + mainModel.getClientSocketWorker().getPort());
        languageBox.setItems(FXCollections.observableArrayList(Stream.of(LanguagesEnum.values()).collect(Collectors.toList())));
        languageBox.setValue(mainModel.getLanguage(getResourceBundle().getLocale().getLanguage()));
        languageBox.getSelectionModel().selectedItemProperty().addListener((m, oldValue, newValue) -> {
            try {
                setResourceBundle(ResourceBundle.getBundle("localization.locale", new Locale(newValue.getLanguageName())));
                switchScene(PathToViews.MAIN_VIEW,
                        param -> new MainController(mainModel.getClientSocketWorker(), mainModel.getSession(), currentVisual), getResourceBundle());
                languageBox.setValue(newValue);
            } catch (ExceptionWithAlert e) {
                e.showAlert();
            }
        });
        loadDataVisualization(currentVisual, visualizationPane);
        mainModel.runUpdateLoop();
    }

    public MainModel getMainModel() {
        return mainModel;
    }

    public AbstractDataController getCurrentDataController() {
        return currentDataController;
    }

    @FXML
    public void switchView() {
        if (currentVisual.equals(PathToVisuals.VISUALIZATION_VIEW)) {
            loadDataVisualization(PathToVisuals.TABLE_VIEW, visualizationPane);
        } else {
            loadDataVisualization(PathToVisuals.VISUALIZATION_VIEW, visualizationPane);
        }
    }

    @FXML
    public void logoutAction() {
        try {
            switchScene(PathToViews.LOGIN_VIEW,
                    param -> new LoginController(mainModel.getClientSocketWorker()),
                    getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void addAction() {
        try {
            showPopUpStage(PathToViews.ADD_VIEW,
                    param -> new AddController(mainModel.getClientSocketWorker(),
                            mainModel.getSession(),
                            mainModel),
                    getResourceBundle().getString("add_menu.title"),
                    getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void clearAction() {
        try {
            mainModel.processClearAction();
            mainModel.getNewCollection();
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void infoAction() {
        mainModel.processInfoAction();
    }

    @FXML
    public void historyAction() {
        mainModel.processHistoryAction();
    }

    @FXML
    public void countAction() {
        try {
            showPopUpStage(PathToViews.COUNT_VIEW,
                    param -> new CountController(mainModel.getClientSocketWorker(),
                            mainModel.getSession(), mainModel),
                    getResourceBundle().getString("count_less.title"),
                    getResourceBundle());
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
            showPopUpStage(PathToViews.UPDATE_VIEW,
                    param -> new UpdateController(mainModel.getClientSocketWorker(),
                            mainModel.getSession(),
                            mainModel),
                    getResourceBundle().getString("update_menu.title"),
                    getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void removeByIdAction() {
        try {
            showPopUpStage(PathToViews.REMOVE_BY_ID_VIEW,
                    param -> new RemoveByIdController(mainModel.getClientSocketWorker(),
                            mainModel.getSession(),
                            mainModel),
                    getResourceBundle().getString("remove_by_id.title"),
                    getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void removeGreaterAction() {
        try {
            showPopUpStage(PathToViews.REMOVE_GREATER_VIEW,
                    param -> new RemoveGreaterController(mainModel.getClientSocketWorker(),
                            mainModel.getSession(),
                            mainModel),
                    getResourceBundle().getString("remove_greater.title"),
                    getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void removeByNumberOfParticipantsAction() {
        try {
            showPopUpStage(PathToViews.REMOVE_ANY_VIEW,
                    param -> new RemoveAnyController(mainModel.getClientSocketWorker(),
                            mainModel.getSession(),
                            mainModel),
                    getResourceBundle().getString("remove_by_number.title"),
                    getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    private void loadDataVisualization(PathToVisuals pathToVisuals, Pane targetPane) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(pathToVisuals.getPath()));
            loader.setResources(getResourceBundle());
            if (pathToVisuals.equals(PathToVisuals.VISUALIZATION_VIEW)) {
                loader.setControllerFactory(param -> new VisualizationController(this));
                switchButton.setText(getResourceBundle().getString("main_menu.button.switch_to_table"));
            } else if (pathToVisuals.equals(PathToVisuals.TABLE_VIEW)) {
                loader.setControllerFactory(param -> new TableController(this));
                switchButton.setText(getResourceBundle().getString("main_menu.button.switch_to_visual"));
            }
            currentVisual = pathToVisuals;
            Parent parent = loader.load();
            currentDataController = loader.getController();
            mainModel.initializeCollection();
            currentDataController.initializeElements(mainModel.getBandSet(), mainModel.getUsersIDs());
            targetPane.getChildren().clear();
            targetPane.getChildren().add(parent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExceptionWithAlert e) {
            if (e.isFatal()) {
                e.showAlert();
                mainModel.prepareForExit();
                Platform.exit();
            } else {
                e.showAlert();
            }
        }
    }
}
