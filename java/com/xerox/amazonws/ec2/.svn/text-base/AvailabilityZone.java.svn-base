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

import java.util.List;

/**
 * This is a container class for an Availability Zone
 */
public class AvailabilityZone {
	private String name;
	private String state;
	private String regionName;
	private List<String> messages;

	public AvailabilityZone(String name, String state, String regionName, List<String> messages) {
		this.name = name;
		this.state = state;
		this.regionName = regionName;
		this.messages = messages;
	}

	public String getName() {
		return name;
	}

	public String getState() {
		return state;
	}

	public String getRegionName() {
		return regionName;
	}

	public List<String> getMessages() {
		return messages;
	}

	public String toString() {
		return "AvailabilityZone[name=" + name + ", state=" + state +
				", region=" + regionName + "]";
	}
}
