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

package com.xerox.amazonws.sns;

/**
 * An instance of this class represents a topic subscription.
 */
public class SubscriptionInfo {
	private String topicArn;
	private String protocol;
	private String subscriptionArn;
	private String owner;
	private String endpoint;

	public SubscriptionInfo(String topicArn, String protocol, String subscriptionArn,
			String owner, String endpoint) {
		this.topicArn = topicArn;
		this.protocol = protocol;
		this.subscriptionArn = subscriptionArn;
		this.owner = owner;
		this.endpoint = endpoint;
	}

	public String getTopicArn() {
		return topicArn;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getSubscriptionArn() {
		return subscriptionArn;
	}

	public String getOwner() {
		return owner;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public String toString() {
		return "SubscriptionInfo[topicArn=" + this.topicArn + ", protocol="
				+ this.protocol + ", subscriptionArn=" + this.subscriptionArn
				+ ", owner=" + this.owner
				+ ", endpoint=" + this.endpoint + "]";
	}
}

