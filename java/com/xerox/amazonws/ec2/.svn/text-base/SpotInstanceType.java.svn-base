package com.xerox.amazonws.ec2;

public enum SpotInstanceType {
    ONE_TIME("one-time"), PERSISTENT("persistent");

    private final String awsString;

    SpotInstanceType(String awsString) {
        this.awsString = awsString;
    }


    public static SpotInstanceType getTypeFromString(String str) {
        for (SpotInstanceType type : values()) {
            if (type.awsString.equals(str)) {
                return type;
            }
        }

        return null;
    }

    public String getAwsString() {
        return awsString;
    }
}
