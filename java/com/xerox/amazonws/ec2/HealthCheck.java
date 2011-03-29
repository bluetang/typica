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
 * This is a container class for endpoint state
 */
public class HealthCheck {
	private String target;
	private int interval;
	private int timeout;
	private int unhealthyThreshold;
	private int healthyThreshold;

	public HealthCheck(String target, int interval,
						int timeout, int unhealthyThreshold,
						int healthyThreshold) {
		this.target = target;
		this.interval = interval;
		this.timeout = timeout;
		this.unhealthyThreshold = unhealthyThreshold;
		this.healthyThreshold = healthyThreshold;
	}

	public String getTarget() {
		return target;
	}

	public int getInterval() {
		return interval;
	}

	public int getTimeout() {
		return timeout;
	}

	public int getUnhealthyThreshold() {
		return unhealthyThreshold;
	}

	public int getHealthyThreshold() {
		return healthyThreshold;
	}

	public String toString() {
		return "HealthCheck[target=" + target +
			", interval=" + interval +
			", timeout=" + timeout +
			", unhealthyThreshold=" + unhealthyThreshold +
			", healthyThreshold=" + healthyThreshold +
			"]";
	}
}
