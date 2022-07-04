package sk.stasko.tester;

import sk.stasko.model.tree.comparator.AbstractComparator;
import sk.stasko.model.tree.interval.AbstractInterval;
import sk.stasko.model.tree.interval.IntervalBorder;

public class IntegerInterval extends AbstractInterval<IntegerKey> {

    public IntegerInterval(Integer left, boolean leftEqual, Integer right, boolean rightEqual, AbstractComparator<IntegerKey> comparator) {
        super(new IntervalBorder<>(Double.valueOf(left), leftEqual), new IntervalBorder<>(Double.valueOf(right), rightEqual), comparator);
    }

    @Override
    public boolean checkIfIsInInterval(IntegerKey value, int numberOfKeys) {
        Integer myValue = value.getIntegerkeys()[value.getCurrentIndexLevel() % numberOfKeys];
        return this.isValueIntegerInInterval(myValue);
    }

    public boolean checkIfIsInInterval(IntegerKey value, int numberOfKeys, int indexLevel) {
        Integer myValue = value.getIntegerkeys()[indexLevel % numberOfKeys];
        return this.isValueIntegerInInterval(myValue);
    }

    @Override
    public boolean checkIfItIsGreaterThanInterval(IntegerKey value, int numberOfKeys) {
        Integer myValue = value.getIntegerkeys()[value.getCurrentIndexLevel() % numberOfKeys];
        if (this.getRightBorder().isEqual()) {
            return myValue.compareTo(this.getRightBorder().getBorder().intValue()) > 0;
        }
        return myValue.compareTo(this.getRightBorder().getBorder().intValue()) >= 0;
    }

    @Override
    public boolean checkIfItIsSmallerThanInterval(IntegerKey value, int numberOfKeys) {
        Integer myValue = value.getIntegerkeys()[value.getCurrentIndexLevel() % numberOfKeys];
        if (this.getLeftBorder().isEqual()) {
            return myValue.compareTo(this.getLeftBorder().getBorder().intValue()) < 0;
        }
        return myValue.compareTo(this.getRightBorder().getBorder().intValue()) <= 0;
    }

    @Override
    public boolean checkIfItIsRightBorder(IntegerKey value, int numberOfKeys) {
        Integer myValue = value.getIntegerkeys()[value.getCurrentIndexLevel() % numberOfKeys];
        return myValue.compareTo(this.getRightBorder().getBorder().intValue()) == 0;
    }

    @Override
    public boolean checkIfItIsLeftBorder(IntegerKey value, int numberOfKeys) {
        Integer myValue = value.getIntegerkeys()[value.getCurrentIndexLevel() % numberOfKeys];
        return myValue.compareTo(this.getLeftBorder().getBorder().intValue()) == 0;
    }

    private boolean isValueIntegerInInterval(Integer myValue) {
        if (this.getLeftBorder().isEqual() && this.getRightBorder().isEqual()) {
            return myValue.compareTo(this.getLeftBorder().getBorder().intValue()) >= 0 &&
                    myValue.compareTo(this.getRightBorder().getBorder().intValue()) <= 0;
        } else if (this.getLeftBorder().isEqual()) {
            return myValue.compareTo(this.getLeftBorder().getBorder().intValue()) >= 0 &&
                    myValue.compareTo(this.getRightBorder().getBorder().intValue()) < 0;
        } else if (this.getRightBorder().isEqual()) {
            return myValue.compareTo(this.getLeftBorder().getBorder().intValue()) > 0 &&
                    myValue.compareTo(this.getRightBorder().getBorder().intValue()) <= 0;
        }
        return myValue.compareTo(this.getLeftBorder().getBorder().intValue()) > 0 &&
                myValue.compareTo(this.getRightBorder().getBorder().intValue()) < 0;
    }
}

