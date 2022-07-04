package sk.stasko.controller.buildingLot;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sk.stasko.controller.MainController;
import sk.stasko.controller.buildingLot.buildingLotInsert.InsertController;
import sk.stasko.controller.buildingLot.buildingLotSearch.SearchController;

import java.net.URL;
import java.util.ResourceBundle;

public class BuildingLotController extends MainController implements Initializable {
    @FXML
    private SearchController searchController;
    @FXML
    private InsertController insertController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.searchController.injectMainController(this);
    }
}
