package com.sf.fire.analysis.models;

public class IncidentRequest {
    private String callNumber;
    private String unitId;
    private String incidentNumber;
    private String callType;
    private String callDate;
    private String watchDate;
    private String callFinalDisposition;
    private String availableDate;
    private String address;
    private String city;
    private Integer zipCode;
    private String battalion;
    private Integer stationArea;
    private Integer box;
    private Integer originalPriority;
    private Integer priority;
    private Integer finalPriority;
    private Boolean alsUnit;
    private String callTypeGroup;
    private Integer numAlarms;
    private String unitType;
    private Integer unitSequenceInCallDispatch;
    private String firePreventionDistrict;
    private Integer supervisorDistrict;
    private String neighborhood;
    private String location;
    private String rowID;
    private Float delay;

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getIncidentNumber() {
        return incidentNumber;
    }

    public void setIncidentNumber(String incidentNumber) {
        this.incidentNumber = incidentNumber;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(String watchDate) {
        this.watchDate = watchDate;
    }

    public String getCallFinalDisposition() {
        return callFinalDisposition;
    }

    public void setCallFinalDisposition(String callFinalDisposition) {
        this.callFinalDisposition = callFinalDisposition;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getBattalion() {
        return battalion;
    }

    public void setBattalion(String battalion) {
        this.battalion = battalion;
    }

    public Integer getStationArea() {
        return stationArea;
    }

    public void setStationArea(Integer stationArea) {
        this.stationArea = stationArea;
    }

    public Integer getBox() {
        return box;
    }

    public void setBox(Integer box) {
        this.box = box;
    }

    public Integer getOriginalPriority() {
        return originalPriority;
    }

    public void setOriginalPriority(Integer originalPriority) {
        this.originalPriority = originalPriority;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getFinalPriority() {
        return finalPriority;
    }

    public void setFinalPriority(Integer finalPriority) {
        this.finalPriority = finalPriority;
    }

    public boolean isAlsUnit() {
        return alsUnit;
    }

    public void setAlsUnit(boolean alsUnit) {
        this.alsUnit = alsUnit;
    }

    public String getCallTypeGroup() {
        return callTypeGroup;
    }

    public void setCallTypeGroup(String callTypeGroup) {
        this.callTypeGroup = callTypeGroup;
    }

    public Integer getNumAlarms() {
        return numAlarms;
    }

    public void setNumAlarms(Integer numAlarms) {
        this.numAlarms = numAlarms;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public Integer getUnitSequenceInCallDispatch() {
        return unitSequenceInCallDispatch;
    }

    public void setUnitSequenceInCallDispatch(Integer unitSequenceInCallDispatch) {
        this.unitSequenceInCallDispatch = unitSequenceInCallDispatch;
    }

    public String getFirePreventionDistrict() {
        return firePreventionDistrict;
    }

    public void setFirePreventionDistrict(String firePreventionDistrict) {
        this.firePreventionDistrict = firePreventionDistrict;
    }

    public Integer getSupervisorDistrict() {
        return supervisorDistrict;
    }

    public void setSupervisorDistrict(Integer supervisorDistrict) {
        this.supervisorDistrict = supervisorDistrict;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }

    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }
}
