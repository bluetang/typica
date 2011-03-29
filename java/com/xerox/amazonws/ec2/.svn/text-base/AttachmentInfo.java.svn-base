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
 * An instance of this class represents an EC2 volume attachment.
 */
public class AttachmentInfo {
	private String volumeId;
	private String instanceId;
	private String device;
	private String status;
	private Calendar attachTime;

	public AttachmentInfo(String volumeId, String instanceId, String device,
						String status, Calendar attachTime) {
		this.volumeId = volumeId;
		this.instanceId = instanceId;
		this.device = device;
		this.status = status;
		this.attachTime = attachTime;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getDevice() {
		return device;
	}

	public String getStatus() {
		return status;
	}

	public Calendar getAttachTime() {
		return attachTime;
	}

	public String toString() {
		return "[Volume=" + this.volumeId + ", instance="
				+ this.instanceId + ", device=" + this.device
				+ ", status=" + this.status
				+ ", attachTime=" + this.attachTime.toString()
				+ "]";
	}
}

