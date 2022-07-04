package sk.stasko.model.geoObjects.comparators;

import sk.stasko.model.geoObjects.realEstate.RealEstate;

import java.util.Comparator;

public class RealEstateComparator implements Comparator<RealEstate> {
    @Override
    public int compare(RealEstate o1, RealEstate o2) {
        return Long.compare(o1.getID(), o2.getID());
    }
}
