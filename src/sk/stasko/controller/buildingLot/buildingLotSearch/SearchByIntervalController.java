package sk.stasko.controller.buildingLot.buildingLotSearch;

import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import sk.stasko.controller.abstractControllers.AbstractSearchController;
import sk.stasko.model.geoObjects.buildingLot.BuildingLot;
import sk.stasko.model.gps.GpsInterval;
import sk.stasko.service.buildingLot.BuildingLotServiceImpl;
import sk.stasko.util.AlertHandler;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class SearchByIntervalController extends AbstractSearchController implements Initializable {
    private SearchController searchController;

    public void injectParentController(SearchController searchController) {
        this.searchController = searchController;
    }

    public void findByInterval() {
        GpsInterval widthGpsInterval = this.getWidthInterval();
        GpsInterval lengthGpsInterval = this.getLengthInterval();
        if (widthGpsInterval == null || lengthGpsInterval == null) {
            AlertHandler.errorDialog("Error", "You did not type intervals correct");
            return;
        }
        List<BuildingLot> buildingLotList = BuildingLotServiceImpl.getInstance().findDataByInterval(widthGpsInterval, lengthGpsInterval);
        this.searchController.getBuildingLotView().getItems().setAll(buildingLotList);
        this.searchController.getBuildingLotView().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        if (buildingLotList.size() == 0) {
            AlertHandler.informationDialog(
                    "Information Dialog",
                    "There is no Building lot in intervals between  [ "
                            + widthGpsInterval.getLeftBorder().getBorder() + ", "
                            + widthGpsInterval.getRightBorder().getBorder() + " ] and  [" +
                            + lengthGpsInterval.getLeftBorder().getBorder() + ", "
                            + lengthGpsInterval.getRightBorder().getBorder() + " ]"

            );
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    protected void find() {

    }
}
