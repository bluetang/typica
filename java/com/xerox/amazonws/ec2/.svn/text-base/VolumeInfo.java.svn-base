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
 * An instance of this class represents an EC2 Volume.
 * <p/>
 * Instances are returned by calls to
 * {@link com.xerox.amazonws.ec2.Jec2#createVolume(String)},
 * {@link com.xerox.amazonws.ec2.Jec2#describeVolumes(List)}, and
 * {@link com.xerox.amazonws.ec2.Jec2#describeVolumes(String[])}.
 */
public class VolumeInfo {
	private String volumeId;
	private String size;
	private String snapshotId;
	private String zone;
	private String status;
	private Calendar createTime;
	private List<AttachmentInfo> attachmentSets = new ArrayList<AttachmentInfo>();

	public VolumeInfo(String volumeId, String size, String snapshotId, String zone,
			String status, Calendar createTime) {
		this.volumeId = volumeId;
		this.size = size;
		this.snapshotId = snapshotId;
		this.zone = zone;
		this.status = status;
		this.createTime = createTime;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public String getSize() {
		return size;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public String getZone() {
		return zone;
	}

	public String getStatus() {
		return status;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public AttachmentInfo addAttachmentInfo(String volumeId, String instanceId,
			String device, String status, Calendar attachTime) {
		AttachmentInfo aSet = new AttachmentInfo(volumeId, instanceId, device,
												status, attachTime);
		attachmentSets.add(aSet);
		return aSet;
	}

	public List<AttachmentInfo> getAttachmentInfo() {
		return attachmentSets;
	}

	public String toString() {
		return "Volume[id=" + this.volumeId + ", size="
				+ this.size + ", snapshotId=" + this.snapshotId
				+ ", zone=" + this.zone
				+ ", status=" + this.status
				+ ", createTime=" + this.createTime.toString()
				+ "]";
	}
}

