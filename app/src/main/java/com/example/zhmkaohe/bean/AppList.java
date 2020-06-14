package com.example.zhmkaohe.bean;

public class AppList {
    private String appName;
    private String packName;
    private String version;

    public AppList(String appName, String packName, String version, Boolean forceUpdate) {
        this.appName = appName;
        this.packName = packName;
        this.version = version;
        this.forceUpdate = forceUpdate;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    private Boolean forceUpdate;
}
