package com.company.structureinventorysystem.domain.structureinventorydetails.building.technical_details;

import java.util.List;

public class TechnicalBuildingDetails {

    BuildingFoundationType foundationType;
    BuildingBasementType basementType;
    List<BuildingWall> buildingWalls;

    public TechnicalBuildingDetails(BuildingFoundationType foundationType, BuildingBasementType basementType, List<BuildingWall> buildingWalls) {
        this.foundationType = foundationType;
        this.basementType = basementType;
        this.buildingWalls = buildingWalls;
    }

    public BuildingFoundationType getFoundationType() {
        return foundationType;
    }

    public BuildingBasementType getBasementType() {
        return basementType;
    }

    public List<BuildingWall> getBuildingWalls() {
        return buildingWalls;
    }

    @Override
    public String toString() {
        return "TechnicalBuildingDetails{" +
                "foundationType=" + foundationType +
                ", basementType=" + basementType +
                '}';
    }
}
