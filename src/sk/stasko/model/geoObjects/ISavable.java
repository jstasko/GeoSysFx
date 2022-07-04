package sk.stasko.model.geoObjects;

public interface ISavable<T> {
    /**
     *
     * @param id save primary key of object
     */
    void savePrimaryKey(T id);

    /**
     *
     * @return primary key in String
     */
    String getPrimaryKey();

    /**
     * Data to be saved
     * @return data in formatted state in String
     */
    String getDataForPrint();
}
