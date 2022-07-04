package sk.stasko.tester.comparators;

import sk.stasko.tester.IntegerKey;
import sk.stasko.model.tree.comparator.AbstractComparator;

public class IntegerKeyComparer extends AbstractComparator<IntegerKey> {
    public IntegerKeyComparer(int numberOfKeys) {
        super(numberOfKeys);
    }

    @Override
    public int compare(IntegerKey o1, IntegerKey o2) {
        int value = o2.getKey().getCurrentIndexLevel() % numberOfKeys;
        return (o1.getIntegerkeys()[value] < o2.getIntegerkeys()[value]) ? -1 :
                (o2.getIntegerkeys()[value] == o1.getIntegerkeys()[value] ? 0 : 1);
    }
}
