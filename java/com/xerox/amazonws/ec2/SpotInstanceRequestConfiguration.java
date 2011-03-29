package com.xerox.amazonws.ec2;

import java.util.Calendar;
import java.util.Map;

public class SpotInstanceRequestConfiguration {
    private double price;
    private int maxInstanceCount = 1;
    private SpotInstanceType type = SpotInstanceType.ONE_TIME;
    private Calendar validFrom;
    private Calendar validUntil;
    private String launchGroup;
    private String availabilityZoneGroup;

    public SpotInstanceRequestConfiguration(double price, int maxInstanceCount, SpotInstanceType type) {
        this.price = price;
        this.maxInstanceCount = maxInstanceCount;
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMaxInstanceCount() {
        return maxInstanceCount;
    }

    public void setMaxInstanceCount(int maxInstanceCount) {
        this.maxInstanceCount = maxInstanceCount;
    }

    public SpotInstanceType getType() {
        return type;
    }

    public void setType(SpotInstanceType type) {
        this.type = type;
    }

    public Calendar getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Calendar validFrom) {
        this.validFrom = validFrom;
    }

    public Calendar getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Calendar validUntil) {
        this.validUntil = validUntil;
    }

    public String getLaunchGroup() {
        return launchGroup;
    }

    public void setLaunchGroup(String launchGroup) {
        this.launchGroup = launchGroup;
    }

    public String getAvailabilityZoneGroup() {
        return availabilityZoneGroup;
    }

    public void setAvailabilityZoneGroup(String availabilityZoneGroup) {
        this.availabilityZoneGroup = availabilityZoneGroup;
    }

    void prepareQueryParams(Map<String, String> params) {
        params.put("SpotPrice", "" + price);
        params.put("InstanceCount", "" + maxInstanceCount);
        params.put("Type", type.getAwsString());

        if (validFrom != null) {
            // todo
            params.put("ValidFrom", validFrom.toString());
        }

        if (validUntil != null) {
            // todo
            params.put("ValidUntil", validUntil.toString());
        }

        if (launchGroup != null) {
            params.put("LaunchGroup", launchGroup);
        }

        if (availabilityZoneGroup != null) {
            params.put("AvailabilityZoneGroup", availabilityZoneGroup);
        }
    }
}
