package sk.stasko.controller.abstractControllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import sk.stasko.controller.MainController;
import sk.stasko.model.gps.GpsInterval;
import sk.stasko.service.gps.GpsServiceImpl;
import sk.stasko.util.AlertHandler;

public abstract class AbstractSearchController extends MainController {
    @FXML private TextField widthRight;
    @FXML private TextField widthLeft;
    @FXML private TextField lengthRight;
    @FXML private TextField lengthLeft;
    @FXML private CheckBox widthRightEqual;
    @FXML private CheckBox widthLeftEqual;
    @FXML private CheckBox lengthRightEqual;
    @FXML private CheckBox lengthLeftEqual;

    /**
     *
     * @return newly created interval from width field
     */
    protected GpsInterval getWidthInterval() {
        try {
            return GpsServiceImpl.getInstance()
                    .getNewGpsInterval(this.widthLeft.getText(), widthLeftEqual.isSelected(), widthRight.getText(), widthRightEqual.isSelected());
        } catch (NumberFormatException e) {
            AlertHandler.errorDialog("Error", "Bad input for width or length");
            return null;
        }
    }

    /**
     *
     * @return newly created interval from length field
     */
    protected GpsInterval getLengthInterval() {
        try {
           return GpsServiceImpl.getInstance()
                    .getNewGpsInterval(this.lengthLeft.getText(), lengthLeftEqual.isSelected(), lengthRight.getText(), lengthRightEqual.isSelected());
        } catch (NumberFormatException e) {
            AlertHandler.errorDialog("Error", "Bad input for width or length");
            return null;
        }
    }

    protected abstract void find();
}
