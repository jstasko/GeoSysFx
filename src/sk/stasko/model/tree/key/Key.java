package sk.stasko.model.tree.key;

public abstract class Key<T> {
    protected int currentIndexLevel;

    public Key() {
        this.currentIndexLevel = -1;
    }

    /**
     *
     * @param currentIndexLevel new level of key in tree
     */
    public void setCurrentIndexLevel(int currentIndexLevel) {
        this.currentIndexLevel = currentIndexLevel;
    }

    /**
     *
     * @param key key of node
     * @return copy object of key
     */
    public abstract Key<T> getCopyKeyObject(Key<T> key);

    /**
     *
     * @param key new set of keys/key
     */
    public abstract void setKey(T key);

    /**
     *
     * @return current level of key in tree
     */
    public abstract int getCurrentIndexLevel();

    /**
     *
     * @param key key to be compared
     * @return true if keys are equal
     */
    public abstract boolean checkIfIsEqual(Key<T> key);

    /**
     *
     * @return get keys/key
     */
    public abstract T getKey();
}