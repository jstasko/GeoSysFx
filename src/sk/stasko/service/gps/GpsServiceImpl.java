package sk.stasko.service.gps;

import sk.stasko.model.gps.Gps;
import sk.stasko.model.gps.GpsInterval;
import sk.stasko.service.IGpsService;
import sk.stasko.util.GpsHandler;

public class GpsServiceImpl implements IGpsService {
    private static IGpsService instance;

    public static IGpsService getInstance() {
        if (instance == null) {
            instance = new GpsServiceImpl();
        }
        return instance;
    }
    @Override
    public Gps getNewGpsFromStrings(String positionWidth, String width, String lengthPosition, String length) throws NumberFormatException {
        char positionWidthChar = GpsHandler.getWidthPosition(positionWidth);
        char positionLengthChar = GpsHandler.getLengthPosition(lengthPosition);
        double longText = Double.parseDouble(length);
        double widthText = Double.parseDouble(width);
        return new Gps(positionWidthChar, positionLengthChar, widthText, longText);
    }

    @Override
    public GpsInterval getNewGpsInterval(String left, boolean leftEqual, String right, boolean rightEqual) {
        try {
            return new GpsInterval(Double.valueOf(left), leftEqual, Double.valueOf(right), rightEqual, null);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
