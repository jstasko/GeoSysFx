package sk.stasko.model.geoObjects.realEstate;

import sk.stasko.model.geoObjects.AbstractBuildingObject;
import sk.stasko.model.geoObjects.buildingLot.BuildingLot;
import sk.stasko.model.gps.Gps;

import java.util.List;

public class RealEstate extends AbstractBuildingObject<BuildingLot> {
    /**
     *
     * @param number catalogue number
     * @param description description of reale estate
     * @param gps gps of real estate
     * @param list list of building lots with same gps
     */
    public RealEstate(int number, String description, Gps gps, List<BuildingLot> list) {
        super(AbstractBuildingObject.getIdGen().getAndIncrement(), number, description, gps, list);
    }

    @Override
    public String toString() {
        return "Id " +
                this.getID() + " " +
                "Real Estate number is " +
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


