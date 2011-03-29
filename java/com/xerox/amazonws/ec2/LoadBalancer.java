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

import java.util.Calendar;
import java.util.List;

/**
 * This is a container class for a load balancer
 */
public class LoadBalancer {
	private String name;
	private String dnsName;
	private List<Listener> listeners;
	private List<String> availabilityZones;
	private List<String> instances;
	private HealthCheck healthCheck;
	private Calendar createdTime;

	public LoadBalancer(String name, String dnsName,
					List<Listener> listeners, List<String> availabilityZones,
					List<String> instances, HealthCheck healthCheck,
					Calendar createdTime) {
		this.name = name;
		this.dnsName = dnsName;
		this.listeners = listeners;
		this.availabilityZones = availabilityZones;
		this.instances = instances;
		this.healthCheck = healthCheck;
		this.createdTime = createdTime;
	}

	public String getName() {
		return name;
	}

	public String getDnsName() {
		return dnsName;
	}

	public List<Listener> getListeners() {
		return listeners;
	}

	public List<String> getAvailabilityZones() {
		return availabilityZones;
	}

	public List<String> getInstances() {
		return instances;
	}

	public HealthCheck getHealthCheck() {
		return healthCheck;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public String toString() {
		return "LoadBalancer[name=" + name + ", dnsName=" + dnsName + 
				", createdTime=" + createdTime.toString() +
				"]";
	}
}
