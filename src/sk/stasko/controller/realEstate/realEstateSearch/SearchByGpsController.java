package sk.stasko.controller.realEstate.realEstateSearch;

import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import sk.stasko.controller.abstractControllers.AbstractSearchController;
import sk.stasko.model.geoObjects.realEstate.RealEstate;
import sk.stasko.model.gps.Gps;
import sk.stasko.service.realEstate.RealEstateServiceImpl;
import sk.stasko.util.AlertHandler;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchByGpsController extends AbstractSearchController implements Initializable {
    private SearchController searchController;

    public void injectParentController(SearchController searchController) {
        this.searchController = searchController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    protected void find() {
        Gps gps = this.getGps();
        if (gps == null) {
            AlertHandler.errorDialog("Error", "You did not type correct GPS");
            return;
        }
        List<RealEstate> realEstateList = RealEstateServiceImpl.getInstance().findDataByGpsCoordinates(gps);
        this.searchController.getRealEstateView().getItems().setAll(realEstateList);
        this.searchController.getRealEstateView().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        if (realEstateList.size() == 0) {
            AlertHandler.informationDialog(
                    "Information Dialog",
                    "There is no Real estate with coordinates { " + gps.getWidth() + ", " + gps.getLength() + " ]"
            );
        } else {
            AlertHandler.informationDialog("Found elements", "I have found " + realEstateList.size() + " elements");
        }
    }

    public void findByGps() {
        this.find();
    }
}
