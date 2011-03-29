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

/**
 * This is a container class for Block Device Mapping
 */
public class BlockDeviceMapping {
	private String virtualName;
	private String deviceName;
	private String snapshotId;
	private int volumeSize;	// GB
	private boolean deleteOnTerminate;

	public BlockDeviceMapping(String virtualName, String deviceName) {
		this.virtualName = virtualName;
		this.deviceName = deviceName;
	}

	public BlockDeviceMapping(String deviceName, String snapshotId,
								int volumeSize, boolean deleteOnTerminate) {
		this.deviceName = deviceName;
		this.snapshotId = snapshotId;
		this.volumeSize = volumeSize;
		this.deleteOnTerminate = deleteOnTerminate;
	}

	public String getVirtualName() {
		return virtualName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public int getVolumeSize() {
		return volumeSize;
	}

	public boolean isDeleteOnTerminate() {
		return deleteOnTerminate;
	}

	public String toString() {
		return "BlockDeviceMapping[virtualName=" + virtualName + ", deviceName=" + deviceName + ", snapshotId=" + snapshotId + ", volumeSize=" + volumeSize + ", delOnTerm=" + deleteOnTerminate + "]";
	}
}
