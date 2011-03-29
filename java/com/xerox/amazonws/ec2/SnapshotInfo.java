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
 * An instance of this class represents an EC2 Snapshot.
 * <p/>
 * Instances are returned by calls to
 * {@link com.xerox.amazonws.ec2.Jec2#createSnapshot(String)},
 * {@link com.xerox.amazonws.ec2.Jec2#describeSnapshots(List)}, and
 * {@link com.xerox.amazonws.ec2.Jec2#describeSnapshots(String[])}.
 */
public class SnapshotInfo {
	private String snapshotId;
	private String volumeId;
	private String status;
	private Calendar startTime;
	private String progress;
	private String ownerId;
	private String volumeSize;
	private String description;
	private String ownerAlias;

	public SnapshotInfo(String snapshotId, String volumeId,
			String status, Calendar startTime, String progress,
			String ownerId, String volumeSize, String description, String ownerAlias) {
		this.snapshotId = snapshotId;
		this.volumeId = volumeId;
		this.status = status;
		this.startTime = startTime;
		this.progress = progress;
		this.ownerId = ownerId;
		this.volumeSize = volumeSize;
		this.description = description;
		this.ownerAlias = ownerAlias;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public String getStatus() {
		return status;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public String getProgress() {
		return progress;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getVolumeSize() {
		return volumeSize;
	}

	public String getDescription() {
		return description;
	}

	public String getOwnerAlias() {
		return ownerAlias;
	}

	public String toString() {
		return "Snapshot[id=" + this.snapshotId
				+ ", volumeId=" + this.volumeId
				+ ", status=" + this.status
				+ ", startTime=" + this.startTime.toString()
				+ ", ownerId=" + this.ownerId
				+ ", volumeSize=" + this.volumeSize
				+ ", description=" + this.description
				+ ", ownerAlias=" + this.ownerAlias
				+ "]";
	}
}

