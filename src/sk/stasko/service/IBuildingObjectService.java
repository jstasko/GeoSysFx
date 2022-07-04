package sk.stasko.service;

import sk.stasko.model.gps.Gps;
import sk.stasko.model.gps.GpsInterval;
import sk.stasko.model.tree.comparator.AbstractComparator;

import java.util.Comparator;
import java.util.List;

public interface IBuildingObjectService<T> {
    /**
     *
     * @param gps find object with this gps
     * @return object with gps
     */
    List<T> findDataByGpsCoordinates(Gps gps);

    /**
     *
     * @param gpsWidthInterval - interval for width
     * @param gpsLengthInterval - interval for length
     * @return objects that are fit into both intervals
     */
    List<T> findDataByInterval(GpsInterval gpsWidthInterval, GpsInterval gpsLengthInterval);

    /**
     * @param gps - find object with it
     * @param obj - object to be edited
     * @param comparator - comparator to compare objects
     * @param desc - new description
     * @param number - new number
     * @return true if edit was success
     */
    boolean findDataByCoordinatesAndDataAndSave(Gps gps, T obj, Comparator<T> comparator, String desc, int number);

    /**
     *
     * @param number - catalogue number
     * @param description - description of obj
     * @param gps - gps of obj
     * @return newly added data
     */
    T addData(int number, String description, Gps gps);

    /**
     *
     * @param data - data to be deleted
     * @param dataComparator - data to be compared to find correct data
     * @param comp - comparator to compare keys
     * @return true if element was deleted
     */
    boolean deleteData(T data, Comparator<T> dataComparator, AbstractComparator<Gps> comp);
    void clearRoot();
}
