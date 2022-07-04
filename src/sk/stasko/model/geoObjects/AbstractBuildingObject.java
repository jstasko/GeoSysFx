package sk.stasko.model.geoObjects;

import sk.stasko.model.gps.Gps;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @param <T>
 */
public abstract class AbstractBuildingObject<T> implements ISavable<Long> {
    private static final AtomicLong idGen = new AtomicLong(0L);
    private long ID;
    private int number;
    private String description;
    private Gps gps;
    private List<T> items;

    public AbstractBuildingObject(long ID, int number, String description, Gps gps, List<T> items) {
        this.ID = ID;
        this.number = number;
        this.description = description;
        this.gps = gps;
        this.items = items;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGps(Gps gps) {
        this.gps = gps;
    }

    public static AtomicLong getIdGen() {
        return idGen;
    }

    public long getID() {
        return ID;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public Gps getGps() {
        return gps;
    }

    public List<T> getItems() {
        return items;
    }

    /**
     *
     * @param removeObject item to be removed from list
     */
    public void removeItem(T removeObject) {
        this.items.remove(removeObject);
    }

    /**
     *
     * @param item item to be added into list
     */
    public void addItem(T item) {
        this.items.add(item);
    }

    @Override
    public String getPrimaryKey() {
        return String.valueOf(this.getID());
    }

    @Override
    public String getDataForPrint() {
        return this.getID() +
                ";" + this.getNumber() +
                ";" + this.getDescription() +
                ";" + this.getGps().toString();
    }
}
