package sk.stasko;

import sk.stasko.model.gps.Gps;
import sk.stasko.service.buildingLot.BuildingLotServiceImpl;
import sk.stasko.service.realEstate.RealEstateServiceImpl;

import java.util.Random;

public class AppGen {
    /**
     * @param numberOfItems - number of items to be generated
     */
    public static void generateRealEstates(int numberOfItems, Random random) {
        for (int x = 0; x < numberOfItems; x++) {
            if (x % 10000 == 0) {
                System.out.println(x);
            }
            int number = random.nextInt(100);
            RealEstateServiceImpl.getInstance().addData(number, getDesc(), generateGps(random.nextInt(1000)));
        }
    }

    /**
     * @param numberOfItems - number of items to be generated
     */
    public static void generateBuildingLot(int numberOfItems, Random random) {
        for (int x = 0; x < numberOfItems; x++) {
            if (x % 10000 == 0) {
                System.out.println(x);
            }
            int number = random.nextInt(100);
            BuildingLotServiceImpl.getInstance().addData(number, getDesc(), generateGps(random.nextInt(1000)));
        }
    }

    /**
     * @return random String
     */
    private static String getDesc() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    /**
     * @param seed - seed for generator
     * @return random GPS generated
     */
    public static Gps generateGps(int seed) {
        Random random = new Random();
        random.setSeed(seed);
        char lengthPos;
        char widthPos;
        int number = random.nextInt(100);
        if (number > 5) {
            lengthPos = 'E';
        } else {
            lengthPos = 'W';
        }
        number = random.nextInt(100);
        if (number > 10) {
            widthPos = 'S';
        } else {
            widthPos = 'N';
        }
        double width = random.nextInt(100 - 1) + 1;
        double length = random.nextInt(100 - 1) + 1;;
        return new Gps(widthPos, lengthPos, width , length);
    }
}
