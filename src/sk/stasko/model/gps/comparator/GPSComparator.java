package sk.stasko.model.gps.comparator;

import sk.stasko.model.gps.Gps;
import sk.stasko.model.tree.comparator.AbstractComparator;

/**
 * Comparator for gps
 */
public class GPSComparator extends AbstractComparator<Gps> {

    public GPSComparator(int numberOfKeys) {
        super(numberOfKeys);
    }

    public GPSComparator(int numberOfKeys, int value) {
        super(numberOfKeys, value);
    }

    @Override
    public int compare(Gps o1, Gps o2) {
        int value = o2.getKey().getCurrentIndexLevel() % numberOfKeys;
        if (value == 0) {
            return Double.compare(o1.getWidth(), o2.getWidth());
        } else {
            return Double.compare(o1.getLength(), o2.getLength());
        }
    }
}
