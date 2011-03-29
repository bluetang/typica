package com.xerox.amazonws.ec2;

import com.xerox.amazonws.typica.jaxb.CancelSpotInstanceRequestsResponseSetItemType;

public class SpotInstanceCancellationResponse {
    private String spotInstanceRequestId;
    protected SpotInstanceState state;

    public SpotInstanceCancellationResponse() {
    }

    SpotInstanceCancellationResponse(CancelSpotInstanceRequestsResponseSetItemType item) {
        this.spotInstanceRequestId = item.getSpotInstanceRequestId();
        this.state = SpotInstanceState.getStateFromString(item.getState());
    }

    public String getSpotInstanceRequestId() {
        return spotInstanceRequestId;
    }

    public SpotInstanceState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "SpotInstanceCancellationResponse[" +
                "spotInstanceRequestId='" + spotInstanceRequestId + '\'' +
                ", state=" + state +
                ']';
    }
}
