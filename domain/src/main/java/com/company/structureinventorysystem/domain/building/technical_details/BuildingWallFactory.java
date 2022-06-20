package com.company.structureinventorysystem.domain.structureinventorydetails.building.technical_details;

public class BuildingWallFactory {

    public BuildingWall createBuildingWall(BuildingWallVerticalLocation verticalLocation, BuildingWallConstructionMaterialType constructionMaterialType) {
        switch (verticalLocation) {
            case BASEMENT:
                return new BasementBuildingWall(BuildingWallVerticalLocation.BASEMENT, constructionMaterialType);
            case OVER_GROUND:

            default:

        }
        return null;
    }

}
