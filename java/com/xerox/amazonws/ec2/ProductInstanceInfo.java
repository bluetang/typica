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
 * An instance of this class represents an Product Instance relationship
 * <p>
 * Instances are returned by calls to
 * {@link com.xerox.amazonws.ec2.Jec2#confirmProductInstance(String, String)}.
 */
public class ProductInstanceInfo {
	private String instanceId;
	private String productCode;
	private String ownerId;

	public ProductInstanceInfo(String instanceId, String productCode, String ownerId) {
		this.instanceId = instanceId;
		this.productCode = productCode;
		this.ownerId = ownerId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String toString() {
		return "ProductInstance[instance=" + this.instanceId + ", productCode="
				+ this.productCode + ", owrnerId=" + this.ownerId + "]";
	}
}

