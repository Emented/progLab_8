package emented.lab8FX.client.controllers;

import emented.lab8FX.client.models.MainModel;

public class MainController extends AbstractController {

    private MainModel mainModel;

    @Override
    public void initializeController() {
        mainModel = (MainModel) getModel();
    }

}
