package com.company.structureinventorysystem.domain.building.technical_details;

public abstract class BuildingWall {

    protected BuildingWallVerticalLocation verticalLocation;

    public BuildingWall(BuildingWallVerticalLocation verticalLocation) {
        this.verticalLocation = verticalLocation;
    }

    public BuildingWallVerticalLocation getVerticalLocation() {
        return verticalLocation;
    }

}
