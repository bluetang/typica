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
 * This is a container class for instance state
 */
public class InstanceState {
	private String instanceId;
	private String state;
	private String reasonCode;
	private String description;

	public InstanceState(String instanceId, String state,
						String reasonCode, String description) {
		this.instanceId = instanceId;
		this.state = state;
		this.reasonCode = reasonCode;
		this.description = description;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getState() {
		return state;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		return "InstanceState[instanceId=" + instanceId +
			", state=" + state +
			", reasonCode=" + reasonCode +
			", description=" + description +
			"]";
	}
}
