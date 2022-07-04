package sk.stasko.service.buildingLot;

import sk.stasko.App;
import sk.stasko.model.geoObjects.buildingLot.BuildingLot;
import sk.stasko.model.geoObjects.realEstate.RealEstate;
import sk.stasko.model.gps.comparator.GPSComparator;
import sk.stasko.model.gps.Gps;
import sk.stasko.model.gps.GpsInterval;
import sk.stasko.model.tree.IKDTree;
import sk.stasko.model.tree.KDTree;
import sk.stasko.model.tree.comparator.AbstractComparator;
import sk.stasko.model.tree.interval.AbstractInterval;
import sk.stasko.service.AbstractService;
import sk.stasko.service.realEstate.RealEstateServiceImpl;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BuildingLotServiceImpl extends AbstractService<BuildingLot> {
    protected static AbstractService<BuildingLot> instance;
    private static final String dataFile = "buildingLotData.csv";
    private final IKDTree<BuildingLot, Gps> buildingLot;

    public static AbstractService<BuildingLot> getInstance() {
        if (instance == null) {
            instance = new BuildingLotServiceImpl();
        }
        return instance;
    }

    private BuildingLotServiceImpl() {
        this.buildingLot = new KDTree<>(new GPSComparator(App.numberOfKeys), App.numberOfKeys);
    }

    public List<BuildingLot> findDataByGpsCoordinates(Gps gps) {
        List<BuildingLot> buildingLots = this.buildingLot.find(gps);

        if (buildingLots == null) {
            return new LinkedList<>();
        }
        return buildingLots;
    }

    public List<BuildingLot> findDataByInterval(GpsInterval gpsWidthInterval, GpsInterval gpsLengthInterval) {
        List<AbstractInterval<Gps>> intervalList = new LinkedList<>();
        intervalList.add(gpsWidthInterval);
        intervalList.add(gpsLengthInterval);
        List<BuildingLot> found = this.buildingLot.find(intervalList);
        if (found == null) {
            return new LinkedList<>();
        }
        return found;
    }

    public boolean findDataByCoordinatesAndDataAndSave(Gps gps, BuildingLot obj, Comparator<BuildingLot> comparator, String desc, int number) {
        List<BuildingLot> buildingLotsList = this.buildingLot.find(gps);
        for (BuildingLot lot: buildingLotsList) {
            if (comparator.compare(lot, obj) == 0) {
                lot.setNumber(number);
                lot.setDescription(desc);
                lot.getGps().setPositionOfWidth(gps.getPositionOfWidth());
                lot.getGps().setPositionOfLength(gps.getPositionOfLength());
                return true;
            }
        }
        return false;
    }

    public BuildingLot addData(int number, String description, Gps gps) {
        List<RealEstate> linkedList = RealEstateServiceImpl.getInstance().findDataByGpsCoordinates(gps);
        BuildingLot newBuildingLot = new BuildingLot(number, description, gps, linkedList);
        BuildingLot newLot = this.buildingLot.insert(newBuildingLot, gps);
        for (RealEstate estate: linkedList) {
            estate.addItem(newLot);
        }
        return newLot;
    }

    public boolean deleteData(BuildingLot buildingLot, Comparator<BuildingLot> dataComparator, AbstractComparator<Gps> comp) {
        List<RealEstate> realEstateList = RealEstateServiceImpl.getInstance().findDataByGpsCoordinates(buildingLot.getGps());
        for (RealEstate realEstate: realEstateList) {
            realEstate.removeItem(buildingLot);
        }
        return this.buildingLot.delete(buildingLot, buildingLot.getGps(), dataComparator, comp);
    }

    @Override
    public void save() throws IOException {
        List<BuildingLot> levelOrderList = this.buildingLot.getLevelOrder();
        this.saveData(dataFile, levelOrderList);
    }

    @Override
    public void load() throws IOException {
        this.loadData(dataFile);
    }

    @Override
    public void clearRoot() {
        if (this.buildingLot.hasRoot()) {
            this.buildingLot.clearRoot();
        }
    }
}
