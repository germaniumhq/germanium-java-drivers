package com.germaniumhq.drivers.platform;

public class Platform {
    private OperatingSystem operatingSystem;
    private BitArchitecture bitArchitecture;

    public Platform(OperatingSystem operatingSystem, BitArchitecture bitArchitecture) {
        this.operatingSystem = operatingSystem;
        this.bitArchitecture = bitArchitecture;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public BitArchitecture getBitArchitecture() {
        return bitArchitecture;
    }

    public void setBitArchitecture(BitArchitecture bitArchitecture) {
        this.bitArchitecture = bitArchitecture;
    }
}
