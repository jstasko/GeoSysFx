package sk.stasko.controller.buildingLot.buildingLotEdit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import sk.stasko.App;
import sk.stasko.controller.abstractControllers.AbstractEditController;
import sk.stasko.model.geoObjects.AbstractBuildingObject;
import sk.stasko.model.geoObjects.buildingLot.BuildingLot;
import sk.stasko.model.geoObjects.comparators.BuildingLotComparator;
import sk.stasko.model.geoObjects.realEstate.RealEstate;
import sk.stasko.model.gps.Gps;
import sk.stasko.model.gps.comparator.GpsMinMaxComparator;
import sk.stasko.service.buildingLot.BuildingLotServiceImpl;
import sk.stasko.util.AlertHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditController extends AbstractEditController<RealEstate> implements Initializable {
    @FXML
    private ListView<RealEstate> buildingLotListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setAllFields() {
        this.setFields();
        this.buildingLotListView.getItems().setAll(this.data.getItems());
    }

    @Override
    public void setData(AbstractBuildingObject<RealEstate> data) {
        this.data = data;
        AlertHandler.informationDialog("Number of Building Lot", "Number of Real estates with same GPS is " + data.getItems().size());
    }

    @Override
    protected boolean delete() {
        return BuildingLotServiceImpl.getInstance().deleteData(
                (BuildingLot) this.data,
                new BuildingLotComparator(),
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
            BuildingLotServiceImpl.getInstance().findDataByCoordinatesAndDataAndSave(
                    gps,
                    (BuildingLot) this.data,
                    new BuildingLotComparator(),
                    this.descriptionObject,
                    this.numberObject
            );
        } else {
            this.delete();
            BuildingLotServiceImpl.getInstance().addData(this.numberObject, this.descriptionObject, gps);
        }
    }

    public void onClose() throws IOException {
        App.setNewRoot("BuildingLot");
    }

    public void onDelete() throws IOException {
        if (this.delete()) {
            AlertHandler.informationDialog("Success", "You have deleted Real Estate");
        }
        App.setNewRoot("BuildingLot");
    }

    public void onEdit() throws IOException {
        this.edit();
        AlertHandler.informationDialog("Success","You have successfully edited building lot");
        App.setNewRoot("BuildingLot");
    }
}
