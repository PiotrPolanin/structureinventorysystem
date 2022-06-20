package com.company.structureinventorysystem.domain.shared;

public class Address {

    protected String country;
    protected String province;
    protected String locality;
    protected String street;
    protected String locationId;
    protected String premisesId;
    protected String postalCode;

    public Address(String locality, String street, String postalCode) {
        this.locality = locality;
        this.street = street;
        this.postalCode = postalCode;
    }

    public Address() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPremisesId() {
        return premisesId;
    }

    public void setPremisesId(String premisesId) {
        this.premisesId = premisesId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
