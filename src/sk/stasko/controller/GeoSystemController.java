package sk.stasko.controller;

import java.io.IOException;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sk.stasko.App;
import sk.stasko.AppGen;
import sk.stasko.model.geoObjects.AbstractBuildingObject;
import sk.stasko.service.buildingLot.BuildingLotServiceImpl;
import sk.stasko.service.realEstate.RealEstateServiceImpl;
import sk.stasko.util.AlertHandler;

public class GeoSystemController extends MainController {

    @FXML private TextField realEstatesNumber;
    @FXML private TextField buildingLotsNumber;


    @FXML
    private void moveToSearchBuildingLot() throws IOException {
        App.setNewRoot("BuildingLot");
    }

    @FXML
    private void moveToSearchRealEstate() throws IOException {
        App.setNewRoot("RealEstate");
    }

    public void onSave() {
        if (App.save()) {
            AlertHandler.informationDialog("Success", "You have saved your data");
        }
    }

    public void onLoad() {
        AbstractBuildingObject.getIdGen().set(0);
        RealEstateServiceImpl.getInstance().clearRoot();
        BuildingLotServiceImpl.getInstance().clearRoot();
        try {
            RealEstateServiceImpl.getInstance().load();
            BuildingLotServiceImpl.getInstance().load();
            AlertHandler.informationDialog("Success", "You have loaded your latest save");
        } catch (IOException e) {
            AlertHandler.errorDialog("Error", "Loading was unsuccessful");
        }
    }

    public void generateObjects() {
        int numberRealEstates;
        int numberBuildingLots;
        try {
            numberRealEstates = Integer.parseInt(this.realEstatesNumber.getText());
            numberBuildingLots = Integer.parseInt(this.buildingLotsNumber.getText());
        } catch (NumberFormatException e) {
            AlertHandler.errorDialog("Error", "You did not type a number");
            return;
        }
        if (numberBuildingLots < 0 || numberRealEstates < 0) {
            AlertHandler.errorDialog("Error", "You can not type negative number");
            return;
        }
        Random random = new Random();
        AppGen.generateBuildingLot(numberBuildingLots, random);
        AppGen.generateRealEstates(numberRealEstates, random);
        AlertHandler.informationDialog("Success", "You have successfully generated objects");
    }
}
