package sk.stasko.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sk.stasko.App;
import sk.stasko.model.gps.Gps;
import sk.stasko.service.gps.GpsServiceImpl;
import sk.stasko.util.AlertHandler;

import java.io.IOException;

public class MainController {
    @FXML protected TextField widthGps;
    @FXML protected TextField lengthGps;
    @FXML protected TextField widthGpsPosition;
    @FXML protected TextField lengthGpsPosition;

    /**
     *
     * @return newly created gps from fields
     */
    protected Gps getGps() {
        String randomCorrectWidthPosition = "S";
        String randomCorrectLengthPosition = "E";
        try {
            return GpsServiceImpl.getInstance().
                    getNewGpsFromStrings(randomCorrectWidthPosition, widthGps.getText(), randomCorrectLengthPosition, lengthGps.getText());
        } catch (NumberFormatException e) {
            AlertHandler.errorDialog("Error", "You did not type length or width");
            return null;
        } catch (RuntimeException e) {
            AlertHandler.errorDialog("Error", e.getMessage());
            return null;
        }
    }

    @FXML
    private void moveBackToGeo() throws IOException {
        App.setNewRoot("GeoSystem");
    }

    @FXML
    private void exit() {
        System.exit(-1);
    }
}
