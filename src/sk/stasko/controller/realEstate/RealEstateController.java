package sk.stasko.controller.realEstate;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sk.stasko.controller.MainController;
import sk.stasko.controller.realEstate.realEstateInsert.InsertController;
import sk.stasko.controller.realEstate.realEstateSearch.SearchController;

import java.net.URL;
import java.util.ResourceBundle;

public class RealEstateController extends MainController implements Initializable {

    @FXML
    private SearchController searchController;
    @FXML
    private InsertController insertController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.searchController.injectMainController(this);
    }
}
