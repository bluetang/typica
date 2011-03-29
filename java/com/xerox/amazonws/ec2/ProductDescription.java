//
// typica - A client library for Amazon Web Services
// Copyright (C) 2009 Xerox Corporation
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

public class ProductDescription {
	private String id;
	private InstanceType instanceType;
	private String availabilityZone;
	private long duration;
	private double fixedPrice;
	private double usagePrice;
	private String productDescription;

	public ProductDescription(String reservedInstanceOfferingId, InstanceType instanceType, String availabilityZone,
							long duration, double fixedPrice, double usagePrice, String productDescription) {
		this.id = reservedInstanceOfferingId;
		this.instanceType = instanceType;
		this.availabilityZone = availabilityZone;
		this.duration = duration;
		this.fixedPrice = fixedPrice;
		this.usagePrice = usagePrice;
		this.productDescription = productDescription;
	}

	public String getId() {
		return id;
	}

	public InstanceType getInstanceType() {
		return instanceType;
	}

	public String getAvailabilityZone() {
		return availabilityZone;
	}

	public long getDuration() {
		return duration;
	}

	public double getFixedPrice() {
		return fixedPrice;
	}

	public double getUsagePrice() {
		return usagePrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public String toString() {
		return "ProductDescription[id=" + this.id + ", type=" + instanceType.getTypeId() +
				", zone=" + availabilityZone + ", duration=" + duration + ", fixedPrice=" + fixedPrice +
				", usagePrice=" + usagePrice + ", description=" + productDescription;
	}
}
