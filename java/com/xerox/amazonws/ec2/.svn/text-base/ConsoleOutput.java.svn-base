//
// typica - A client library for Amazon Web Services
// Copyright (C) 2007 Xerox Corporation
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

/**
 * An instance of this class represents an instance's console output.
 * <p>
 * {@link com.xerox.amazonws.ec2.Jec2#getConsoleOutput(String instanceId)}
 */
public class ConsoleOutput {
	private String requestId;
	private String instanceId;
	private Calendar timestamp;
	private String output;                     // naked (i.e. not escaped, not BASE64)

	public ConsoleOutput(String requestId, String instanceId,
						java.util.Calendar timestamp,
						String output) {
		this.instanceId = instanceId;
		this.timestamp = timestamp;
		this.output = output;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public String getOutput() {
		return output;
	}

	public String toString() {
		return "ConsoleOutput[requestId=" + requestId + " instanceId=" + instanceId +
			", timestamp=" + timestamp + ", output=" + output + "]";
	}
}
