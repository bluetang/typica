package com.xerox.amazonws.ec2;

public enum SpotInstanceState {
    OPEN, ACTIVE, CLOSED, CANCELLED, FAILED;

    public static SpotInstanceState getStateFromString(String str) {
        for (SpotInstanceState state : values()) {
            if (state.toString().equalsIgnoreCase(str)) {
                return state;
            }
        }
        
        return null;
    }
}
