package sk.stasko.model.geoObjects.comparators;

import sk.stasko.model.geoObjects.buildingLot.BuildingLot;

import java.util.Comparator;

public class BuildingLotComparator implements Comparator<BuildingLot> {
    @Override
    public int compare(BuildingLot o1, BuildingLot o2) {
        return Long.compare(o1.getID(), o2.getID());
    }
}
