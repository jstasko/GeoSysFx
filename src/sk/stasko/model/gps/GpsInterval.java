package sk.stasko.model.gps;

import sk.stasko.model.tree.comparator.AbstractComparator;
import sk.stasko.model.tree.interval.AbstractInterval;
import sk.stasko.model.tree.interval.IntervalBorder;


public class GpsInterval extends AbstractInterval<Gps> {

    /**
     *
     * @param left left border of interval
     * @param leftEqual if left border is equal
     * @param right right border for interval
     * @param rightEqual if right border is equal
     * @param comparator comparator if value of interval has special comparator
     */
    public GpsInterval(Double left, boolean leftEqual, Double right, boolean rightEqual, AbstractComparator<Gps> comparator) {
        super(new IntervalBorder<>(left, leftEqual), new IntervalBorder<>(right, rightEqual), comparator);
    }

    @Override
    public boolean checkIfIsInInterval(Gps value, int numberOfKeys) {
        int index = value.getCurrentIndexLevel() % numberOfKeys;
        Double myValue = index == 0 ? value.getWidth() : value.getLength();
        return this.isValueDoubleInInterval(myValue);
    }

    @Override
    public boolean checkIfItIsGreaterThanInterval(Gps value, int numberOfKeys) {
        int index = value.getCurrentIndexLevel() % numberOfKeys;
        Double myValue = index == 0 ? value.getWidth() : value.getLength();
        if (this.getRightBorder().isEqual()) {
            return myValue.compareTo(this.getRightBorder().getBorder()) > 0;
        }
        return myValue.compareTo(this.getRightBorder().getBorder()) >= 0;
    }

    @Override
    public boolean checkIfItIsSmallerThanInterval(Gps value, int numberOfKeys) {
        int index = value.getCurrentIndexLevel() % numberOfKeys;
        Double myValue = index == 0 ? value.getWidth() : value.getLength();

        if (this.getLeftBorder().isEqual()) {
            return myValue.compareTo(this.getLeftBorder().getBorder()) < 0;
        }
        return myValue.compareTo(this.getRightBorder().getBorder()) <= 0;
    }

    @Override
    public boolean checkIfItIsRightBorder(Gps value, int numberOfKeys) {
        int index = value.getCurrentIndexLevel() % numberOfKeys;
        Double myValue = index == 0 ? value.getWidth() : value.getLength();

        return myValue.compareTo(this.getRightBorder().getBorder()) == 0;
    }

    @Override
    public boolean checkIfItIsLeftBorder(Gps value, int numberOfKeys) {
        int index = value.getCurrentIndexLevel() % numberOfKeys;
        Double myValue = index == 0 ? value.getWidth() : value.getLength();

        return myValue.compareTo(this.getLeftBorder().getBorder()) == 0;
    }

    @Override
    public boolean checkIfIsInInterval(Gps value, int numberOfKeys, int indexLevel) {
        int index = indexLevel % numberOfKeys;
        Double myValue = index == 0 ? value.getWidth() : value.getLength();
        return isValueDoubleInInterval(myValue);
    }

    private boolean isValueDoubleInInterval(Double myValue) {
        if (this.getLeftBorder().isEqual() && this.getRightBorder().isEqual()) {
            return myValue.compareTo(this.getLeftBorder().getBorder()) >= 0 &&
                    myValue.compareTo(this.getRightBorder().getBorder()) <= 0;
        } else if (this.getLeftBorder().isEqual()) {
            return myValue.compareTo(this.getLeftBorder().getBorder()) >= 0 &&
                    myValue.compareTo(this.getRightBorder().getBorder()) < 0;
        } else if (this.getRightBorder().isEqual()) {
            return myValue.compareTo(this.getLeftBorder().getBorder()) > 0 &&
                    myValue.compareTo(this.getRightBorder().getBorder()) <= 0;
        }
        return myValue.compareTo(this.getLeftBorder().getBorder()) > 0 &&
                myValue.compareTo(this.getRightBorder().getBorder()) < 0;
    }
}
