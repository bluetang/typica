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

/**
 * This is a container class for scaling activity
 */
public class Activity {
	private String id;
	private String description;
	private String cause;
	private String startTime;
	private String endTime;
	private String statusCode;
	private String statusMessage;
	private int progress;

	public Activity(String id, String description,
					String cause, String startTime, String endTime,
					String statusCode, String statusMessage,
					int progress) {
		this.id = id;
		this.description = description;
		this.cause = cause;
		this.startTime = startTime;
		this.endTime = endTime;
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.progress = progress;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getCause() {
		return cause;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public int getProgress() {
		return progress;
	}

	public String toString() {
		return "Activity[id=" + id + ", description=" + description + 
				", cause=" + cause +
				", startTime=" + startTime.toString() +
				", endTime=" + endTime.toString() +
				", statusCode=" + statusCode +
				", statusMessage=" + statusMessage +
				", progress=" + progress + "]";
	}
}
