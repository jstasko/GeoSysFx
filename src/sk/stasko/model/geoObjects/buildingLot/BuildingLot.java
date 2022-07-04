package sk.stasko.model.geoObjects.buildingLot;

import sk.stasko.model.geoObjects.AbstractBuildingObject;
import sk.stasko.model.geoObjects.realEstate.RealEstate;
import sk.stasko.model.gps.Gps;

import java.util.List;

public class BuildingLot extends AbstractBuildingObject<RealEstate> {
    /**
     *
     * @param number catalogue number
     * @param description description of reale estate
     * @param gps gps of real estate
     * @param list list of building lots with same gps
     */
    public BuildingLot(int number, String description, Gps gps, List<RealEstate> list) {
        super(AbstractBuildingObject.getIdGen().getAndIncrement(), number, description, gps, list);
    }

    @Override
    public String toString() {
        return "Id " +
                this.getID() + " " +
                "Building Lot number is " +
                super.getNumber() +
                ", gps coordinates are ["
                + super.getGps().getWidth() +
                ", " +
                super.getGps().getLength() + "]";
    }

    @Override
    public void savePrimaryKey(Long id) {
        this.setID(id);
    }
}
