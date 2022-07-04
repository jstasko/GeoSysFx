package sk.stasko.model.gps.comparator;

import sk.stasko.model.gps.Gps;
import sk.stasko.model.tree.comparator.AbstractComparator;

/**
 * Comparator used for finding correct minimum of key
 */
public class GpsMinMaxComparator extends AbstractComparator<Gps> {
    public GpsMinMaxComparator(int numberOfKeys, int value) {
        super(numberOfKeys, value);
    }

    @Override
    public int compare(Gps o1, Gps o2) {
        if (this.value == 0) {
            return Double.compare(o1.getWidth(), o2.getWidth());
        } else {
            return Double.compare(o1.getLength(), o2.getLength());
        }
    }
}
