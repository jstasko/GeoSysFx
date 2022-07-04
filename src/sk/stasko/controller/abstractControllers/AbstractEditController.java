package sk.stasko.controller.abstractControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sk.stasko.controller.MainController;
import sk.stasko.model.geoObjects.AbstractBuildingObject;
import sk.stasko.model.geoObjects.ISavable;
import sk.stasko.util.AlertHandler;

public abstract class AbstractEditController<T extends ISavable<Long>> extends MainController {
    @FXML private TextField catalogNumber;
    @FXML private TextArea description;

    protected AbstractBuildingObject<T> data;
    protected String descriptionObject;
    protected int numberObject;

    protected void setFields() {
        this.widthGps.setText(String.valueOf(this.data.getGps().getWidth()));
        this.lengthGps.setText(String.valueOf(this.data.getGps().getLength()));
        this.widthGpsPosition.setText(String.valueOf(this.data.getGps().getPositionOfWidth()));
        this.lengthGpsPosition.setText(String.valueOf(this.data.getGps().getPositionOfLength()));
        this.catalogNumber.setText(String.valueOf(this.data.getNumber()));
        this.description.setText(String.valueOf(this.data.getDescription()));
    }

    protected boolean setCatalogNumber() {
        try {
            this.numberObject = Integer.parseInt(this.catalogNumber.getText());
            return true;
        } catch (NumberFormatException e) {
            AlertHandler.errorDialog("Error", "You did not type correct catalog number");
            return false;
        }
    }

    protected void setDescription() {
        this.descriptionObject = this.description.getText();
    }

    public abstract void setAllFields();
    public abstract void setData(AbstractBuildingObject<T> data);
    protected abstract boolean delete();
    protected abstract void edit();
}
