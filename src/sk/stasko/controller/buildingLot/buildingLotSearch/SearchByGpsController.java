package sk.stasko.controller.buildingLot.buildingLotSearch;

import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import sk.stasko.controller.abstractControllers.AbstractSearchController;
import sk.stasko.model.geoObjects.buildingLot.BuildingLot;
import sk.stasko.model.gps.Gps;
import sk.stasko.service.buildingLot.BuildingLotServiceImpl;
import sk.stasko.util.AlertHandler;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchByGpsController extends AbstractSearchController implements Initializable {
    private SearchController searchController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void injectParentController(SearchController searchController) {
        this.searchController = searchController;
    }

    public void findByGps() {
        this.find();
    }

    @Override
    protected void find() {
        Gps gps = this.getGps();
        if (gps == null) {
            AlertHandler.errorDialog("Error", "You did not type correct GPS");
            return;
        }
        List<BuildingLot> buildingLots = BuildingLotServiceImpl.getInstance().findDataByGpsCoordinates(gps);
        this.searchController.getBuildingLotView().getItems().setAll(buildingLots);
        this.searchController.getBuildingLotView().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        if (buildingLots.size() == 0) {
            AlertHandler.informationDialog(
                    "Information Dialog",
                    "There is no Building lot with coordinates { " + gps.getWidth() + ", " + gps.getLength() + " ]"
            );
        } else {
            AlertHandler.informationDialog("Found elements", "I have found " + buildingLots.size() + " elements");
        }
    }
}
