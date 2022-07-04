package sk.stasko.model.tree.comparator;

import java.util.Comparator;

public abstract class AbstractComparator<T> implements Comparator<T> {
    /**
     * number of keys in tree
     */
    protected final int numberOfKeys;
    /**
     * index of key to be compared
     */
    protected int value;

    public AbstractComparator(int numberOfKeys, int value) {
        this.numberOfKeys = numberOfKeys;
        this.value = value;
    }

    public AbstractComparator(int numberOfKeys) {
        this.numberOfKeys = numberOfKeys;
        this.value = -1;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
