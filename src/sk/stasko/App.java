package sk.stasko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.stasko.service.buildingLot.BuildingLotServiceImpl;
import sk.stasko.service.gps.GpsServiceImpl;
import sk.stasko.service.realEstate.RealEstateServiceImpl;
import sk.stasko.util.AlertHandler;

import java.io.IOException;
import java.util.Random;

/**
 * <h1>Geo system</h1>
 * The GeoSystem program implements an application that
 * can find insert and delete objects from KD tree
 * <p>
 * @author  Jozef Stasko
 * @version 1.0
 */
public class App extends Application {

    private static Scene scene;
    public static final int numberOfKeys = 2;

    /**
     *
     * @param stage - current stage of application
     * @throws IOException - exception that are throwing if something happened during work with file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("GeoSystem.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void stop() throws Exception {
        super.stop();
    }

    /**
     * @return boolean
     */
    public static boolean save() {
        try {
            RealEstateServiceImpl.getInstance().save();
            BuildingLotServiceImpl.getInstance().save();
            return true;
        } catch (IOException e) {
            AlertHandler.errorDialog("Error", "Saving was unsuccessful");
            return false;
        }
    }

    /**
     * @param fxml - view
     */
    public static void setRoot(Parent fxml) {
        scene.setRoot(fxml);
    }

    /**
     *
     * @param newFxml - new fxml file to be load
     * @throws IOException - throws during work with file
     */
    public static void setNewRoot(String newFxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(newFxml + ".fxml"));
        App.setRoot(fxmlLoader.load());
    }

    public static void main(String[] args) {
        RealEstateServiceImpl.getInstance();
        BuildingLotServiceImpl.getInstance();
        GpsServiceImpl.getInstance();
        launch();
    }
}