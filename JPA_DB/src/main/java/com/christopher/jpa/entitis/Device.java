package com.christopher.jpa.entitis;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Device {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String appModule;

    private String deviceType;

    private String physicalLocation;

    private String manutacturer;

    @ManyToOne
    private Version version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public String getAppModule() {
        return appModule;
    }

    public void setAppModule(String appModule) {
        this.appModule = appModule;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getPhysicalLocation() {
        return physicalLocation;
    }

    public void setPhysicalLocation(String physicalLocation) {
        this.physicalLocation = physicalLocation;
    }

    public String getManutacturer() {
        return manutacturer;
    }

    public void setManutacturer(String manutacturer) {
        this.manutacturer = manutacturer;
    }

    @Override
    public String toString() {
        return name;
    }

}
