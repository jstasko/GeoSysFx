package sk.stasko.controller.buildingLot.buildingLotSearch;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import sk.stasko.App;
import sk.stasko.controller.MainController;
import sk.stasko.controller.buildingLot.BuildingLotController;
import sk.stasko.controller.buildingLot.buildingLotEdit.EditController;
import sk.stasko.model.geoObjects.buildingLot.BuildingLot;
import sk.stasko.util.AlertHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController extends MainController implements Initializable {

    private BuildingLotController buildingLotController;

    @FXML
    private SearchByIntervalController searchByIntervalController;
    @FXML
    private SearchByGpsController searchByGpsController;
    @FXML
    private ListView<BuildingLot> buildingLotView;

    public void injectMainController(BuildingLotController buildingLotController){
        this.buildingLotController = buildingLotController;
    }

    public ListView<BuildingLot> getBuildingLotView() {
        return buildingLotView;
    }

    public BuildingLotController getBuildingLotController() {
        return buildingLotController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.searchByGpsController.injectParentController(this);
        this.searchByIntervalController.injectParentController(this);
    }

    public void handleClick() throws IOException {
        BuildingLot buildingLot = this.buildingLotView.getSelectionModel().getSelectedItem();
        if (buildingLot == null) {
            AlertHandler.errorDialog("Error", "You did not select any item from list");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("buildingLot/Edit.fxml"));
        Parent mainLoader = fxmlLoader.load();
        EditController editController = fxmlLoader.getController();
        editController.setData(buildingLot);
        editController.setAllFields();
        App.setRoot(mainLoader);
    }
}
