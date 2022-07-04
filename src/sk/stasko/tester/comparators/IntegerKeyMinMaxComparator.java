package sk.stasko.tester.comparators;

import sk.stasko.tester.IntegerKey;
import sk.stasko.model.tree.comparator.AbstractComparator;

public class IntegerKeyMinMaxComparator extends AbstractComparator<IntegerKey>
{
    public IntegerKeyMinMaxComparator(int numberOfKeys, int value) {
        super(numberOfKeys, value);
    }

    @Override
    public int compare(IntegerKey o1, IntegerKey o2) {
        return Integer.compare(o1.getIntegerkeys()[this.value], o2.getIntegerkeys()[this.value]);
    }
}
