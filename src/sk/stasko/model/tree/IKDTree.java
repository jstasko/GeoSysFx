package sk.stasko.model.tree;

import sk.stasko.model.tree.comparator.AbstractComparator;
import sk.stasko.model.tree.interval.AbstractInterval;
import sk.stasko.model.tree.key.Key;

import java.util.Comparator;
import java.util.List;

public interface IKDTree<T, S> {
    /**
     *
     * @param data data to be inserted
     * @param key key to be inserted
     * @return data that was inserted
     */
    T insert(T data, Key<S> key);

    /**
     *
     * @param key for finding objects
     * @return founded objects
     */
    List<T> find(Key<S> key);

    /**
     *
     * @param interval for finding objects
     * @return founded objects
     */
    List<T> find(List<AbstractInterval<S>> interval);

    /**
     *
     * @param data to be deleted
     * @param keys to found data for deletion
     * @param dataComparator for finding correct data
     * @param comp for finding correct minimum in keys
     * @return true if it was deleted
     */
    boolean delete(T data, Key<S> keys,Comparator<T> dataComparator, AbstractComparator<S> comp);

    /**
     *
     * @return number of secondaries keys in tree
     */
    int getNumberOfKeys();

    /**
     *
     * @return true if has root
     */
    boolean hasRoot();

    /**
     * clear whole tree
     */
    void clearRoot();

    /**
     *
     * @return main comparator for tree
     */
    AbstractComparator<S> getComparator();

    /**
     *
     * @return return elements by level from tree
     */
    List<T> getLevelOrder();

    /**
     *
     * @return number of elements in tree
     */
    int countElements();
}
