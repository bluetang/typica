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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This is a container class for a auto scaling group
 */
public class AutoScalingGroup {
	private String groupName;
	private String launchConfigurationName;
	private int minSize;
	private int maxSize;
	private int desiredCapacity;
	private int cooldown;
	private List<String> availabilityZones;
	private List<Instance> instances = new ArrayList<Instance>();
	private Calendar createdTime;

	public AutoScalingGroup(String groupName, String launchConfigurationName,
						int minSize, int maxSize, int desiredCapacity,
						int cooldown, List<String> availabilityZones,
						Calendar createdTime) {
		this.groupName = groupName;
		this.launchConfigurationName = launchConfigurationName;
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.desiredCapacity = desiredCapacity;
		this.cooldown = cooldown;
		this.availabilityZones = availabilityZones;
		this.createdTime = createdTime;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getLaunchConfigurationName() {
		return launchConfigurationName;
	}

	public int getMinSize() {
		return minSize;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public int getDesiredCapacity() {
		return desiredCapacity;
	}

	public int getCooldown() {
		return cooldown;
	}

	public List<String> getAvailabilityZones() {
		return availabilityZones;
	}

	public List<Instance> getInstances() {
		return instances;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void addInstance(String instanceId, String lifecycleState) {
		instances.add(new Instance(instanceId, lifecycleState));
	}

	public String toString() {
		return "AutoScalingGroup[groupName=" + groupName +
			", launchConfigurationName=" + launchConfigurationName +
			", minSize=" + minSize + ", maxSize=" + maxSize +
			", desiredCapacity=" + desiredCapacity +
			", cooldown=" + cooldown +
			", zones=" + availabilityZones.get(0) +
			", instances=" + instances +
			"]";
	}

	/**
	 * Encapsulates information about an EC2 instance within a
	 * {@link AutoScalingGroup}.
	 */
	public class Instance {
		private String instanceId;
		private String lifecycleState;

		public Instance(String instanceId, String lifecycleState) {
			this.instanceId = instanceId;
			this.lifecycleState = lifecycleState;
		}

		public String getInstanceId() {
			return instanceId;
		}

		public String getLifecycleState() {
			return lifecycleState;
		}

		public String toString() {
			return "[instance=" + this.instanceId +
					", lifecycleState=" + this.lifecycleState + "]";
		}
	}
}
