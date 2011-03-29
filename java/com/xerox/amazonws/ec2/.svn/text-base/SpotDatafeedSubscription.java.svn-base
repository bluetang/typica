//
// typica - A client library for Amazon Web Services
// Copyright (C) 2007,2008 Xerox Corporation
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package com.xerox.amazonws.ec2;

import com.xerox.amazonws.typica.jaxb.SpotDatafeedSubscriptionType;
import com.xerox.amazonws.typica.jaxb.SpotInstanceStateFaultType;

/**
 * This is a container class for a Spot Datafeed Subscription
 */
public class SpotDatafeedSubscription {
	private String requestId;
	private String bucket;
	private String prefix;
	private String state;
	private String faultCode;
	private String faultMessage;

	public SpotDatafeedSubscription(String requestId,
					SpotDatafeedSubscriptionType sub) {
		this.requestId = requestId;
		this.bucket = sub.getBucket();
		this.prefix = sub.getPrefix();
		this.state = sub.getState();
		SpotInstanceStateFaultType fault = sub.getFault();
		if (fault != null) {
			this.faultCode = sub.getFault().getCode();
			this.faultMessage = sub.getFault().getMessage();
		}
	}

	public String getRequestId() {
		return requestId;
	}

	public String getBucket() {
		return bucket;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getState() {
		return state;
	}

	public String getFaultCode() {
		return faultCode;
	}

	public String getFaultMessage() {
		return faultMessage;
	}

	public String toString() {
		return "SpotDatafeedSubscription[bucket=" + bucket + ", prefix=" + prefix + 
				", state=" + state + ", fault=" + faultMessage + ":" +
				faultCode + "]";
	}
}
