package sk.stasko.model.tree.interval;

import sk.stasko.model.tree.comparator.AbstractComparator;

import java.util.Comparator;

public abstract class AbstractInterval<T> {
    private final IntervalBorder<Double> leftBorder;
    private final IntervalBorder<Double> rightBorder;
    private final AbstractComparator<T> comparator;

    public AbstractInterval(IntervalBorder<Double> leftBorder, IntervalBorder<Double> rightBorder, AbstractComparator<T> comparator) {
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.comparator = comparator;
    }

    public IntervalBorder<Double> getLeftBorder() {
        return leftBorder;
    }

    public IntervalBorder<Double> getRightBorder() {
        return rightBorder;
    }

    public AbstractComparator<T> getComparator() {
        return comparator;
    }

    /**
     *
     * @param value value to be compared
     * @param numberOfKeys number of keys in tree
     * @return true if it is in interval
     */
    public abstract boolean checkIfIsInInterval(T value, int numberOfKeys);
    /**
     *
     * @param value value to be compared
     * @param numberOfKeys number of keys in tree
     * @param indexLevel level of value in tree
     * @return true if it is in interval
     */
    public abstract boolean checkIfIsInInterval(T value, int numberOfKeys, int indexLevel);
    /**
     *
     * @param value value to be compared
     * @param numberOfKeys number of keys in tree
     * @return true if it is greater than interval
     */
    public abstract boolean checkIfItIsGreaterThanInterval(T value, int numberOfKeys);
    /**
     *
     * @param value value to be compared
     * @param numberOfKeys number of keys in tree
     * @return true if it is smaller than interval
     */
    public abstract boolean checkIfItIsSmallerThanInterval(T value, int numberOfKeys);
    /**
     *
     * @param value value to be compared
     * @param numberOfKeys number of keys in tree
     * @return true if it is right border
     */
    public abstract boolean checkIfItIsRightBorder(T value, int numberOfKeys);
    /**
     *
     * @param value value to be compared
     * @param numberOfKeys number of keys in tree
     * @return true if it is left border
     */
    public abstract boolean checkIfItIsLeftBorder(T value, int numberOfKeys);
}
