package emented.lab8FX.client.controllers;

import emented.lab8FX.client.models.AbstractModel;

public class AbstractController {
    private AbstractModel model;

    public void setModel(AbstractModel model) {
        this.model = model;
    }

    public AbstractModel getModel() {
        return model;
    }
}
