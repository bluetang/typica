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
 * This is a container class for Block Device Mapping
 */
public class InstanceBlockDeviceMapping {
	private String deviceName;
	private String volumeId;
	private String status;
	private Calendar attachTime;
	private boolean deleteOnTerminate;

	public InstanceBlockDeviceMapping(String deviceName, String volumeId,
								String status, Calendar attachTime,
								boolean deleteOnTerminate) {
		this.deviceName = deviceName;
		this.volumeId = volumeId;
		this.status = status;
		this.attachTime = attachTime;
		this.deleteOnTerminate = deleteOnTerminate;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public String getStatus() {
		return status;
	}

	public Calendar getAttachtime() {
		return attachTime;
	}

	public boolean isDeleteOnTerminate() {
		return deleteOnTerminate;
	}

	public String toString() {
		return "InstanceBlockDeviceMapping[deviceName=" + deviceName + ", volumeId=" + volumeId + ", status=" + status + ", attachTime=" + attachTime.toString() + ", delOnTerm=" + deleteOnTerminate + "]";
	}
}
