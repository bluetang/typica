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

import com.xerox.amazonws.typica.jaxb.BundleInstanceTaskErrorType;
import com.xerox.amazonws.typica.jaxb.BundleInstanceTaskStorageType;

/**
 * An instance of this class represents an EC2 bundle instance task.
 */
public class BundleInstanceInfo {
	private String requestId;
	private String instanceId;
	private String bundleId;
	private String state;
	private Calendar startTime;
	private Calendar updateTime;
	private StorageInfo storage;
	private String progress;
	private String error;

	public BundleInstanceInfo(String requestId, String instanceId, String bundleId,
							String state, Calendar startTime, Calendar updateTime,
							BundleInstanceTaskStorageType storage,
							String progress, BundleInstanceTaskErrorType error) {
		this.requestId = requestId;
		this.instanceId = instanceId;
		this.bundleId = bundleId;
		this.state = state;
		this.startTime = startTime;
		this.updateTime = updateTime;
		if (storage != null) {
			this.storage = new StorageInfo(storage);
		}
		this.progress = progress;
		if (error != null) {
			this.error = "("+error.getCode()+")"+error.getMessage();
		}
	}

	public String getRequestId() {
		return requestId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getBundleId() {
		return bundleId;
	}

	public String getState() {
		return state;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public Calendar getUpdateTime() {
		return updateTime;
	}

	public StorageInfo getStorage() {
		return storage;
	}

	public String getProgress() {
		return progress;
	}

	public String getError() {
		return error;
	}

	public class StorageInfo {
		private String bucket;
		private String prefix;
		private String accessId;
		private UploadPolicy policy;

		StorageInfo(BundleInstanceTaskStorageType storage) {
			this.bucket = storage.getS3().getBucket();
			this.prefix = storage.getS3().getPrefix();
			this.accessId = storage.getS3().getAwsAccessKeyId();
			this.policy = new UploadPolicy(storage.getS3().getUploadPolicy());
		}

		public StorageInfo(String bucket, String prefix, String accessId, UploadPolicy policy) {
			this.bucket = bucket;
			this.prefix = prefix;
			this.accessId = accessId;
			this.policy = policy;
		}

		public String getBucket() {
			return bucket;
		}

		public String getPrefix() {
			return prefix;
		}

		public String accessId() {
			return accessId;
		}

		public UploadPolicy getUploadPolicy() {
			return policy;
		}

		public String toString() {
			return "[bucket="+this.bucket+", prefix="+this.prefix+", accessId="
					+this.accessId+", policy="+this.policy.toString()+"]";
		}
	}

	public String toString() {
		return "BundleInstance[bundleId="+this.bundleId+", instanceId="+this.instanceId+", state="
				+this.state+", progress="+this.progress+", startTime="+this.startTime+", updateTime="
				+this.updateTime+", storage="+this.storage.toString()+", error="+this.error+"]";
	}
}
