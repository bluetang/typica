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
 * An instance of this class represents an EC2 security group.
 * <p>
 * Instances are returned by calls to
 * {@link com.xerox.amazonws.ec2.Jec2#describeSecurityGroups(List)}, and
 * {@link com.xerox.amazonws.ec2.Jec2#describeSecurityGroups(String[])}.
 */
public class GroupDescription {
	private String name;
	private String desc;
	private String owner;
	private List<IpPermission> perms = new ArrayList<IpPermission>();

	public GroupDescription(String name, String desc, String owner) {
		this.name = name;
		this.desc = desc;
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return desc;
	}

	public String getOwner() {
		return owner;
	}

	public IpPermission addPermission(String protocol, int fromPort,
			int toPort) {
		IpPermission perm = new IpPermission(protocol, fromPort, toPort);
		perms.add(perm);
		return perm;
	}

	public List<IpPermission> getPermissions() {
		return perms;
	}

	public class IpPermission {
		private String protocol;
		private int fromPort;
		private int toPort;
		private List<String> cidrIps = new ArrayList<String>();
		private List<String[]> uid_group_pairs = new ArrayList<String[]>();
		
		public IpPermission(String protocol, int fromPort, int toPort) {
			this.protocol = protocol;
			this.fromPort = fromPort;
			this.toPort = toPort;
		}

		public String getProtocol() {
			return protocol;
		}

		public int getFromPort() {
			return fromPort;
		}

		public int getToPort() {
			return toPort;
		}

		public void addIpRange(String cidrIp) {
			this.cidrIps.add(cidrIp);
		}

		public List<String> getIpRanges() {
			return cidrIps;
		}

		public void addUserGroupPair(String userId, String groupName) {
			this.uid_group_pairs.add(new String[] { userId, groupName });
		}

		public List<String []> getUidGroupPairs() {
			return uid_group_pairs;
		}

		public String toString() {
			List<String> uid_grp_str = new ArrayList<String>();
			for (String[] pair : this.uid_group_pairs) {
				uid_grp_str.add("(" + pair[0] + "," + pair[1] + ")");
			}
			return "[proto=" + this.protocol + ", portRng=("
					+ this.fromPort + ".." + this.toPort + "), cidrIps="
					+ this.cidrIps + ", uidgrp=" + uid_grp_str
					+ "]";
		}
	}

	public String toString() {
		return "Group[name=" + this.name + ", Desc=" + this.desc + ", own="
				+ this.owner + ", perms=" + this.perms + "]";
	}
}

