package sk.stasko.controller.realEstate.realEstateInsert;

import javafx.fxml.Initializable;
import sk.stasko.controller.abstractControllers.AbstractInsertController;
import sk.stasko.model.gps.Gps;
import sk.stasko.service.realEstate.RealEstateServiceImpl;
import sk.stasko.util.AlertHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class InsertController extends AbstractInsertController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
    @Override
    protected void insertItem() {
        Gps gps = this.getGps();
        if (gps == null || !this.setCatalogNumber()) {
            return;
        }
        setDescription();
        RealEstateServiceImpl.getInstance().addData(catalogNumber, description, gps);
        AlertHandler.informationDialog("Success", "You have added new real estate");
        this.clearFields();
    }

    public void insertRealEstate() {
        insertItem();
    }
}
