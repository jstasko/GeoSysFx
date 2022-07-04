package sk.stasko.controller.realEstate.realEstateSearch;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import sk.stasko.App;
import sk.stasko.controller.MainController;
import sk.stasko.controller.realEstate.RealEstateController;
import sk.stasko.controller.realEstate.realEstateEdit.EditController;
import sk.stasko.model.geoObjects.realEstate.RealEstate;
import sk.stasko.util.AlertHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController extends MainController implements Initializable {

    private RealEstateController realEstateController;
    @FXML private SearchByIntervalController searchByIntervalController;
    @FXML private SearchByGpsController searchByGpsController;
    @FXML public ListView<RealEstate> realEstateView;


    public void injectMainController(RealEstateController realEstateController){
        this.realEstateController = realEstateController;
    }

    public RealEstateController getRealEstateController() {
        return realEstateController;
    }

    public ListView<RealEstate> getRealEstateView() {
        return realEstateView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchByGpsController.injectParentController(this);
        searchByIntervalController.injectParentController(this);
    }

    public void handleClick() throws IOException {
        RealEstate realEstate = this.realEstateView.getSelectionModel().getSelectedItem();
        if (realEstate == null) {
            AlertHandler.errorDialog("Error", "You did not select any item from list");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("realEstate/Edit.fxml"));
        Parent mainLoader = fxmlLoader.load();
        EditController editController = fxmlLoader.getController();
        editController.setData(realEstate);
        editController.setAllFields();
        App.setRoot(mainLoader);
    }
}
