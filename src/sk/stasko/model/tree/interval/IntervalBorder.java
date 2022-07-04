package sk.stasko.model.tree.interval;

/**
 * Border of interval
 * @param <T> value of border
 */
public class IntervalBorder<T> {
    T border;
    boolean equal;

    public IntervalBorder(T border, boolean equal) {
        this.border = border;
        this.equal = equal;
    }

    public T getBorder() {
        return border;
    }

    public void setBorder(T border) {
        this.border = border;
    }

    public boolean isEqual() {
        return equal;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }
}
