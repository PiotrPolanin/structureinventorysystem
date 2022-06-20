package com.company.structureinventorysystem.domain.building.technical_details;

import java.util.ArrayList;
import java.util.List;

public class TechnicalBuildingDetailsTest {
    public static void main(String[] args) {

        List<BuildingWall> buildingWalls = new ArrayList<>();
        BuildingWallFactory buildingWallFactory = new BuildingWallFactory();
        BuildingWall basementBuildingWall = buildingWallFactory.createBuildingWall(BuildingWallVerticalLocation.BASEMENT, BuildingWallConstructionMaterialType.CONCRETE);
        BuildingWall firstFloorBuildingWall = buildingWallFactory.createBuildingWall(BuildingWallVerticalLocation.OVER_GROUND, BuildingWallConstructionMaterialType.CONCRETE);
        buildingWalls.add(basementBuildingWall);
        buildingWalls.add(firstFloorBuildingWall);

        TechnicalBuildingDetails tbd = new TechnicalBuildingDetails(BuildingFoundationType.STONE, BuildingBasementType.COMPLETE, buildingWalls);
        System.out.println(tbd);
    }
}
