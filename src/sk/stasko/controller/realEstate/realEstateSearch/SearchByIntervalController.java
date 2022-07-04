package sk.stasko.controller.realEstate.realEstateSearch;

import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import sk.stasko.controller.abstractControllers.AbstractSearchController;
import sk.stasko.model.geoObjects.realEstate.RealEstate;
import sk.stasko.model.gps.GpsInterval;
import sk.stasko.service.realEstate.RealEstateServiceImpl;
import sk.stasko.util.AlertHandler;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class SearchByIntervalController extends AbstractSearchController implements Initializable {
    private SearchController searchController;

    public void injectParentController(SearchController searchController) {
        this.searchController = searchController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    protected void find() {
        GpsInterval widthGpsInterval = this.getWidthInterval();
        GpsInterval lengthGpsInterval = this.getLengthInterval();

        if (widthGpsInterval == null || lengthGpsInterval == null) {
            AlertHandler.errorDialog("Error", "You did not type intervals correct");
            return;
        }

        List<RealEstate> realEstateList = RealEstateServiceImpl.getInstance().findDataByInterval(widthGpsInterval, lengthGpsInterval);
        this.searchController.getRealEstateView().getItems().setAll(realEstateList);
        this.searchController.getRealEstateView().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        if (realEstateList.size() == 0) {
            AlertHandler.informationDialog(
                    "Information Dialog",
                    "There is no Real estate in intervals between  [ "
                            + widthGpsInterval.getLeftBorder().getBorder() + ", "
                            + widthGpsInterval.getRightBorder().getBorder() + " ] and  [" +
                            + lengthGpsInterval.getLeftBorder().getBorder() + ", "
                            + lengthGpsInterval.getRightBorder().getBorder() + " ]"

            );
        } else {
            AlertHandler.informationDialog("Found elements", "I have found " + realEstateList.size() + " elements");
        }
    }

    public void findByInterval() {
        this.find();
    }
}
