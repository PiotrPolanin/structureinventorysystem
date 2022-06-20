package com.company.structureinventorysystem.domain.building.technical_details;

public class BasementBuildingWall extends BuildingWall {

    protected BuildingWallConstructionMaterialType constructionMaterialType;

    public BasementBuildingWall(BuildingWallVerticalLocation verticalLocation, BuildingWallConstructionMaterialType constructionMaterialType) {
        super(verticalLocation);
        this.constructionMaterialType = constructionMaterialType;
    }
}
