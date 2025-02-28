package net.danh.mythicCore.NMS;


public class NMSAssistant {

    public NMSVersion getNMSVersion() {
        return new NMSVersion();
    }

    public boolean isVersionGreaterThan(int version) {
        return getNMSVersion().getMinor() > version;
    }

    public boolean isVersionGreaterThanOrEqualTo(int version) {
        return getNMSVersion().getMinor() >= version;
    }

    public boolean isVersionLessThan(int version) {
        return getNMSVersion().getMinor() < version;
    }

    public boolean isVersionLessThanOrEqualTo(int version) {
        return getNMSVersion().getMinor() <= version;
    }

    public boolean isVersion(int version) {
        return getNMSVersion().getMinor() == version;
    }

    public boolean isNotVersion(int version) {
        return getNMSVersion().getMinor() != version;
    }
}