package sk.stasko.service.realEstate;

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
import sk.stasko.service.buildingLot.BuildingLotServiceImpl;

import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class RealEstateServiceImpl extends AbstractService<RealEstate> {
    private static AbstractService<RealEstate> instance;
    private static final String dataFile = "realEstateData.csv";
    private final IKDTree<RealEstate, Gps> realEstates;

    public static AbstractService<RealEstate> getInstance() {
        if (instance == null) {
            instance = new RealEstateServiceImpl();
        }
        return instance;
    }

    private RealEstateServiceImpl() {
        this.realEstates = new KDTree<>(new GPSComparator(App.numberOfKeys), App.numberOfKeys);
    }

    @Override
    public List<RealEstate> findDataByGpsCoordinates(Gps gps) {
        List<RealEstate> realEstates = this.realEstates.find(gps);
        if (realEstates == null) {
            return new LinkedList<>();
        }
        return realEstates;
    }

    public List<RealEstate> findDataByInterval(GpsInterval gpsWidthInterval, GpsInterval gpsLengthInterval) {
        List<AbstractInterval<Gps>> intervalList = new LinkedList<>();
        intervalList.add(gpsWidthInterval);
        intervalList.add(gpsLengthInterval);
        List<RealEstate> realEstates = this.realEstates.find(intervalList);
        if (realEstates == null) {
            return new LinkedList<>();
        }
        return realEstates;
    }

    public boolean findDataByCoordinatesAndDataAndSave(Gps gps, RealEstate obj, Comparator<RealEstate> comparator, String desc, int number) {
        List<RealEstate> realEstateFound = this.realEstates.find(gps);
        for (RealEstate estate: realEstateFound) {
            if (comparator.compare(estate, obj) == 0) {
                estate.setNumber(number);
                estate.setDescription(desc);
                estate.getGps().setPositionOfWidth(gps.getPositionOfWidth());
                estate.getGps().setPositionOfLength(gps.getPositionOfLength());
                return true;
            }
        }
        return false;
    }

    public RealEstate addData(int number, String description, Gps gps) {
        List<BuildingLot> linkedList = BuildingLotServiceImpl.getInstance().findDataByGpsCoordinates(gps);
        RealEstate newRealEstate = new RealEstate(number, description, gps, linkedList);
        RealEstate addedRealEstate = this.realEstates.insert(newRealEstate, gps);
        for (BuildingLot buildingLotItem: linkedList) {
            buildingLotItem.addItem(addedRealEstate);
        }
        return addedRealEstate;
    }

    public boolean deleteData(RealEstate realEstate, Comparator<RealEstate> dataComparator, AbstractComparator<Gps> comp) {
        List<BuildingLot> buildingLots = BuildingLotServiceImpl.getInstance().findDataByGpsCoordinates(realEstate.getGps());
        for (BuildingLot buildingLot: buildingLots) {
            buildingLot.removeItem(realEstate);
        }

        return this.realEstates.delete(realEstate, realEstate.getGps(), dataComparator, comp);
    }

    @Override
    public void save() throws IOException {
        List<RealEstate> levelOrderList = this.realEstates.getLevelOrder();
        this.saveData(dataFile, levelOrderList);
    }

    @Override
    public void load() throws IOException {
        this.loadData(dataFile);
    }

    @Override
    public void clearRoot() {
        if (this.realEstates.hasRoot()) {
            this.realEstates.clearRoot();
        }
    }
}
