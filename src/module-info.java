module GeoSysFx {
    requires javafx.controls;
    requires javafx.fxml;

    // export controller
    opens sk.stasko.controller to javafx.fxml;
    exports sk.stasko.controller;

    opens sk.stasko.controller.realEstate to javafx.fxml;
    exports sk.stasko.controller.realEstate;

    opens sk.stasko.controller.realEstate.realEstateSearch to javafx.fxml;
    exports sk.stasko.controller.realEstate.realEstateSearch;

    opens sk.stasko.controller.realEstate.realEstateInsert to javafx.fxml;
    exports sk.stasko.controller.realEstate.realEstateInsert;

    opens sk.stasko.controller.realEstate.realEstateEdit to javafx.fxml;
    exports sk.stasko.controller.realEstate.realEstateEdit;

    opens sk.stasko.controller.buildingLot to javafx.fxml;
    exports sk.stasko.controller.buildingLot;

    opens sk.stasko.controller.buildingLot.buildingLotSearch to javafx.fxml;
    exports sk.stasko.controller.buildingLot.buildingLotSearch;

    opens sk.stasko.controller.buildingLot.buildingLotInsert to javafx.fxml;
    exports sk.stasko.controller.buildingLot.buildingLotInsert;

    opens sk.stasko.controller.buildingLot.buildingLotEdit to javafx.fxml;
    exports sk.stasko.controller.buildingLot.buildingLotEdit;

    opens sk.stasko.controller.abstractControllers to javafx.fxml;
    exports sk.stasko.controller.abstractControllers;


    // export models
    exports sk.stasko.model.gps;
    exports sk.stasko.model.geoObjects;
    exports sk.stasko.model.geoObjects.buildingLot;
    exports sk.stasko.model.geoObjects.realEstate;
    exports sk.stasko.model.geoObjects.comparators;
    exports sk.stasko.model.tree.key;
    exports sk.stasko.model.tree.comparator;

    opens sk.stasko;
}