package sk.stasko.service;

import sk.stasko.model.geoObjects.ISavable;
import sk.stasko.model.gps.Gps;
import sk.stasko.service.gps.GpsServiceImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public abstract class AbstractService<T extends ISavable<Long>> implements IBuildingObjectService<T> {
    /**
     *
     * @param file name of file to save into
     * @param levelOrderList list of objects to save
     * @throws IOException throws during bad handling of file
     */
    protected void saveData(String file, List<T> levelOrderList) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(file, StandardCharsets.UTF_8)) {
            printWriter.println(
                    "id;catalogNumber;description;gpsLength;gpsWidth;gpsLengthPosition;gpsWidthPosition;indexLevel"
            );
            for (T data : levelOrderList) {
                printWriter.println(data.getDataForPrint());
            }
        }
    }

    /**
     *
     * @param file name of file to load from
     * @throws IOException throws during bad handling of file
     */
    public void loadData(String file) throws IOException {
        try (BufferedReader printWriter = new BufferedReader(new FileReader(file))) {
            String line;
            printWriter.readLine();
            while ((line = printWriter.readLine()) != null) {
                String[] items = line.split(";");
                Gps gps = GpsServiceImpl.getInstance().getNewGpsFromStrings(items[5], items[3],items[6],items[4]);
                T newRealEstate = addData(Integer.parseInt(items[1]), items[2], gps);
                newRealEstate.savePrimaryKey(Long.parseLong(items[0]));
            }
        }
    }

    /**
     * save data
     * @throws IOException throws during bad handling of file
     */
    public abstract void save() throws IOException;

    /**
     * load data
     * @throws IOException throws during bad handling of file
     */
    public abstract void load() throws IOException;
}
