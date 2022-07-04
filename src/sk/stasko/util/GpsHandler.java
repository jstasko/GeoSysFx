package sk.stasko.util;

import sk.stasko.util.exception.GpsPositionException;

public class GpsHandler {
    /**
     * @param position position to be evaluated
     * @return char
     */
    public static char getLengthPosition(String position) {
        if (position.length() != 1) {
            throw new RuntimeException("Type length position please");
        }

        if (position.charAt(0) == 'W' || position.charAt(0) == 'E') {
            return position.charAt(0);
        }
        throw new GpsPositionException("Bad letter for length");
    }

    /**
     * @param position position to be evaluated
     * @return char
     */
    public static char getWidthPosition(String position) {
        if (position.length() != 1) {
            throw new RuntimeException("Type width position please");
        }

        if (position.charAt(0) == 'N' || position.charAt(0) == 'S') {
            return position.charAt(0);
        }
        throw new GpsPositionException("Bad letter for Width");
    }
}
