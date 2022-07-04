package sk.stasko.controller.abstractControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sk.stasko.controller.MainController;
import sk.stasko.util.AlertHandler;

public abstract class AbstractInsertController extends MainController {
    @FXML private TextField catalogNumberInsert;
    @FXML private TextArea descriptionInsert;

    protected int catalogNumber;
    protected String description;

    public AbstractInsertController() {
        this.catalogNumber = -1;
        this.description = "";
    }

    protected void clearFields() {
        this.lengthGps.setText("");
        this.widthGps.setText("");
        this.lengthGpsPosition.setText("");
        this.widthGpsPosition.setText("");
        this.catalogNumberInsert.setText("");
        this.descriptionInsert.setText("");
    }

    protected boolean setCatalogNumber() {
        try {
            this.catalogNumber = Integer.parseInt(this.catalogNumberInsert.getText());
            return true;
        } catch (NumberFormatException e) {
            AlertHandler.errorDialog("Error", "You did not type correct catalog number");
            return false;
        }
    }

    protected void setDescription() {
        this.description = this.descriptionInsert.getText();
    }

    protected abstract void insertItem();
}
