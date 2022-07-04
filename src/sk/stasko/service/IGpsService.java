package sk.stasko.service;

import sk.stasko.model.gps.Gps;
import sk.stasko.model.gps.GpsInterval;

/**
 * Service for handling Gps creation in one place
 */
public interface IGpsService {
    /**
     * @param positionWidth - position of width
     * @param width - value of width
     * @param lengthPosition - position of length
     * @param length - value of length
     * @return - new GPS
     * @throws NumberFormatException - throws when bad value for width or length
     */
    Gps getNewGpsFromStrings(String positionWidth, String width, String lengthPosition, String length) throws NumberFormatException;

    /**
     * @param left - left interval
     * @param leftEqual - left equal interval
     * @param right - right interval
     * @param rightEqual - right equal interval
     * @return new Gps interval
     */
    GpsInterval getNewGpsInterval(String left, boolean leftEqual, String right, boolean rightEqual);
}
