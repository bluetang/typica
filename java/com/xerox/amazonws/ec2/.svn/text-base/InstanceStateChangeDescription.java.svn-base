//
// typica - A client library for Amazon Web Services
// Copyright (C) 2010 Xerox Corporation
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

/**
 * An instance of this class represents an EC2 instance after a request has
 * been issued to terminate/start/stop that instance.
 */
public class InstanceStateChangeDescription {
	private String instanceId;
	private String prevState;
	private String currentState;
	private int prevStateCode;
	private int currentStateCode;

	public InstanceStateChangeDescription(String id, String prevState,
			int prevStateCode, String currentState, int currentStateCode) {
		this.instanceId = id;
		this.prevState = prevState;
		this.prevStateCode = prevStateCode;
		this.currentState = currentState;
		this.currentStateCode = currentStateCode;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getPreviousState() {
		return prevState;
	}

	public String getCurrentState() {
		return currentState;
	}

	public int getPreviousStateCode() {
		return prevStateCode;
	}

	public int getCurrentStateCode() {
		return currentStateCode;
	}

	public String toString() {
		return "Instance[ID=" + instanceId + ", prevState=" + prevState
				+ "(" + prevStateCode + "), currentState=" + currentState
				+ "(" + currentStateCode + ")]";
	}
}

