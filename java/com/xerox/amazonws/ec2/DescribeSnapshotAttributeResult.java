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

import java.util.ArrayList;
import java.util.List;

/**
 * The results of a call to describe snapshot attributes. 
 */
public class DescribeSnapshotAttributeResult {
	private String snapshotId;
	private List<CreateVolumePermission> permissions;
	
	public DescribeSnapshotAttributeResult(String snapshotId) {
		this.snapshotId = snapshotId;
		this.permissions = new ArrayList<CreateVolumePermission>();
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public List<CreateVolumePermission> getCreateVolumePermissions() {
		return permissions;
	}

	public void addCreateVolumePermission(String userId, String group) {
		permissions.add(new CreateVolumePermission(userId, group));
	}

	public class CreateVolumePermission {
		private String userId;
		private String group;

		public CreateVolumePermission(String userId, String group) {
			this.userId = userId;
			this.group = group;
		}

		public String getUserId() {
			return userId;
		}

		public String getGroup() {
			return group;
		}
	}
}
