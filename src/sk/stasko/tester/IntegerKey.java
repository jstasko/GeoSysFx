package sk.stasko.tester;

import sk.stasko.model.tree.key.Key;

public class IntegerKey extends Key<IntegerKey> {

    private int[] integerkeys;

    public IntegerKey(int[] integerKeys) {
        super();
        this.integerkeys = integerKeys;
    }

    public IntegerKey(int first, int second) {
        super();
        this.integerkeys = new int[]{first, second};
    }

    private IntegerKey(Key<IntegerKey> anotherKey) {
        super();
        this.integerkeys = anotherKey.getKey().integerkeys;
        this.currentIndexLevel = anotherKey.getCurrentIndexLevel();
    }

    public int[] getIntegerkeys() {
        return integerkeys;
    }

    @Override
    public Key<IntegerKey> getCopyKeyObject(Key<IntegerKey> key) {
         return new IntegerKey(key);
    }

    @Override
    public void setKey(IntegerKey key) {
        this.integerkeys = key.integerkeys;
    }

    @Override
    public int getCurrentIndexLevel() {
        return this.currentIndexLevel;
    }

    @Override
    public boolean checkIfIsEqual(Key<IntegerKey> key) {
        for (int x = 0; x < this.integerkeys.length; x++) {
            if (this.integerkeys[x] == key.getKey().integerkeys[x]) {
                continue;
            }
            return false;
        }
        return true;
    }

    @Override
    public IntegerKey getKey() {
        return this;
    }
}
