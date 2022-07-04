package sk.stasko.controller.realEstate.realEstateEdit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import sk.stasko.App;
import sk.stasko.controller.abstractControllers.AbstractEditController;
import sk.stasko.model.geoObjects.AbstractBuildingObject;
import sk.stasko.model.geoObjects.buildingLot.BuildingLot;
import sk.stasko.model.geoObjects.realEstate.RealEstate;
import sk.stasko.model.geoObjects.comparators.RealEstateComparator;
import sk.stasko.model.gps.Gps;
import sk.stasko.model.gps.comparator.GpsMinMaxComparator;
import sk.stasko.service.realEstate.RealEstateServiceImpl;
import sk.stasko.util.AlertHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class EditController extends AbstractEditController<BuildingLot> implements Initializable {

    @FXML private ListView<BuildingLot> buildingLotListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void setAllFields() {
        this.setFields();
        this.buildingLotListView.getItems().setAll(this.data.getItems());
    }

    @Override
    public void setData(AbstractBuildingObject<BuildingLot> data) {
        this.data = data;
        AlertHandler.informationDialog("Number of Building Lot", "Number of building lot with same GPS is " + data.getItems().size());
    }

    @Override
    protected boolean delete() {
        return RealEstateServiceImpl.getInstance().deleteData(
                (RealEstate) this.data,
                new RealEstateComparator(),
                new GpsMinMaxComparator(App.numberOfKeys, this.data.getGps().getCurrentIndexLevel())
        );
    }

    @Override
    protected void edit() {
        Gps gps = this.getGps();
        this.setCatalogNumber();
        this.setDescription();
        if (gps == null || !this.setCatalogNumber()) {
            AlertHandler.errorDialog("Error", "You did not type correct information");
            return;
        }
        if (this.data.getGps().checkIfIsEqual(gps)) {
            RealEstateServiceImpl.getInstance().findDataByCoordinatesAndDataAndSave(
                    gps,
                    (RealEstate) this.data,
                    new RealEstateComparator(),
                    this.descriptionObject,
                    this.numberObject
            );
        } else {
            this.delete();
            RealEstateServiceImpl.getInstance().addData(this.numberObject, this.descriptionObject, gps);
        }
    }

    public void onDelete() throws IOException {
        if (this.delete()) {
            AlertHandler.informationDialog("Success", "You have deleted Real Estate");
        }
        App.setNewRoot("RealEstate");
    }

    public void onEdit() throws IOException {
        this.edit();
        AlertHandler.informationDialog("Success","You have successfully edited real estate");
        App.setNewRoot("RealEstate");
    }

    public void onClose() throws IOException {
        App.setNewRoot("RealEstate");
    }
}
