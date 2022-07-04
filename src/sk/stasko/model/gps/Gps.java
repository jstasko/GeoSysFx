package sk.stasko.model.gps;

import sk.stasko.model.tree.key.Key;

public class Gps extends Key<Gps> {
    private char positionOfWidth;
    private char positionOfLength;
    private double width;
    private double length;

    public Gps(char positionOfWidth, char positionOfLength, double width, double length) {
        this.positionOfWidth = positionOfWidth;
        this.positionOfLength = positionOfLength;
        this.width = width;
        this.length = length;
    }

    /**
     * Copy constructor
     * @param gps copy gps
     */
    private Gps(Key<Gps> gps) {
        super();
        this.positionOfWidth = gps.getKey().positionOfWidth;
        this.positionOfLength = gps.getKey().positionOfLength;
        this.width = gps.getKey().width;
        this.length = gps.getKey().length;
    }

    public void setPositionOfWidth(char positionOfWidth) {
        this.positionOfWidth = positionOfWidth;
    }

    public void setPositionOfLength(char positionOfLength) {
        this.positionOfLength = positionOfLength;
    }

    public char getPositionOfWidth() {
        return positionOfWidth;
    }

    public char getPositionOfLength() {
        return positionOfLength;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    @Override
    public Key<Gps> getCopyKeyObject(Key<Gps> key) {
        return new Gps(key);
    }

    @Override
    public void setKey(Gps key) {
        this.width = key.width;
        this.positionOfLength = key.getPositionOfLength();
        this.positionOfWidth = key.getPositionOfWidth();
        this.length = key.length;
    }

    @Override
    public int getCurrentIndexLevel() {
        return this.currentIndexLevel;
    }

    @Override
    public boolean checkIfIsEqual(Key<Gps> key) {
        return this.width == key.getKey().width && this.length == key.getKey().length;
    }

    @Override
    public Gps getKey() {
        return this;
    }

    @Override
    public String toString() {
        return this.getWidth() + ";" +
                this.getLength() + ";" +
                this.getPositionOfWidth() + ";" +
                this.getPositionOfLength() + ";" +
                this.getCurrentIndexLevel();
    }

    public String getCoordinates() {
        return this.getWidth() + "," + this.getLength();
    }
}
